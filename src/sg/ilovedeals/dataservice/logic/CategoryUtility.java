package sg.ilovedeals.dataservice.logic;

import sg.ilovedeals.dataservice.logic.CategoryProvider.Table;
import android.content.ContentValues;
import android.database.Cursor;

public class CategoryUtility {

	public static CategoryEntity cursorToEntity(Cursor cursor){
		String name = cursor.getString(cursor.getColumnIndex(Table.CLUMN_NAME));
		String extra = cursor.getString(cursor.getColumnIndex(Table.CLUMN_EXTRA));
		String iconUrl = cursor.getString(cursor.getColumnIndex(Table.CLUMN_ICON));
		int cateId = cursor.getInt(cursor.getColumnIndex(Table.CLUMN_ID));
		int parentId = cursor.getInt(cursor.getColumnIndex(Table.CLUMN_PARENT));
		return new CategoryEntity(name, cateId, parentId, iconUrl, extra);
	}
	
	public static ContentValues entityToValues(CategoryEntity cate){
		ContentValues values = new ContentValues();
		values.put(Table.CLUMN_EXTRA, cate.extra);
		values.put(Table.CLUMN_NAME, cate.name);
		values.put(Table.CLUMN_ICON, cate.iconUrl);
		values.put(Table.CLUMN_ID, cate.cateId);
		values.put(Table.CLUMN_PARENT, cate.parentId);
		return values;
	}
	
}
