module com.example.project316 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.poi.poi;
    


    opens com.example.hw316 to javafx.fxml;
    exports com.example.hw316;
}
