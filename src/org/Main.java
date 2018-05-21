package org;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.teemo.HextechUI;

public class Main extends Application {

    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
       this.stage = primaryStage;

       primaryStage.setScene(new Scene(new HextechUI(this), 400,600));
       primaryStage.setResizable(false);
       primaryStage.setTitle("Ping Hextech | Report JG");
       primaryStage.show();
    }


    public Stage getStage() {
        return stage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
