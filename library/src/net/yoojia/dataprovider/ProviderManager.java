package net.yoojia.dataprovider;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

import net.yoojia.dataprovider.utility.UriUtility;
import android.content.Context;
import android.content.UriMatcher;
import android.net.Uri;
import android.util.Log;
import android.util.SparseArray;

public class ProviderManager {
	
	static final String TAG = "InvokerManager";
	
	private final SparseArray<InvokerWrapper> refrences = new SparseArray<InvokerWrapper>();
	private final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
	
	private static final ArrayList<Class<? extends Provider<?>>> registedClassList = new ArrayList<Class<? extends Provider<?>>>();
	
	private static int registerCode = 1;
	
	static class InvokerWrapper{
		
		final String path;
		final int code;
		final String type;
		final Provider<?> invoker;
		
		public InvokerWrapper(Provider<?> invoker,String path,int code){
			this.path = path;
			this.code = code;
			this.invoker = invoker;
			boolean isArray = path.endsWith("/#") || path.endsWith("/*");
			type = isArray ? "vnd.android.cursor.dir/rssitem" : "vnd.android.cursor.item/rssitem";
		}
	}
	
	private Context context;
	
	public ProviderManager(Context context){
		this.context = context;
		init();
	}
	
	/**
	 * 注册Provider的实现。
	 *  **在使用Resolver之前注册**
	 * @param provider Provider的实现
	 */
	public static void register(Class<? extends Provider<?>> provider){
		if(ProviderLauncher.isDebugMode()){
			Log.d(TAG, String.format("### Registed Provider(%s).", provider.getName()));
		}
		registedClassList.add(provider);
	}
	
	void init(){
		for(Class<? extends Provider<?>> clazz : registedClassList){
			try {
				registerByClass(clazz);
				if(ProviderLauncher.isDebugMode()){
					Log.d(TAG, String.format(":::::: Initialized Provider(%s) ::::::", clazz.getName()));
				}
			} catch (Exception e) {
				Log.e(TAG, ":::::: Cannot initialize Provider("+clazz.getSimpleName()+") ::::::");
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	void registerByClass(Class<?> clazz) throws Exception{
		if( Provider.class.isAssignableFrom(clazz)){
            Constructor<? extends Provider<?>> constructor = (Constructor<? extends Provider<?>>) clazz.getConstructor(Context.class);
            Provider<?> invoker = constructor.newInstance(context);
			register(invoker);
		}
	}
	
	void register(Provider<?> invoker){
		String[] actionPathGroup = UriUtility.makeActionPathGroup(invoker.getClass(), invoker);
		for(String actionPath : actionPathGroup ){
			matcher.addURI(ProviderLauncher.AUTHORITY, actionPath, registerCode);
			refrences.put(registerCode, new InvokerWrapper(invoker, actionPath, registerCode));
			registerCode++;
		}
	}
	
	
	Provider<?> matchInvoker(Uri uri){
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
	
}
