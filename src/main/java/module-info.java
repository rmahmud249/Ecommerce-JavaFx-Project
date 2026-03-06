module bd.edu.seu.ecommerce {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires java.xml.crypto;


    opens bd.edu.seu.ecommerce to javafx.fxml;
    exports bd.edu.seu.ecommerce;
    exports bd.edu.seu.ecommerce.controller;
    opens bd.edu.seu.ecommerce.controller to javafx.fxml;
    exports bd.edu.seu.ecommerce.classes;
    opens bd.edu.seu.ecommerce.classes to javafx.fxml;
    exports bd.edu.seu.ecommerce.singleton;
    opens bd.edu.seu.ecommerce.singleton to javafx.fxml;
}