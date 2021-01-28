module com.sustainabledev {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.sustainabledev to javafx.fxml;
    exports com.sustainabledev;
}