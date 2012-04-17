package de.lemo.dms.db.miningDBclass;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

public class DepartmentDegreeMining {

	private long id;
	private DepartmentMining department;
	private	DegreeMining degree;

	/** standard getter for the attribut id
	 * @return the identifier for the assoziation between department and resource
	 */	
	public long getId() {
		return id;
	}

	/** standard setter for the attribut id
	 * @param id the identifier for the assoziation between department and resource
	 */	
	public void setId(long id) {
		this.id = id;
	}

	/** standard getter for the attribut 
	 * @return a department in which the resource is used
	 */	
	public DepartmentMining getDepartment() {
		return department;
	}
	/** standard setter for the attribut department
	 * @param department a department in which the resource is used
	 */		
	public void setDepartment(DepartmentMining department) {
		this.department = department;
	}
	/** parameterized setter for the attribut 
	 * @param department the id of a department in which the resource is used
	 * @param departmentMining a list of new added departments, which is searched for the department with the id submitted in the department parameter
	 * @param oldDepartmentMining a list of department in the miningdatabase, which is searched for the department with the id submitted in the department parameter
	 */	
	public void setDepartment(long department, HashMap<Long, DepartmentMining> departmentMining, HashMap<Long, DepartmentMining> oldDepartmentMining) {		
		if(departmentMining.get(department) != null)
		{
			this.department = departmentMining.get(department);
			departmentMining.get(department).addDepartment_degree(this);
		}
		if(this.department == null && oldDepartmentMining.get(department) != null)
		{
			this.department = oldDepartmentMining.get(department);
			oldDepartmentMining.get(department).addDepartment_degree(this);
		}
	}

	/** standard getter for the attribut degree
	 * @return the degree which is used in the department
	 */	
	public DegreeMining getDegree() {
		return degree;
	}
	/** standard setter for the attribut degree
	 * @param degree the degree which is used in the department
	 */	
	public void setDegree(DegreeMining degree) {
		this.degree = degree;
	}
	/** parameterized setter for the attribut degree
	 * @param degree the id of the degree which is used in the department
	 * @param degreeMining a list of new added degree, which is searched for the degree with the id submitted in the degree parameter
	 * @param oldDegreeMining a list of degree in the miningdatabase, which is searched for the degree with the id submitted in the degree parameter
	 */	
	public void setDegree(long degree, HashMap<Long, DegreeMining> degreeMining, HashMap<Long, DegreeMining> oldDegreeMining) {	
		
		if(degreeMining.get(degree) != null)
		{
			this.degree = degreeMining.get(degree);
			degreeMining.get(degree).addDepartment_degree(this);
		}
		if(this.degree == null && oldDegreeMining.get(degree) != null)
		{
			this.degree = oldDegreeMining.get(degree);
			oldDegreeMining.get(degree).addDepartment_degree(this);
		}
	}
	
}
