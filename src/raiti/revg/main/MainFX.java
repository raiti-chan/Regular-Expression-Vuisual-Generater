package raiti.revg.main;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import raiti.revg.api.Double2DPoint;
import raiti.revg.gui.Dialog.SizeSetDialogController;
import raiti.revg.gui.MODE;
import raiti.revg.gui.MainGUIController;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Raiti-chan on 2016/11/03.
 * GUIのメイン処理群
 *
 * @author Raiti-chan
 */
public class MainFX {
	
	/**
	 * エディターのマウスカーソルのモード
	 */
	private MODE editMode = MODE.NONE;
	
	public void setEditMode(MODE editMode) {
		this.editMode = editMode;
	}
	
	/**
	 * GUIのコントローラー(FXNodeの参照用)
	 */
	private MainGUIController mainGUIController;
	
	/**
	 * コンストラクタ
	 *
	 * @param controller FXNodeの参照インスタンス
	 */
	public MainFX(MainGUIController controller) {
		this.mainGUIController = controller;
	}
	
	/**
	 * エディターパネルのリサイズ
	 *
	 * @param event ボタンイベント
	 */
	@SuppressWarnings("UnusedParameters")
	public void EditPanelResize(ActionEvent event) {
		try {
			//FXMLのロード
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("raiti/revg/gui/Dialog/SizeSetDialog.fxml"));
			Parent root = loader.load();
			SizeSetDialogController controller = loader.getController();
			controller.setXandY((int) mainGUIController.editor_Panel.getWidth(), (int) mainGUIController.editor_Panel.getHeight());
			Scene scene = new Scene(root);
			Stage stage = new Stage(StageStyle.UTILITY);
			stage.setScene(scene);
			stage.initOwner(mainGUIController.thisStage.getScene().getWindow());
			stage.initModality(Modality.WINDOW_MODAL);
			stage.setResizable(false);
			stage.setTitle("サイズの変更");
			//表示して閉じられるまで待機
			stage.showAndWait();
			if (controller.isOk()) {//OKが押されたらリサイズ
				ObservableList<Node> nodes = mainGUIController.editor_Panel.getChildren();
				ArrayList<Node> delNode = new ArrayList<>();
				//リサイズ時にはみ出るnodeの取得
				nodes.stream().filter(node -> node instanceof Region).forEach(node -> {
					if (node.getLayoutX() + ((Region) node).getWidth() > controller.getX()) {
						delNode.add(node);
					} else if (node.getLayoutY() + ((Region) node).getHeight() > controller.getY()) {
						delNode.add(node);
					}
				});
				
				if (delNode.size() > 0) {
					//消去していいかの確認
					Alert alert = new Alert(Alert.AlertType.WARNING, "警告", ButtonType.OK, ButtonType.CANCEL);
					alert.setHeaderText("");
					alert.setContentText("指定されたサイズからはみ出ているノードは消去されます\r\nよろしいですか?");
					if (alert.showAndWait().get() == ButtonType.CANCEL) return;
					//はみ出たnodeの消去
					nodes.removeAll(delNode);
				}
				
				mainGUIController.editor_Panel.setPrefSize(controller.getX(), controller.getY());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	//エディターパネル==================================================================================================
	/**
	 * {@link MainFX#editor_Panel_MousePressed(MouseEvent)}でclickされたpane上の座標
	 */
	private Double2DPoint editor_Panel_MousePressedPoint = new Double2DPoint();
	/**
	 * 作成中のパネル
	 */
	private BorderPane createPanel = null;
	private boolean isDragged = false;
	
	/**
	 * {@link MainGUIController#editor_Panel}が押された場合に処理されます
	 *
	 * @param event 発生したイベント
	 */
	public void editor_Panel_MousePressed(MouseEvent event) {
		editor_Panel_MousePressedPoint.setXandY(event.getX(), event.getY());
		isDragged = false;
		switch (this.editMode) {
			case NONE:
				break;
			case CREATE_NODE://新規要素作成モード
				createMode_EPMP();
				break;
		}
	}
	
	private void createMode_EPMP() {
		BorderPane ap = new BorderPane();
		ap.setLayoutX(editor_Panel_MousePressedPoint.X);
		ap.setLayoutY(editor_Panel_MousePressedPoint.Y);
		ap.setStyle("-fx-border-color: #000000; -fx-border-style: dashed");
		mainGUIController.editor_Panel.getChildren().add(ap);
		createPanel = ap;
	}
	
	/**
	 * {@link MainGUIController#editor_Panel}上でドラッグされた場合に処理されます
	 *
	 * @param event 発生したイベント
	 */
	public void editor_Panel_MouseDragged(MouseEvent event) {
		isDragged = true;
		switch (this.editMode) {
			case NONE:
				break;
			case CREATE_NODE://新規作成モード
				createMode_EPMD(event);
				break;
		}
		
	}
	
	private void createMode_EPMD(MouseEvent event) {
		double difference;
		//X座標の計算など
		if ((difference = event.getX() - editor_Panel_MousePressedPoint.X) < 0) {
			createPanel.setLayoutX(event.getX());
			createPanel.setPrefWidth(-difference);
		} else if (event.getX() <= mainGUIController.editor_Panel.getWidth()) {
			createPanel.setLayoutX(editor_Panel_MousePressedPoint.X);
			createPanel.setPrefWidth(difference);
		} else {
			createPanel.setPrefWidth(mainGUIController.editor_Panel.getWidth() - createPanel.getLayoutX());
		}
		
		//Y座標の計算
		if ((difference = event.getY() - editor_Panel_MousePressedPoint.Y) < 0) {
			createPanel.setLayoutY(event.getY());
			createPanel.setPrefHeight(-difference);
		} else if (event.getY() <= mainGUIController.editor_Panel.getHeight()) {
			createPanel.setLayoutY(editor_Panel_MousePressedPoint.Y);
			createPanel.setPrefHeight(difference);
		} else {
			createPanel.setPrefHeight(mainGUIController.editor_Panel.getHeight() - createPanel.getLayoutY());
		}
	}
	
	/**
	 * {@link MainGUIController#editor_Panel}上でボタンが離された場合に処理されます
	 *
	 * @param event 発生したイベント
	 */
	@SuppressWarnings("UnusedParameters")
	public void editor_Panel_MouseReleased(MouseEvent event) {
		switch (this.editMode) {
			case NONE:
				break;
			case CREATE_NODE:
				createMode_EPMR(event);
				break;
		}
	}
	
	private void createMode_EPMR(MouseEvent event) {
		if (!isDragged){
			mainGUIController.editor_Panel.getChildren().removeAll(createPanel);
			return;
		}
		
		TextField field = new TextField();
		field.setOnAction(event1 -> {
			TextField eField = (TextField) event1.getSource();
			BorderPane borderPane = (BorderPane)eField.getParent();
			borderPane.setCenter(new Label(eField.getText()));
		});
		field.setStyle("-fx-alignment: center;");
		this.createPanel.setCenter(field);
		createPanel = null;
	}
	//==================================================================================================================
	
}
