package com.skin.admin.setting;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.skin.common.entity.Currency;

public interface CurrencyRepository extends CrudRepository<Currency, Integer> {
	public List<Currency> findAllByOrderByNameAsc();

}
