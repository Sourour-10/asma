package tn.esprit.spring.IService;

import java.util.List;

import tn.esprit.spring.entity.Department;

public interface IDepartmentService {

	public List<Department> GetAllDepartments();
	public Department GetDepartmentById(int id);
	public Department SaveDepartment (Department department);
	public void DeleteDepartment(int id);
	public void UpdateDepartment (Department department);
	

}
