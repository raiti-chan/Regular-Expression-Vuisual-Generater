package raiti.revg.gui.Dialog;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Raiti-chan on 2016/10/19.
 * サイズ選択ダイアログのコントローラー
 * @author Raiti-chan
 * @since 1.0.0
 */
public class SizeSetDialogController implements Initializable {
	
	
	private boolean isOk = false;
	
	@FXML
	private TextField X;
	
	@FXML
	private TextField Y;
	
	@FXML
	private Button ok;
	
	@FXML
	private Button cancel;
	
	/**
	 * initialize
	 * @param location ロケ―ション
	 * @param resources リソース
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ok.setOnAction(event -> {
			this.isOk = true;
			ok.getScene().getWindow().hide();
		});
		
		cancel.setOnAction(event -> cancel.getScene().getWindow().hide());
	}
	
	
	/**
	 * ダイアログのX,Yに初期値をセットします。
	 * @param x X
	 * @param y Y
	 */
	public void setXandY(int x,int y){
		X.setText(Integer.toString(x));
		Y.setText(Integer.toString(y));
	}
	
	/**
	 * 入力されたXを取得
	 * @return X
	 */
	public int getX(){
		return Integer.parseInt(X.getText());
	}
	
	/**
	 * 入力されたYを取得
	 * @return Y
	 */
	public int getY(){
		return Integer.parseInt(Y.getText());
	}
	
	/**
	 * OKが押されてダイアログが閉じたか
	 * @return
	 */
	public boolean isOk(){
		return isOk;
	}
}
