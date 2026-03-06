package bd.edu.seu.ecommerce.controller;
import bd.edu.seu.ecommerce.HelloApplication;
import bd.edu.seu.ecommerce.classes.Product;
import bd.edu.seu.ecommerce.singleton.ConnectionSingleton;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AddcardController implements Initializable{

    @FXML
    private TableView<Product> cardTable;

    @FXML
    private TableColumn<Product,String> colorColumn;

    @FXML
    private TableColumn<Product,String> detailsColumn;

    @FXML
    private TableColumn<Product,String> nameColumn;

    @FXML
    private TableColumn<Product,Number> priceColumn;

    @FXML
    private Label priceLabel;

    @FXML
    private TableColumn<Product,String> sizeColumn;

    @FXML
    private TableColumn<Product,String> typeColumn;
    private ObservableList<Product>cartList;
    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       nameColumn.setCellValueFactory(c->new SimpleStringProperty(c.getValue().getProductName()));
       typeColumn.setCellValueFactory(c->new SimpleStringProperty(c.getValue().getProductType()));
       sizeColumn.setCellValueFactory(c->new SimpleStringProperty(c.getValue().getProductSize()));
       colorColumn.setCellValueFactory(c->new SimpleStringProperty(c.getValue().getProductColor()));
       detailsColumn.setCellValueFactory(c->new SimpleStringProperty(c.getValue().getProductDetails()));
       priceColumn.setCellValueFactory(c->new SimpleDoubleProperty(c.getValue().getProductPrice()));
       cardTable.setItems(DashboardController.cardList);
        updateTotalPrice();

    }
    private void updateTotalPrice() {
        double total = 0;
        for (Product p : cardTable.getItems()) {
            total=total+p.getProductPrice();
        }
        priceLabel.setText(String.format("%.2f", total));
    }
    @FXML
    void removeProduct(MouseEvent event) {
        Product selected = cardTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Alert alert=new Alert(Alert.AlertType.WARNING);
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
        confirm.setContentText("Are you sure you want to remove this product?");
        DialogPane dialogPane = confirm.getDialogPane();
        dialogPane.setStyle("-fx-font-size: 18px;");
        if (confirm.showAndWait().get() == ButtonType.OK) {
            DashboardController.cardList.remove(selected);
            updateTotalPrice();
        }
    }
    @FXML
    void dashboardEvent(ActionEvent event) {
        HelloApplication.changeScene("Dashboard");
    }

}

