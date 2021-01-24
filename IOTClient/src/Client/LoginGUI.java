package Client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class LoginGUI extends Application {
    Stage window;
    HomeGUI home = new HomeGUI();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {


        window = primaryStage;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("login.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        window.setTitle("Home");
        window.setScene(new Scene(root, 474, 900));
        window.show();
//        receiveData();

    }
    @FXML
    public void enter(ActionEvent event) throws IOException {
        System.out.println("loging");
        String temp = Test.requestPOST(Utils.SigninPackage(username.getText(),password.getText()));
        System.out.println("Temp: "+temp);
        System.out.println("\"accept\"");
        if(temp.equals("\"accept\"")){
            System.out.println("thông tin đúng!");
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("fxml/home.fxml"));
            Parent homeGUI = loader.load();
            Scene home = new Scene(homeGUI);
            //truyen data
            HomeGUI HomeController = loader.getController();
            HomeController.setName(username.getText());
            stage.setScene(home);
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    Platform.exit();
                    System.exit(0);
                }
            });
        } else {
            System.out.println("không chạy vào if.");
        }

    }


//    public void receiveData(){
//        ReceiveDataThread thread = new ReceiveDataThread();
//        thread.start();
//    }
    @FXML
    private TextField username;
    @FXML
    private TextField password;
}
