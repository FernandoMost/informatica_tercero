package MyPublishSubscribe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.rmi.Naming;

public class HeartRateClient extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("HeartRateClientGraphics/login.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String args[]) {
        launch(args);
        /*
            HeartRateServerInterface h = (HeartRateServerInterface)Naming.lookup(registryURL);
            
            System.out.print("Introduzca la duración (en segundos) de la suscripción: ");
            String timeDuration = br.readLine();
            int time = Integer.parseInt(timeDuration);

            HeartRateClientInterface callbackObj =  new HeartRateClientImpl();

            h.registerForHeartRateTransmission(callbackObj);
            System.out.println("------------------------- Suscrito y listo para la transmisión -------------------------");

            try { Thread.sleep(time * 1000);
            } catch (InterruptedException ex){}

            h.unregisterForHeartRateTransmission(callbackObj);
            System.out.println("---------------------------------- Conexión terminada ----------------------------------");
            System.exit(0);

        } catch (Exception e) {
            System.out.println("Exception in HeartRateClient: " + e);
        }
        */
    }
}
