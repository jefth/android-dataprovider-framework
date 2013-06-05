package net.yoojia.dataprovider.utility;

import java.lang.reflect.Field;

/**
 * 生成JSON数据
 */
public class JSONAbility {

	@Override
	public final String toString(){
		return parseFields2JSON().toString();
	}
	
	public StringBuffer parseFields2JSON(){
		Class<?> clazz = this.getClass();  
		Field[] fields = clazz.getDeclaredFields();
		StringBuffer buffer = new StringBuffer("{");
		for(Field f : fields){
			try {
				f.setAccessible(true);
				buffer.append(f.getName()).append(":");
				Object value = f.get(this);
				if(value != null) {
					if(value instanceof String){
						buffer.append("'").append(value).append("'").append(", ");
					}else{
						buffer.append(value).append(", ");
					}
				}else{
					buffer.append("''").append(", ");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		int offset = buffer.length();
		buffer.replace(offset-2, offset, "");
		return buffer.append("}");
	}
	
}
