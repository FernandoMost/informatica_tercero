import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("graphics/login.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root, 413, 397));
        primaryStage.show();












        /*
        *
        * import java.net.*;
        import java.io.*;
        public class MulticastPeer{
            public static void main(String args[]){
                // args give message contents & destination multicast group (e.g. "228.5.6.7")
                MulticastSocket s =null;
                try {
                    InetAddress group = InetAddress.getByName(args[1]);
                    s = new MulticastSocket(6789);
                    s.joinGroup(group);

                    byte [] m = args[0].getBytes();
                    DatagramPacket messageOut = new DatagramPacket(m, m.length, group, 6789);
                    s.send(messageOut);
                    // TTL


                    // get messages from others in group
                    byte[] buffer = new byte[1000];
                    for(int i=0; i< 3; i++) {
                        DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);
                        s.receive(messageIn);
                        System.out.println("Received:" + new String(messageIn.getData()));
                    }
                    s.leaveGroup(group);
                }catch (SocketException e){System.out.println("Socket: " + e.getMessage());
                }catch (IOException e){System.out.println("IO: " + e.getMessage());}
                }finally {if(s != null) s.close();}
            }
        }
        *
        *
        * */
    }


    public static void main(String[] args) {
        launch(args);
    }
}