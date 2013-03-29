package sg.ilovedeals.dataservice.logic;

import sg.ilovedeals.dataservice.ActionInvoker;
import sg.ilovedeals.dataservice.annotation.SQLiteTable;
import sg.ilovedeals.dataservice.annotation.SQLiteTable.SQLiteColumn;
import sg.ilovedeals.dataservice.annotation.SQLiteTable.SQLiteColumn.PrimitiveType;
import sg.ilovedeals.dataservice.annotation.UriPath;
import sg.ilovedeals.dataservice.util.SQLiteHelper;
import sg.ilovedeals.dataservice.util.UriUtility;
import android.content.Context;
import android.net.Uri;

public class ProfileProvider extends ActionInvoker {

	@UriPath
	static final String ACCESS_PREFIX = "profiles";
	
	/**
	 * 数据表配置类
	 */
	public static class Table{
		
		@SQLiteTable
		static final String TABLE = ACCESS_PREFIX;
		
		@SQLiteColumn(primitiveType=PrimitiveType.INT, isPrimaryKey=true, isAutoIncrese=true)
		static final String CLUMN_KEY = "_id";
		
		@SQLiteColumn(primitiveType=PrimitiveType.INT,isNotNull=true)
		public static final String CLUMN_ID = "user_id";
		
		@SQLiteColumn
		public static final String CLUMN_FULL_NAME = "full_name";
		
		@SQLiteColumn
		public static final String CLUMN_USER_NAME = "user_name";
		
		@SQLiteColumn(primitiveType=PrimitiveType.INT)
		public static final String CLUMN_GENDER = "gender";
		
		@SQLiteColumn(isNotNull=true)
		public static final String CLUMN_EMAIL = "email";
		
		@SQLiteColumn
		public static final String CLUMN_PASSWORD = "password";
		
		@SQLiteColumn
		public static final String CLUMN_AVATAR = "avatar";
		
		@SQLiteColumn
		public static final String CLUMN_INTRO = "intro";
		
		@SQLiteColumn(primitiveType=PrimitiveType.INT)
		public static final String CLUMN_CREDITS = "credits";
	}
	
	public static final Uri URI_RECOGNITION;
	public static final Uri URI_GROUP;
	public static final Uri URI_ITEM;
	
	//构建访问URI
	static{
		URI_GROUP = UriUtility.makeAccessGroupUri(ACCESS_PREFIX);
		URI_ITEM = UriUtility.makeAccessItemUri(ACCESS_PREFIX);
		URI_RECOGNITION = URI_GROUP;
	}
	
	public ProfileProvider(Context context) {
		super(context);
		SQLiteHelper.registerTableConfig(Table.class);
	}

	@Override
	protected String tableName() {
		return Table.TABLE;
	}

}
