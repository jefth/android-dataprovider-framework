package net.yoojia.dataprovider.logic;

import net.yoojia.dataprovider.util.JSONAbility;

/**
 * 数据实体封装类
 */
public class CategoryEntity extends JSONAbility{

	public final String name;
	public final int cateId;
	public final int parentId;
	public final String iconUrl;
	public final int count;

	public CategoryEntity(String name, int cateId, int parentId, String iconUrl, int count) {
		this.name = name;
		this.cateId = cateId;
		this.parentId = parentId;
		this.iconUrl = iconUrl;
		this.count = count;
	}
}