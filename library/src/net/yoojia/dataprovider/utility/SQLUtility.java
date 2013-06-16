package net.yoojia.dataprovider.utility;

import net.yoojia.dataprovider.annotation.Column;
import net.yoojia.dataprovider.annotation.IgnoreField;
import net.yoojia.dataprovider.annotation.TableEntity;
import net.yoojia.dataprovider.annotation.TableName;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public final class SQLUtility {

	public interface SQLFilter{
		String onColumnNameFilter(String columnName);
		String onFieleNameFilter(String fieldName);
	}

	private static HashMap<Class<?>, String> createTableSQLStatement = new HashMap<Class<?>, String>();
	private static HashMap<Class<?>, String> dropTableSQLStatement = new HashMap<Class<?>, String>();
	private static HashMap<Class<?>, String> tableNames = new HashMap<Class<?>, String>();
	private static HashMap<Class<?>, String[]> javaColumns = new HashMap<Class<?>, String[]>();
	private static HashMap<Class<?>, String[]> sqlColumns = new HashMap<Class<?>, String[]>();

	private static List<Field> getFields(Class<?> clazz){
		List<Field> fields = new ArrayList<Field>();
		Field[] fieldArray = clazz.getDeclaredFields();
		if(fieldArray != null){
			fields.addAll(Arrays.asList(fieldArray));
		}
		Class superClass = clazz.getSuperclass();
		if( superClass != null){
			List<Field> superFields = getFields(superClass);
			if( superFields != null ) fields.addAll(superFields);
		}
		return fields.isEmpty() ? null : fields;
	}

	public static void prepareSQL(Class<?> entity){
		prepare(entity, null);
	}

	/**
	 * 预处理SQL数据。
	 * 处理完成后的数据将被缓存。
	 * @param entity 数据实体类对象
	 * @param filter 字段过滤器
	 */
	public static void prepare (Class<?> entity, SQLFilter filter){
		TableEntity tableEntityAnno = entity.getAnnotation(TableEntity.class);
		// 如果没有TableEntity字段，说明不需要创建SQL，只是缓存字段名
		boolean createSQLFlag = tableEntityAnno != null;

		List<String> javaColumnsList = new ArrayList<String>();
		List<String> sqlColumnsList = new ArrayList<String>();
		List<Field> fields = getFields(entity);

		String tableName = null;

		StringBuilder columnBuffer = new StringBuilder("( '_id' INTEGER PRIMARY KEY AUTOINCREMENT, ");
		try{
			for(Field field : fields){
				field.setAccessible(true);

				// Table Name
				TableName tableNameAnno = field.getAnnotation(TableName.class);
				if( tableNameAnno != null ){
					tableName = String.valueOf(ReflectUtil.getFieldValue(entity.newInstance(), field.getName()));
					continue;
				}

				// 跳过NotColumn字段
				IgnoreField ignoreFieldAnno = field.getAnnotation(IgnoreField.class);
				if( ignoreFieldAnno != null ) continue;

				String fieldName = field.getName();
				if(filter != null){
					fieldName = filter.onFieleNameFilter(fieldName);
				}
				javaColumnsList.add(fieldName);

				String sqlColumnName = convertToUnderline(fieldName);
				if(filter != null){
					sqlColumnName = filter.onColumnNameFilter(sqlColumnName);
				}
				sqlColumnsList.add(sqlColumnName);

				if( !createSQLFlag ) continue;

				columnBuffer.append("'").append( sqlColumnName ).append("' ");
				ReflectUtil.DataType dataType = ReflectUtil.getFieldType(field);
				columnBuffer.append(dataType.name());

				// 配置Column
				Column columnAnnotation = field.getAnnotation(Column.class);
				if(columnAnnotation != null){
					boolean isNotNull = columnAnnotation.isNotNull();
					String defaultValue = columnAnnotation.defaultStringValue();
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
					columnBuffer.append(isNotNull ? " NOT NULL":"");
					if( !"".equals(defaultValue) ){
						columnBuffer.append(" DEFAULT ").append(defaultValue);
					}
				}
				columnBuffer.append(", ");
			}

			javaColumns.put(entity, javaColumnsList.toArray(new String[javaColumnsList.size()]));
			sqlColumns.put(entity, sqlColumnsList.toArray(new String[sqlColumnsList.size()]));

			// 只是创建字段名，则下面的代码就不需要执行了。
			if( !createSQLFlag ) return;

		}catch(Exception exp){
			exp.printStackTrace();
		}
		int length = columnBuffer.length();
		columnBuffer.replace(length-2, length, "");
		columnBuffer.append(");");

		StringBuilder sqlBuffer = new StringBuilder("CREATE TABLE '");
		sqlBuffer.append(tableName).append("' ").append(columnBuffer);

		dropTableSQLStatement.put(entity, "DROP TABLE IF EXISTS "+tableName+";");
		tableNames.put(entity, tableName);

		createTableSQLStatement.put(entity, sqlBuffer.toString());
	}
	
	/**
	 * 驼峰式名称转换成下划线形式
	 */
	public static String convertToUnderline(String words){
		if(words == null || words.length()<2) return words;
		StringBuilder wordBuffer = new StringBuilder();
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
