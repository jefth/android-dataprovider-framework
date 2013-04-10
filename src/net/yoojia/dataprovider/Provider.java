package net.yoojia.dataprovider;

import net.yoojia.dataprovider.utility.SQLiteDBAccessor;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public abstract class Provider {
	
	protected Context mContext;
	protected SQLiteDBAccessor sqliteHelper = null;
	
	private boolean sendNotifyFlag = true;
	
	public Provider(Context context){
		this.mContext = context;
		sqliteHelper = SQLiteDBAccessor.getInstance(context);
	}
	
	protected int delete(Uri uri, String selection, String[] selectionArgs){
		SQLiteDatabase database = sqliteHelper.getWritableDatabase();
		int deletingRows = database.delete(tableName(), selection, selectionArgs);
		if(deletingRows > 0 && sendNotifyFlag){
			mContext.getContentResolver().notifyChange(uri, null);
		}
		return deletingRows;
	}

	protected Uri insert(Uri uri, ContentValues values){
		SQLiteDatabase database = sqliteHelper.getWritableDatabase();
		long newlyId = database.insert(tableName(), null, values);
		if(newlyId != -1 && sendNotifyFlag){
			mContext.getContentResolver().notifyChange(uri, null);
		}
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
		if(sendNotifyFlag){
			mContext.getContentResolver().notifyChange(uri, null);
		}
		return values.length;
	}

	protected Cursor query(Uri uri, String[] projection, String selection,String[] selectionArgs, String sortOrder){
		SQLiteDatabase database = sqliteHelper.getWritableDatabase();
		if(projection != null){
			projection = catArray(projection, "_id");
		}
		Cursor cursor = database.query(tableName(), projection, selection, selectionArgs, null, null, sortOrder);
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
		int updateRows = database.update(tableName(), values, where, whereArgs);
		if(updateRows > 0 && sendNotifyFlag){
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
		sendNotifyFlag = flag;
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
