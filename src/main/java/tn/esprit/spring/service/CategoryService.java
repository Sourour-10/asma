package tn.esprit.spring.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.IService.ICategoryService;
import tn.esprit.spring.entity.Activity;
import tn.esprit.spring.entity.Category;
import tn.esprit.spring.entity.User;
import tn.esprit.spring.repository.ActivityRepository;
import tn.esprit.spring.repository.CategoryRepository;
import tn.esprit.spring.repository.UserRepository;

@Service
public class CategoryService implements ICategoryService {
	
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	ActivityRepository activityRepository;
	

	@Override
	public String createCategory(Category category) {
		boolean x = validateDictionary(category.getDictionary());
		if (x == true)
		{
			categoryRepository.save(category);
			return "Category added successfully";

		}
		else 
		{
			return "insert a valid dictionary. EXEMPLE: word1-word2-word3 ";
		}
	}

	@Override
	public void deleteCategory(int idCategory) {
		Category category = categoryRepository.findById(idCategory).orElse(null);
		categoryRepository.delete(category);
	}

	@Override
	public List<Category> getAllCategorys() {
		List<Category> categorys = (List<Category>) categoryRepository.findAll();
		return categorys;

	}
	
	@Override
	public Category getCategoryById(int idCategory) {
		Category category = categoryRepository.findById(idCategory).orElse(null);
		return category;

	}

	@Override
	public void updateCategory(Category category) {
		categoryRepository.save(category);

	}
	@Override
	public boolean validateDictionary(String dictionary){

            String regex = "^([a-zA-Z0-9]+-[a-zA-Z0-9]*)+$";

            Pattern p = Pattern.compile(regex);

                Matcher m = p.matcher(dictionary);

                if (m.matches()) {
                	return true;
                } else {
                	return false;
                }
		
	}




}
