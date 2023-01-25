
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

import tn.esprit.spring.IService.IDepartmentService;
import tn.esprit.spring.entity.Department;

@RestController
@RequestMapping("/Department")
public class DepartmentController {
	@Autowired
	IDepartmentService departmentService;

	// http://localhost:8089/Department/create

	@PostMapping("create")
	@ResponseBody
	public void createDepartment(@RequestBody Department department) {
		departmentService.SaveDepartment(department);
	}

	// http://localhost:8089/Department/getAllDepartments
	@GetMapping("/getAllDepartments")
	@ResponseBody
	public List<Department> getAllDepartments() {

		List<Department> departments = departmentService.GetAllDepartments();
		return departments;

	}
	// http://localhost:8089/Department/delete/1

	@DeleteMapping("/delete/{id}")
	public void deleteCar(@PathVariable("id") int departmentId) {

		departmentService.DeleteDepartment(departmentId);
		;
	}
	// http://localhost:8089/Department/update/1

	@PutMapping("/update")
	public void updateDepartment( @RequestBody Department department) {
		departmentService.UpdateDepartment(department);
	}

	
	}


