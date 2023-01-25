
package tn.esprit.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.IService.ICategoryService;
import tn.esprit.spring.entity.Category;

@RestController
@RequestMapping("/Category")
public class CategoryController {
	@Autowired
	ICategoryService categoryService;

	// http://localhost:8089/SpringMVC/Category/create

	@PostMapping("create")
	@ResponseBody
	public String createCategory(@RequestBody Category category) {
		return categoryService.createCategory(category);
	}

	// http://localhost:8089/SpringMVC/Category/getAllCategorys
	@GetMapping("/getAllCategorys")
	@ResponseBody
	public List<Category> getAllCategorys() {

		List<Category> categorys = categoryService.getAllCategorys();
		return categorys;

	}
	
	// http://localhost:8089/SpringMVC/Category/getCategoryById/1
	@GetMapping("/getCategoryById/{id}")
	@ResponseBody
	public Category getCategoryById(@PathVariable("id") int categoryId) {

		Category category = categoryService.getCategoryById(categoryId);
		return category;

	}
	// http://localhost:8089/SpringMVC/Category/delete/1

	@DeleteMapping("/delete/{id}")
	public void deleteCar(@PathVariable("id") int categoryId) {

		categoryService.deleteCategory(categoryId);
		;
	}
	// http://localhost:8089/SpringMVC/Category/update

	@PutMapping("/update")
	public void updateCategory(@RequestBody Category category) {
		categoryService.updateCategory(category);
	}

}

