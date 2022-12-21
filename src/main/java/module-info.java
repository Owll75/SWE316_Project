module swe316_hw {
    requires javafx.controls;
    requires javafx.fxml;

    opens swe316_hw to javafx.fxml;
    exports swe316_hw;
}
