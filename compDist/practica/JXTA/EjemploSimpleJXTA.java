import net.jxta.discovery.DiscoveryEvent;
import net.jxta.discovery.DiscoveryListener;
import net.jxta.discovery.DiscoveryService;
import net.jxta.document.AdvertisementFactory;
import net.jxta.endpoint.ByteArrayMessageElement;
import net.jxta.endpoint.Message;
import net.jxta.endpoint.MessageElement;
import net.jxta.exception.PeerGroupException;
import net.jxta.id.IDFactory;
import net.jxta.peer.PeerID;
import net.jxta.peergroup.PeerGroup;
import net.jxta.peergroup.PeerGroupID;
import net.jxta.pipe.*;
import net.jxta.platform.Module;
import net.jxta.platform.ModuleClassID;
import net.jxta.platform.NetworkConfigurator;
import net.jxta.platform.NetworkManager;
import net.jxta.protocol.ModuleClassAdvertisement;
import net.jxta.protocol.ModuleImplAdvertisement;
import net.jxta.protocol.ModuleSpecAdvertisement;
import net.jxta.protocol.PipeAdvertisement;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EjemploSimpleJXTA implements DiscoveryListener, PipeMsgListener {
    private SimpleDateFormat dateFormat;

    public static void main(String[] args) throws PeerGroupException, IOException {
        // nivel de logs que va a imprimir JXTA (que son muchos) se recomienta Level.OFF para ver sólo mis prints
        Logger.getLogger("net.jxta").setLevel(Level.OFF);

        // Puerto aleatorio, JXTA usa TCP para recibir conexiones y entran en conflicto con más de un proceso de esta clase
        int port = 9000 + new Random().nextInt(100);

        EjemploSimpleJXTA hello = new EjemploSimpleJXTA(port);

        hello.start();

        hello.fetch_advertisements();
    }

    // ──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────

    private String peerNombre;
    private PeerID peerId;
    private File archivoConfiguracion;
    private NetworkManager networkManager;

    private EjemploSimpleJXTA(int port) {
        dateFormat =  new SimpleDateFormat("HH:mm:ss");

        print("Comienza nodo JXTA en el puerto " + port);

        // nombre random y unico
        peerNombre = "Peer" + new Random().nextInt(1000000);

        // ID del peer, se usa el nombre random como semilla
        peerId = IDFactory.newPeerID(PeerGroupID.defaultNetPeerGroupID, peerNombre.getBytes());

        // caché local del nodo, con un fichero particular por peer
        archivoConfiguracion = new File("." + System.getProperty("file.separator") + peerNombre);

        // se crea la red, el nodo y el grupo; con el tipo: ad-hoc, edge, proxy, relay, rendezvous ...
        try {
            networkManager = new NetworkManager(NetworkManager.ConfigMode.ADHOC, peerNombre, archivoConfiguracion.toURI());

            NetworkConfigurator configurator = networkManager.getConfigurator();

            if (configurator != null) {
                configurator.setTcpPort(port);
                configurator.setTcpEnabled(true);
                configurator.setTcpIncoming(true);
                configurator.setTcpOutgoing(true);
                configurator.setUseMulticast(true);
                configurator.setPeerID(peerId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        print("Configuración del peer OK");
    }

    // ──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────

    private static final String nombreGrupo =       "grupo_usc";
    private static final String descripcionGrupo =  "Grupo de prueba, ejemplo simple de JXTA";
    private static final PeerGroupID idGrupo =      IDFactory.newPeerGroupID(PeerGroupID.defaultNetPeerGroupID, nombreGrupo.getBytes());

    private static final String nombreUnicast =     "nombreCanalUnicast";
    private static final String nombreMulticast =   "nombreCanalMulticast";

    private static final String nombreServicio = "nombreServicioEjemploSimpleJxta";

    private PeerGroup elSubgrupo;
    private PipeService pipeService;
    private PipeID idUnicast;
    private PipeID idMulticast;
    private PipeID idCanal;
    private DiscoveryService discoveryService;
    private ModuleSpecAdvertisement moduleSpecAdvertisement;

    private void start() throws PeerGroupException, IOException {
        print("Grupo definido {");
        System.out.println("\tid: " + idGrupo);
        System.out.println("\tnombre: " + nombreGrupo);
        System.out.println("\tdescripcion: " + descripcionGrupo);
        System.out.println("\t}");

        // arranca la red
        PeerGroup myPeerGroup = networkManager.startNetwork();

        print("Nuevo peer {");
        System.out.println("\tid: " + peerId);
        System.out.println("\tnombre: " + peerNombre);
        System.out.println("\t}");

        // nos conectamos a nuestro subgrupo, si no existe se crea
        ModuleImplAdvertisement mAdv = null;

        try {
            mAdv = myPeerGroup.getAllPurposePeerGroupImplAdvertisement();
        } catch (Exception ex) {
            System.err.println(ex.toString());
        }

        // se asocia al grupo
        elSubgrupo = myPeerGroup.newGroup(idGrupo, mAdv, nombreGrupo, descripcionGrupo);

        // chequeo si se ha conectado al grupo
        if (Module.START_OK != elSubgrupo.startApp(new String[0])) print("NO estás dentro del grupo");
        else print("Estás dentro del grupo");

        // obtenemos los servicios de dicho grupo
        pipeService = elSubgrupo.getPipeService();
        discoveryService = elSubgrupo.getDiscoveryService();

        // ────────────────────────────────────────────────────────────────────────────────────────────
        // se anuncian/publican los dos canales unicast y multicast

        // IDs de los canales
        idUnicast = IDFactory.newPipeID(elSubgrupo.getPeerGroupID(), nombreUnicast.getBytes());
        idMulticast = IDFactory.newPipeID(elSubgrupo.getPeerGroupID(), nombreMulticast.getBytes());

        // anuncio unicast
        PipeAdvertisement advertisement1 = (PipeAdvertisement) AdvertisementFactory.newAdvertisement(PipeAdvertisement.getAdvertisementType());

        advertisement1.setPipeID(idUnicast);
        advertisement1.setType(PipeService.UnicastType);
        advertisement1.setName("nombreAnuncioUnicast");
        advertisement1.setDescription("Canal de recepción de mensajes unicast");

        // al servicio de canales, que cree el canal de recepción INPUT
        pipeService.createInputPipe(advertisement1, this);

        print("Canal unicast de entrada OK, id: " + idUnicast);

        // anuncio multicast
        PipeAdvertisement advertisement2 = (PipeAdvertisement) AdvertisementFactory.newAdvertisement(PipeAdvertisement.getAdvertisementType());

        advertisement2.setPipeID(idMulticast);
        advertisement2.setType(PipeService.PropagateType);
        advertisement2.setName("nombreAnuncioMulticast");
        advertisement2.setDescription("Canal de recepción de mensajes multicast");

        // al servicio de canales, que cree el canal de recepción INPUT
        pipeService.createInputPipe(advertisement2, this);

        print("Canal multicast de entrada OK, id: " + idUnicast);

        // Al servicio de "descubrimiento" se le añade esta clase, para que el resto de nodos lo encuentren y nos respondan los anuncios
        discoveryService.addDiscoveryListener(this);

        // ────────────────────────────────────────────────────────────────────────────────────────────

        // anuncio de la clase modulo, usada para promocionar a los nodos de su existencia
        ModuleClassAdvertisement advertisement = (ModuleClassAdvertisement) AdvertisementFactory.newAdvertisement(ModuleClassAdvertisement.getAdvertisementType());

        advertisement.setName("COMP-DIST:EJEMPLO");
        advertisement.setDescription("Module class advertisement, ejemplo simple de JXTA");

        // ID del servicio
        ModuleClassID moduleClassID = IDFactory.newModuleClassID();
        advertisement.setModuleClassID(moduleClassID);

        // publicamos anuncio tanto a vecinos locales y remoto
        discoveryService.publish(advertisement);
        discoveryService.remotePublish(advertisement);

        print("Clase módulo publicada OK");

        // ─────────────────────────────────────────────────────────────────

        // anuncio de la especificación del módulo, usado para acceder a él
        // contiene lo necesario para invocarlo, como puede ser un canal para que así que se comuniquen con el
        moduleSpecAdvertisement = (ModuleSpecAdvertisement) AdvertisementFactory.newAdvertisement(ModuleSpecAdvertisement.getAdvertisementType());
        moduleSpecAdvertisement.setName("COMP-DIST:EJEMPLO");
        moduleSpecAdvertisement.setVersion("Version 1.0");
        moduleSpecAdvertisement.setCreator("sun.com");
        moduleSpecAdvertisement.setModuleSpecID(IDFactory.newModuleSpecID(moduleClassID));
        moduleSpecAdvertisement.setSpecURI("http://www.jxta.org/Ex1");

        idCanal = IDFactory.newPipeID(elSubgrupo.getPeerGroupID(), nombreServicio.getBytes());
        PipeAdvertisement pipeAdv = (PipeAdvertisement) AdvertisementFactory.newAdvertisement(PipeAdvertisement.getAdvertisementType());

        pipeAdv.setPipeID(idCanal);
        pipeAdv.setType(PipeService.UnicastType);
        pipeAdv.setName("nombreAnuncioServicio");
        pipeAdv.setDescription("Canal del servicio");

        moduleSpecAdvertisement.setPipeAdvertisement(pipeAdv);

        // publicamos al grupo el servicio
        discoveryService.publish(moduleSpecAdvertisement);
        discoveryService.remotePublish(moduleSpecAdvertisement);

        // el canal comienza a "escuchar", hasta que salte el método discoveryEvent()
        pipeService.createInputPipe(pipeAdv, this);

        print("Especificación del módulo publicada OK, canal escuchando");
    }

    // ──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────

    // Hilo que cada 5 segundos busca si hay anuncios en el grupo, diferenciando por nombre
    // el metodo getRemoteAdvertisements() no se bloquea, pero si encuentra un anuncio salta el método discoveryEvent()
    private void fetch_advertisements() {
        new Thread(() -> {
            while(true) {
                try {
                    discoveryService.getRemoteAdvertisements(null, DiscoveryService.ADV, "Name", "COMP-DIST:EJEMPLO", 1, null);

                    print("No hay anuncios, esperamos otros 5 segundos");

                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // ──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────

    // este método "salta" cuando otro nodo publica su servicio, se le responde con un hola :)
    @Override
    public void discoveryEvent(DiscoveryEvent evento) {
        print("Anuncio encontrado, hay un nuevo nodo en la ciudad!");

        String idNodoEncontrado = "urn:jxta:" + evento.getSource().toString().substring(7);
        String contenidoMensaje = "Hola nodooo!!";

        /*  para enviar el mensaje, al usar canales unidireccionales, necesitamos un canal OUTPUT
            desde nuestro nodo hasta el nuevo encontrado, por eso conservamos el id generado anteriormente   */

        // anuncio que "promociona" el canal de salida
        PipeAdvertisement anuncio = (PipeAdvertisement) AdvertisementFactory.newAdvertisement(PipeAdvertisement.getAdvertisementType());

        anuncio.setPipeID(idUnicast);
        anuncio.setType(PipeService.UnicastType);
        anuncio.setName("outputPipe");
        anuncio.setDescription("canal de salida para el envío de mensajes, ejemplo simple de JXTA");

        // el canal de salida puede tener múltiples destinatarios, para crearlo es necesario un conjunto
        // en nuestro caso sólo tiene un ID, el del nodo que encontramos

        Set<PeerID> nodosDestinatarios = new HashSet<>();

        try {
            nodosDestinatarios.add((PeerID)IDFactory.fromURI(new URI(idNodoEncontrado)));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        OutputPipe canalDeSalida = null;

        try {
            canalDeSalida = pipeService.createOutputPipe(
                    anuncio,                // anuncio del canal en cuestión
                    nodosDestinatarios,     // nodos en los que el canal es resuelto
                    10000);                 // tiempo (ms) a esperar hasta que el canal sea resuelto
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (canalDeSalida != null) {
            print("Canal de salida OK");

            Message mensaje = new Message();

            MessageElement origen =     new ByteArrayMessageElement("From", null, peerId.toString().getBytes(StandardCharsets.ISO_8859_1),  null);
            MessageElement contenido =  new ByteArrayMessageElement("Msg",  null, contenidoMensaje.getBytes(StandardCharsets.ISO_8859_1),   null);

            mensaje.addMessageElement(origen);
            mensaje.addMessageElement(contenido);

            // envío del mensaje
            try {
                canalDeSalida.send(mensaje);
                print("Mensaje enviado OK");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // ──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────

    // este método "salta" cuando otro nodo envía un mensaje
    @Override
    public void pipeMsgEvent(PipeMsgEvent event) {
        print("Mensaje nuevo recibido");

        try {
            Message msg = event.getMessage();

            byte[] msgBytes = msg.getMessageElement("Msg").getBytes(true);
            byte[] fromBytes = msg.getMessageElement("From").getBytes(true);

            String from = new String(fromBytes);
            String message = new String(msgBytes);

            print("Mensaje del nodo: " + from);
            System.out.println("\t" + message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────

    private void print(String s) {
        System.out.println("[" + dateFormat.format(new Date()) + "]\t" + s);
    }
}
