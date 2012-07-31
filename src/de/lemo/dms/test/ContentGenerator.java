package de.lemo.dms.test;

import java.util.ArrayList;
import java.util.Random;

import de.lemo.dms.db.miningDBclass.*;
import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

public class ContentGenerator {

	
	
	public  ArrayList<IMappingClass> generateMiningDB(Integer departments, Integer degreesPerDepartment, Integer coursesPerDegree, Long startdate)
	{
		ArrayList<IMappingClass> all = new ArrayList<IMappingClass>();
		
		ArrayList<DepartmentMining> departmentList = new ArrayList<DepartmentMining>();
		ArrayList<DegreeMining> degreeList = new ArrayList<DegreeMining>();
		ArrayList<CourseMining> courseList = new ArrayList<CourseMining>();
		ArrayList<DegreeCourseMining> degreeCourseList = new ArrayList<DegreeCourseMining>();
		ArrayList<DepartmentDegreeMining> departmentDegreeList = new ArrayList<DepartmentDegreeMining>();
		
		Long enddate = startdate + 31536000L; //Create one year space between start and end
		Random randy = new Random(15768000);
		
		
		for(int i = 1; i <= departments; i++)
		{
			DepartmentMining dep = new DepartmentMining();
			dep.setId(i);
			dep.setTitle("Department "+i);
			departmentList.add(dep);
			for(int j = 1 ; j <= degreesPerDepartment; j++)
			{
				DegreeMining deg = new DegreeMining();
				deg.setId(degreeList.size()+1);
				deg.setTitle("Degree "+i+"."+j);
				degreeList.add(deg);
				
				DepartmentDegreeMining depDeg = new DepartmentDegreeMining();
				depDeg.setDegree(deg);
				depDeg.setDepartment(dep);
				depDeg.setId(departmentDegreeList.size()+1);
				
				departmentDegreeList.add(depDeg);
				
				for(int k = 1 ; k <= coursesPerDegree ; k++)
				{
					CourseMining cou = new CourseMining();
					cou.setTitle("Course "+i+"."+j+"."+k);
					
					randy.setSeed(15768000); 				//Half year random
					Long ts = startdate + randy.nextLong();
					cou.setTimecreated(ts);
					randy.setSeed(31536000 - ts);
					ts = ts + randy.nextLong();					
					cou.setTimecreated(ts);
					cou.setEnrolstart(ts + 	172800);		//Two Days after creation
					cou.setEnrolend(ts +   	345600);		//Four days after creation
					cou.setTimemodified(ts +518400);		//Six days after creation
					cou.setStartdate(ts +   	345600);
					cou.setShortname("C "+i+"."+j+"."+k);					
					cou.setId(courseList.size()+1);
					
					DegreeCourseMining degCou = new DegreeCourseMining();
					degCou.setDegree(deg);
					degCou.setCourse(cou);
					degCou.setId(degreeCourseList.size()+1);
					
					
					courseList.add(cou);
					degreeCourseList.add(degCou);
					
				}
			}
		}
		
		all.addAll(departmentList);
		all.addAll(degreeList);
		all.addAll(courseList);
		all.addAll(departmentDegreeList);
		all.addAll(degreeCourseList);
		
		return all;
	}
	
}
