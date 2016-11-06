package raiti.revg.gui.control;

import javafx.scene.Cursor;
import javafx.scene.control.Control;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import raiti.revg.api.Double2DPoint;
import raiti.revg.gui.MODE;

/**
 * Created by Raiti-chan on 2016/11/05.
 *
 * @author Raiti-chan
 */
@SuppressWarnings("WeakerAccess")
public abstract class NodeBase extends AnchorPane {
	
	public static final String POINT_POSITION_KEY = "PointPosition";
	
	protected BorderPane nodePanel = new BorderPane();
	
	protected Pane dragPoints[] = new Pane[DragPointPosition.values().length];
	
	protected NodeBase() {
		setBottomAnchor(nodePanel, 1.0);
		setTopAnchor(nodePanel, 1.0);
		setLeftAnchor(nodePanel, 1.0);
		setRightAnchor(nodePanel, 1.0);
		this.getChildren().add(nodePanel);
		
		for (DragPointPosition position : DragPointPosition.values()) {
			Pane pane = new Pane();
			switch (position) {
				case TopAndLeft:
				case TopAndRight:
					setTopAnchor(pane, 0.0);
					break;
				case BottomAndLeft:
				case BottomAndRight:
					setBottomAnchor(pane, 0.0);
					break;
			}
			switch (position) {
				case TopAndLeft:
				case BottomAndLeft:
					setLeftAnchor(pane, 0.0);
					break;
				case TopAndRight:
				case BottomAndRight:
					setRightAnchor(pane, 0.0);
					break;
			}
			pane.getProperties().put(POINT_POSITION_KEY, position);
			pane.setOnMousePressed(this::DragPointPressed);
			pane.setOnMouseDragged(this::DragPointDragged);
			pane.setOnMouseReleased(this::DragPointReleased);
			pane.setCursor(position.ordinal() == 0 ? Cursor.NW_RESIZE : position.ordinal() == 1 ? Cursor.NE_RESIZE : position.ordinal() == 2 ? Cursor.SW_RESIZE : Cursor.SE_RESIZE);
			pane.setPrefHeight(3);
			pane.setPrefWidth(3);
			pane.setStyle("-fx-background-color: #000000");
			dragPoints[position.ordinal()] = pane;
			
		}
		
		this.getChildren().addAll(dragPoints);
	}
	
	public void unSelect() {
		this.nodePanel.setStyle("-fx-border-color: #000000; -fx-border-style: solid");
		for (Pane pane : dragPoints) {
			pane.setVisible(false);
		}
	}
	
	public void creating() {
		this.nodePanel.setStyle("-fx-border-color: #000000; -fx-border-style: dashed");
		for (Pane pane : dragPoints) {
			pane.setVisible(false);
		}
	}
	
	public void onSelect() {
		this.nodePanel.setStyle("-fx-border-color: #000000; -fx-border-style: dashed");
		this.toFront();
		for (Pane pane : dragPoints) {
			pane.setVisible(true);
		}
	}
	
	public void manySelect() {
		this.nodePanel.setStyle("-fx-border-color: #000000; -fx-border-style: dashed");
		this.toFront();
		for (Pane pane : dragPoints) {
			pane.setVisible(false);
		}
	}
	
	/**
	 * ノードのレイアウト座標、SIZEが決まり、固定されたときに処理されます。<br>
	 * つまりクリエイトモードでMouseがドラッグされ離された際に処理されます。
	 */
	public abstract void created();
	
	private static final Double2DPoint pressedPoint = new Double2DPoint();
	private static final Double2DPoint pressedSize = new Double2DPoint();
	private static final Double2DPoint pressedLayout = new Double2DPoint();
	
	private void DragPointPressed(MouseEvent event) {
		if (event.getButton() != MouseButton.PRIMARY) return;
		Pane point = (Pane) event.getSource();
		pressedSize.setXandY(this.getWidth(), this.getHeight());
		pressedLayout.setXandY(this.getLayoutX(), this.getLayoutY());
		switch ((DragPointPosition) point.getProperties().get(POINT_POSITION_KEY)) {
			case TopAndLeft:
				pressedPoint.X = this.getBoundsInParent().getMinX();
				pressedPoint.Y = this.getBoundsInParent().getMinY();
				break;
			case TopAndRight:
				pressedPoint.X = this.getBoundsInParent().getMaxX();
				pressedPoint.Y = this.getBoundsInParent().getMinY();
				break;
			case BottomAndLeft:
				pressedPoint.X = this.getBoundsInParent().getMinY();
				pressedPoint.Y = this.getBoundsInParent().getMaxY();
				break;
			case BottomAndRight:
				pressedPoint.X = this.getBoundsInParent().getMaxX();
				pressedPoint.Y = this.getBoundsInParent().getMaxY();
				break;
		}
	}
	
	private void DragPointDragged(MouseEvent event) {
		if (event.getButton() != MouseButton.PRIMARY) return;
		Pane point = (Pane) event.getSource();
		double moveX, moveY;
		switch ((DragPointPosition) point.getProperties().get(POINT_POSITION_KEY)) {
			case TopAndLeft:
				moveX = this.getBoundsInParent().getMinX() + event.getX() - pressedPoint.X;
				moveY = this.getBoundsInParent().getMinY() + event.getY() - pressedPoint.Y;
				if (pressedPoint.X + moveX < 0) this.setLayoutX(0);
				else {
					this.setLayoutX(pressedPoint.X + moveX);
					this.setPrefWidth(pressedSize.X - moveX);
				}
				if (pressedPoint.Y + moveY < 0) this.setLayoutY(0);
				else {
					this.setLayoutY(pressedPoint.Y + moveY);
					this.setPrefHeight(pressedSize.Y - moveY);
				}
				
				
				break;
			case TopAndRight:
				moveX = this.getBoundsInParent().getMaxX() + event.getX() - pressedPoint.X;
				moveY = this.getBoundsInParent().getMinY() + event.getY() - pressedPoint.Y;
				if (this.getBoundsInParent().getMaxX() + event.getX() <= ((Pane) this.getParent()).getWidth())
					this.setPrefWidth(pressedSize.X + moveX);
				else this.setPrefWidth(((Pane) this.getParent()).getWidth() - this.getLayoutX());
				if (pressedPoint.Y + moveY < 0) this.setLayoutY(0);
				else {
					this.setLayoutY(pressedPoint.Y + moveY);
					this.setPrefHeight(pressedSize.Y - moveY);
				}
				break;
			case BottomAndLeft:
				moveX = this.getBoundsInParent().getMinX() + event.getX() - pressedPoint.X;
				moveY = this.getBoundsInParent().getMaxY() + event.getY() - pressedPoint.Y;
				if (pressedPoint.X + moveX < 0) this.setLayoutX(0);
				else {
					this.setLayoutX(pressedPoint.X + moveX);
					this.setPrefWidth(pressedSize.X - moveX);
				}
				if (this.getBoundsInParent().getMaxY() + event.getY() <= ((Pane) this.getParent()).getHeight())
					this.setPrefHeight(pressedSize.Y + moveY);
				else this.setPrefHeight(((Pane) this.getParent()).getHeight() - this.getLayoutY());
				break;
			case BottomAndRight:
				moveX = this.getBoundsInParent().getMaxX() + event.getX() - pressedPoint.X;
				moveY = this.getBoundsInParent().getMaxY() + event.getY() - pressedPoint.Y;
				if (this.getBoundsInParent().getMaxX() + event.getX() <= ((Pane) this.getParent()).getWidth())
					this.setPrefWidth(pressedSize.X + moveX);
				else this.setPrefWidth(((Pane) this.getParent()).getWidth() - this.getLayoutX());
				if (this.getBoundsInParent().getMaxY() + event.getY() <= ((Pane) this.getParent()).getHeight())
					this.setPrefHeight(pressedSize.Y + moveY);
				else this.setPrefHeight(((Pane) this.getParent()).getHeight() - this.getLayoutY());
				break;
		}
	}
	
	private void DragPointReleased(MouseEvent event) {
		if (event.getButton() != MouseButton.PRIMARY) return;
		Pane point = (Pane) event.getSource();
		switch ((DragPointPosition) point.getProperties().get(POINT_POSITION_KEY)) {
			case TopAndLeft:
				break;
			case TopAndRight:
				break;
			case BottomAndLeft:
				break;
			case BottomAndRight:
				break;
		}
	}
	
	/**
	 * エディターパネルがなんかしらでclickされた場合に処理されます
	 *
	 * @param event エディターパネルのEvent
	 * @param mode  エディターのモード
	 */
	public void editorPanelClickEvent(MouseEvent event, MODE mode) {
	}
	
	/**
	 * 位置の相対インデックス定数
	 */
	public enum DragPointPosition {
		/**
		 * 左上
		 */
		TopAndLeft,
		/**
		 * 右上
		 */
		TopAndRight,
		/**
		 * 左下
		 */
		BottomAndLeft,
		/**
		 * 右下
		 */
		BottomAndRight
		
		
	}
	
}
