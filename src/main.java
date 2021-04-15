import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(main.class.getResource("resources/fxml/main_page.fxml"));
        primaryStage.setTitle("Entegrasyon Paneli");
        primaryStage.setResizable(false);
        Scene scene = new Scene(root, 1024, 700);
        scene.getStylesheets().add("controllers/main.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
