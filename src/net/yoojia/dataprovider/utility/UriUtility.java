package net.yoojia.dataprovider.utility;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import net.yoojia.dataprovider.DataProvider;
import net.yoojia.dataprovider.annotation.UriPath;

import android.net.Uri;

public final class UriUtility {

	/**
	 * 构建单项目访问Uri
	 * @param pathPrefix
	 * @return
	 */
	public static Uri makeAccessItemUri(String pathPrefix){
		return Uri.parse(makeUri(pathPrefix, false));
	}
	
	/**
	 * 构建多项目访问Uri
	 * @param pathPrefix
	 * @return
	 */
	public static Uri makeAccessGroupUri(String pathPrefix){
		return Uri.parse(makeUri(pathPrefix, true));
	}
	
	private static String makeUri(String pathPrefix,boolean isGroupAccess){
		StringBuffer buffer = new StringBuffer("content://");
		buffer.append(DataProvider.authority()).append("/").append(pathPrefix);
		buffer.append( isGroupAccess ? "" : "/#");
		return buffer.toString();
	}
	
	public static String[] makeActionPathGroup(Class<?> clazz,Object object){
		Field[] fields = clazz.getDeclaredFields();
		List<String> group = new ArrayList<String>();
		try{
			for(Field field : fields){
				UriPath prefix = field.getAnnotation(UriPath.class);
				if(prefix != null){
					field.setAccessible(true);
					String value = field.get(object).toString();
					group.add(value);
					group.add(value + "/#");
				}
			}
		}catch(Exception exp){
			exp.printStackTrace();
		}
		return group.toArray(new String[group.size()]);
	}
}
