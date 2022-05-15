package com.skin.admin.setting.state;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.skin.common.entity.Country;
import com.skin.common.entity.State;

public interface StateRepository extends CrudRepository<State, Integer> {
	public  List<State> findByCountryOrderByNameAsc(Country country);

}
