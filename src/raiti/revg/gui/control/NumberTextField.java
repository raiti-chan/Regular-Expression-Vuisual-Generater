package raiti.revg.gui.control;

import javafx.scene.control.TextField;

import java.util.regex.Pattern;

/**
 * Created by Raiti-chan on 2016/10/19.
 * 数値のみ入力可能なテキストフィールド
 * @author Raiti-chan
 */
public class NumberTextField extends TextField{
	
	private static final Pattern NUMBER_ONLY = Pattern.compile("[0-9]");
	
	@Override
	public void replaceText(int start, int end, String text) {
		if (text.equals("")){
			super.replaceText(start,end, text);
		} else {
			if (NUMBER_ONLY.matcher(text).find()){
				super.replaceText(start, end, text);
			}
		}
	}
	
	@Override
	public void replaceSelection(String text) {
		if (text.equals("")){
			super.replaceSelection(text);
		} else {
			if (NUMBER_ONLY.matcher(text).find()){
				super.replaceSelection(text);
			}
		}
	}
}
