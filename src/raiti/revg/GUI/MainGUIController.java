package raiti.revg.GUI;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
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
	private BorderPane mainPanel;
	
	/**
	 * 中央のスクロールパネル
	 */
	@FXML
	private ScrollPane center_Panel;
	
	/**
	 * エディターのパネル
	 */
	@FXML
	private Pane editor_Panel;
	
	/**
	 * Closeボタン
	 */
	@FXML
	private MenuItem menu_File_Close;
	
	@FXML
	private Pane test_Panel;
	
	private Pane d_panel;
	
	
	private double dragAnchorX;
	private double dragAnchorY;
	
	/**
	 * GUIの初期化時に呼ばれます。
	 * @param location URLロケーション
	 * @param resources リソースバンドル
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		menu_File_Close.setOnAction(event -> {this.thisStage.close(); System.exit(0);});
		
	}
	
	/**
	 * GUIの初期化(Showが呼ばれた直後に呼ばれます。)
	 */
	public void postInit(){
		
	}
	
	
	/**
	 * このコントローラーを使用しているステージを設定します。
	 * @param stage ステージ
	 */
	public MainGUIController setThisStage(Stage stage){
		this.thisStage = stage;
		return this;
	}
	
}
