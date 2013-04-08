package net.yoojia.dataprovider.logic;

import net.yoojia.dataprovider.ActionInvoker;
import net.yoojia.dataprovider.annotation.UriPath;
import net.yoojia.dataprovider.utility.SQLiteDBAccessor;
import net.yoojia.dataprovider.utility.UriUtility;
import android.content.Context;
import android.net.Uri;

public class CategoryProvider extends ActionInvoker {
	
	@UriPath
	static final String ACCESS_PREFIX = CategoryEntity.TABLE_NAME;
	
	public static Uri URI_RECOGNITION;
	public static Uri URI_GROUP;
	public static Uri URI_ITEM;
	
	public CategoryProvider(Context context) {
		super(context);
		URI_GROUP = UriUtility.makeAccessGroupUri(ACCESS_PREFIX);
		URI_ITEM = UriUtility.makeAccessItemUri(ACCESS_PREFIX);
		URI_RECOGNITION = URI_GROUP;
		SQLiteDBAccessor.registerTableConfig(CategoryEntity.class);
	}
	
	@Override
	protected String tableName() {
		return CategoryEntity.TABLE_NAME;
	}

}
