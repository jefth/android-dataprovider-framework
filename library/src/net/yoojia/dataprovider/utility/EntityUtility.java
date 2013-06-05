package net.yoojia.dataprovider.utility;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.List;

public class EntityUtility<T> {
	
	final Class<T> clazz;
    
	public EntityUtility(Class<T> clazz) {
		this(clazz, null);
    }

	/**
	 * 指定SQL生成过滤器
	 * @param clazz 实体类对象
	 * @param filter 字段过滤器
	 */
	public EntityUtility(Class<T> clazz,SQLUtility.SQLFilter filter) {
		this.clazz = clazz;
		SQLUtility.prepareSQL(clazz,filter);
	}
	
	/**
	 * 将Cursor转换成数据实体
	 * @param cursor 数据库游标Cursor
	 * @return 数据实体
	 */
	public T cursorToEntity(Cursor cursor){
		T instance = null;
		try {
			instance = clazz.newInstance();
			final String[] columnArray = SQLUtility.getSQLColumns(clazz);
			final String[] fieldArray = SQLUtility.getJavaColumns(clazz);
			int size = columnArray.length;
			for(int i=0;i<size;i++){
				String fieldName = fieldArray[i];
				String columnName = columnArray[i];
				ReflectUtil.DataType dataType = ReflectUtil.getFieldType(clazz,fieldName);
				int index = cursor.getColumnIndex(columnName);
				Object value;
				switch(dataType){
				case INTEGER:
					value = cursor.getInt(index);
					break;
				case FLOAT:
					value = cursor.getFloat(index);
					break;
				case BOOL:
					value = (cursor.getLong(index) > 0);
					break;
				default:
					value = cursor.getString(index);
					break;
				}
				ReflectUtil.setFieldValue(instance, fieldName, value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return instance;
	}
	
	/**
	 * 将数据实体对象转换成键值对
	 * @param data 数据实体
	 * @return ContentValues键值对
	 */
	public ContentValues entityToValues(T data){
		ContentValues values = new ContentValues();
		final String[] columnArray = SQLUtility.getSQLColumns(clazz);
		final String[] fieldArray = SQLUtility.getJavaColumns(clazz);
		int size = columnArray.length;
		for(int i=0;i<size;i++){
			try {
				String fieldName = fieldArray[i];
				String columnName = columnArray[i];

				ReflectUtil.DataType dataType = ReflectUtil.getFieldType(clazz, fieldName);
				switch(dataType){
				case INTEGER:
					int intVal = (Integer) ReflectUtil.getFieldValue(data, fieldName);
					if(intVal == 0) continue;
					values.put(columnName, intVal);
					break;
				case FLOAT:
					float floatVal = (Float) ReflectUtil.getFieldValue(data, fieldName);
					if(floatVal == 0) continue;
					values.put(columnName, floatVal);
					break;
				case BOOL:
					boolean boolVal = (Boolean) ReflectUtil.getFieldValue(data, fieldName);
					if(!boolVal) continue;
					values.put(columnName, boolVal);
					break;
				default:
					String stringValue = String.valueOf(ReflectUtil.getFieldValue(data, fieldName));
					if(stringValue == null) continue;
					values.put(columnName, stringValue);
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return values;
	}
	
	/**
	 * 将数据实体对象队列转换成键值对组
	 * @param dataset 数据集
	 * @return 键值对组
	 */
	public ContentValues[] entityArrayToValues(List<T> dataset){
		final int size = dataset.size();
		ContentValues[] values = new ContentValues[size];
		for(int i=0;i<size;i++){
			values[i] = entityToValues(dataset.get(i));
		}
		return values;
	}

}
