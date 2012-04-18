package de.lemo.dms.db.miningDBclass;


//import java.io.Serializable;
import java.util.HashMap;

/**This class represents the relationship between the courses and quiz.*/
public class CourseQuizMining {
//implements Serializable
//	private static final long serialVersionUID = 1L;
	private long id;
	private CourseMining course;
	private	QuizMining quiz;

//	public boolean equals(Object obj) {
//		if(!(obj instanceof Course_quiz_mining)){
//			return false;			
//		}
//		Course_quiz_mining q = (Course_quiz_mining)obj;
//		if(q.quiz == this.quiz && q.course == this.course){
//			return true;
//		}		
//		return false;
//	}	
//
//	private Integer hashcodeValue = null;
//
//	public synchronized int hashCode(){
//	   if( hashcodeValue == null){
//		   if(quiz != null && course != null){
//			   hashcodeValue = 42 + Long.bitCount(this.quiz.getId()) +
//			   Long.bitCount(this.course.getId() +
//				this.quiz.getQtype().length());
//		   }
//		   else{
//			   hashcodeValue = 42 * Long.bitCount(System.currentTimeMillis());  
//		   }
//		   return hashcodeValue;
//	   }
//	   return hashcodeValue.intValue();
//	}
	
	/** standard getter for the attribut course
	 * @return a course in which the quiz is used
	 */		
	public CourseMining getCourse() {
		return course;
	}
	/** standard setter for the attribut course
	 * @param course a course in which the quiz is used
	 */	
	public void setCourse(CourseMining course) {
		this.course = course;
	}
	/** parameterized setter for the attribut course
	 * @param course the id of a course in which the quiz is used
	 * @param courseMining a list of new added courses, which is searched for the course with the id submitted in the course parameter
	 * @param oldCourseMining a list of course in the miningdatabase, which is searched for the course with the id submitted in the course parameter
	 */	
	public void setCourse(long course, HashMap<Long, CourseMining> courseMining, HashMap<Long, CourseMining> oldCourseMining) {		
        
		if(courseMining.get(course) != null)
		{
			this.course = courseMining.get(course);
			courseMining.get(course).addCourse_quiz(this);
		}
		if(this.course == null && oldCourseMining.get(course) != null)
		{
			this.course = oldCourseMining.get(course);
			oldCourseMining.get(course).addCourse_quiz(this);
		}
	}
	
	
	/** standard getter for the attribut quiz
	 * @return the quiz which is used in the course
	 */	
	public QuizMining getQuiz() {
		return quiz;
	}
	/** standard setter for the attribut quiz
	 * @param quiz the quiz which is used in the course
	 */	
	public void setQuiz(QuizMining quiz) {
		this.quiz = quiz;
	}
	/** parameterized setter for the attribut quiz
	 * @param quiz the id of the quiz in which the action takes place
	 * @param quizMining a list of new added quiz, which is searched for the quiz with the qid and qtype submitted in the other parameters
	 * @param oldQuizMining a list of quiz in the miningdatabase, which is searched for the quiz with the qid and qtype submitted in the other parameters
	 */	
	public void setQuiz(long quiz, HashMap<Long, QuizMining> quizMining, HashMap<Long, QuizMining> oldQuizMining) {	
		if(quizMining.get(quiz) != null)
		{
			this.quiz = quizMining.get(quiz);
			quizMining.get(quiz).addCourse_quiz(this);
		}
		if(this.quiz == null && oldQuizMining.get(quiz) != null)
		{
			this.quiz = oldQuizMining.get(quiz);
			oldQuizMining.get(quiz).addCourse_quiz(this);
		}
	}
	/** standard setter for the attribut id
	 * @param id the identifier for the assoziation between course and quiz
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	/** standard getter for the attribut id
	 * @return the identifier for the assoziation between course and quiz
	 */
	public long getId() {
		return id;
	}
}
