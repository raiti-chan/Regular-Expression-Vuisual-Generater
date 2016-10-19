package raiti.revg.api;

/**
 * Created by Raiti-chan on 2016/10/19.
 * アプリケーションのメインクラスにつけてください。<br>
 * アプリケーションに設定された、{@link Version}アノテーションの情報を取得します。
 * @author Raiti-chan
 */
public interface AppVersion {
	
	/**
	 * アプリケーションのバージョンを取得します。
	 * @return バージョン
	 */
	default String getVersion(){
		Version versionA = this.getClass().getAnnotation(Version.class);
		if(versionA == null) throw new NullPointerException("クラスが Versionアノテーションを持っていません！！");
		return versionA.value();
	}
}
