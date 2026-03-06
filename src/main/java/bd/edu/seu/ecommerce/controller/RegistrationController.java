package bd.edu.seu.ecommerce.controller;
import bd.edu.seu.ecommerce.singleton.ConnectionSingleton;
import bd.edu.seu.ecommerce.HelloApplication;
import bd.edu.seu.ecommerce.classes.UserDetails;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class RegistrationController {
    @FXML
    private TextField emailField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField mobailField;
    @FXML
    private CheckBox checkboxField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField passwordShow;
    @FXML
    private TextField repassShow;
    @FXML
    private PasswordField repasswordField;
    @FXML
    private Label emailError;
    @FXML
    private Label firstnameError;
    @FXML
    private Label lastnameError;
    @FXML
    private Label mobileError;
    @FXML
    private Label passwordError;
    @FXML
    private Label repassError;
    @FXML
    private Label regSuccesfull;
    @FXML
    void registerEvent(ActionEvent event) {
         String firstname=firstNameField.getText();
         String lastname=lastNameField.getText();
         String email=emailField.getText();
         String mobile=mobailField.getText();
         String password=passwordField.getText();
         String repassword=repasswordField.getText();

         //Email or Mobile List//
        List<String> userEmail= getEmailList();
        List<String> userMobile=getMobileList();

        //ERROR PART//
         if (firstname.isEmpty()){
             firstnameError.setTextFill(Color.RED);
             firstnameError.setText("Your first name required");
             return;
         }
         else {
             firstnameError.setText("");
         }
        if (lastname.isEmpty()){
            lastnameError.setTextFill(Color.RED);
            lastnameError.setText("Your last name is required");
            return;
        }
        else {
            lastnameError.setText("");
        }
        if (email.isEmpty()){
            emailError.setTextFill(Color.RED);
            emailError.setText("E-mail is required");
            return;
        }
        else {
            emailError.setText("");
        }
        for (String n : userEmail){
            if (email.equals(n)){
                emailError.setTextFill(Color.RED);
                emailError.setText("This email already exist");
                return;
            }
            else {
                emailError.setText("");
            }
        }
        if (mobile.isEmpty()){
            mobileError.setTextFill(Color.RED);
            mobileError.setText("Mobile number is required");
            return;
        }
        else {
            mobileError.setText("");
        }
        for (String x:userMobile){
            if (mobile.equals(x)){
                mobileError.setTextFill(Color.RED);
                mobileError.setText("Mobile number already exist");
                return;
            }
        }
        if (password.isEmpty()){
            passwordError.setTextFill(Color.RED);
            passwordError.setText("Enter password");
            return;
        }
        else {
            passwordError.setText("");
        }
        if (repassword.isEmpty()){
            repassError.setTextFill(Color.RED);
            repassError.setText("Enter re-type password");
            return;
        }
        else {
            repassError.setText("");
        }
        if (password.equals(repassword)){
           UserDetails userDetails=new UserDetails(firstname,lastname,email,mobile,password);
           insertInfoDatabase(userDetails);//insert database
           regSuccesfull.setText("Register Successfully");
           firstNameField.setText("");
           lastNameField.setText("");
           emailField.setText("");
           mobailField.setText("");
           passwordField.setText("");
           repasswordField.setText("");
        }
        else {
            repassError.setTextFill(Color.RED);
            repassError.setText(" Dose Not Match");
        }
        //Field Clear//
//        firstNameField.clear();
//        lastNameField.clear();
//        emailField.clear();
//        mobailField.clear();
//        passwordField.clear();
//        repasswordField.clear();
    }
    //Databases//
    public void insertInfoDatabase(UserDetails user){
       try{
           Connection connection= ConnectionSingleton.getConnection();
           Statement statement=connection.createStatement();
           String query="INSERT INTO REGISTRATION VALUES('"+user.getFirstName()+"','"+user.getLastName()+"','"+user.getEmail()+"','"+user.getMobile()+"','"+user.getPassword()+"')";
           statement.executeUpdate(query);
         }catch(SQLException e){
           e.printStackTrace();
           System.out.println("Failed to connect to database");
       }
    }
    //Gate Email list for check duplicate in database exist?//
    List<String>getEmailList(){
        List<String>emailList=new ArrayList<>();
       try{
           Connection connection= DriverManager.getConnection("jdbc:mysql://localhost/ecommerce","root","raselCSE1234");
           Statement statement=connection.createStatement();
           String query="SELECT * FROM REGISTRATION";
           ResultSet resultSet=statement.executeQuery(query);
           while (resultSet.next()){
               String email=resultSet.getString("email");
               emailList.add(email);
           }
       }catch(SQLException e){
           e.printStackTrace();
           System.out.println("Failed to connect to database");
           }
        return emailList;
    }
    //Gate Mobile list for check duplicate in database exist?//
    List<String> getMobileList(){
        List<String>mobileLiset=new ArrayList<>();
        try{
            Connection connection= DriverManager.getConnection("jdbc:mysql://localhost/ecommerce","root","raselCSE1234");
            Statement statement=connection.createStatement();
            String query="SELECT * FROM REGISTRATION";
            ResultSet resultSet=statement.executeQuery(query);
            while (resultSet.next()){
                String mobile=resultSet.getString("mobile");
                mobileLiset.add(mobile);
            }
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("Failed to connect to database");
            }
        return mobileLiset;
    }
    //Show hide password//
    public void passwordShow(){
        if (checkboxField.isSelected()){
            passwordShow.setVisible(true);
            passwordShow.setText(passwordField.getText());
            repassShow.setVisible(true);
            repassShow.setText(repasswordField.getText());
            passwordField.setVisible(false);
            repasswordField.setVisible(false);
        } else {
            passwordShow.setVisible(false);
            repassShow.setVisible(false);
            passwordField.setVisible(true);
            repasswordField.setVisible(true);
        }
    }
    @FXML
    void regtologinEvent(MouseEvent event) {
      HelloApplication.changeScene("Login-User");
    }
}
