package sg.ilovedeals.dataservice.util;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper {

	public static String DATABASE_NAME = "sg.ilovedeals.database";
	public static int DATABASE_VERSION = 1;
	
	private static SQLiteHelper instance = null;
	
	public static SQLiteHelper getInstance(Context context){
		if(instance == null){
			instance = new SQLiteHelper(context);
		}
		return instance;
	}
	
	static List<Class<?>> tables = new ArrayList<Class<?>>();
	
	public static void registerTableConfig(Class<?> table){
		tables.add(table);
	}
	
	public SQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.beginTransaction();
		for(Class<?> table : tables){
			SQLUtility.preProcessSQL(table);
			String sql = SQLUtility.makeCreateTableSQL(table);
			Log.d(this.getClass().getSimpleName(), sql);
			db.execSQL(sql);
		}
		db.setTransactionSuccessful();
		db.endTransaction();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		for(Class<?> table : tables){
			SQLUtility.preProcessSQL(table);
			String sql = SQLUtility.makeDropTableSQL(table);
			db.execSQL(sql);
		}
		onCreate(db);
	}

}
