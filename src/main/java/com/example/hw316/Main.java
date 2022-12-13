package com.example.hw316;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Scene scene = new Scene(new ExcelView(), 750, 500);

        stage.setTitle("Excel Management");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }

}
