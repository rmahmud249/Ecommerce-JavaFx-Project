package bd.edu.seu.ecommerce.controller;
import bd.edu.seu.ecommerce.singleton.ConnectionSingleton;
import bd.edu.seu.ecommerce.HelloApplication;
import bd.edu.seu.ecommerce.classes.UserDetails;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class LoginUserController {
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private CheckBox checkboxField;
    @FXML
    private Label passwordError;
    @FXML
    private TextField showPassField;
    @FXML
   public void loginEvent(){
       String email=emailField.getText();
       String password=passwordField.getText();
        HashMap<String,String >userInfo=new HashMap<>();
       for (UserDetails userDetails:getUserList()){
           userInfo.put(userDetails.getEmail(),userDetails.getPassword());
           if (userInfo.containsKey(emailField.getText())&& userInfo.get(email).equals(password)) {
               passwordError.setTextFill(Color.BLACK);
               passwordError.setText("Login Successfully");
               HelloApplication.changeScene("Dashboard");
           } else {
               passwordError.setTextFill(Color.RED);
               passwordError.setText("Invalid UserID or Password");
           }
       }
   }
   //Get from database email or mobile number to for login//
   private List<UserDetails> getUserList(){
       List<UserDetails>userList=new ArrayList<>();
       try{
           Connection connection= ConnectionSingleton.getConnection();
           Statement statement=connection.createStatement();
           String query="SELECT * FROM REGISTRATION";
           ResultSet resultSet=statement.executeQuery(query);
           while (resultSet.next()){
             String firstname=resultSet.getString("first_name");
             String lastname=resultSet.getString("last_name");
             String email=resultSet.getString("email");
             String mobile=resultSet.getString("mobile");
             String password=resultSet.getString("password");
             UserDetails userDetails=new UserDetails(firstname,lastname,email,mobile,password);
             userList.add(userDetails);
           }
       }catch(SQLException e){
           e.printStackTrace();
           System.out.println("Failed to connect to database");
       }
       return userList;
   }
   //Password Show//
   @FXML
   public void passwordShow(){
       if (checkboxField.isSelected()){
           showPassField.setVisible(true);
           showPassField.setText(passwordField.getText());
           passwordField.setVisible(false);
       } else {
           showPassField.setVisible(false);
           passwordField.setVisible(true);
       }
   }
    //Login Page to Register Page scene Change//
    @FXML
    void googleEvent(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert Box");
        alert.setHeaderText(null);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-font-size: 18px;");
        alert.setContentText("This feature will be coming soon....!");
        alert.showAndWait();
    }
    @FXML
    void facebookEvent(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert Box");
        alert.setHeaderText(null);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-font-size: 18px;");
        alert.setContentText("This feature will be coming soon....!");
        alert.showAndWait();
    }
    @FXML
    void gotoRegisterEvent(MouseEvent event) {
    HelloApplication.changeScene("Registration");
    }
    @FXML
    void gotoHomeEvent(MouseEvent event) {
    HelloApplication.changeScene("Home-Scene");
    }
}
