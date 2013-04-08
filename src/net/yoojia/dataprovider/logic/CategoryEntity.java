package net.yoojia.dataprovider.logic;

import net.yoojia.dataprovider.annotation.Column;
import net.yoojia.dataprovider.annotation.Table;
import net.yoojia.dataprovider.utility.JSONAbility;

/**
 * 数据实体封装类
 */
@Table(name=CategoryEntity.TABLE_NAME)
public class CategoryEntity extends JSONAbility{

	public static final String TABLE_NAME = "categories";
	
	@Column
	private String name;
	
	@Column
	private int cateId;
	
	@Column
	private int parentId;
	
	@Column
	private String iconUrl;
	
	@Column
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