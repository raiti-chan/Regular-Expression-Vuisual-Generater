package raiti.revg.gui.control;

import javafx.scene.Cursor;
import javafx.scene.control.Control;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import raiti.revg.api.Double2DPoint;

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
	
	protected NodeBase(Control control) {
		nodePanel.setCenter(control);
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
	
	private static final Double2DPoint pressedPoint = new Double2DPoint();
	private static final Double2DPoint pressedSize = new Double2DPoint();
	private static final Double2DPoint pressedLayout = new Double2DPoint();
	
	private void DragPointPressed(MouseEvent event) {
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
		Pane point = (Pane) event.getSource();
		double moveX, moveY;
		switch ((DragPointPosition) point.getProperties().get(POINT_POSITION_KEY)) {
			case TopAndLeft:
				moveX = this.getBoundsInParent().getMinX() + event.getX() - pressedPoint.X;
				moveY = this.getBoundsInParent().getMinY() + event.getY() - pressedPoint.Y;
				this.setLayoutX(pressedPoint.X + moveX);
				this.setLayoutY(pressedPoint.Y + moveY);
				this.setPrefWidth(pressedSize.X - moveX);
				this.setPrefHeight(pressedSize.Y - moveY);
				break;
			case TopAndRight:
				moveX = this.getBoundsInParent().getMaxX() + event.getX() - pressedPoint.X;
				moveY = this.getBoundsInParent().getMinY() + event.getY() - pressedPoint.Y;
				this.setLayoutY(pressedPoint.Y + moveY);
				this.setPrefWidth(pressedSize.X + moveX);
				this.setPrefHeight(pressedSize.Y - moveY);
				break;
			case BottomAndLeft:
				moveX = this.getBoundsInParent().getMinX() + event.getX() - pressedPoint.X;
				moveY = this.getBoundsInParent().getMaxY() + event.getY() - pressedPoint.Y;
				this.setLayoutX(pressedPoint.X + moveX);
				this.setPrefWidth(pressedSize.X - moveX);
				this.setPrefHeight(pressedSize.Y + moveY);
				break;
			case BottomAndRight:
				moveX = this.getBoundsInParent().getMaxX() + event.getX() - pressedPoint.X;
				moveY = this.getBoundsInParent().getMaxY() + event.getY() - pressedPoint.Y;
				this.setPrefWidth(pressedSize.X + moveX);
				this.setPrefHeight(pressedSize.Y + moveY);
				break;
		}
	}
	
	private void DragPointReleased(MouseEvent event) {
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
