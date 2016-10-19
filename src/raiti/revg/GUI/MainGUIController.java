package raiti.revg.GUI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import raiti.revg.MainApp;
import raiti.revg.api.Version;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * REVG のメインGUIのコントローラークラス
 * @author Raiti-chan
 * @since 1.0.0
 */
public class MainGUIController implements Initializable{
	
	/**
	 * メインウィンドウのステージ
	 */
	private Stage thisStage = null;
	
	/**
	 * メインパネル
	 */
	@FXML
	BorderPane mainPanel;
	
	/**
	 * GUIの初期化時に呼ばれます。
	 * @param location URLロケーション
	 * @param resources リソースバンドル
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	
	/**
	 * このコントローラーを使用しているステージを設定します。
	 * @param stage ステージ
	 */
	public void setThisStage(Stage stage){
		this.thisStage = stage;
	}
	
}
