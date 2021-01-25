package Client;

import Client.Connection.Connection;
import Client.Connection.ReceiveDataThread;
import Client.Entites.AppData;
import Client.Utils.Utils;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class HomeGUI extends Application {

//    private int[] wet = new int[16];

    public static void main(String[] args) {
        launch(args);
    }



//    public static void setWetColor(int value) {
//        getWet1().setStyle("");
//    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/home.fxml"));
        primaryStage.setTitle("Home");
        primaryStage.setScene(new Scene(root, 1060, 805));
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.exit();
                System.exit(0);
            }
        });
    }

    public void setCloseOperation(Stage primaryStage){
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.exit();
                System.exit(0);
            }
        });
    }
    public void test(ActionEvent event) throws Exception {
        Button btn = (Button) event.getSource();
        System.out.println("clicked");
        if(btn.getText().equals("ON")){
            if(!turnOn().equals("")){
                btn.setText("OFF");
                btn.setStyle("-fx-background-color: red;");
                wet1.setStyle("-fx-background-color: #ACFA58;");
            }

        } else if(btn.getText().equals("OFF")){
            if(!turnOff().equals("")){
                btn.setText("ON");
                btn.setStyle("-fx-background-color: green;");
                wet1.setStyle("-fx-background-color:  #FA5858;");
            }

        }
    }
//    public void Switch(Button btn, ActionEvent event) {
//        System.out.println("clicked");
//        if(btn.getText().equals("ON")){
//            turnOff();
//            btn.setText("OFF");
//            btn.setStyle("-fx-background-color: red;");
//        } else if(btn.getText().equals("OFF")){
//            turnOn();
//            btn.setText("ON");
//            btn.setStyle("-fx-background-color: green;");
//        }
//    }


    public String turnOn()  {
        String temp = "";
        try {
            temp = Connection.requestPOST(Utils.ControlPackage(1));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return temp;
    }

    public String turnOff() {
        String temp="";
        try {
            temp = Connection.requestPOST(Utils.ControlPackage(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return temp;
    }



    public void rePaintButtonColor(){
//        System.out.println("repainting..");
        wet1.setStyle(colorValue(AppData.humd));
    }

    public String colorValue(int wet){

        if(wet<20) {
            return "-fx-background-color: #FA5858";
        } else if (wet<40) {
            return "-fx-background-color: #FA8258";
        } else if (wet<60) {
            return "-fx-background-color: #F7D358";
        }else if (wet<80) {
            return "-fx-background-color: #F4FA58";
        } else if (wet<100) {
            return "-fx-background-color: #ACFA58";
        } else {
            return "-fx-background-color: #FFFFFF";
        }
    }

//    public void setWet(int[] value) {
//        for(int i=0;i<16;i++){
//            wet[i] = value[i];
////            System.out.print(wet[i]+" - ");
//        }
//
//    }

    @FXML
    public void viewInfo(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("fxml/chart.fxml"));
        Parent chartGUI = loader.load();
        Scene home = new Scene(chartGUI);
        //truyen data
        ChartGUI HomeController = loader.getController();
        HomeController.setName(AppData.username);
        stage.setScene(home);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.exit();
                System.exit(0);
            }
        });
    }



    @FXML
    private Label Name;
    @FXML
    private Button AutoPumpbtn;
    @FXML
    private Button SwitchBtn1;
    @FXML
    private Button viewInfoBtn;
    @FXML
    private Text uuid;


    @FXML
    private Pane wet5;
    @FXML
    private Pane wet4;
    @FXML
    private Pane wet3;
    @FXML
    private Pane wet2;
    @FXML
    public  Pane wet1;


    public void setName(String name) {
        Name.setText(name);
    }
    public String getUUID(){
        return uuid.getText();
    }



    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert AutoPumpbtn != null : "fx:id=\"AutoPumpbtn\" was not injected: check your FXML file 'home.fxml'.";
        assert wet5 != null : "fx:id=\"wet5\" was not injected: check your FXML file 'home.fxml'.";
        assert wet4 != null : "fx:id=\"wet4\" was not injected: check your FXML file 'home.fxml'.";
        assert wet3 != null : "fx:id=\"wet3\" was not injected: check your FXML file 'home.fxml'.";
        assert wet2 != null : "fx:id=\"wet2\" was not injected: check your FXML file 'home.fxml'.";
        assert wet1 != null : "fx:id=\"wet1\" was not injected: check your FXML file 'home.fxml'.";
        assert SwitchBtn1 != null : "fx:id=\"SwitchBtn1\" was not injected: check your FXML file 'home.fxml'.";
        assert Name != null : "fx:id=\"Name\" was not injected: check your FXML file 'home.fxml'.";
        assert viewInfoBtn != null : "fx:id=\"viewInfoBtn\" was not injected: check your FXML file 'home.fxml'.";
//        ReceiveDataThread thread = new ReceiveDataThread(this);
//        thread.start();
    }
}
