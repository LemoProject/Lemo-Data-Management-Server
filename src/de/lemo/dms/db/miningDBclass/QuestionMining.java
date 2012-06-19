package de.lemo.dms.db.miningDBclass;

import java.util.HashSet;
import java.util.Set;

import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

/**This class represents the table question.*/
public class QuestionMining  implements IMappingClass{

	private long id;
	private String title;
	private String text;
	private String type;
	private long timecreated;
	private long timemodified;

	private Set<QuizQuestionMining> quiz_question = new HashSet<QuizQuestionMining>();
	private Set<QuestionLogMining> question_log = new HashSet<QuestionLogMining>();

	public boolean equals(IMappingClass o)
	{
		if(o == null || !(o instanceof QuestionMining))
			return false;
		if(o.getId() == this.getId() && (o instanceof QuestionMining))
			return true;
		return false;
	}
	
	/** standard getter for the attribut id
	 * @return the identifier of the question
	 */	
	public long getId() {
		return id;
	}
	/** standard setter for the attribut id
	 * @param id the identifier of the question
	 */	
	public void setId(long id) {
		this.id = id;
	}
	/** standard getter for the attribut title
	 * @return the title of the question
	 */	
	public String getTitle() {
		return title;
	}
	/** standard setter for the attribut title
	 * @param title the title of the question
	 */	
	public void setTitle(String title) {
		this.title = title;
	}
	/** standard getter for the attribut text
	 * @return the text of the question
	 */	
	public String getText() {
		return text;
	}
	/** standard setter for the attribut text
	 * @param text the text of the question
	 */	
	public void setText(String text) {
		this.text = text;
	}
	/** standard getter for the attribut type
	 * @return the type of the question
	 */	
	public String getType() {
		return type;
	}
	/** standard setter for the attribut type
	 * @param type the type of the question
	 */	
	public void setType(String type) {
		this.type = type;
	}
	/** standard getter for the attribut timecreated
	 * @return the timestamp when the question was created 
	 */	
	public long getTimecreated() {
		return timecreated;
	}
	/** standard setter for the attribut timecreated
	 * @param timecreated the timestamp when the question was created 
	 */	
	public void setTimecreated(long timecreated) {
		this.timecreated = timecreated;
	}
	/** standard getter for the attribut timemodified
	 * @return the timestamp when the question was changed the last time
	 */	
	public long getTimemodified() {
		return timemodified;
	}
	/** standard setter for the attribut timemodified
	 * @param timemodified the timestamp when the question was changed the last time
	 */	
	public void setTimemodified(long timemodified) {
		this.timemodified = timemodified;
	}
	
	/** standard setter for the attribut quiz_question
	 * @param quiz_question a set of entrys in the quiz_question table which which relate the quiz to questions
	 */	
	public void setQuiz_question(
			Set<QuizQuestionMining> quiz_question) {
		this.quiz_question = quiz_question;
	}
	/** standard getter for the attribut quiz_question
	 * @return a set of entrys in the quiz_question table which which relate the quiz to questions
	 */	
	public Set<QuizQuestionMining> getQuiz_question() {
		return quiz_question;
	}
	/** standard setter for the attribut quiz_question
	 * @param quiz_question_add this entry will be added to the list of quiz_question in this question
	 * */	
	public void addQuiz_question(QuizQuestionMining quiz_question_add){	
		quiz_question.add(quiz_question_add);	
	}
	/** standard setter for the attribut question_log
	 * @param question_log a set of entrys in the question_log table which are related with the question
	 */	
	public void setQuestion_log(Set<QuestionLogMining> question_log) {
		this.question_log = question_log;
	}
	/** standard getter for the attribut question_log
	 * @return a set of entrys in the question_log table which are related with the question
	 */	
	public Set<QuestionLogMining> getQuestion_log() {
		return question_log;
	}
	/** standard setter for the attribut question_log
	 * @param  question_log_add this entry will be added to the list of question_log in this question
	 * */	
	public void addQuestion_log(QuestionLogMining question_log_add){	
		question_log.add(question_log_add);	
	}
}
