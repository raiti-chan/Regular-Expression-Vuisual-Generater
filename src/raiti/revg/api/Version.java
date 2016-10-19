package raiti.revg.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Raiti-chan on 2016/10/19.
 * アプリケーションのバージョンを指定するアノテーション
 * @author Raiti-chan
 * @since 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Version {
	String value();
	
	
}
