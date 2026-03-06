package bd.edu.seu.ecommerce.controller;
import bd.edu.seu.ecommerce.HelloApplication;
import bd.edu.seu.ecommerce.classes.Orderlist;
import bd.edu.seu.ecommerce.classes.Product;
import bd.edu.seu.ecommerce.singleton.ConnectionSingleton;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
public class OrderlistController implements Initializable{
    @FXML
    private TableColumn<Orderlist,String> addressCoulmn;
    @FXML
    private TableColumn<Orderlist,String> cityCoulmn;
    @FXML
    private TableColumn<Orderlist,String> emailColum;
    @FXML
    private TableColumn<Orderlist,String> mobileCoulmn;
    @FXML
    private TableColumn<Orderlist, String> nameColumn;
    @FXML
    private TableColumn<Orderlist,String> payColumn;
    @FXML
    private TableColumn<Orderlist,Number> priceColumn;
    @FXML
    private TableColumn<Orderlist,String> productNameColumn;
    @FXML
    private TableColumn<Orderlist,Number> quantityCoulmn;
    @FXML
    private TableColumn<Orderlist,Number> totalColumn;
    @FXML
    private TableView<Orderlist> orderTable;
    @FXML
    private Label priceLabel;
    private ObservableList<Orderlist>orderList;

    @FXML
    void gotoDashboardEvent(ActionEvent event) {
        HelloApplication.changeScene("Admin");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameColumn.setCellValueFactory(c->new SimpleStringProperty(c.getValue().getName()));
        emailColum.setCellValueFactory(c->new SimpleStringProperty(c.getValue().getEmail()));
        mobileCoulmn.setCellValueFactory(c->new SimpleStringProperty(c.getValue().getMobile()));
        cityCoulmn.setCellValueFactory(c->new SimpleStringProperty(c.getValue().getCity()));
        addressCoulmn.setCellValueFactory(c->new SimpleStringProperty(c.getValue().getAddress()));
        productNameColumn.setCellValueFactory(c->new SimpleStringProperty(c.getValue().getProductName()));
        payColumn.setCellValueFactory(c->new SimpleStringProperty(c.getValue().getPayMethod()));
        quantityCoulmn.setCellValueFactory(c->new SimpleIntegerProperty(c.getValue().getQuantity()));
        priceColumn.setCellValueFactory(c->new SimpleDoubleProperty(c.getValue().getPrice()));
        totalColumn.setCellValueFactory(c->new SimpleDoubleProperty(c.getValue().getTotal()));
        orderList= FXCollections.observableArrayList();
        getorderList();
        orderTable.setItems(orderList);
        updateTotalPrice();
    }
    private void updateTotalPrice() {
        double total = 0;
        for (Orderlist p : orderTable.getItems()) {
            total=total+p.getTotal();
        }
        priceLabel.setText(String.format("%.2f", total));
    }
    private void getorderList(){
        try{
            orderList.clear();
            Connection connection= ConnectionSingleton.getConnection();
            Statement statement=connection.createStatement();
            String query="SELECT*FROM ORDERLIST;";
            ResultSet resultSet=statement.executeQuery(query);
            while (resultSet.next()){
                String name=resultSet.getString("name");
                String email=resultSet.getString("email");
                String mobile=resultSet.getString("mobile");
                String city=resultSet.getString("city");
                String address=resultSet.getString("address");
                String pName=resultSet.getString("product_name");
                String payMethod=resultSet.getString("payment_method");
                int quantity=resultSet.getInt("quantity");
                double price=resultSet.getDouble("price");
                double total=resultSet.getDouble("total");
                Orderlist orderlist=new Orderlist(name,email,mobile,city,address,pName,payMethod,quantity,price,total);
                orderList.add(orderlist);

            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
            System.out.println("Failed to database connect");
        }
    }
}
