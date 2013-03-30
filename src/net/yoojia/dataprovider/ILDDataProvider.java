package net.yoojia.dataprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class ILDDataProvider extends ContentProvider {
	
	public static final String AUTHORITY = "sg.ilovedeals.dataprovider"; 

	private InvokerManager invokerManager;
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		ActionInvoker invoker = invokerManager.matchInvoker(uri);
		if(invoker != null){
			return invokerManager.matchInvoker(uri).delete(uri, selection, selectionArgs);
		}
		return 0;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		ActionInvoker invoker = invokerManager.matchInvoker(uri);
		if(invoker != null){
			return invokerManager.matchInvoker(uri).insert(uri, values);
		}
		return null;
	}

	@Override
	public int bulkInsert(Uri uri, ContentValues[] values) {
		ActionInvoker invoker = invokerManager.matchInvoker(uri);
		if(invoker != null){
			invokerManager.matchInvoker(uri).batchInsert(uri, values);
			return values.length;
		}
		return 0;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,String[] selectionArgs, String sortOrder) {
		ActionInvoker invoker = invokerManager.matchInvoker(uri);
		if(invoker != null){
			return invokerManager.matchInvoker(uri).query(uri, projection, selection, selectionArgs, sortOrder);
		}
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,String[] selectionArgs) {
		ActionInvoker invoker = invokerManager.matchInvoker(uri);
		if(invoker != null){
			return invokerManager.matchInvoker(uri).update(uri, values, selection, selectionArgs);
		}
		return 0;
	}
	
	@Override
	public boolean onCreate() {
		this.invokerManager = new InvokerManager();
		invokerManager.loadInvokers(getContext());
		return true;
	}
	
	@Override
	public String getType(Uri uri) {
		return invokerManager.matchType(uri);
	}

}
