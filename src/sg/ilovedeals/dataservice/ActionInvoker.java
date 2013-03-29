package sg.ilovedeals.dataservice;

import sg.ilovedeals.dataservice.util.SQLiteHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public abstract class ActionInvoker {
	
	protected Context mContext;
	protected SQLiteHelper sqliteHelper = null;
	
	public ActionInvoker(Context context){
		this.mContext = context;
		sqliteHelper = SQLiteHelper.getInstance(context);
	}
	
	protected int delete(Uri uri, String selection, String[] selectionArgs){
		SQLiteDatabase database = sqliteHelper.getWritableDatabase();
		int deletingRows = database.delete(tableName(), selection, selectionArgs);
		return deletingRows;
	}

	protected Uri insert(Uri uri, ContentValues values){
		SQLiteDatabase database = sqliteHelper.getWritableDatabase();
		database.insert(tableName(), null, values);
		return uri;
	}
	
	protected int batchInsert(Uri uri, ContentValues[] values){
		SQLiteDatabase database = sqliteHelper.getWritableDatabase();
		database.beginTransaction();
		try{
			final String tableName = tableName();
			for(ContentValues value : values){
				database.insert(tableName, null, value);
			}
			database.setTransactionSuccessful();
		}finally{
			database.endTransaction();
		}
		return values.length;
	}

	protected Cursor query(Uri uri, String[] projection, String selection,String[] selectionArgs, String sortOrder){
		SQLiteDatabase database = sqliteHelper.getWritableDatabase();
		Cursor cursor = database.query(tableName(), projection, selection, selectionArgs, null, null, sortOrder);
		return cursor;
	}

	protected int update(Uri uri, ContentValues values, String where,String[] whereArgs){
		SQLiteDatabase database = sqliteHelper.getReadableDatabase();
		int updateRows = database.update(tableName(), values, where, whereArgs);
		return updateRows;
	}
	
	/**
	 * 返回表名
	 * @return
	 */
	protected abstract String tableName();
	
}
