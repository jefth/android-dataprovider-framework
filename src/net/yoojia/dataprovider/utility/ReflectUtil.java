package net.yoojia.dataprovider.utility;

import java.lang.reflect.Field;


public class ReflectUtil {
	
	public enum DataType{
		INTEGER,TEXT,FLOAT,LONG
	}
	
	/**
	 * 取得字段的数据类型
	 * @param field
	 * @return
	 */
	public static DataType getFieldType(Field field){
		DataType type = DataType.TEXT;
		String genericTypeName = field.getGenericType().toString();
		if("int".equalsIgnoreCase(genericTypeName)){
			type = DataType.INTEGER;
		}else if("float".equalsIgnoreCase(genericTypeName)){
			type = DataType.FLOAT;
		}else if("double".equalsIgnoreCase(genericTypeName)){
			type = DataType.FLOAT;
		}else if("boolean".equalsIgnoreCase(genericTypeName)){
			type = DataType.LONG;
		}
		return type;
	}
	
	/**
	 * 取得指定对象的字段的数据类型
	 * @param object
	 * @param fieldName
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	public static DataType getFieldType(Object object, String fieldName) throws SecurityException, NoSuchFieldException{
		Field field = object.getClass().getDeclaredField(fieldName);
		return getFieldType(field);
	}
	
	/**
	 * 读取指定对象字段的值
	 * @param object
	 * @param fieldName
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static Object getFieldValue(Object object,String fieldName) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		Field field = object.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		return field.get(object);
	}
	
	/**
	 * 设置指定对象字段的值
	 * @param object
	 * @param fieldName
	 * @param value
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static void setFieldValue(Object object,String fieldName,Object value) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		Field field = object.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(object, value);
	}
	
}
