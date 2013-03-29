package sg.ilovedeals.dataservice.logic;

import sg.ilovedeals.dataservice.util.JSONAbilityEntity;

/**
 * 数据实体封装类
 */
public class CategoryEntity extends JSONAbilityEntity{

	public final String name;
	public final int cateId;
	public final int parentId;
	public final String iconUrl;
	public final String extra;

	public CategoryEntity(String name, int cateId, int parentId, String iconUrl, String extra) {
		this.name = name;
		this.cateId = cateId;
		this.parentId = parentId;
		this.iconUrl = iconUrl;
		this.extra = extra;
	}
}