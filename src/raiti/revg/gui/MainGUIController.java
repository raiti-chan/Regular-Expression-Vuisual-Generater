package raiti.revg.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import raiti.revg.main.MainFX;

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
	public Stage thisStage = null;
	
	/**
	 * エディターモード
	 */
	private MODE EditMode = MODE.NONE;
	
	
	/**
	 * エディターのパネル
	 */
	@FXML
	public Pane editor_Panel;
	
	@FXML
	private BorderPane mainPanel;
	
//==メニューアイテム====================================================================================================
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

//==ツールバー==========================================================================================================
	@FXML
	private Button ADD;
	@FXML
	private Button SELECT;
	
	
	/**
	 * GUIの初期化時に呼ばれます。
	 * @param location URLロケーション
	 * @param resources リソースバンドル
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		MainFX fxs = new MainFX(this);
		
		//==メニューアイテム============================================================================================
			//ファイル
		menu_File_NewFile.setOnAction(event -> {/*TODO:  新規作成 */});
		menu_File_OpenFile.setOnAction(event -> {/*TODO: ファイルを開く */});
		menu_File_SaveFile.setOnAction(event -> {/*TODO: 保存 */});
		menu_File_SaveNewName.setOnAction(event -> {/*TODO: 名前を付けて保存 */});
		menu_File_Exit.setOnAction(event -> {this.thisStage.close(); System.exit(0);});
			//編集
		menu_Edit_Size.setOnAction(fxs::EditPanelResize);
			//ヘルプ
		menu_Help_About.setOnAction(event -> {/*TODO: について */});
		
		//==ツールバー==================================================================================================
		ADD.setOnAction(event -> fxs.setEditMode(MODE.CREATE_NODE));
		SELECT.setOnAction(event -> fxs.setEditMode(MODE.SELECT_NODE));
		
		//==メインパネル================================================================================================
		this.mainPanel.setOnKeyPressed(fxs::editor_Pane_KeyPressed);
		
		//==エディターパネル============================================================================================
		this.editor_Panel.setOnMousePressed(fxs::editor_Panel_MousePressed);
		this.editor_Panel.setOnMouseDragged(fxs::editor_Panel_MouseDragged);
		this.editor_Panel.setOnMouseReleased(fxs::editor_Panel_MouseReleased);
		
		
		
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
