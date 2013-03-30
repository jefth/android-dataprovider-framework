package net.yoojia.dataprovider.logic;

import net.yoojia.dataprovider.ActionInvoker;
import net.yoojia.dataprovider.annotation.Table;
import net.yoojia.dataprovider.annotation.UriPath;
import net.yoojia.dataprovider.annotation.Table.Column;
import net.yoojia.dataprovider.annotation.Table.Column.Type;
import net.yoojia.dataprovider.util.SQLiteDBAccessor;
import net.yoojia.dataprovider.util.UriUtility;
import android.content.Context;
import android.net.Uri;

public class ProfileProvider extends ActionInvoker {

	@UriPath
	static final String ACCESS_PREFIX = "profiles";
	
	/**
	 * 数据表配置类
	 */
	public static class TableConfig{
		
		@Table
		static final String TABLE = ACCESS_PREFIX;
		
		@Column(type=Type.INT, isPrimaryKey=true, isAutoIncrese=true)
		static final String CLUMN_KEY = "_id";
		
		@Column(type=Type.INT,isNotNull=true)
		public static final String CLUMN_ID = "user_id";
		
		@Column
		public static final String CLUMN_FULL_NAME = "full_name";
		
		@Column
		public static final String CLUMN_USER_NAME = "user_name";
		
		@Column(type=Type.INT)
		public static final String CLUMN_GENDER = "gender";
		
		@Column(isNotNull=true)
		public static final String CLUMN_EMAIL = "email";
		
		@Column
		public static final String CLUMN_PASSWORD = "password";
		
		@Column
		public static final String CLUMN_AVATAR = "avatar";
		
		@Column
		public static final String CLUMN_INTRO = "intro";
		
		@Column(type=Type.INT)
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
		SQLiteDBAccessor.registerTableConfig(TableConfig.class);
	}

	@Override
	protected String tableName() {
		return TableConfig.TABLE;
	}

}
