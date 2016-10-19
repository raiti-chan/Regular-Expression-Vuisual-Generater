package raiti.revg;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import raiti.revg.api.AppVersion;
import raiti.revg.api.Version;

/**
 * REVG の起動クラス。
 * @author Raiti-chan
 * @version 1.0.0
 */
@Version("1.0.0")
public class MainApp extends Application implements AppVersion{
    
    private static MainApp instance;
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        instance = this;
        Parent root = FXMLLoader.load(getClass().getResource("GUI/mainGUI.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
    
    public static MainApp getInstance(){
        return instance;
    }
    
}
