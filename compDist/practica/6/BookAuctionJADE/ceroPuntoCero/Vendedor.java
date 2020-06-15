import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class Vendedor extends Agent {
    private HashMap<String, Double> librosEnVenta;      // Mapa con los libros en venta y su precio mínimo (con el que arranca la subasta)
    //private HashMap<String, HashSet<AID>> interesadosEnMisLibros;
    private HashSet<AID> participaronEnSubasta;
    private HashSet<AID> compradores;                   // Conjunto de todos los compradores
    private double incremento;
    private HashSet<AID> interesados;
    private Iterator misLibrosIterator;
    private AID ganador;

    // ───────────────────────────────────────────────────────

    @Override
    protected void setup() {
        librosEnVenta = new HashMap<>();
        //interesadosEnMisLibros = new HashMap<>();
        compradores = new HashSet<>();
        interesados = new HashSet<>();

        // Obtener los libros que vende, a partir de los argumentos (<titulo-libro>, <precio-mínimo>)
        Object[] args = this.getArguments();

        if (args != null) {
            if (args.length >= 1) {
                incremento = new Double(args[0].toString());

                if (args.length >= 3)
                    for (int i = 1; i < args.length; i += 2)
                        librosEnVenta.put(String.valueOf(args[i]), new Double(args[i + 1].toString()));
            }
        } else {        // si no hay parámetros, valores por defecto
            incremento = 8.0;
            librosEnVenta.put("ElQuijote", 35.0);
            librosEnVenta.put("LaCelestina", 16.25);
            librosEnVenta.put("LaFundacion", 21.0);
            librosEnVenta.put("UnMundoFeliz", 14.23);
            librosEnVenta.put("ElCondeDeMontecristo", 13.50);
        }

        try { Thread.sleep(15000); } catch (InterruptedException e) { e.printStackTrace(); }

        registro();

        this.misLibrosIterator = librosEnVenta.keySet().iterator();

        this.addBehaviour(new BuscarInteresadosEnLibro());

        System.out.println("[" + getLocalName() + "]\tinfo\n\t\t\tincremento: " + incremento);
        for (String libro : librosEnVenta.keySet()) {
            //HashSet<AID> setInteresados = new HashSet<>();

            //interesadosEnMisLibros.put(libro, setInteresados);

            System.out.println("\t\t\tlibro: " + libro + ", " + librosEnVenta.get(libro) + " €");
        }

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
        sd.setType("vendedor");
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

    // Busqueda de vendedores en las páginas amarillas
    private void buscaCompradores() {
        DFAgentDescription ad = new DFAgentDescription();
        ServiceDescription sd  = new ServiceDescription();
        sd.setType("comprador");
        ad.addServices(sd);

        try {
            DFAgentDescription[] result = DFService.search(this, ad);

            for (DFAgentDescription v : result) {
                compradores.add(v.getName());
                System.out.println("[" + getLocalName() + "]\tidentificado comprador " + v.getName().getLocalName());
            }
        } catch (FIPAException e) { e.printStackTrace(); }
    }

    private HashSet<AID> buscaNuevosCompradores() {
        DFAgentDescription ad = new DFAgentDescription();
        ServiceDescription sd  = new ServiceDescription();
        sd.setType("comprador");
        ad.addServices(sd);

        HashSet<AID> todos = new HashSet<>(), diferencia = new HashSet<>();;

        try {
            DFAgentDescription[] result = DFService.search(this, ad);

            for (DFAgentDescription v : result) {
                todos.add(v.getName());
                //System.out.println("[" + getLocalName() + "]\tidentificado comprador " + v.getName().getLocalName());
            }
        } catch (FIPAException e) { e.printStackTrace(); }

        // para cada de los nuevos
            // si está en los viejos break

        for (AID c : todos) {
            boolean esNuevo = true;

            for (AID n : this.compradores) {
                if (n.getLocalName().equals(c.getLocalName())) {
                    esNuevo = false;
                    break;
                }
            }

            if (esNuevo) {
                diferencia.add(c);
            }
        }


        this.compradores.addAll(diferencia);

        return diferencia;
    }

    // ──────────────────────────────────────────────────────────────────────────────────────────────────

    private class BuscarInteresadosEnLibro extends Behaviour {
        String libroActual;
        int paso; boolean salir;

        BuscarInteresadosEnLibro() {
            paso = 0;
            salir = false;
        }

        @Override
        public void action() {
            System.out.println("---------------------------------------------------------------------------------------------------");
            try { Thread.sleep(5000); } catch (InterruptedException e) { e.printStackTrace(); }
            buscaCompradores();
            interesados.clear();

            if (!misLibrosIterator.hasNext())
                misLibrosIterator = librosEnVenta.keySet().iterator();

            libroActual = (String) misLibrosIterator.next();

            ACLMessage mensaje = new ACLMessage(ACLMessage.PROPOSE);
            mensaje.setConversationId("oferta-libro");
            mensaje.setContent(libroActual);

            for (AID comprador : compradores)
                mensaje.addReceiver(comprador);

            this.myAgent.send(mensaje);
            System.out.println("[" + getLocalName() + "]\tenvía oferta de " + libroActual);

            MessageTemplate mt = MessageTemplate.MatchConversationId("respuesta-oferta");

            for (int i = 0; i < compradores.size(); i++) {
                ACLMessage respuesta = this.myAgent.blockingReceive(mt, 5000);      // Se bloquea por 5 segundo esperando el mensaje de respuesta

                if (respuesta != null) {
                    AID sender = respuesta.getSender();

                    if (respuesta.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
                        interesados.add(sender);
                        System.out.println("[" + getLocalName() + "]\trecibe respuesta, oferta aceptada por comprador " + sender.getLocalName());
                    } else
                        System.out.println("[" + getLocalName() + "]\trecibe respuesta, oferta RECHAZADA por comprador " + sender.getLocalName());
                }
            }
        }

        @Override
        public boolean done() {
            boolean haySubasta = interesados.size() >= 2;

            if (!haySubasta) {
                System.out.println("[" + getLocalName() + "]\tno hay subasta por " + libroActual);

                ACLMessage mensaje = new ACLMessage(ACLMessage.CANCEL);
                mensaje.setConversationId("interesados-insuficientes");
                mensaje.setContent(libroActual+"#subastaCanceladaPorFaltaDeInteresados");

                for (AID i : interesados)
                    mensaje.addReceiver(i);

                this.myAgent.send(mensaje);
            }

            return haySubasta;
        }

        @Override
        public int onEnd() {
            this.myAgent.addBehaviour(new SubastarLibro(libroActual));

            return super.onEnd();
        }
    }

    // ──────────────────────────────────────────────────────────────────────────────────────────────────

    private class SubastarLibro extends Behaviour {
        String libroActual;
        HashSet<ACLMessage> propusieron;
        double precioSubasta;
        int respondieron;

        SubastarLibro(String libro) {
            this.libroActual = libro;
            this.propusieron = new HashSet<>();
            respondieron = 0;
        }

        @Override
        public void onStart() {
            participaronEnSubasta = new HashSet<>(interesados);

            precioSubasta = librosEnVenta.get(libroActual);

            System.out.print("[" + getLocalName() + "]\tcomienza subasta con interesados");
            for (AID i : interesados) System.out.print(" " + i.getLocalName()); System.out.println("");

            super.onStart();
        }

        @Override
        public void action() {
            try { Thread.sleep(7500); } catch (InterruptedException e) { e.printStackTrace(); }
            // busca Compradores --> nuevos preguntar
            // Propoee

            HashSet<AID> nuevosCompradores = buscaNuevosCompradores();

            if (!nuevosCompradores.isEmpty()) {
                ACLMessage mensaje = new ACLMessage(ACLMessage.PROPOSE);
                mensaje.setConversationId("oferta-libro");
                mensaje.setContent(libroActual);

                for (AID comprador : nuevosCompradores)
                    mensaje.addReceiver(comprador);

                this.myAgent.send(mensaje);
                System.out.println("[" + getLocalName() + "]\tenvía oferta de " + libroActual);

                MessageTemplate mt = MessageTemplate.MatchConversationId("respuesta-oferta");

                for (int i = 0; i < nuevosCompradores.size(); i++) {
                    ACLMessage respuesta = this.myAgent.blockingReceive(mt, 7500);      // Se bloquea por 5 segundo esperando el mensaje de respuesta

                    if (respuesta != null) {
                        AID sender = respuesta.getSender();

                        if (respuesta.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
                            interesados.add(sender);
                            System.out.println("[" + getLocalName() + "]\trecibe respuesta, oferta aceptada por comprador " + sender.getLocalName());
                        } else
                            System.out.println("[" + getLocalName() + "]\trecibe respuesta, oferta RECHAZADA por comprador " + sender.getLocalName());
                    }
                }
            }

            System.out.println("---------------------------------");


            // iterar por los interesados, esperar por mensaje de continuo/paro
            // terminar subasta cuando sólo quede un interesado
            // iterando por los interesados por el libro, enviando el precio de subasta
            ACLMessage mensaje = new ACLMessage(ACLMessage.CFP);
            mensaje.setConversationId("nuevo-precio-subasta");

            for (AID interesado : interesados) mensaje.addReceiver(interesado);
            mensaje.setContent(String.valueOf(precioSubasta));

            this.myAgent.send(mensaje);

            propusieron.clear();

            //System.out.println("---------------------------------");
            System.out.println("[" + getLocalName() + "]\tenvía precio subasta de " + libroActual + " por " + precioSubasta + " €");

            // recibe mensajes de los compradores, aceptando/rechazando el precio
            MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.PROPOSE), MessageTemplate.MatchConversationId("propuesta-precio-subasta"));
            respondieron = 0;

            for (int i = 0; i < interesados.size(); i++) {
                ACLMessage respuesta = this.myAgent.blockingReceive(mt, 5000);

                if (respuesta != null) {    // respuesta recibida
                    propusieron.add(respuesta);
                    ganador = respuesta.getSender();
                    System.out.println("[" + getLocalName() + "]\t" + ganador.getLocalName() + " acepta " + precioSubasta + "€ por " + libroActual);
                }
            }

            precioSubasta += incremento;
        }

        @Override
        public boolean done() {
            int nuevosInteresados = 0;

            if (propusieron.size() + nuevosInteresados >= 2) {
                interesados.clear();

                for (ACLMessage m : propusieron) {
                    ACLMessage respuesta = m.createReply();
                    respuesta.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
                    respuesta.setConversationId("respuesta-propuesta-subasta");
                    respuesta.setContent("siguesDentro");

                    System.out.println("[" + getLocalName() + "]\taviso a " + m.getSender().getLocalName() + " que sigue en la subasta.");

                    interesados.add(m.getSender());
                    this.myAgent.send(respuesta);
                }

                return false;
            } else return true;
        }

        @Override
        public int onEnd() {
            precioSubasta -= incremento;

            for (ACLMessage perdedor : propusieron) {
                ACLMessage respuesta = perdedor.createReply();
                respuesta.setConversationId("respuesta-propuesta-subasta");

                if (!perdedor.getSender().equals(ganador)) {
                    respuesta.setPerformative(ACLMessage.REJECT_PROPOSAL);
                    System.out.println("[" + getLocalName() + "]\taviso a " + perdedor.getSender().getLocalName() + " que está fuera.");
                } else {
                    respuesta.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
                    respuesta.setContent("siguesDentro");
                    System.out.println("[" + getLocalName() + "]\taviso a " + perdedor.getSender().getLocalName() + " que sigue en la subasta.");
                }

                this.myAgent.send(respuesta);
            }

            this.myAgent.addBehaviour(new TranspasarLibro(libroActual, ganador, precioSubasta));

            return super.onEnd();
        }
    }

    // ──────────────────────────────────────────────────────────────────────────────────────────────────

    private class TranspasarLibro extends Behaviour {
        String libro;
        AID nuevoDueno;
        double precio;

        TranspasarLibro(String libroActual, AID ganador, double precio) {
            this.libro = libroActual;
            nuevoDueno = ganador;
            this.precio = precio;
        }

        @Override
        public void action() {

            // enviar informs a los interesados al principio de la subasta
            // enviar request al ganador con libro#precio
            // esperar por agree para quitarlo de aquí
            // volver a buscar interesados

            ACLMessage mensaje = new ACLMessage(ACLMessage.REQUEST);
            mensaje.setConversationId("ganador-subasta");

            mensaje.addReceiver(ganador);

            mensaje.setContent(libro + "#" + precio);

            this.myAgent.send(mensaje);








            mensaje = new ACLMessage(ACLMessage.INFORM);
            mensaje.setConversationId("perdedor-subasta");

            for (AID p : participaronEnSubasta)
                if (!p.equals(ganador))
                    mensaje.addReceiver(p);

            mensaje.setContent(libro + "#" + precio + "#" + ganador.getLocalName());

            this.myAgent.send(mensaje);







            MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.INFORM), MessageTemplate.MatchConversationId("informa-transpaso"));
            ACLMessage respuesta = this.myAgent.blockingReceive(mt);

            if (respuesta != null) {
                System.out.println("[" + getLocalName() + "]\tme informaron que ya tiene su libro");
            }

            misLibrosIterator.remove();

            librosEnVenta.remove(libro);

            this.myAgent.addBehaviour(new BuscarInteresadosEnLibro());


/*
            // quitarselo al dueño básicamente
            // informar
            librosEnVenta.remove(libro);
            interesadosEnMisLibros.remove(libro);

            ACLMessage mensaje = new ACLMessage(ACLMessage.INFORM);
            mensaje.setConversationId("ganaste-subasta");
            mensaje.addReceiver(ganador);
            mensaje.setContent(libro + "," + precio);
            this.myAgent.send(mensaje);

            System.out.println("[" + getLocalName() + "]\tganó " + ganador.getLocalName() + " el libro " + libro + " por " + precio + "€");
*/
        }

        @Override
        public int onEnd() {
            if (librosEnVenta.isEmpty())
                this.myAgent.doDelete();

            return super.onEnd();
        }

        @Override
        public boolean done() {
            return true;
        }
    }
}
