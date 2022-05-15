package com.skin.admin.category;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.transaction.Transactional;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skin.common.entity.Category;

import exception.CategoryNotFoundException;

@Service
@Transactional
public class CategoryService {
	@Autowired
	private CategoryRepository repo;
	
	public List<Category>listAll(){
		List<Category> rootCategories=repo.findRootCategories();
		return listHierachicalCategories(rootCategories);
	} 
	
	private List<Category> listHierachicalCategories(List<Category>rootCategories){
		List<Category> hierachicalCategories=new ArrayList<>();
		
		for(Category rootCategory:rootCategories) {
			hierachicalCategories.add(Category.copyFull(rootCategory));
			Set<Category> children=rootCategory.getChildren();
			for(Category subCategory:children) {
				String name="--"+subCategory.getName();
				hierachicalCategories.add(Category.copyFull(subCategory,name)); 
				listSubHierachicalCategories(hierachicalCategories,subCategory,1);
			}
			
		}
		return hierachicalCategories;
		
	}
	private void listSubHierachicalCategories(List<Category> hierachicalCategories,Category parent,int subLevel) {
		Set<Category> children=parent.getChildren();
		int newSubLevel=subLevel+1;
		for(Category subCategory :children) {
			String name="";
			for(int i=0;i<newSubLevel;i++) {
				name+="--";
			}
			name+=subCategory.getName();
			hierachicalCategories.add(Category.copyFull(subCategory,name));
			listSubHierachicalCategories(hierachicalCategories,subCategory,newSubLevel);
		}
	}
	public Category save(Category category) {
		Category parent=category.getParent();
		if(parent !=null) {
			String allParentIds=parent.getAllParentIDs()==null ? "-" : parent.getAllParentIDs();
			allParentIds+=String.valueOf(parent.getId())+"-";
			category.setAllParentIDs(allParentIds);
		}
		return repo.save(category);
	}

	public List<Category> listCategoriesUsedInForm(){
		List<Category> categoriesUsedInForm=new ArrayList<>();
		Iterable<Category> categoriesInDB=repo.findAll();
		for (Category category : categoriesInDB) {
			if (category.getParent() == null) {
				categoriesUsedInForm.add(Category.copyIdAndName(category));
				
				Set<Category> children = category.getChildren();
				
				for (Category subCategory : children) {
					String name="--" + subCategory.getName();
					categoriesUsedInForm.add(Category.copyIdAndName(subCategory.getId(), name));
					listSubCategoriesUsedInForm(categoriesUsedInForm,subCategory, 1);
				}
			}
		}
		return categoriesUsedInForm;
	}
	private void listSubCategoriesUsedInForm
	(List<Category> categoriesUsedInForm,Category parent, int subLevel) {
		int newSubLevel = subLevel + 1;
		Set<Category> children = parent.getChildren();
		
		for (Category subCategory : children) {
			String name="";
			for (int i = 0; i < newSubLevel; i++) {				
				name+="--";
			}
			
			categoriesUsedInForm.add(Category.copyIdAndName(subCategory.getId(), name)); 
			
			listSubCategoriesUsedInForm(categoriesUsedInForm,subCategory, newSubLevel);
		}			
}
	public Category get(Integer id) throws CategoryNotFoundException{
		try {
			return repo.findById(id).get();
		}catch(NoSuchElementException ex) {
			throw new CategoryNotFoundException("Could not find any category with ID"+id);
		}
	}
	public String checkUnique(Integer id,String name,String alias)
	{
		boolean isCreatingNew=(id==null || id==0);
		Category categoryByName=repo.findByName(name);
		
		if(isCreatingNew) {
			if(categoryByName!=null)return "DuplicateName";
		}else {
			Category categoryByAlias=repo.findByAlias(alias);
			if(categoryByAlias!=null) {
				return "DuplicateAlias";
			}
		}
		return "OK";
	}
	public void updateCategoryEnabledStatus(Integer id,boolean enabled) {
		repo.updateEnabledStatus(id, enabled);
	}
	public void delete(Integer id)throws CategoryNotFoundException{
		Long countById=repo.countById(id);
		if(countById==null || countById==0) {
			throw new CategoryNotFoundException("Could not find any category with ID"+id);
		}
		repo.deleteById(id);
	}

}