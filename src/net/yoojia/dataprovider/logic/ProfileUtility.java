package net.yoojia.dataprovider.logic;

import net.yoojia.dataprovider.logic.ProfileProvider.TableConfig;
import android.content.ContentValues;
import android.database.Cursor;

public class ProfileUtility {
	
	public static ProfileEntity cursorToEntity(Cursor cursor){
		String full_name = cursor.getString(cursor.getColumnIndex(TableConfig.CLUMN_FULL_NAME));
		String user_name = cursor.getString(cursor.getColumnIndex(TableConfig.CLUMN_USER_NAME));
		String email = cursor.getString(cursor.getColumnIndex(TableConfig.CLUMN_EMAIL));
		String password = cursor.getString(cursor.getColumnIndex(TableConfig.CLUMN_PASSWORD));
		String avatar = cursor.getString(cursor.getColumnIndex(TableConfig.CLUMN_AVATAR));
		String intro = cursor.getString(cursor.getColumnIndex(TableConfig.CLUMN_INTRO));
		int user_id = cursor.getInt(cursor.getColumnIndex(TableConfig.CLUMN_ID));
		int gender = cursor.getInt(cursor.getColumnIndex(TableConfig.CLUMN_GENDER));
		int credits = cursor.getInt(cursor.getColumnIndex(TableConfig.CLUMN_CREDITS));
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
			values.put(TableConfig.CLUMN_FULL_NAME, profile.fullName);
		
		if(profile.userName != null)
			values.put(TableConfig.CLUMN_USER_NAME, profile.userName);
		
		if(profile.email != null)
			values.put(TableConfig.CLUMN_EMAIL, profile.email);
		
		if(profile.password != null)
			values.put(TableConfig.CLUMN_PASSWORD, profile.password);
		
		if(profile.avatar != null)
			values.put(TableConfig.CLUMN_AVATAR, profile.avatar);
		
		if(profile.intro != null)
			values.put(TableConfig.CLUMN_INTRO, profile.intro);
		
		if(profile.uid > 0)
			values.put(TableConfig.CLUMN_ID, profile.uid);
		
		if(profile.gender > 0)
			values.put(TableConfig.CLUMN_GENDER, profile.gender);
		
		if(profile.credits > 0)
			values.put(TableConfig.CLUMN_CREDITS, profile.credits);
		
		return values;
	}
}
