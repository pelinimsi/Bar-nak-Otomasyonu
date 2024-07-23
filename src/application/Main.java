package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
        Parent root = loader.load();
        
        LoginPage loginController = loader.getController();
        loginController.setStage(primaryStage);
        
        Scene scene = new Scene(root, 1280, 720);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("BarýnakAPP");
        
        Animal icon = new Dog(-1, "", "", "https://icons.iconarchive.com/icons/iconarchive/cute-animal/512/Cute-Dog-icon.png", 0, true);
        primaryStage.getIcons().add(icon.get_image());
        
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
