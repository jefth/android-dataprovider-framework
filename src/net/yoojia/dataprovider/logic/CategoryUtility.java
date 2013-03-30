package net.yoojia.dataprovider.logic;

import java.util.List;

import net.yoojia.dataprovider.logic.CategoryProvider.TableConfig;
import android.content.ContentValues;
import android.database.Cursor;

public class CategoryUtility {

	public static CategoryEntity cursorToEntity(Cursor cursor){
		String name = cursor.getString(cursor.getColumnIndex(TableConfig.CLUMN_NAME));
		String iconUrl = cursor.getString(cursor.getColumnIndex(TableConfig.CLUMN_ICON));
		int cateId = cursor.getInt(cursor.getColumnIndex(TableConfig.CLUMN_ID));
		int parentId = cursor.getInt(cursor.getColumnIndex(TableConfig.CLUMN_PARENT));
		int extra = cursor.getInt(cursor.getColumnIndex(TableConfig.CLUMN_COUNT));
		return new CategoryEntity(name, cateId, parentId, iconUrl, extra);
	}
	
	public static ContentValues entityToValues(CategoryEntity cate){
		ContentValues values = new ContentValues();
		values.put(TableConfig.CLUMN_COUNT, cate.count);
		values.put(TableConfig.CLUMN_NAME, cate.name);
		values.put(TableConfig.CLUMN_ICON, cate.iconUrl);
		values.put(TableConfig.CLUMN_ID, cate.cateId);
		values.put(TableConfig.CLUMN_PARENT, cate.parentId);
		return values;
	}
	
	public static ContentValues[] entityArrayToValues(List<CategoryEntity> categories){
		final int size = categories.size();
		ContentValues[] values = new ContentValues[size];
		for(int i=0;i<size;i++){
			values[i] = entityToValues(categories.get(i));
		}
		return values;
	}
	
}
