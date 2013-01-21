package de.lemo.dms.db.miningDBclass;


//import java.io.Serializable;
import java.util.Map;

import de.lemo.dms.db.miningDBclass.abstractions.ICourseRatedObjectAssociation;
import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;
import de.lemo.dms.db.miningDBclass.abstractions.IRatedObject;

/**This class represents the relationship between the courses and quiz.*/
public class CourseQuizMining  implements IMappingClass, ICourseRatedObjectAssociation{
//implements Serializable
//	private static final long serialVersionUID = 1L;
	private long id;
	private CourseMining course;
	private	QuizMining quiz;
	private Long platform;

	public boolean equals(IMappingClass o)
	{
		if(!(o instanceof CourseQuizMining))
			return false;
		if(o.getId() == this.getId() && (o instanceof CourseQuizMining))
			return true;
		return false;
	}
	
	/** standard getter for the attribute course
	 * @return a course in which the quiz is used
	 */		
	public CourseMining getCourse() {
		return course;
	}
	/** standard setter for the attribute course
	 * @param course a course in which the quiz is used
	 */	
	public void setCourse(CourseMining course) {
		this.course = course;
	}
	/** parameterized setter for the attribute course
	 * @param course the id of a course in which the quiz is used
	 * @param courseMining a list of new added courses, which is searched for the course with the id submitted in the course parameter
	 * @param oldCourseMining a list of course in the miningdatabase, which is searched for the course with the id submitted in the course parameter
	 */	
	public void setCourse(long course, Map<Long, CourseMining> courseMining, Map<Long, CourseMining> oldCourseMining) {		
        
		if(courseMining.get(course) != null)
		{
			this.course = courseMining.get(course);
			courseMining.get(course).addCourseQuiz(this);
		}
		if(this.course == null && oldCourseMining.get(course) != null)
		{
			this.course = oldCourseMining.get(course);
			oldCourseMining.get(course).addCourseQuiz(this);
		}
	}
	
	
	/** standard getter for the attribute quiz
	 * @return the quiz which is used in the course
	 */	
	public QuizMining getQuiz() {
		return quiz;
	}
	/** standard setter for the attribute quiz
	 * @param quiz the quiz which is used in the course
	 */	
	public void setQuiz(QuizMining quiz) {
		this.quiz = quiz;
	}
	/** parameterized setter for the attribute quiz
	 * @param quiz the id of the quiz in which the action takes place
	 * @param quizMining a list of new added quiz, which is searched for the quiz with the qid and qtype submitted in the other parameters
	 * @param oldQuizMining a list of quiz in the miningdatabase, which is searched for the quiz with the qid and qtype submitted in the other parameters
	 */	
	public void setQuiz(long quiz, Map<Long, QuizMining> quizMining, Map<Long, QuizMining> oldQuizMining) {	
		if(quizMining.get(quiz) != null)
		{
			this.quiz = quizMining.get(quiz);
			quizMining.get(quiz).addCourseQuiz(this);
		}
		if(this.quiz == null && oldQuizMining.get(quiz) != null)
		{
			this.quiz = oldQuizMining.get(quiz);
			oldQuizMining.get(quiz).addCourseQuiz(this);
		}
	}
	/** standard setter for the attribute id
	 * @param id the identifier for the association between course and quiz
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	/** standard getter for the attribute id
	 * @return the identifier for the association between course and quiz
	 */
	public long getId() {
		return id;
	}

	public Long getPlatform() {
		return platform;
	}

	public void setPlatform(Long platform) {
		this.platform = platform;
	}

	@Override
	public IRatedObject getRatedObject() {
		return this.quiz;
	}
}
