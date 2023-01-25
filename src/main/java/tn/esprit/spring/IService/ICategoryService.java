package tn.esprit.spring.IService;

import java.util.List;

import tn.esprit.spring.entity.Category;


public interface ICategoryService {

	//CRUD
	String createCategory(Category category) ;
	void deleteCategory(int  idCategory);
	void updateCategory(Category category);
	public boolean validateDictionary(String Dictionary);
	public List<Category> getAllCategorys();
	public Category getCategoryById(int idCategory);
	
}
