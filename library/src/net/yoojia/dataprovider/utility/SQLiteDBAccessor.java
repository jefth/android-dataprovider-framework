package net.yoojia.dataprovider.utility;

import java.util.ArrayList;
import java.util.List;

import net.yoojia.dataprovider.ProviderLauncher;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public final class SQLiteDBAccessor extends SQLiteOpenHelper {
	
	static final String TAG = SQLiteDBAccessor.class.getSimpleName();
	
	static final String INSTANCED_ERROR = "Database is using! Please call the method BEFORE access database.";

	private static String DATABASE_NAME = "net.yoojia.database";
	private static int DATABASE_VERSION = 1;
	
	private static SQLiteDBAccessor INSTANCE = null;
	private static List<Class<?>> TABLE_CONFIG_LIST = new ArrayList<Class<?>>();
	
	public static SQLiteDBAccessor getInstance(Context context){
		if(INSTANCE == null){
			INSTANCE = new SQLiteDBAccessor(context);
		}
		return INSTANCE;
	}
	
	/**
	 * 设置数据库名称。在访问数据库之前设置。
	 * @param name
	 */
	public static void setDatabaseName(String name){
		if(INSTANCE == null){
			DATABASE_NAME = name;
		}else{
			throw new IllegalStateException(INSTANCED_ERROR);
		}
	}
	
	/**
	 * 设置数据库版本号。在访问数据库之前设置。
	 * @param version
	 */
	public static void setDatabasVersion(int version){
		if(INSTANCE == null){
			DATABASE_VERSION = version;
		}else{
			throw new IllegalStateException(INSTANCED_ERROR);
		}
	}
	
	/**
	 * 注册表配置
	 * @param table
	 */
	public static void registerTableConfig(Class<?> table){
		TABLE_CONFIG_LIST.add(table);
	}
	
	public SQLiteDBAccessor(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.beginTransaction();
		try{
			for(Class<?> table : TABLE_CONFIG_LIST){
				SQLUtility.prepareSQL(table);
				String sql = SQLUtility.makeCreateTableSQL(table);
				if(ProviderLauncher.isDebugMode()){
					Log.d(TAG, String.format("### Executing SQL : %s",sql ));
				}
				db.execSQL(sql);
			}
			db.setTransactionSuccessful();
		}finally{
			db.endTransaction();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		for(Class<?> table : TABLE_CONFIG_LIST){
			SQLUtility.prepareSQL(table);
			String sql = SQLUtility.makeDropTableSQL(table);
			db.execSQL(sql);
		}
		onCreate(db);
	}

}
