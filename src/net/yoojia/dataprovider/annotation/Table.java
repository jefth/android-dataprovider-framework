package net.yoojia.dataprovider.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface Table {
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.FIELD })
	public @interface Column {
		
		/**
		 * 列的基本类型 
		 */
		public enum Type{INT,STRING}
		
		/**
		 * 列是否为主键
		 * @return
		 */
		boolean isPrimaryKey() default false;
		
		/**
		 * 列是否为自增长
		 * @return
		 */
		boolean isAutoIncrese() default false;
		
		/**
		 * 是否为非NULL
		 * @return
		 */
		boolean isNotNull() default false;
		
		/**
		 * 设置默认值
		 * @return
		 */
		String defaultValue() default "";
		
		/**
		 * 设置列的基本类型。e.g:int | string
		 * @return
		 */
		Type type() default Type.STRING;
	}
}
