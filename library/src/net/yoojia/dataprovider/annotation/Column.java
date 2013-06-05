package net.yoojia.dataprovider.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据表列属性
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
@Inherited
public @interface Column {

	/**
	 * 设置列名
	 * @return
	 */
	String name() default "";
	
	/**
	 * 是否为非NULL
	 * @return
	 */
	boolean isNotNull() default false;
	
	/**
	 * 设置字符值默认值
	 * @return
	 */
	String defaultStringValue() default "";
	
	/**
	 * 设置int值默认值
	 * @return
	 */
	int defaultIntValue() default 0;
	
	/**
	 * 设置float值默认值
	 * @return
	 */
	float defaultFloatValue() default 0.0f;
	
	/**
	 * 设置Double值默认值
	 * @return
	 */
	double defaultDoubleValue() default 0.0;
	
	/**
	 * 设置boolean值默认值
	 * @return
	 */
	boolean defaultBoolValue() default false;
}
