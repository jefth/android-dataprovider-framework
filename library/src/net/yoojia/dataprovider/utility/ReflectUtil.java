package net.yoojia.dataprovider.utility;

import java.lang.reflect.Field;


public class ReflectUtil {
	
	public enum DataType{
		INTEGER,TEXT,FLOAT,BOOL
	}
	
	/**
	 * 取得字段的数据类型
	 * @param field
	 * @return
	 */
	public static DataType getFieldType(Field field){
		DataType type = DataType.TEXT;
		if(field == null) return type;
		String genericTypeName = field.getGenericType().toString();
		if("int".equalsIgnoreCase(genericTypeName)){
			type = DataType.INTEGER;
		}else if("float".equalsIgnoreCase(genericTypeName)){
			type = DataType.FLOAT;
		}else if("double".equalsIgnoreCase(genericTypeName)){
			type = DataType.FLOAT;
		}else if("boolean".equalsIgnoreCase(genericTypeName)){
			type = DataType.BOOL;
		}
		return type;
	}
	
	/**
	 * 取得指定对象的字段的数据类型
	 * @param clazz
	 * @param fieldName
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	public static DataType getFieldType(Class<?> clazz, String fieldName) throws SecurityException, NoSuchFieldException{
		return getFieldType(getField(clazz,fieldName));
	}

	public static Field getField(Class<?> clazz,String fieldName){
		Field field = null;
		try{
			field = clazz.getDeclaredField(fieldName);
		}catch(Exception e){
			Class<?> superClazz = clazz.getSuperclass();
			if(superClazz != null) return getField(superClazz,fieldName);
		}
		return field;
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
		Field field = getField(object.getClass(),fieldName);
		if( field != null ){
			field.setAccessible(true);
			return field.get(object);
		}
		return null;
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
		Field field = getField(object.getClass(),fieldName);
		if(field != null){
			field.setAccessible(true);
			field.set(object, value);
		}
	}
	
}
