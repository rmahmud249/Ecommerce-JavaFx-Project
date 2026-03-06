package bd.edu.seu.ecommerce.controller;
import bd.edu.seu.ecommerce.singleton.ConnectionSingleton;
import bd.edu.seu.ecommerce.HelloApplication;
import bd.edu.seu.ecommerce.classes.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
public class AddProductController implements Initializable{
    @FXML
    private ColorPicker colorField;
    @FXML
    private DatePicker dateField;
    @FXML
    private TextArea detailsField;
    @FXML
    private ImageView imageField;
    @FXML
    private TextField nameField;
    @FXML
    private Slider priceField;
    @FXML
    private TextField productIdField;
    @FXML
    private Spinner<Integer> quantityField;
    @FXML
    private ComboBox<String> sizeField;
    @FXML
    private ChoiceBox<String> typeField;
    @FXML
    private Button addButton;
    @FXML
    private Button updateBtn;
    @FXML
    private Label confirmationLabel;
    @FXML
    private Label idErrorLabel;
    private String imagePath;
    public static Image showImages;
    @FXML
    void productaddEvent(ActionEvent event) {
        int productID = 0;
        if (productIdField.getText().isEmpty()){
            idErrorLabel.setText("Enter product id");
            return;
        } else{
            productID = Integer.parseInt(productIdField.getText());
            idErrorLabel.setText("");
        }
        if (productID <= 0){
            idErrorLabel.setText(" id should be greater than zero");
            return;
        } else {
            idErrorLabel.setText("");
        }
        String productName=nameField.getText();
        String productType=typeField.getValue();
        String productSize=sizeField.getValue();
        Color color=colorField.getValue();
        String productColor=String.valueOf(color).toUpperCase();
        LocalDate date = dateField.getValue();
        String addDate = String.valueOf(date);
        String productDetails=detailsField.getText();
        double productPrice = Double.parseDouble((String.format("%.0f", priceField.getValue())));
        int productQuantity=quantityField.getValue();
        Image image = imageField.getImage();
        showImages = image;
        if (imagePath == null || imagePath.isEmpty()) {
            confirmationLabel.setText("Please upload an image.");
            return;
        }
        //Error validation End//
        Product product=new Product(productID,productName,productType,productSize,productColor,addDate,productDetails,productPrice,productQuantity,productPrice*productQuantity,imagePath);
        insetProductDB(product);
        confirmationLabel.setText("Product successfully added to the database!");


    }
    //Product insert into Database//
    public void insetProductDB(Product product){
        try{
            Connection connection= ConnectionSingleton.getConnection();
            Statement statement=connection.createStatement();
            String query="INSERT INTO PRODUCT VALUES('"+product.getProductId()+"','"+product.getProductName()+"','"+product.getProductType()+"','"+product.getProductSize()+"','"+product.getProductColor()+"','"+product.getAddDate()+"','"+product.getProductDetails()+"','"+product.getProductPrice()+"','"+product.getProductQuantity()+"','"+product.getTotalPrice()+"','"+product.getImagePath()+"')";
            statement.executeUpdate(query);
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("Failed to connect to database");
        }
    }
    @FXML
    void udateEvent(ActionEvent event) {
        String productName=nameField.getText();
        String productType=typeField.getValue();
        String productSize=sizeField.getValue();
        Color color=colorField.getValue();
        String productColor=String.valueOf(color).toUpperCase();
        LocalDate date = dateField.getValue();
        String addDate = String.valueOf(date);
        String productDetails=detailsField.getText();
        double productPrice = Double.parseDouble((String.format("%.0f", priceField.getValue())));
        int productQuantity=quantityField.getValue();
        Image image = imageField.getImage();
        showImages = image;
        if (imagePath == null || imagePath.isEmpty()) {
            confirmationLabel.setText("Please upload an image.");
            return;
        }
        int productID = Integer.parseInt(productIdField.getText());
        Product product = new Product(productID, productName, productType, productSize, productColor, addDate,
                productDetails, productPrice, productQuantity, productPrice * productQuantity, imagePath);
        updateProductInDB(product);
        confirmationLabel.setText("Product successfully updated!");
    }
    public void updateProductInDB(Product product) {
        try {
            Connection connection = ConnectionSingleton.getConnection();
            Statement statement = connection.createStatement();
            String query = "UPDATE PRODUCT SET name='" + product.getProductName() + "', type='" + product.getProductType() + "', size='" + product.getProductSize() + "', color='" + product.getProductColor() + "', date='" + product.getAddDate() + "', details='" + product.getProductDetails() + "', price='" + product.getProductPrice() + "', quantity='" + product.getProductQuantity() + "', total='" + product.getTotalPrice() + "', imagePath='" + product.getImagePath() + "' WHERE id=" + product.getProductId();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to update product");
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String>typeList= FXCollections.observableArrayList();
        typeList.add("Clothing");
        typeList.add("Electronics ");
        typeList.add("Books and Stationery");
        typeList.add("Sports Equipment");
        typeList.add("Toys and Games");
        typeField.setItems(typeList);

        ObservableList<String>sizeList= FXCollections.observableArrayList();
        sizeList.add("M");
        sizeList.add("L");
        sizeList.add("XL");
        sizeList.add("XXL");
        sizeList.add("XXXL");
        sizeField.setItems(sizeList);

        SpinnerValueFactory<Integer>valueFactory=new SpinnerValueFactory.IntegerSpinnerValueFactory(0,500);
        quantityField.setValueFactory(valueFactory);

         updateBtn.setVisible(false);
        if (AdminController.selectedProduct!=null){
            Product product = AdminController.selectedProduct;
            productIdField.setText(String.valueOf(product.getProductId()));
            productIdField.setDisable(true);
            nameField.setText(product.getProductName());
            typeField.setValue(product.getProductType());
            sizeField.setValue(product.getProductSize());
            colorField.setValue(Color.web(product.getProductColor()));
            dateField.setValue(LocalDate.parse(product.getAddDate()));
            detailsField.setText(product.getProductDetails());
            priceField.setValue(product.getProductPrice());
            quantityField.getValueFactory().setValue(product.getProductQuantity());
            imagePath = product.getImagePath();
            imageField.setImage(new Image(imagePath));
            productIdField.setDisable(true);
            addButton.setVisible(false);
            updateBtn.setVisible(true);
        }

    }
    //Images Upload///
    @FXML
    void uploadEvent(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.bmp"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            Image selectedImage = new Image(selectedFile.toURI().toString());
            imageField.setImage(selectedImage);
            imagePath = selectedFile.toURI().toString();
        }
    }
    @FXML
    void dashboardEvent(ActionEvent event) {
        HelloApplication.changeScene("Admin");
    }
}
