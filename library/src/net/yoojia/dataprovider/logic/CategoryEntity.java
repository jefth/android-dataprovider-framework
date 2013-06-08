package net.yoojia.dataprovider.logic;

import net.yoojia.dataprovider.annotation.TableEntity;
import net.yoojia.dataprovider.annotation.TableName;
import net.yoojia.dataprovider.support.JSONAbility;

/**
 * 数据实体封装类
 */
@TableEntity
public class CategoryEntity extends JSONAbility{

	@TableName
	public static final String TableName = "categories";
	
	private int cateId;
	
	private String name;
	
	private int parentId;
	
	private String iconUrl;
	
	private int count;

	/////////////

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCateId() {
		return cateId;
	}

	public void setCateId(int cateId) {
		this.cateId = cateId;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
}