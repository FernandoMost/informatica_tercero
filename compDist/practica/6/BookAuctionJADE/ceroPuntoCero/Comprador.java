import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;


import java.util.HashMap;
import java.util.HashSet;

public class Comprador extends Agent {
    private HashMap<String, Double> librosInteresantes;
    private HashMap<String, Double> misLibrosComprados;
    private String libroActual;
    private boolean esperaInicial;

    // ────────────────────────────────────────────

    @Override
    protected void setup() {
        librosInteresantes = new HashMap<>();
        misLibrosComprados = new HashMap<>();

        // Obtener los libros que interesan al comprador a partir de los argumentos (<titulo-libro>, <precio-máximo>)
        Object[] args = this.getArguments();
        esperaInicial = false;

        if (args != null && args.length >= 2) {
            int inicial = 0;

            if (args.length%2 == 1) {   // si es impar
                if (args[0].equals("espera")) {
                    esperaInicial = true;
                    inicial = 1;
                }
            }

            for (int i = inicial; i < args.length; i+=2)
                librosInteresantes.put(String.valueOf(args[i]), new Double(args[i+1].toString()));
        } else
            librosInteresantes.put("ElQuijote", 57.50);

        if (esperaInicial) try { Thread.sleep(15000); } catch (InterruptedException e) { e.printStackTrace(); }

        registro();

        this.addBehaviour(new ResponderOfertas());

        for (String libro : librosInteresantes.keySet()) System.out.println("[" + getLocalName() + "]\tme interesa " + libro + ", " + librosInteresantes.get(libro) + " €");

        super.setup();
    }

    @Override
    protected void takeDown() {
        deregistro();
        super.takeDown();
    }

    // ──────────────────────────────────────────────────────────────────────────────────────────────────

    // Registro del comprador en el servicio de páginas amarillas
    private void registro() {
        DFAgentDescription ad = new DFAgentDescription();
        ad.setName(this.getAID());

        ServiceDescription sd = new ServiceDescription();
        sd.setType("comprador");
        sd.setName("JADE-book-auction");
        ad.addServices(sd);

        try {
            DFService.register(this, ad);
            System.out.println("[" + getLocalName() + "]\tregistrado");
        } catch (FIPAException fe) { fe.printStackTrace(); }
    }

    // Quitando al agente del servicio de páginas amarillas
    private void deregistro() {
        try {
            DFService.deregister(this);
            System.out.println("[" + getLocalName() + "]\tfuera");
        } catch (FIPAException fe) { fe.printStackTrace(); }
    }

    // ──────────────────────────────────────────────────────────────────────────────────────────────────

    private class ResponderOfertas extends Behaviour {
        ResponderOfertas() {
        }

        @Override
        public void onStart() {
            libroActual = null;

            super.onStart();
        }

        @Override
        public void action() {
            // Plantilla de oferta recibida

            MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchConversationId("oferta-libro"), MessageTemplate.MatchPerformative(ACLMessage.PROPOSE));
            ACLMessage oferta = this.myAgent.blockingReceive(mt);

            if (oferta != null) {
                String libro = oferta.getContent();                         // nombre del libro ofrecido
                ACLMessage respuesta = oferta.createReply();            // respuesta para el vendedor
                respuesta.setConversationId("respuesta-oferta");

                if (librosInteresantes.containsKey(libro)) {
                    respuesta.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
                    respuesta.setContent("meInteresaEntroEnSubasta");
                    System.out.println("[" + getLocalName() + "]\tme ofrecieron " + libro + " y me interesa");
                    libroActual = libro;
                } else {
                    respuesta.setPerformative(ACLMessage.REJECT_PROPOSAL);
                    respuesta.setContent("NOmeInteresa");
                    System.out.println("[" + getLocalName() + "]\tme ofrecieron " + libro + " y NO me interesa");
                }

                this.myAgent.send(respuesta);
            }
        }

        @Override
        public boolean done() {
            return libroActual != null;
        }

        @Override
        public int onEnd() {
            this.myAgent.addBehaviour(new ParticiparEnSubasta(libroActual));

            return super.onEnd();
        }
    }

    // ──────────────────────────────────────────────────────────────────────────────────────────────────

    private class ParticiparEnSubasta extends Behaviour {
        boolean sigoParticipando, soyGanador, seSubasta; int paso;
        String libroActual;
        ACLMessage mensajeGanador;

        ParticiparEnSubasta(String libro) {
            sigoParticipando = true; seSubasta = true;
            soyGanador = false;
            paso = 0;
            this.libroActual = libro;
            mensajeGanador = null;
        }

        @Override
        public void action() {
            MessageTemplate mt;

            switch (paso) {
                case 0:
                    // esperar 5 s por un cancel y si no llega vamos pa lante
                    mt = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.CANCEL), MessageTemplate.MatchConversationId("interesados-insuficientes"));
                    ACLMessage mensaje = this.myAgent.blockingReceive(mt, 2500);

                    // nuevo precio recibido
                    if (mensaje != null) {
                        sigoParticipando = false;
                        seSubasta = false;
                    } else {
                        paso++;
                    }
                    break;
                case 1:
                    // Plantilla de precio recibida
                    mt = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.CFP), MessageTemplate.MatchConversationId("nuevo-precio-subasta"));
                    ACLMessage nuevoMensajePrecio = this.myAgent.blockingReceive(mt);

                    // nuevo precio recibido
                    if (nuevoMensajePrecio != null) {
                        Double precio = Double.valueOf(nuevoMensajePrecio.getContent());                // precio actual en la subasta

                        if (precio <= librosInteresantes.get(libroActual)) {
                            ACLMessage respuesta = nuevoMensajePrecio.createReply();                        // respuesta para el vendedor

                            respuesta.setConversationId("propuesta-precio-subasta");
                            respuesta.setPerformative(ACLMessage.PROPOSE);
                            respuesta.setContent("sigo-en-subasta");

                            System.out.println("[" + getLocalName() + "]\tacepto los " + precio + " € por " + libroActual);

                            this.myAgent.send(respuesta);
                            paso = 2;
                        } else {
                            System.out.println("[" + getLocalName() + "]\tNO acepto los " + precio + " € por " + libroActual);
                            sigoParticipando = false;
                        }
                    }

                    break;
                case 2:
                    mt = MessageTemplate.MatchConversationId("respuesta-propuesta-subasta");
                    ACLMessage respuesta = this.myAgent.blockingReceive(mt);

                    // nuevo precio recibido
                    if (respuesta != null) {
                        if (respuesta.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
                            System.out.println("[" + getLocalName() + "]\tsigo en subasta");
                            paso = 1;
                        } else {
                            System.out.println("[" + getLocalName() + "]\tme sacaron de subasta");
                            sigoParticipando = false;
                        }
                    }

                    break;
            }
        }

        @Override
        public boolean done() {
            if (seSubasta) {
                MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST), MessageTemplate.MatchConversationId("ganador-subasta"));
                mensajeGanador = this.myAgent.blockingReceive(mt, 2500);

                if (mensajeGanador != null) {
                    System.out.println("[" + getLocalName() + "]\tya me informaron que gané");
                    soyGanador = true;
                    sigoParticipando = false;
                } else if (!sigoParticipando)
                    return true;
            }

            return !sigoParticipando;
        }

        @Override
        public int onEnd() {
            if (seSubasta && !soyGanador) {
                MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.INFORM), MessageTemplate.MatchConversationId("perdedor-subasta"));
                ACLMessage respuesta = this.myAgent.blockingReceive(mt);

                if (respuesta != null) {
                    System.out.println("[" + getLocalName() + "]\tme informaron que ya ganó otro");
                }

                this.myAgent.addBehaviour(new ResponderOfertas());
            } else if (!seSubasta) {
                System.out.println("[" + getLocalName() + "]\tno se subasta " + libroActual);
                this.myAgent.addBehaviour(new ResponderOfertas());
            } else {
                this.myAgent.addBehaviour(new TranspasarLibro(mensajeGanador));
            }

            // si no gano
                // esperar por un inform
            //si gano
                // pasar a transferir el libro y mandar agree para que el otro venga

            return super.onEnd();
        }
    }

    // ──────────────────────────────────────────────────────────────────────────────────────────────────

    private class TranspasarLibro extends Behaviour {
        ACLMessage mensaje;

        TranspasarLibro(ACLMessage mensajeGanador) {
            this.mensaje = mensajeGanador;
        }

        @Override
        public void action() {


            String contenido = mensaje.getContent();
            String[] partes = contenido.split("#");

            misLibrosComprados.put(partes[0], Double.valueOf(partes[1]));

            ACLMessage respuesta = mensaje.createReply();
            respuesta.setPerformative(ACLMessage.INFORM);
            respuesta.setConversationId("informa-transpaso");
            respuesta.setContent("todoCorrectoYaTengoMiLibro");

            System.out.println("[" + getLocalName() + "]\tya tengo mi libro " + partes[0]);
            this.myAgent.send(respuesta);

            this.myAgent.addBehaviour(new ResponderOfertas());
        }

        @Override
        public boolean done() {
            return true;
        }
    }
}
