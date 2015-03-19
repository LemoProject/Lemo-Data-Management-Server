package de.lemo.dms.processing.questions;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.processing.FeatureProcessor;
import de.lemo.dms.processing.resulttype.ResultListUserInstance;
import de.lemo.dms.processing.resulttype.UserInstance;

public class QDatabaseTest {

	private QDatabase qDatabase;
	private Long testCourseId;
	private Long startTime;
	private Long endTime;
	private Long trainCourseId;

	@Before
	public void setUp() {
		ServerConfiguration.getInstance().loadConfig("/lemo");
		testCourseId = 2L;
		trainCourseId =  2L;
		startTime = 0L;
		endTime = Long.MAX_VALUE;
		qDatabase = new QDatabase();
	}
	
	//@Test
	public void generateUserInstancesFromFeatures(){
		List<UserInstance> studentInstances = qDatabase.generateUserInstancesFromFeatures(testCourseId);
		assertNotNull(studentInstances);
		assertTrue(ArrayList.class.isInstance(studentInstances));
	}
	
	@Test
	public void changeTimeintervalForTrainingTest(){
		setPrivateProperty(qDatabase,"testCourseId", 0L);
		setPrivateProperty(qDatabase,"trainCourseId", 1L);
		setPrivateProperty(qDatabase,"startTime", 0L);
		setPrivateProperty(qDatabase,"endTime", Long.MAX_VALUE);
		excutePrivateMethod(qDatabase,"changeTimeintervalForTraining");
		Long result = getPrivateProperty(qDatabase,"endTime");
		assertTrue(result > 0);
	}
	
	private Long getPrivateProperty(Object targetObject, String fieldName) {
		Field field = null;
		Long returnValue = null;
		try {
			field = targetObject.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			returnValue = (Long) field.get(targetObject);
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnValue;
	}

	private Object excutePrivateMethod(Object targetObject, String methodName){
		Method method = null;
		Object returnObj = null;
		try {
			method = targetObject.getClass().getDeclaredMethod(methodName);
			method.setAccessible(true);
			returnObj = method.invoke(targetObject);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnObj;		
	}
	
	private void setPrivateProperty(Object targetObject,String fieldName, Long value){
		Field field = null;
		try {
			field = targetObject.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(targetObject, value);
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void computeOneCourse(){
		ResultListUserInstance resultLarge  = qDatabase.compute(testCourseId, startTime, endTime, trainCourseId);
		assertNotNull(resultLarge.getElements());		
	}
	
	@Test
	public void computeMathe1(){
		Long mathe1StartTime = 1381795200L;
		Long mathe1EndTime = 1395360000L;
		Long fuenfTage = 432000L;
		ResultListUserInstance resultLarge  = qDatabase.compute(0L, mathe1StartTime+fuenfTage, mathe1EndTime, 0L);
		assertNotNull(resultLarge.getElements());		
	}

	@Test
	public void computeMathe2(){
		Long mathe2StartTime = 1397433600L;
		Long mathe2EndTime = 1406764800L;
		Long fuenfTage = 432000L;
		ResultListUserInstance resultLarge  = qDatabase.compute(2L, mathe2StartTime+fuenfTage, mathe2EndTime, 2L);
		assertNotNull(resultLarge.getElements());		
	}
	
	@Test
	public void computeStoryelling(){
		Long storytellingStartTime = 1382659200L;
		Long storytellingEndTime = 1387497600L;
		Long fuenfTage = 432000L;
		ResultListUserInstance resultLarge  = qDatabase.compute(1L, storytellingStartTime+fuenfTage, storytellingEndTime, 1L);
		assertNotNull(resultLarge.getElements());		
	}

	//@Test
	public void compute() {
		ResultListUserInstance resultLarge  = qDatabase.compute(testCourseId, startTime, endTime, trainCourseId);
		assertNotNull(resultLarge.getElements());
		startTime=1376263683L;
		endTime=1379643589L;
		ResultListUserInstance result = qDatabase.compute(testCourseId, startTime, endTime, trainCourseId);
		System.out.println("Large: "+resultLarge.getElements().size());
		System.out.println("Smaller: "+result.getElements().size());
		assertTrue(resultLarge.getElements().size()>result.getElements().size());		
	}
}
