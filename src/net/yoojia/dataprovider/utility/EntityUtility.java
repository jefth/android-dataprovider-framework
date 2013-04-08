package net.yoojia.dataprovider.utility;

import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;



public class EntityUtility<T> {
	
	final Class<T> clazz;
    
	public EntityUtility(Class<T> clazz) {
         this.clazz = clazz;
         SQLUtility.prepareSQL(clazz);
    }
	
	/**
	 * 将Cursor转换成数据实体
	 * @param cursor
	 * @return
	 */
	public T cursorToEntity(Cursor cursor){
		T instance = null;
		try {
			instance = (T) clazz.newInstance();
			final String[] columnArray = SQLUtility.getSQLColumns(clazz);
			final String[] fieldArray = SQLUtility.getJavaColumns(clazz);
			int size = columnArray.length;
			for(int i=0;i<size;i++){
				String fieldName = fieldArray[i];
				String columnName = columnArray[i];
				ReflectUtil.DataType dataType = ReflectUtil.getFieldType(instance,fieldName);
				int index = cursor.getColumnIndex(columnName);
				Object value = null;
				switch(dataType){
				case INTEGER:
					value = cursor.getInt(index);
					break;
				case FLOAT:
					value = cursor.getFloat(index);
					break;
				case LONG:
					value = cursor.getLong(index);
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
	 * @param data
	 * @return
	 */
	public ContentValues entityToValues(T data){
		ContentValues values = new ContentValues();
		final String[] columnArray = SQLUtility.getSQLColumns(clazz);
		final String[] fieldArray = SQLUtility.getJavaColumns(clazz);
		int size = columnArray.length;
		for(int i=0;i<size;i++){
			try {
				String fieldName = fieldArray[i];
				ReflectUtil.DataType dataType = ReflectUtil.getFieldType(data, fieldName);
				switch(dataType){
				case INTEGER:
					int intVal = (Integer) ReflectUtil.getFieldValue(data, fieldName);
					values.put(columnArray[i], intVal);
					break;
				case FLOAT:
					float floatVal = (Float) ReflectUtil.getFieldValue(data, fieldName);
					values.put(columnArray[i], floatVal);
					break;
				case LONG:
					int longVal = (Integer) ReflectUtil.getFieldValue(data, fieldName);
					values.put(columnArray[i], longVal);
					break;
				default:
					String stringValue = String.valueOf(ReflectUtil.getFieldValue(data, fieldName));
					values.put(columnArray[i], stringValue);
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
	 * @param dataset
	 * @return
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
