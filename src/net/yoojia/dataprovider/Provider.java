package net.yoojia.dataprovider;

import net.yoojia.dataprovider.utility.SQLUtility;
import net.yoojia.dataprovider.utility.SQLiteDBAccessor;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public abstract class Provider {
	
	static final String _ID = "_id";
	
	protected Context mContext;
	protected SQLiteDBAccessor sqliteHelper = null;
	
	private boolean sendNotifyChange = true;
	
	public Provider(Context context){
		this.mContext = context;
		sqliteHelper = SQLiteDBAccessor.getInstance(context);
	}
	
	protected int delete(Uri uri, String selection, String[] selectionArgs){
		SQLiteDatabase database = sqliteHelper.getWritableDatabase();
		int deletedRows = database.delete(tableName(), SQLUtility.convertToUnderline(selection), selectionArgs);
		if(deletedRows > 0 && sendNotifyChange){
			mContext.getContentResolver().notifyChange(uri, null);
		}
		return deletedRows;
	}

	protected Uri insert(Uri uri, ContentValues values){
		SQLiteDatabase database = sqliteHelper.getWritableDatabase();
		long affected = insertOrUpdate(database, uri, values);
		if(affected > 0 && sendNotifyChange){
			Uri newUri = ContentUris.withAppendedId(uri, affected);
			mContext.getContentResolver().notifyChange(newUri, null);
		}
		return uri;
	}
	
	protected long insertOrUpdate(SQLiteDatabase database, Uri uri, ContentValues values){
		final String TABLE_NAME = tableName();
		
		final String where;
		final String[] whereArgs;
		if(QueryHelper.hasWhere(values)){
			where = SQLUtility.convertToUnderline(QueryHelper.getWhere(values));
			whereArgs = QueryHelper.getWhereArgs(values);
		}else{
			where = null;
			whereArgs = null;
		}
		Cursor cursor = database.query(TABLE_NAME, toArgs(_ID), where, whereArgs, null, null, null);
		boolean isRecordExists = cursor.moveToNext();
		cursor.close();
		long affected = 0;
		
		ContentValues dataValues = values;
		if(QueryHelper.isKeepOrigin(dataValues)){
			dataValues = new ContentValues(values);
		}
		QueryHelper.removeWhere(dataValues);
		
		if(isRecordExists){
			affected = database.update(TABLE_NAME, dataValues, where, whereArgs);
		}else{
			affected = database.insert(TABLE_NAME, null, dataValues);
		}
		return affected;
	}
	
	protected int batchInsert(Uri groupUri, ContentValues[] values){
		SQLiteDatabase database = sqliteHelper.getWritableDatabase();
		database.beginTransaction();
		try{
			for(ContentValues value : values){
				insertOrUpdate(database, null, value);
			}
			database.setTransactionSuccessful();
		}finally{
			database.endTransaction();
		}
		if(sendNotifyChange){
			mContext.getContentResolver().notifyChange(groupUri, null);
		}
		return values.length;
	}

	protected Cursor query(Uri uri, String[] projection, String selection,String[] selectionArgs, String sortOrder){
		SQLiteDatabase database = sqliteHelper.getWritableDatabase();
		if(projection != null){
			projection = catArray(projection, _ID);
		}
		Cursor cursor = database.query(tableName(), projection, SQLUtility.convertToUnderline(selection), selectionArgs, null, null, sortOrder);
		return cursor;
	}
	
	/**
	 * 拼接两个字符串数组
	 * @param aArray
	 * @param bArray
	 * @return
	 */
	static String[] catArray(String[] aArray,String...bArray){
	    final int len = aArray.length + bArray.length;
	    String[] target = new String[len];
	    System.arraycopy(aArray, 0, target, 0, aArray.length);
	    System.arraycopy(bArray, 0, target, aArray.length, bArray.length);
	    return target;
	}

	protected int update(Uri uri, ContentValues values, String where,String[] whereArgs){
		SQLiteDatabase database = sqliteHelper.getReadableDatabase();
		int updateRows = database.update(tableName(), values, SQLUtility.convertToUnderline(where), whereArgs);
		if(updateRows > 0 && sendNotifyChange){
			mContext.getContentResolver().notifyChange(uri, null);
		}
		return updateRows;
	}
	
	/**
	 * 返回表名
	 * @return
	 */
	protected abstract String tableName();
	
	/**
	 * 设置是否对Resolver发送数据更新通知
	 * @param flag
	 */
	public void setResolverNotify(boolean flag){
		sendNotifyChange = flag;
	}
	
	/**
	 * 将参数列表转换成字符数组
	 * @param values
	 * @return
	 */
	public static String[] toArgs(Object...values){
        String[] args = new String[values.length];
        for(int i=0;i<values.length;i++){
            args[i] = String.valueOf(values[i]);
        }
        return args;
    }
	
}
