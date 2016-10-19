package raiti.revg.GUI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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
	
//======================================================================================================メニューアイテム
	//ファイル
	@FXML
	private MenuItem menu_File_NewFile;
	@FXML
	private MenuItem menu_File_OpenFile;
	@FXML
	private MenuItem menu_File_SaveFile;
	@FXML
	private MenuItem menu_File_SaveNewName;
	@FXML
	private MenuItem menu_File_Exit;
	//編集
	@FXML
	private MenuItem menu_Edit_Size;
	//ヘルプ
	@FXML
	private MenuItem menu_Help_About;
	
	
	
	
	@FXML
	private Pane test_Panel;
	
	
	/**
	 * GUIの初期化時に呼ばれます。
	 * @param location URLロケーション
	 * @param resources リソースバンドル
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		menu_File_Exit.setOnAction(event -> {this.thisStage.close(); System.exit(0);});
		
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
