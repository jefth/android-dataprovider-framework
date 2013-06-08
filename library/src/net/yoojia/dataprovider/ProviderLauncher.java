package net.yoojia.dataprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

/**
 * URI映射到不同的Provider实现类。
 * 在ContentProvider被调用时，取出并执行映射URI的Provider实现类。
 */
public class ProviderLauncher extends ContentProvider {
	
	static String AUTHORITY = "net.yoojia.dataprovider"; 
	
	private ProviderManager invokerManager;
	
	static boolean IS_DEBUG = false;
	
	/**
	 * 设置是否为调试模式
	 * @param isDebug 是否为调试模式
	 */
	public static void setDebugMode(boolean isDebug){
		IS_DEBUG = isDebug;
	}
	
	/**
	 *
	 * @return 返回是否处理调试模式
	 */
	public static boolean isDebugMode(){
		return IS_DEBUG;
	}

	/**
	 * 获取识别码
	 * @return ContentProvider的识别码
	 */
	public static String authority(){
		return AUTHORITY;
	}
	
	/**
	 * 设置识别码
	 * @param authority ContentProvider的识别码
	 */
	public static void authority(String authority){
		AUTHORITY = authority;
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		Provider<?> invoker = invokerManager.matchInvoker(uri);
		if(invoker != null){
			return invokerManager.matchInvoker(uri).delete(uri, selection, selectionArgs);
		}
		return 0;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		Provider<?> invoker = invokerManager.matchInvoker(uri);
		if(invoker != null){
			return invokerManager.matchInvoker(uri).insert(uri, values);
		}
		return null;
	}

	@Override
	public int bulkInsert(Uri uri, ContentValues[] values) {
		Provider<?> invoker = invokerManager.matchInvoker(uri);
		if(invoker != null){
			invokerManager.matchInvoker(uri).batchInsert(uri, values);
			return values.length;
		}
		return 0;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,String[] selectionArgs, String sortOrder) {
		Provider<?> invoker = invokerManager.matchInvoker(uri);
		if(invoker != null){
			return invokerManager.matchInvoker(uri).query(uri, projection, selection, selectionArgs, sortOrder);
		}
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,String[] selectionArgs) {
		Provider<?> invoker = invokerManager.matchInvoker(uri);
		if(invoker != null){
			return invokerManager.matchInvoker(uri).update(uri, values, selection, selectionArgs);
		}
		return 0;
	}
	
	@Override
	public boolean onCreate() {
		this.invokerManager = new ProviderManager(getContext());
		return true;
	}
	
	@Override
	public String getType(Uri uri) {
		return invokerManager.matchType(uri);
	}

}
