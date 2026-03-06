package bd.edu.seu.ecommerce.controller;
import bd.edu.seu.ecommerce.HelloApplication;
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
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.security.PublicKey;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
public class DashboardController implements Initializable{
    @FXML
    private TableColumn<Product,Number> quantityColumn;
    @FXML
    private TableColumn<Product, String> colorColumn;
    @FXML
    private TableView<Product> dashboardTable;
    @FXML
    private TableColumn<Product,String> detailsColumn;
    @FXML
    private TableColumn<Product,String> nameColumn;
    @FXML
    private TableColumn<Product, Number> priceColumn;
    @FXML
    private TableColumn<Product,String> sizeColumn;
    @FXML
    private TableColumn<Product,String> imagesColumn;
    @FXML
    private ImageView imageView;
    @FXML
    private TextField searchBaerField;
    @FXML
    private Label colorLabel;
    @FXML
    private Label detailLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label quantityLabel;
    @FXML
    private Label sizeLabel;
    @FXML
    private Label typeLabel;
    @FXML
    private TableColumn<Product,String> typeColumn;
    public static Product selectedProduct;
    private  ObservableList<Product>productList;
    public static ObservableList<Product>cardList=FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameColumn.setCellValueFactory(c->new SimpleStringProperty(c.getValue().getProductName()));
        typeColumn.setCellValueFactory(c->new SimpleStringProperty(c.getValue().getProductType()));
        sizeColumn.setCellValueFactory(c->new SimpleStringProperty(c.getValue().getProductSize()));
        colorColumn.setCellValueFactory(c->new SimpleStringProperty(c.getValue().getProductColor()));
        detailsColumn.setCellValueFactory(c->new SimpleStringProperty(c.getValue().getProductDetails()));
        quantityColumn.setCellValueFactory(c->new SimpleIntegerProperty(c.getValue().getProductQuantity()));
        priceColumn.setCellValueFactory(c->new SimpleDoubleProperty(c.getValue().getProductPrice()));
        imagesColumn.setCellValueFactory(c->new SimpleStringProperty(c.getValue().getImagePath()));
        productList=FXCollections.observableArrayList();
        getList();
        dashboardTable.setItems(productList);
    }
    public void getList() {
        try {
            productList.clear();
            Connection connection = ConnectionSingleton.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM PRODUCT;";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String type = resultSet.getString("type");
                String size = resultSet.getString("size");
                String color = resultSet.getString("color");
                String details = resultSet.getString("details");
                int quantity = resultSet.getInt("quantity");
                double price = resultSet.getDouble("price");
                String image=resultSet.getString("imagePath");
                Product product = new Product(name, type, size, color,  details , quantity, price,image);
                productList.add(product);
            }
        }
        catch (SQLException ex) {
            System.err.println("Failed to connect to database");
            ex.printStackTrace();
        }
    }
    @FXML
    void onMousClick(MouseEvent event) {
        Product product=dashboardTable.getSelectionModel().getSelectedItem();
        if (product!=null){
        showProductDetails(product);
        }
    }
    private void showProductDetails(Product product){
     nameLabel.setText(product.getProductName());
     typeLabel.setText(product.getProductType());
     sizeLabel.setText(product.getProductSize());
     colorLabel.setText(product.getProductColor());
     detailLabel.setText(product.getProductDetails());
     quantityLabel.setText(String.valueOf(product.getProductQuantity()));
     priceLabel.setText(String.valueOf(product.getProductPrice()));
        try {
            imageView.setImage(new javafx.scene.image.Image(product.getImagePath()));
        } catch (Exception e) {
            imageView.setImage(null);
        }
    }
    @FXML
    void addtocardEvent(MouseEvent event) {
        Product selectedProduct = dashboardTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
          //  insertCartProduct(selectedProduct);
            cardList.add(selectedProduct);
        }
    }
    private void insertCartProduct(Product product) {
       try{
           Connection connection= ConnectionSingleton.getConnection();
           Statement statement=connection.createStatement();
           String query="INSERT INTO CARD VALUES('"+product.getProductName()+"','"+product.getProductType()+"','"+product.getProductSize()+"','"+product.getProductColor()+"','"+product.getProductDetails()+"','"+product.getProductPrice()+"')";
           statement.executeUpdate(query);
        }catch (Exception e) {
            System.err.println("Error inserting product into cart:");
            e.printStackTrace();
        }
    }
    @FXML
    void gotouserLogin(MouseEvent event) {
        HelloApplication.changeScene("Login-User");
    }
    @FXML
    void searchBaeEvent(KeyEvent event) {
        String search = searchBaerField.getText().toLowerCase();
        ObservableList<Product> filteredList = FXCollections.observableArrayList();
        for (Product product : productList) {
            if (String.valueOf(product.getProductId()).contains(search) ||
                    product.getProductName().toLowerCase().contains(search)) {
                filteredList.add(product);
            }
        }
        dashboardTable.setItems(filteredList);
    }
    @FXML
    void logoutEvent(MouseEvent event) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmation");
        confirm.setHeaderText(null);
        confirm.setContentText("Are you sure you want to logout!");
        DialogPane dialogPane = confirm.getDialogPane();
        dialogPane.setStyle("-fx-font-size: 18px;");
        if (confirm.showAndWait().get() == ButtonType.OK) {
             HelloApplication.changeScene("Login-User");
        }
    }

    @FXML
    void myCardEvent(MouseEvent event) {
    HelloApplication.changeScene("Add-Card");
    }
    @FXML
    void gotoBuynowEvent(ActionEvent event) {
        Product product=dashboardTable.getSelectionModel().getSelectedItem();
        if (product==null){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.setStyle("-fx-font-size: 18px;");
            alert.setContentText("Please select a product to buy");
            alert.showAndWait();
        }
        else {
            selectedProduct=product;
            HelloApplication.changeScene("Buy-Now");
        }

    }
    @FXML
    void adminEvent(ActionEvent event) {
      HelloApplication.changeScene("Login-Admin");
    }
}
