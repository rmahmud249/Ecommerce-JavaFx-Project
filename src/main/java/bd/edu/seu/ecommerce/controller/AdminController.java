package bd.edu.seu.ecommerce.controller;
import bd.edu.seu.ecommerce.singleton.ConnectionSingleton;
import bd.edu.seu.ecommerce.HelloApplication;
import bd.edu.seu.ecommerce.classes.Product;
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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
public class AdminController implements Initializable {
    @FXML
    private Label typeLabel;
    @FXML
    private Label colorLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label detailsLabel;
    @FXML
    private Label idLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label quantityLabel;
    @FXML
    private Label totalPriceLabel;
    @FXML
    private Label sizeLabel;
    @FXML
    private ImageView imageView;
    @FXML
    private TableColumn<Product, String> typeColumn;
    @FXML
    private TableColumn<Product, String> colorColumn;
    @FXML
    private TableColumn<Product, String> dateColumn;
    @FXML
    private TableColumn<Product, String> detailsColumn;
    @FXML
    private TableColumn<Product, Number> idColumn;
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product, Number> priceColumn;
    @FXML
    private TableColumn<Product, Number> quantityColumn;
    @FXML
    private TableColumn<Product, String> sizeColumn;
    @FXML
    private TableColumn<Product, Number> totalColumn;
    @FXML
    private TableColumn<Product,String> imageColumn;
    @FXML
    private TextField searchField;

    @FXML
    private TableView<Product> productTable;
    private ObservableList<Product> productList;
    public static Product selectedProduct = null;

    @FXML
    void adminlogoutEvent(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert Box");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to logout");
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-font-size: 18px;");
        ButtonType type = alert.showAndWait().orElse(new ButtonType("YES"));
        if (type == ButtonType.OK) {
            HelloApplication.changeScene("Login-Admin");
        }
        System.err.println("Register Scene");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getProductId()));
        nameColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getProductName()));
        typeColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getProductType()));
        sizeColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getProductSize()));
        colorColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getProductColor()));
        dateColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getAddDate()));
        detailsColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getProductDetails()));
        priceColumn.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getProductPrice()));
        quantityColumn.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getProductQuantity()));
        totalColumn.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getTotalPrice()));
        imageColumn.setCellValueFactory(c->new SimpleStringProperty(c.getValue().getImagePath()));
        productList = FXCollections.observableArrayList();
        getList();
        productTable.setItems(productList);
        double total = calculateTotal();
        totalPriceLabel.setText(String.format("%.2f", total));

    }
    @FXML
    void onMouseClick(MouseEvent event) {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            showProductDetails(selectedProduct);
        }
    }
    private void showProductDetails(Product product) {
        idLabel.setText(String.valueOf(product.getProductId()));
        nameLabel.setText(product.getProductName());
        typeLabel.setText(product.getProductType());
        sizeLabel.setText(product.getProductSize());
        colorLabel.setText(product.getProductColor());
        dateLabel.setText(product.getAddDate());
        detailsLabel.setText(product.getProductDetails());
        priceLabel.setText(String.format("%.2f", product.getProductPrice()));
        quantityLabel.setText(String.valueOf(product.getProductQuantity()));
        try {
            imageView.setImage(new javafx.scene.image.Image(product.getImagePath()));
        } catch (Exception e) {
            imageView.setImage(null);
        }
    }
    private double calculateTotal(){
        double sum = 0;
        for (Product product : productList) {
            sum=sum+product.getTotalPrice();
        }
        return sum;
    }
    public void getList() {
        try {
            productList.clear();
            Connection connection = ConnectionSingleton.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM PRODUCT;";
            ResultSet resultSet = statement.executeQuery(query);
            double totalAmount=0;
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String type = resultSet.getString("type");
                String size = resultSet.getString("size");
                String color = resultSet.getString("color");
                String date = resultSet.getString("date");
                String details = resultSet.getString("details");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");
                double total=resultSet.getDouble("total");
                String image=resultSet.getString("imagePath");
                Product product = new Product(id, name, type, size, color, date, details, price, quantity,total,image);
                productList.add(product);
                totalAmount=totalAmount+total;
            }
        }
        catch (SQLException ex) {
            System.err.println("Failed to connect to database");
            ex.printStackTrace();
        }
    }
    @FXML
    public void editProduct() {
        selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.setStyle("-fx-font-size: 18px;");
            alert.setContentText("Please select a product to update.");
            alert.showAndWait();
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Update Confirmation");
        confirm.setHeaderText(null);
        confirm.setContentText("Are you sure you want to Update this product?");
        DialogPane dialogPane = confirm.getDialogPane();
        dialogPane.setStyle("-fx-font-size: 18px;");
        if (confirm.showAndWait().get() == ButtonType.OK) {
            HelloApplication.changeScene("Add-Product");
        }
    }
    @FXML
    public void deleteProduct() {
        selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.setStyle("-fx-font-size: 18px;");
            alert.setHeaderText(null);
            alert.setContentText("Please select a product to delete.");
            alert.showAndWait();
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Delete Confirmation");
        confirm.setHeaderText(null);
        confirm.setContentText("Are you sure you want to delete this product?");
        DialogPane dialogPane = confirm.getDialogPane();
        dialogPane.setStyle("-fx-font-size: 18px;");
        if (confirm.showAndWait().get() == ButtonType.OK) {
            deleteFromDB(selectedProduct);
            getList();
            productTable.setItems(productList);
            clearLabels();
        }
    }
    public void deleteFromDB(Product product) {
        try {
            Connection connection = ConnectionSingleton.getConnection();
            Statement statement = connection.createStatement();
            String query = "DELETE FROM PRODUCT WHERE id=" + product.getProductId() + ";";
            statement.executeUpdate(query);
            System.out.println("Product deleted successfully.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Failed to delete product.");
        }
    }
    private void clearLabels() {
        idLabel.setText("");
        nameLabel.setText("");
        typeLabel.setText("");
        sizeLabel.setText("");
        colorLabel.setText("");
        dateLabel.setText("");
        detailsLabel.setText("");
        priceLabel.setText("");
        quantityLabel.setText("");
        imageView.setImage(null);
        totalPriceLabel.setText("");
    }
    //Serach Product//
    @FXML
    void searchEvent(KeyEvent event) {
        String search = searchField.getText().toLowerCase();
        ObservableList<Product> filteredList = FXCollections.observableArrayList();
        for (Product product : productList) {
            if (String.valueOf(product.getProductId()).contains(search) ||
                    product.getProductName().toLowerCase().contains(search)) {
                filteredList.add(product);
            }
        }
        productTable.setItems(filteredList);
    }

    @FXML
    void gotoAddProductEvent(MouseEvent event) {
       HelloApplication.changeScene("Add-Product");
    }
    @FXML
    void orderListEvent(MouseEvent event) {
      HelloApplication.changeScene("Order-List");
    }
    @FXML
    void sellAmountEvent(MouseEvent event) {
     HelloApplication.changeScene("Sell-Amount");
    }

    @FXML
    void homeEvent(ActionEvent event) {
      HelloApplication.changeScene("Dashboard");
    }
}
