package tn.esprit.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.IService.IDepartmentService;

import tn.esprit.spring.entity.Department;

import tn.esprit.spring.repository.DepartmentRepository;

@Service
public class DepartmentService implements IDepartmentService {
	@Autowired
	DepartmentRepository departmentRepository;
	
	public List<Department> GetAllDepartments() {

		return (List<Department>) departmentRepository.findAll();

	}

	@Override
	public Department GetDepartmentById(int id) {
		Department dep = departmentRepository.findById(id).orElse(null);
		return dep;
	}

	@Override
	public Department SaveDepartment(Department department) {
		Department dep = departmentRepository.save(department);
		return dep;
	}

	@Override
	public void DeleteDepartment(int id) {
		// Department department=departmentRepository.findById(id).orElse(null);

		departmentRepository.deleteById(id);
	}

	@Override
	public void UpdateDepartment(Department department) {
		
		 
		departmentRepository.save(department);
	}
	


}
