package net.yoojia.dataprovider;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import net.yoojia.dataprovider.util.UriUtility;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.UriMatcher;
import android.content.res.Resources;
import android.net.Uri;

public class InvokerManager {
	
	@SuppressLint("UseSparseArrays")
	private Map<Integer, InvokerWrapper> refrences = new HashMap<Integer, InvokerWrapper>();
	private final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
	
	private HashMap<String,String> alreadyRegisterMapping = new HashMap<String,String>();
	public final String CONFIG_PREFIX = "ivk_config_";
	
	private static int code = 1;
	
	static class InvokerWrapper{
		
		final String path;
		final int code;
		final String type;
		final ActionInvoker invoker;
		
		public InvokerWrapper(ActionInvoker invoker,String path,int code){
			this.path = path;
			this.code = code;
			this.invoker = invoker;
			boolean isArray = path.endsWith("/#") || path.endsWith("/*");
			type = isArray ? "vnd.android.cursor.dir/rssitem" : "vnd.android.cursor.item/rssitem";
		}
	}
	
	/**
	 * 加载R文件的string配置，如果以ivk_config_开头，则认为这个选项指定的内容为ActionInvoker的实现类
	 * @param context
	 */
	public void loadInvokers(Context context){
		Resources res = context.getResources();
		String packageName = context.getPackageName();
		Class<?> clazzR = null;
		try {
			clazzR = Class.forName(packageName+".R$string");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		if(clazzR == null) return;
		Field[] fields = clazzR.getDeclaredFields();
		for(Field field : fields){
			String classConfig = field.getName();
			if(classConfig.startsWith(CONFIG_PREFIX)){
				if(alreadyRegisterMapping.containsKey(classConfig)) continue;
				alreadyRegisterMapping.put(classConfig, classConfig);
				int resid = res.getIdentifier(classConfig, "string", packageName);
				String className = res.getString(resid);
				instanceByClass(className,context);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	void instanceByClass(String className,Context context){
		try {
			Class<?> target = Class.forName(className);
			if( ActionInvoker.class.isAssignableFrom(target)){
	            Constructor<? extends ActionInvoker> constructor = (Constructor<? extends ActionInvoker>) target.getConstructor(Context.class);
	            ActionInvoker invoker = constructor.newInstance(context);
				register(invoker);
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	ActionInvoker matchInvoker(Uri uri){
		InvokerWrapper wrapper = refrences.get(matcher.match(uri));
		if( wrapper != null ){
			return wrapper.invoker;
		}
		return null;
	}
	
	String matchType(Uri uri){
		InvokerWrapper wrapper = refrences.get(matcher.match(uri));
		if( wrapper != null ){
			return wrapper.type;
		}
		return null;
	}
	
	void register(ActionInvoker invoker){
		String[] actionPathGroup = UriUtility.makeActionPathGroup(invoker.getClass(), invoker);
		for(String actionPath : actionPathGroup ){
			matcher.addURI(ILDDataProvider.AUTHORITY, actionPath, code);
			refrences.put(code, new InvokerWrapper(invoker, actionPath, code));
			code++;
		}
	}
	
}
