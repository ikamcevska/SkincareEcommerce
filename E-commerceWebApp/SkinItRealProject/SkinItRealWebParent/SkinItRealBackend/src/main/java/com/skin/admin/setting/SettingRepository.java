package com.skin.admin.setting;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.skin.common.entity.Setting;
import com.skin.common.entity.SettingCategory;

public interface SettingRepository extends CrudRepository<Setting,String>{
	public List<Setting> findByCategory(SettingCategory category);
}
