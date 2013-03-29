package sg.ilovedeals.dataservice.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标注此字段为路径前缀，一般使用复数形式。e.g: categories 用于Uri注册时使用。
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface UriPath {
}
