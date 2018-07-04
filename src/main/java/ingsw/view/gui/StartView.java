package ingsw.view.gui;

import ingsw.client.Client;
import ingsw.client.ClientRMI;
import ingsw.client.ClientSocket;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Author : Alessio Tonelli - Elio Salvini
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
     * @param ip
     * @see GUI
     */
    public void setupConnection(String connectionType, String ip){
        if(connectionType.equals("socket")){
            client = new ClientSocket(ip,1080);
            new GUI().display(client);
        } else if(connectionType.equals("RMI")){
            client = new ClientRMI();
            try {
                client.startClient(ip);
            } catch (Exception e) {
                e.printStackTrace();
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
        chooser.setPrefSize(450, 100);
        chooser.setPadding(new Insets(50));
        chooser.setHgap(20);
        chooser.setVgap(50);
        Button rmiBtn = new Button("RMI");
        Button socketBtn = new Button ("Socket");
        Label lblServer = new Label("Server IP:");
        TextField tfIPServer = new TextField();

        rmiBtn.setOnAction(e->{
            if(!validateIPAddress(tfIPServer.getText())){
               new Warning("Incorrect IP", -1);
            }else{
                setupConnection("RMI",tfIPServer.getText());
                primaryStage.close();
            }
        });
        socketBtn.setOnAction(e->{
            if(!validateIPAddress(tfIPServer.getText())){
                new Warning("Incorrect IP", -1);
            }else{
                setupConnection("socket",tfIPServer.getText());
                primaryStage.close();
            }
        });

        chooser.add(lblServer, 0, 0);
        chooser.add(tfIPServer, 1, 0);
        chooser.add(rmiBtn, 0, 1);
        chooser.add(socketBtn, 2, 1);
        primaryStage.setTitle("Choose connection");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /** Same control in cli
     * This method validates ip address format
     * @param ipAddress ip address you want to validate
     * @return  true if the ip address format is correct
     */
    public static boolean validateIPAddress(String ipAddress){
        String tmp;
        try {
            for (int i=0; i<4; i++){
                if(i==3){
                    if (Integer.parseInt(ipAddress) < 0 || Integer.parseInt(ipAddress) > 255) return false;
                    break;
                }
                tmp = ipAddress.substring(0,ipAddress.indexOf('.'));
                ipAddress = ipAddress.substring(ipAddress.indexOf('.')+1);
                if (Integer.parseInt(tmp) < 0 || Integer.parseInt(tmp) > 255) return false;
            }
        }catch (Exception e){
            return false;
        }
        return true;
    }
}
