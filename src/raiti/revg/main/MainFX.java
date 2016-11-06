package raiti.revg.main;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import raiti.revg.api.Double2DPoint;
import raiti.revg.gui.Dialog.SizeSetDialogController;
import raiti.revg.gui.MODE;
import raiti.revg.gui.MainGUIController;
import raiti.revg.gui.control.NodeBase;
import raiti.revg.gui.control.TextNode;

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
		this.setSelectNode(null);
		this.editMode = editMode;
	}
	
	/**
	 * GUIのコントローラー(FXNodeの参照用)
	 */
	private static MainGUIController mainGUIController;
	
	/**
	 * コンストラクタ
	 *
	 * @param controller FXNodeの参照インスタンス
	 */
	public MainFX(MainGUIController controller) {
		mainGUIController = controller;
		//テキスト入力フィールドの初期化
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
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/Dialog/SizeSetDialog.fxml"));
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
	private final ArrayList<NodeBase> selectNode = new ArrayList<>();
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
				createMode_EPMP(event);
				break;
			case SELECT_NODE://選択モード
				selectMode_EPMP(event);
				break;
			
		}
	}
	
	private void createMode_EPMP(MouseEvent event) {
		if (event.getButton() != MouseButton.PRIMARY) return;
		NodeBase ap = new TextNode();
		ap.setLayoutX(editor_Panel_MousePressedPoint.X);
		ap.setLayoutY(editor_Panel_MousePressedPoint.Y);
		mainGUIController.editor_Panel.getChildren().add(ap);
		setSelectNode(ap);
		ap.creating();
		
	}
	
	private void selectMode_EPMP(MouseEvent event) {
		if (event.getButton() != MouseButton.PRIMARY) return;
		Node node = (Node) event.getTarget();
		while (!(node instanceof NodeBase)) {
			if (node == mainGUIController.editor_Panel) {
				setSelectNode(null);
				return;
			}
			node = node.getParent();
		}
		if (event.isShiftDown() && selectNode.size() > 0) {
			if (selectNode.indexOf(node) == -1) {
				addSelectNode((NodeBase) node);
			} else {
				removeSelectNode((NodeBase) node);
			}
			
		} else setSelectNode((NodeBase) node);
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
			case SELECT_NODE://選択モード
				break;
		}
		
	}
	
	/**
	 * 作成モードドラッグ処理
	 *
	 * @param event Mouseイベント
	 */
	private void createMode_EPMD(MouseEvent event) {
		if (event.getButton() != MouseButton.PRIMARY) return;
		double difference;
		NodeBase selectNode = this.selectNode.get(0);
		//X座標の計算など
		if ((difference = event.getX() - editor_Panel_MousePressedPoint.X) < 0) {
			if (event.getX() < 0) {
				selectNode.setLayoutX(0);
			} else {
				selectNode.setLayoutX(event.getX());
				selectNode.setPrefWidth(-difference);
			}
		} else if (event.getX() <= mainGUIController.editor_Panel.getWidth()) {
			selectNode.setLayoutX(editor_Panel_MousePressedPoint.X);
			selectNode.setPrefWidth(difference);
		} else {
			selectNode.setPrefWidth(mainGUIController.editor_Panel.getWidth() - selectNode.getLayoutX());
		}
		
		//Y座標の計算
		if ((difference = event.getY() - editor_Panel_MousePressedPoint.Y) < 0) {
			if (event.getY() < 0) {
				selectNode.setLayoutY(0);
			} else {
				selectNode.setLayoutY(event.getY());
				selectNode.setPrefHeight(-difference);
			}
		} else if (event.getY() <= mainGUIController.editor_Panel.getHeight()) {
			selectNode.setLayoutY(editor_Panel_MousePressedPoint.Y);
			selectNode.setPrefHeight(difference);
		} else {
			selectNode.setPrefHeight(mainGUIController.editor_Panel.getHeight() - selectNode.getLayoutY());
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
			case CREATE_NODE://新規作成モード
				createMode_EPMR(event);
				break;
			case SELECT_NODE://選択モード
		}
	}
	
	@SuppressWarnings("UnusedParameters")
	private void createMode_EPMR(MouseEvent event) {
		if (event.getButton() != MouseButton.PRIMARY) return;
		if (!isDragged) {
			mainGUIController.editor_Panel.getChildren().remove(selectNode.get(0));
			return;
		}
		if (selectNode.get(0) != null)selectNode.get(0).unSelect();
		
	}
	
	
	//メインパネル==============================================================================================================
	
	/**
	 * ウィンドウにフォーカスがある状態でキーが押されたときの処理
	 *
	 * @param event イベント
	 */
	public void editor_Pane_KeyPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.DELETE) {
			mainGUIController.editor_Panel.getChildren().removeAll(selectNode);
			selectNode.clear();
		}
	}
	//==================================================================================================================
	
	/**
	 * 選択ノードを設定します
	 *
	 * @param node 選択するノード
	 */
	private void setSelectNode(NodeBase node) {
		if (this.selectNode.size() != 0) {
			this.selectNode.forEach(nodeBase -> {
				if (nodeBase != null) nodeBase.unSelect();
			});
			this.selectNode.clear();
		}
		
		if (node != null) {
			this.selectNode.add(node);
			node.onSelect();
		}
		
		
	}
	
	/**
	 * 選択中ノードを追加します
	 *
	 * @param node 追加するノード
	 */
	private void addSelectNode(NodeBase node) {
		if (this.selectNode.size() == 1) {
			if (selectNode.get(0) != null) this.selectNode.get(0).manySelect();
		}
		if (node != null) node.manySelect();
		this.selectNode.add(node);
	}
	
	/**
	 * 指定したノードの選択を解除します
	 * @param node 解除するノード
	 */
	private void removeSelectNode(NodeBase node) {
		if (node != null) node.unSelect();
		this.selectNode.remove(node);
		if (this.selectNode.size() == 1) {
			selectNode.get(0).onSelect();
		}
	}
	
	
}
