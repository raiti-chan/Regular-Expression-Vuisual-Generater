package raiti.revg.api;

/**
 * Created by Raiti-chan on 2016/11/03.
 * Doubleの2次元座標ポイント
 *
 * @author Raiti-chan
 */
public class Double2DPoint {
	
	/**
	 * X座標
	 */
	public double X;
	
	/**
	 * Y座標
	 */
	public double Y;
	
	/**
	 * 初期値(0,0)でインスタンスを生成します
	 */
	public Double2DPoint() {
		this.X = 0D;
		this.Y = 0D;
	}
	
	/**
	 * 初期値(x,y)でインスタンスを生成します。
	 *
	 * @param x 初期X座標
	 * @param y 初期Y座標
	 */
	@SuppressWarnings("unused")
	public Double2DPoint(double x, double y) {
		this.X = x;
		this.Y = y;
	}
	
	/**
	 * X座標、Y座標をセットします
	 *
	 * @param x X座標
	 * @param y Y座標
	 */
	public void setXandY(double x, double y) {
		this.X = x;
		this.Y = y;
	}
	
}
