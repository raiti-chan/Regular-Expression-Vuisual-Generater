package raiti.revg.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import raiti.revg.gui.Dialog.SizeSetDialogController;

import java.io.IOException;
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
	 * エディターのパネル
	 */
	@FXML
	private Pane editor_Panel;
	
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
	
	
	
	/**
	 * GUIの初期化時に呼ばれます。
	 * @param location URLロケーション
	 * @param resources リソースバンドル
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//==メニューアイテム============================================================================================
			//ファイル
		menu_File_NewFile.setOnAction(event -> {/*TODO:  新規作成 */});
		menu_File_OpenFile.setOnAction(event -> {/*TODO: ファイルを開く */});
		menu_File_SaveFile.setOnAction(event -> {/*TODO: 保存 */});
		menu_File_SaveNewName.setOnAction(event -> {/*TODO: 名前を付けて保存 */});
		menu_File_Exit.setOnAction(event -> {this.thisStage.close(); System.exit(0);});
			//編集
		menu_Edit_Size.setOnAction(event -> {/*TODO: サイズ */
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("Dialog/SizeSetDialog.fxml"));
				Parent root = loader.load();
				SizeSetDialogController controller = loader.getController();
				controller.setXandY((int)this.editor_Panel.getWidth(),(int)this.editor_Panel.getHeight());
				Scene scene = new Scene(root);
				Stage stage = new Stage(StageStyle.UTILITY);
				stage.setScene(scene);
				stage.initOwner(this.thisStage.getScene().getWindow());
				stage.initModality(Modality.WINDOW_MODAL);
				stage.setResizable(false);
				stage.setTitle("サイズの変更");
				stage.showAndWait();
				if(controller.isOk()){
					this.editor_Panel.setPrefSize(controller.getX(),controller.getY());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		});
			//ヘルプ
		menu_Help_About.setOnAction(event -> {/*TODO: について */});
		
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
