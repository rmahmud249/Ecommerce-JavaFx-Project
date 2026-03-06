package bd.edu.seu.ecommerce.controller;
import bd.edu.seu.ecommerce.HelloApplication;
import bd.edu.seu.ecommerce.classes.Orderlist;
import bd.edu.seu.ecommerce.classes.Product;
import bd.edu.seu.ecommerce.singleton.ConnectionSingleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
public class BuyNowController implements Initializable{
    @FXML
    private TextArea addressField;
    @FXML
    private ChoiceBox<String>cityChoiceField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField mobileField;
    @FXML
    private TextField nameField;
    @FXML
    private ToggleGroup paymentGroup;
    @FXML
    private Label confirmLabel;
    @FXML
    private Label pdetailsLabel;
    @FXML
    private Label ppriceLabel;
    @FXML
    private ImageView imagesField;
    @FXML
    private Spinner<Integer> quantityField;
    private Product selectedProduct;
    @FXML
    void confirmorderEvent(ActionEvent event) {
            String name=nameField.getText();
            String email=emailField.getText();
            String mobile=mobileField.getText();
            String city=String.valueOf(cityChoiceField.getValue());
            String address=addressField.getText();
            int quantity=quantityField.getValue();
            RadioButton radioButton=(RadioButton)paymentGroup.getSelectedToggle();
            String payment=radioButton.getText();
            String productName=selectedProduct.getProductName();
            double price = selectedProduct.getProductPrice();
            Orderlist orderlist=new Orderlist(name,email,mobile,city,address,productName,payment,quantity,price,quantity*price);
            insertDB(orderlist);
            confirmLabel.setText("Order successfully placed!");
    }
    private void insertDB(Orderlist orderlist){
        try{
            Connection connection=ConnectionSingleton.getConnection();
            Statement statement=connection.createStatement();
            String query="INSERT INTO ORDERLIST VALUES('"+orderlist.getName()+"','"+orderlist.getEmail()+"','"+orderlist.getMobile()+"','"+orderlist.getCity()+"','"+orderlist.getAddress()+"','"+orderlist.getProductName()+"','"+orderlist.getPayMethod()+"','"+orderlist.getQuantity()+"','"+orderlist.getPrice()+"','"+orderlist.getTotal()+"')";
            statement.executeUpdate(query);
        }
        catch (SQLException ex){
            ex.printStackTrace();
            System.out.println("Failed To database connect");
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> cityList = FXCollections.observableArrayList();
        cityList.add("Dhaka");
        cityList.add("Chattogram ");
        cityList.add("Khulna");
        cityList.add("Barishal");
        cityList.add("Rangpur");
        cityList.add("Mymensingh");
        cityChoiceField.setItems(cityList);

        SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 500);
        quantityField.setValueFactory(spinnerValueFactory);
        quantityField.valueProperty().addListener((obs, oldVal, newVal) -> updatePrice());

        selectedProduct = DashboardController.selectedProduct;
        if (selectedProduct != null) {
            selectedProduct.getProductName();
            selectedProduct.getProductPrice();
            pdetailsLabel.setText(selectedProduct.getProductDetails());
            ppriceLabel.setText(String.valueOf(selectedProduct.getProductPrice()));
            updatePrice();
            try {
                imagesField.setImage(new javafx.scene.image.Image(selectedProduct.getImagePath()));
            }
            catch (Exception e) {
                imagesField.setImage(null);
            }
        }
    }
    private void updatePrice() {
        int quantity = quantityField.getValue();
        double totalPrice = selectedProduct.getProductPrice() * quantity;
        ppriceLabel.setText(String.valueOf(totalPrice));
    }
    @FXML
    void gotoDashboardEvent(ActionEvent event) {
        HelloApplication.changeScene("Dashboard");
    }
}
