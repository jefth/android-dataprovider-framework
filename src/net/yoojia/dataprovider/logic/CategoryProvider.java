package net.yoojia.dataprovider.logic;

import net.yoojia.dataprovider.Provider;
import net.yoojia.dataprovider.annotation.UriPath;
import net.yoojia.dataprovider.utility.UriUtility;
import android.content.Context;
import android.net.Uri;

public class CategoryProvider extends Provider<CategoryEntity> {
	
	@UriPath
	static final String ACCESS_PREFIX = CategoryEntity.TABLE_NAME;
	
	public static final Uri URI_RECOGNITION;
	public static final Uri URI_GROUP;
	public static final Uri URI_ITEM;
	
	static{
		URI_GROUP = UriUtility.makeAccessGroupUri(ACCESS_PREFIX);
		URI_ITEM = UriUtility.makeAccessItemUri(ACCESS_PREFIX);
		URI_RECOGNITION = URI_GROUP;
	}
	
	public CategoryProvider(Context context) {
		super(context, CategoryEntity.class);
	}
	
	@Override
	protected String tableName() {
		return CategoryEntity.TABLE_NAME;
	}

}
