package com.skin.admin.setting;
import com.skin.admin.FileUploadUtil;
import com.skin.common.entity.GeneralSettingBag;
import com.skin.common.entity.Setting;
import com.skin.common.entity.SettingCategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@Service
public class SettingService {
	@Autowired 
	private SettingRepository repo;

	
	public List<Setting> listAllSettings(){
		return (List<Setting>)repo.findAll();
	}
	public GeneralSettingBag getGeneralSetting() {
		List<Setting> settings=new ArrayList<>();
		List<Setting> generalSettings=repo.findByCategory(SettingCategory.GENERAL);
		List<Setting> currencySettings=repo.findByCategory(SettingCategory.CURRENCY);
		
		settings.addAll(generalSettings);
		settings.addAll(currencySettings);
		return new GeneralSettingBag(generalSettings);
	}
	public void saveAll(Iterable<Setting> settings) {
		repo.saveAll(settings);
	}

}
