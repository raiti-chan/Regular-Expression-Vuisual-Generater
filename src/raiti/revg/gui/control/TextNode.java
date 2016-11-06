package raiti.revg.gui.control;

import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import raiti.revg.gui.MODE;

/**
 * Created by Raiti-chan on 2016/11/05.
 *
 * @author Raiti-chan
 */
public class TextNode extends NodeBase {
	
	private static final TextField TEXT_FIELD = new TextField();
	
	private Label label = new Label();
	
	static {
		TEXT_FIELD.setStyle("-fx-background-color: rgba(0,0,0,0); -fx-alignment: center;");
		TEXT_FIELD.setOnAction(event -> {
			TextField node = (TextField) event.getSource();
			if (node.getParent().getParent() instanceof TextNode) {
				((TextNode) node.getParent().getParent()).label.setText(node.getText());
				((TextNode) node.getParent().getParent()).nodePanel.setCenter(((TextNode) node.getParent().getParent()).label);
			}
		});
	}
	
	public TextNode() {
		label.setCursor(Cursor.TEXT);
		label.setOnMouseClicked(event -> {
			if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
				Label label = (Label) event.getSource();
				if (label.getParent().getParent() instanceof NodeBase) {
					((NodeBase) label.getParent().getParent()).nodePanel.setCenter(TEXT_FIELD);
					TEXT_FIELD.setText(label.getText());
					TEXT_FIELD.requestFocus();
					TEXT_FIELD.selectAll();
				}
			}
		});
	}
	
	@Override
	public void created() {
		TEXT_FIELD.setText("");
		this.nodePanel.setCenter(TEXT_FIELD);
		TEXT_FIELD.requestFocus();
	}
	
	@Override
	public void editorPanelClickEvent(MouseEvent event, MODE mode) {
		if (event.getTarget() != TEXT_FIELD && this.nodePanel.getCenter() instanceof TextField) {
			this.label.setText(((TextField) this.nodePanel.getCenter()).getText());
			this.nodePanel.setCenter(label);
		}
	}
}
