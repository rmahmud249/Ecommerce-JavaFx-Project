package bd.edu.seu.ecommerce.controller;
import bd.edu.seu.ecommerce.HelloApplication;
import bd.edu.seu.ecommerce.singleton.ConnectionSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
public class SellController implements Initializable {
    @FXML
    private LineChart<String, Number> lineChart;
    @FXML
    void gotoAdminEvent(ActionEvent event) {
        HelloApplication.changeScene("Admin");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadProfitLineChart();
    }
    private void loadProfitLineChart() {
        Map<String, Double> profitMap = new HashMap<>();
        try {
            Connection connection = ConnectionSingleton.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT product_name, total FROM ORDERLIST;";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                String productName = rs.getString("product_name");
                double total = rs.getDouble("total");
                profitMap.put(productName, profitMap.getOrDefault(productName, 0.0) + total);
            }
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Total Profit");
            for (Map.Entry<String, Double> entry : profitMap.entrySet()) {
                series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
            }
            lineChart.getData().clear();
            lineChart.getData().add(series);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading profit chart");
        }
    }
}
