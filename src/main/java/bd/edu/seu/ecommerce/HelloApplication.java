package bd.edu.seu.ecommerce;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
public class HelloApplication extends Application {
    public static Stage stage;
    @Override
    public void start(Stage stage) throws IOException {
        this.stage=stage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Home-Scene.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("E-Commerce!");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
    //Scene Change Method//
    public static void changeScene(String fileName){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fileName+".fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
        }
        catch (IOException e){
            System.out.println("Field to load file");
            e.printStackTrace();
        }
    }
}


