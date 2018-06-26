package ingsw.view;

import ingsw.Client;
import ingsw.ClientRMI;
import ingsw.ClientSocket;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * First class of Gui which allows client to choose connection
 *
 * @see ClientRMI
 * @see ClientSocket
 */

public class StartView extends Application {

    private Client client;

    public static void main() {
        Application.launch();
    }

    /**Start RMI connection if param is "RMI", else
     * start socket connection if "socket".
     *
     * @param connectionType
     * @see GUI
     */
    public void setupConnection(String connectionType){
        if(connectionType.equals("socket")){
            client = new ClientSocket("127.0.0.1",1080);
            new GUI().display(client);
        } else if(connectionType.equals("RMI")){
            client = new ClientRMI();
            try {
                client.startClient();
            } catch (IOException e) {
                e.printStackTrace(); //TODO possibile errore di connesione
            }
            new GUI().display(client);
        }
    }

    /**Simple stage in which the client must choose the connection.
     *
     * Two button : rmi and socket.
     *
     * @param primaryStage
     */

    @Override
    public void start(Stage primaryStage) {
        GridPane chooser = new GridPane();
        Scene scene = new Scene(chooser);
        chooser.setPrefSize(250, 100);
        chooser.setPadding(new Insets(50));
        chooser.setHgap(20);
        Button rmiBtn = new Button("RMI");
        Button socketBtn = new Button ("Socket");

        rmiBtn.setOnAction(e->{
            setupConnection("RMI");
            primaryStage.close();
        });
        socketBtn.setOnAction(e->{
            setupConnection("socket");
            primaryStage.close();
        });

        chooser.add(rmiBtn, 0, 0);
        chooser.add(socketBtn, 1, 0);
        primaryStage.setTitle("Choose connection");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
