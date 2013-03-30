package net.yoojia.dataprovider.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.yoojia.dataprovider.annotation.Table;
import net.yoojia.dataprovider.annotation.Table.Column;


public final class SQLUtility {

	private static HashMap<Class<?>, String> createTableSQLStatement = new HashMap<Class<?>, String>();
	private static HashMap<Class<?>, String> dropTableSQLStatement = new HashMap<Class<?>, String>();
	private static HashMap<Class<?>, String> tableNames = new HashMap<Class<?>, String>();
	private static HashMap<Class<?>, String[]> tableColumns = new HashMap<Class<?>, String[]>();
	
	
	public static void preProcessSQL(Class<?> table){
		if(createTableSQLStatement.containsKey(table)) return;
		Field[] fields = table.getDeclaredFields();
		StringBuffer sqlBuffer = new StringBuffer("CREATE TABLE '");
		StringBuffer columnBuffer = new StringBuffer("(");
		List<String> columnsList = new ArrayList<String>();
		try{
			for(Field field : fields){
				field.setAccessible(true);
				boolean isTable = field.getAnnotation(Table.class) != null;
				if(isTable){
					String tableName = field.get(table).toString();
					sqlBuffer.append(tableName).append("' ");
					dropTableSQLStatement.put(table, "DROP TABLE IF EXISTS "+tableName+";");
					tableNames.put(table, tableName);
				}else{
					Column columnAnnotation = field.getAnnotation(Column.class);
					if(columnAnnotation != null){
						String name = field.get(table).toString();
						boolean isKey = columnAnnotation.isPrimaryKey();
						boolean isNotNull = columnAnnotation.isNotNull();
						boolean isIncrese = columnAnnotation.isAutoIncrese();
						String defaultValue = columnAnnotation.defaultValue();
						String type = "TEXT";
						switch(columnAnnotation.type()){
						case INT:
							type = "INTEGER";
							break;
						default:
						}
						columnsList.add(name);
						columnBuffer.append("'").append(name).append("' ");
						columnBuffer.append(type);
						columnBuffer.append(isKey ? " PRIMARY KEY":"");
						columnBuffer.append(isIncrese ? " AUTOINCREMENT":"");
						columnBuffer.append(isNotNull ? " NOT NULL":"");
						if( !"".equals(defaultValue) ){
							columnBuffer.append(" DEFAULT ").append(defaultValue);
						}
						columnBuffer.append(", ");
					}
				}
			}
			tableColumns.put(table, columnsList.toArray(new String[columnsList.size()]));
		}catch(Exception exp){
			exp.printStackTrace();
		}
		int length = columnBuffer.length();
		columnBuffer.replace(length-2, length, "");
		columnBuffer.append(");");
		sqlBuffer.append(columnBuffer);
		createTableSQLStatement.put(table, sqlBuffer.toString());
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
	 * 返回表的全部列名称
	 * @param clazz
	 * @return
	 */
	public static String[] genColumnArray(Class<?> clazz){
		return tableColumns.get(clazz);
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
