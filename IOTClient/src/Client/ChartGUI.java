package Client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Client.Connection.ReceiveDataThread;
import Client.Entites.AppData;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import static javafx.application.Application.launch;

public class ChartGUI extends Application {

    ReceiveDataThread thread;


    XYChart.Series series;
    private void loadData(){
        series = new XYChart.Series();
        series.getData().add(new XYChart.Data<>(0,0));
        chart.getData().addAll(series);
    }

    public void addNewData(int val){
        series.getData().add(new XYChart.Data<>(series.getData().size(),val));
    }

    public static void main(String[] args) {
        launch(args);

    }










    @FXML
    private Button backBtn;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button Backbtn;

    @FXML
    private Pane homePane;

    @FXML
    private Text uuid;

    @FXML
    private LineChart<NumberAxis, NumberAxis> chart;

    @FXML
    private Label Name;
    @FXML
    void backToHome(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("fxml/home.fxml"));
        Parent homeGUI = loader.load();
        Scene home = new Scene(homeGUI);
        //truyen data
        HomeGUI HomeController = loader.getController();
        HomeController.setName(AppData.username);
        stage.setScene(home);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.exit();
                System.exit(0);
            }
        });
        thread.stop();
    }
    @FXML
    void initialize() {
        assert Backbtn != null : "fx:id=\"Backbtn\" was not injected: check your FXML file 'chart.fxml'.";
        assert homePane != null : "fx:id=\"homePane\" was not injected: check your FXML file 'chart.fxml'.";
        assert uuid != null : "fx:id=\"uuid\" was not injected: check your FXML file 'chart.fxml'.";
        assert chart != null : "fx:id=\"chart\" was not injected: check your FXML file 'chart.fxml'.";
        assert Name != null : "fx:id=\"Name\" was not injected: check your FXML file 'chart.fxml'.";
        loadData();
        thread = new ReceiveDataThread(this);
        thread.start();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/chart.fxml"));
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

    public void setName(String username) {
        Name.setText(username);
    }
}
