package bd.edu.seu.ecommerce.controller;
import bd.edu.seu.ecommerce.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
public class HomesceneController {
    @FXML
    void userEvent(ActionEvent event) {
      HelloApplication.changeScene("Login-User");
    }
    @FXML
    void adminEvent(ActionEvent event) {
        HelloApplication.changeScene("Login-Admin");
    }
}
