package raiti.revg;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import raiti.revg.GUI.MainGUIController;
import raiti.revg.api.AppVersion;
import raiti.revg.api.Version;

/**
 * REVG の起動クラス。
 *
 * @author Raiti-chan
 * @version 1.0.0
 */
@Version("1.0.0 alpha")
public class MainApp extends Application implements AppVersion {
	
	/**
	 * アプリケーションのインスタンス
	 */
	private static MainApp instance;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		instance = this;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("GUI/mainGUI.fxml"));
		Parent root = loader.load();
		((MainGUIController)loader.getController()).setThisStage(primaryStage);
		primaryStage.setTitle("Regular Expression Visual Generator Version-"+this.getVersion());
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
		((MainGUIController)loader.getController()).postInit();
	}
	
	
	/**
	 * 起動メソッド
	 * @param args パラメーター
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * このアプリケーションのインスタンスを取得します。
	 * @return アプリケーションのインスタンス
	 */
	public static MainApp getInstance() {
		return instance;
	}
	
	
	
}
