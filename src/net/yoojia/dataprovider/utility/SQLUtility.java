package net.yoojia.dataprovider.utility;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.yoojia.dataprovider.annotation.Column;
import net.yoojia.dataprovider.annotation.Table;


public final class SQLUtility {

	private static HashMap<Class<?>, String> createTableSQLStatement = new HashMap<Class<?>, String>();
	private static HashMap<Class<?>, String> dropTableSQLStatement = new HashMap<Class<?>, String>();
	private static HashMap<Class<?>, String> tableNames = new HashMap<Class<?>, String>();
	private static HashMap<Class<?>, String[]> javaColumns = new HashMap<Class<?>, String[]>();
	private static HashMap<Class<?>, String[]> sqlColumns = new HashMap<Class<?>, String[]>();
	
	/**
	 * 预处理SQL数据。
	 * 处理完成后的数据将被缓存。
	 * @param entity
	 */
	public static void prepareSQL(Class<?> entity){
		Table tableAnno = entity.getAnnotation(Table.class);
		if(tableAnno == null) {
			throw new IllegalArgumentException("Given entity class is NOT a table-config entity.");
		}
		StringBuffer sqlBuffer = new StringBuffer("CREATE TABLE '");
		String tableName = tableAnno.name();
		sqlBuffer.append(tableName).append("' ");
		dropTableSQLStatement.put(entity, "DROP TABLE IF EXISTS "+tableName+";");
		tableNames.put(entity, tableName);
		
		StringBuffer columnBuffer = new StringBuffer("( '_id' INTEGER PRIMARY KEY AUTOINCREMENT, ");
		List<String> javaColumnsList = new ArrayList<String>();
		List<String> sqlColumnsList = new ArrayList<String>();
		Field[] fields = entity.getDeclaredFields();
		
		try{
			for(Field field : fields){
				field.setAccessible(true);
				Column columnAnnotation = field.getAnnotation(Column.class);
				if(columnAnnotation != null){
					String fieldName = field.getName();
					String sqlFieldName = convertToUnderline(fieldName);
					javaColumnsList.add(fieldName);
					sqlColumnsList.add(sqlFieldName);
					boolean isNotNull = columnAnnotation.isNotNull();
					String defaultValue = columnAnnotation.defaultStringValue();
					ReflectUtil.DataType dataType = ReflectUtil.getFieldType(field);
					switch(dataType){
					case INTEGER:
						defaultValue = String.valueOf(columnAnnotation.defaultIntValue());
						break;
					case FLOAT:
						defaultValue = String.valueOf(columnAnnotation.defaultFloatValue());
						break;
					case BOOL:
						defaultValue = String.valueOf(columnAnnotation.defaultBoolValue());
						break;
					default:
						defaultValue = String.valueOf(columnAnnotation.defaultStringValue());
						break;
					}
					columnBuffer.append("'").append( sqlFieldName ).append("' ");
					columnBuffer.append(dataType.name());
					columnBuffer.append(isNotNull ? " NOT NULL":"");
					if( !"".equals(defaultValue) ){
						columnBuffer.append(" DEFAULT ").append(defaultValue);
					}
					columnBuffer.append(", ");
				}
			}
			javaColumns.put(entity, javaColumnsList.toArray(new String[javaColumnsList.size()]));
			sqlColumns.put(entity, sqlColumnsList.toArray(new String[sqlColumnsList.size()]));
		}catch(Exception exp){
			exp.printStackTrace();
		}
		int length = columnBuffer.length();
		columnBuffer.replace(length-2, length, "");
		columnBuffer.append(");");
		sqlBuffer.append(columnBuffer);
		createTableSQLStatement.put(entity, sqlBuffer.toString());
	}
	
	/**
	 * 驼峰式名称转换成下划线形式
	 */
	public static String convertToUnderline(String words){
		if(words == null || words.length()<2) return words;
		StringBuffer wordBuffer = new StringBuffer();
		for(int i = 0; i < words.length(); i++) {
            char ch = words.charAt(i);
            char preChar = i > 0 ? words.charAt(i-1) : '\u0000';
            if( Character.isLowerCase(preChar) && Character.isUpperCase(ch)) {
            	wordBuffer.append("_").append(Character.toLowerCase(ch));
            } else {
            	wordBuffer.append(ch);
            }
        }
        return wordBuffer.toString();
	}
	
	/**
	 * 生成创建表的SQL语句
	 * @param table
	 * @return
	 */
	public static String makeCreateTableSQL(Class<?> table){
		return createTableSQLStatement.get(table);
	}
	
	/**
	 * 生成删除表的SQL语句
	 * @param clazz
	 * @return
	 */
	public static String makeDropTableSQL(Class<?> clazz){
		return dropTableSQLStatement.get(clazz);
	}
	
	/**
	 * 返回对象的字段
	 * @param clazz
	 * @return
	 */
	public static String[] getJavaColumns(Class<?> clazz){
		return javaColumns.get(clazz);
	}
	
	/**
	 * 返回SQL字段
	 * @param clazz
	 * @return
	 */
	public static String[] getSQLColumns(Class<?> clazz){
		return sqlColumns.get(clazz);
	}
	
	/**
	 * 返回表名
	 * @param clazz
	 * @return
	 */
	public static String genTableName(Class<?> clazz){
		return tableNames.get(clazz);
	}
}
