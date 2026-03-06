package bd.edu.seu.ecommerce.controller;
import bd.edu.seu.ecommerce.classes.AdminPanel;
import bd.edu.seu.ecommerce.singleton.ConnectionSingleton;
import bd.edu.seu.ecommerce.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class LoginAdminController {
    @FXML
    private TextField adminEmailField;
    @FXML
    private Label adminLabelError;
    @FXML
    private PasswordField adminPassField;
    @FXML
    private CheckBox checkBoxField;
    @FXML
    private TextField showPassField;

    @FXML
    void adminLoginEvent(ActionEvent event) {
        String email=adminEmailField.getText();
        String password=adminPassField.getText();
        HashMap<String,String >adminInfo=new HashMap<>();
        for (AdminPanel adminPanel : getAdminList()){
            adminInfo.put(adminPanel.getEmailId(),adminPanel.getAdminPass());
            if (adminInfo.containsKey(adminEmailField.getText())&& adminInfo.get(email).equals(password)) {
                adminLabelError.setTextFill(Color.BLACK);
                HelloApplication.changeScene("Admin");

            } else {
                adminLabelError.setTextFill(Color.RED);
                adminLabelError.setText("Invalid email or Password");
            }
        }

    }
    //Get from database email or mobile number to for login//
    private List<AdminPanel> getAdminList(){
        List<AdminPanel>adminList=new ArrayList<>();
        try{
            Connection connection= ConnectionSingleton.getConnection();
            Statement statement=connection.createStatement();
            String query="SELECT * FROM ADMIN";
            ResultSet resultSet=statement.executeQuery(query);
            while (resultSet.next()){
                String email=resultSet.getString("email");
                String password=resultSet.getString("password");
                AdminPanel adminPanel=new AdminPanel(email,password);
                adminList.add(adminPanel);
            }
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("Failed to connect to database");
        }
        return adminList;
    }

    //Back home//
    @FXML
    void homeEvent(ActionEvent event) {
     HelloApplication.changeScene("Home-Scene");
    }
    //Password Show//
    @FXML
    void showPassEvent(ActionEvent event) {
        if (checkBoxField.isSelected()){
            showPassField.setVisible(true);
            showPassField.setText(adminPassField.getText());
            adminPassField.setVisible(false);
        } else {
            showPassField.setVisible(false);
            adminPassField.setVisible(true);
        }
    }
}
