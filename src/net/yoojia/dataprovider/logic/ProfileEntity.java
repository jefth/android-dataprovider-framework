package net.yoojia.dataprovider.logic;

import net.yoojia.dataprovider.annotation.Column;
import net.yoojia.dataprovider.annotation.Table;
import net.yoojia.dataprovider.utility.JSONAbility;

@Table(name=ProfileEntity.TABLE_NAME)
public class ProfileEntity extends JSONAbility{
	
	public static final String TABLE_NAME = "profiles";
	
	public static final String SecretLabel = "Secret";
	public static final String MaleLabel = "Male";
	public static final String FemaleLabel = "Female";
	
	public static final String[] GENDER_GROUP = {SecretLabel, MaleLabel, FemaleLabel};
	
	public static final int SECRET = 0;
	public static final int MALE = 1;
	public static final int FEMALE = 2;

	@Column
	private long uid;
	
	@Column
	private String fullName;
	
	@Column
	private String userName;
	
	@Column
	private int gender;
	
	@Column
	private String genderLabel;
	
	@Column
	private String email;
	
	@Column
	private String password;
	
	@Column
	private String avatar;
	
	@Column
	private String intro;
	
	@Column
	private int credits;
	
	//////////////

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getGenderLabel() {
		return genderLabel;
	}

	public void setGenderLabel(String genderLabel) {
		this.genderLabel = genderLabel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}
	
	
	
}
