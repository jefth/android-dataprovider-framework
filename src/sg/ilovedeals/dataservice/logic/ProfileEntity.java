package sg.ilovedeals.dataservice.logic;

import sg.ilovedeals.dataservice.util.JSONAbilityEntity;

public class ProfileEntity extends JSONAbilityEntity{
	
	public static final String SecretLabel = "Secret";
	public static final String MaleLabel = "Male";
	public static final String FemaleLabel = "Female";
	
	public static final String[] GENDER_GROUP = {SecretLabel, MaleLabel, FemaleLabel};
	
	public static final int SECRET = 0;
	public static final int MALE = 1;
	public static final int FEMALE = 2;

	public final long uid;
	public final String fullName;
	public final String userName;
	public final int gender;
	public final String genderLabel;
	public final String email;
	public final String password;
	public final String avatar;
	public final String intro;
	public final int credits;
	
	public static class Builder{
		long uid;
		String fullName;
		String userName;
		int gender;
		String genderLabel;
		String email;
		String password;
		String avatar;
		String intro;
		int credits;

		public ProfileEntity build(){
			return new ProfileEntity(this);
		}
        
		public Builder setUid(long uid) {
			this.uid = uid;
			return this;
		}

		public Builder setFullName(String fullName) {
			this.fullName = fullName;
			return this;
		}

		public Builder setUserName(String userName) {
			this.userName = userName;
			return this;
		}
		
		public Builder setCredits(int credits) {
			this.credits = credits;
			return this;
		}

		public Builder setGender(int gender) {
			this.gender = gender;
			switch(gender){
			case FEMALE:
				this.genderLabel = FemaleLabel;
				break;
			case MALE:
				this.genderLabel = MaleLabel;
				break;
			default:
				this.genderLabel = SecretLabel;
				break;
			}
			return this;
		}

		public Builder setEmail(String email) {
			this.email = email;
			return this;
		}

		public Builder setPassword(String password) {
			this.password = password;
			return this;
		}

		public Builder setAvatar(String avatar) {
			this.avatar = avatar;
			return this;
		}

		public Builder setIntro(String intro) {
			this.intro = intro;
			return this;
		}

	}
	
	public ProfileEntity(Builder builder){
		this.uid = builder.uid;
		this.fullName = builder.fullName;
		this.userName = builder.userName;
		this.gender = builder.gender;
		this.genderLabel = builder.genderLabel;
		this.email = builder.email;
		this.password = builder.password;
		this.avatar = builder.avatar;
		this.intro = builder.intro;
		this.credits = builder.credits;
	}
	
}
