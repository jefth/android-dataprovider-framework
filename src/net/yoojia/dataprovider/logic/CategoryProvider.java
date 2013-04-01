package net.yoojia.dataprovider.logic;

import net.yoojia.dataprovider.ActionInvoker;
import net.yoojia.dataprovider.annotation.Table;
import net.yoojia.dataprovider.annotation.UriPath;
import net.yoojia.dataprovider.annotation.Table.Column;
import net.yoojia.dataprovider.annotation.Table.Column.Type;
import net.yoojia.dataprovider.utility.SQLiteDBAccessor;
import net.yoojia.dataprovider.utility.UriUtility;
import android.content.Context;
import android.net.Uri;

public class CategoryProvider extends ActionInvoker {
	
	@UriPath
	static final String ACCESS_PREFIX = "categories";
	
	/**
	 * 数据表配置类
	 */
	public static class TableConfig{
		
		@Table
		static final String TABLE = ACCESS_PREFIX;
		
		@Column(type=Type.INTEGER, isPrimaryKey=true, isAutoIncrese=true)
		static final String CLUMN_KEY = "_id";
		
		@Column(isNotNull=true)
		public static final String CLUMN_NAME = "name";
		
		@Column(type=Type.INTEGER,isNotNull=true)
		public static final String CLUMN_ID = "cate_id";
		
		@Column(isNotNull=true)
		public static final String CLUMN_ICON = "icon_url";
		
		@Column(type=Type.INTEGER, defaultValue="0")
		public static final String CLUMN_COUNT = "cate_count";
		
		@Column(type=Type.INTEGER, defaultValue="0")
		public static final String CLUMN_PARENT = "parent_id";
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
	
	public CategoryProvider(Context context) {
		super(context);
		SQLiteDBAccessor.registerTableConfig(TableConfig.class);
	}
	
	@Override
	protected String tableName() {
		return TableConfig.TABLE;
	}

}
