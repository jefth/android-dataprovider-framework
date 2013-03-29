package sg.ilovedeals.dataservice.logic;

import sg.ilovedeals.dataservice.logic.ProfileProvider.Table;
import android.content.ContentValues;
import android.database.Cursor;

public class ProfileUtility {
	
	/**
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
	 * @param cursor
	 * @return
	 */

	public static ProfileEntity cursorToEntity(Cursor cursor){
		String full_name = cursor.getString(cursor.getColumnIndex(Table.CLUMN_FULL_NAME));
		String user_name = cursor.getString(cursor.getColumnIndex(Table.CLUMN_USER_NAME));
		String email = cursor.getString(cursor.getColumnIndex(Table.CLUMN_EMAIL));
		String password = cursor.getString(cursor.getColumnIndex(Table.CLUMN_PASSWORD));
		String avatar = cursor.getString(cursor.getColumnIndex(Table.CLUMN_AVATAR));
		String intro = cursor.getString(cursor.getColumnIndex(Table.CLUMN_INTRO));
		int user_id = cursor.getInt(cursor.getColumnIndex(Table.CLUMN_ID));
		int gender = cursor.getInt(cursor.getColumnIndex(Table.CLUMN_GENDER));
		int credits = cursor.getInt(cursor.getColumnIndex(Table.CLUMN_CREDITS));
		ProfileEntity.Builder builder = new ProfileEntity.Builder();
		builder.setAvatar(avatar)
			.setCredits(credits)
			.setEmail(email)
			.setFullName(full_name)
			.setGender(gender)
			.setIntro(intro)
			.setPassword(password)
			.setUid(user_id)
			.setUserName(user_name);
		return builder.build();
	}
	
	public static ContentValues entityToValues(ProfileEntity profile){
		ContentValues values = new ContentValues();
		
		if(profile.fullName != null)
			values.put(Table.CLUMN_FULL_NAME, profile.fullName);
		
		if(profile.userName != null)
			values.put(Table.CLUMN_USER_NAME, profile.userName);
		
		if(profile.email != null)
			values.put(Table.CLUMN_EMAIL, profile.email);
		
		if(profile.password != null)
			values.put(Table.CLUMN_PASSWORD, profile.password);
		
		if(profile.avatar != null)
			values.put(Table.CLUMN_AVATAR, profile.avatar);
		
		if(profile.intro != null)
			values.put(Table.CLUMN_INTRO, profile.intro);
		
		if(profile.uid > 0)
			values.put(Table.CLUMN_ID, profile.uid);
		
		if(profile.gender > 0)
			values.put(Table.CLUMN_GENDER, profile.gender);
		
		if(profile.credits > 0)
			values.put(Table.CLUMN_CREDITS, profile.credits);
		return values;
	}
}
