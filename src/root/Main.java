package root;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Window w = new Window();
        primaryStage.setScene(w.getScene());
        primaryStage.show();
    }

    @Override
    public void stop() {
        System.out.println("Application closed");
    }
}