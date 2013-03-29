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

public class CategoryProvider extends ActionInvoker {
	
	@UriPath
	static final String ACCESS_PREFIX = "categories";
	
	/**
	 * 数据表配置类
	 */
	public static class Table{
		
		@SQLiteTable
		static final String TABLE = ACCESS_PREFIX;
		
		@SQLiteColumn(primitiveType=PrimitiveType.INT, isPrimaryKey=true, isAutoIncrese=true)
		static final String CLUMN_KEY = "_id";
		
		@SQLiteColumn(isNotNull=true)
		public static final String CLUMN_NAME = "name";
		
		@SQLiteColumn(primitiveType=PrimitiveType.INT,isNotNull=true)
		public static final String CLUMN_ID = "cate_id";
		
		@SQLiteColumn(isNotNull=true)
		public static final String CLUMN_ICON = "icon_url";
		
		@SQLiteColumn
		public static final String CLUMN_EXTRA = "cate_extra";
		
		@SQLiteColumn(primitiveType=PrimitiveType.INT, defaultValue="0")
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
		SQLiteHelper.registerTableConfig(Table.class);
	}
	
	@Override
	protected String tableName() {
		return Table.TABLE;
	}

}
