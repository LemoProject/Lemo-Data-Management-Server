package de.lemo.dms.db.miningDBclass;

import java.util.HashSet;
import java.util.Set;

import de.lemo.dms.db.miningDBclass.abstractions.ILearningObject;
import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

/**This class represents the table question.*/
public class QuestionMining  implements IMappingClass, ILearningObject{

	private long id;
	private String title;
	private String text;
	private String type;
	private long timeCreated;
	private long timeModified;
	private Long platform;

	private Set<QuizQuestionMining> quizQuestions = new HashSet<QuizQuestionMining>();
	private Set<QuestionLogMining> questionLogs = new HashSet<QuestionLogMining>();

	public boolean equals(IMappingClass o)
	{
		if(!(o instanceof QuestionMining))
			return false;
		if(o.getId() == this.getId() && (o instanceof QuestionMining))
			return true;
		return false;
	}
	
	/** standard getter for the attribute id
	 * @return the identifier of the question
	 */	
	public long getId() {
		return id;
	}
	/** standard setter for the attribute id
	 * @param id the identifier of the question
	 */	
	public void setId(long id) {
		this.id = id;
	}
	/** standard getter for the attribute title
	 * @return the title of the question
	 */	
	public String getTitle() {
		return title;
	}
	/** standard setter for the attribute title
	 * @param title the title of the question
	 */	
	public void setTitle(String title) {
		this.title = title;
	}
	/** standard getter for the attribute text
	 * @return the text of the question
	 */	
	public String getText() {
		return text;
	}
	/** standard setter for the attribute text
	 * @param text the text of the question
	 */	
	public void setText(String text) {
		this.text = text;
	}
	/** standard getter for the attribute type
	 * @return the type of the question
	 */	
	public String getType() {
		return type;
	}
	/** standard setter for the attribute type
	 * @param type the type of the question
	 */	
	public void setType(String type) {
		this.type = type;
	}
	/** standard getter for the attribute timecreated
	 * @return the timestamp when the question was created 
	 */	
	public long getTimecreated() {
		return timeCreated;
	}
	/** standard setter for the attribute timecreated
	 * @param timecreated the timestamp when the question was created 
	 */	
	public void setTimecreated(long timecreated) {
		this.timeCreated = timecreated;
	}
	/** standard getter for the attribute timemodified
	 * @return the timestamp when the question was changed the last time
	 */	
	public long getTimemodified() {
		return timeModified;
	}
	/** standard setter for the attribute timemodified
	 * @param timemodified the timestamp when the question was changed the last time
	 */	
	public void setTimemodified(long timemodified) {
		this.timeModified = timemodified;
	}
	
	/** standard setter for the attribute quiz_question
	 * @param quizQuestion a set of entries in the quiz_question table which which relate the quiz to questions
	 */	
	public void setQuizQuestions(Set<QuizQuestionMining> quizQuestion) {
		this.quizQuestions = quizQuestion;
	}
	/** standard getter for the attribute quiz_question
	 * @return a set of entries in the quiz_question table which which relate the quiz to questions
	 */	
	public Set<QuizQuestionMining> getQuizQuestions() {
		return quizQuestions;
	}
	/** standard setter for the attribute quiz_question
	 * @param quizQuestion this entry will be added to the list of quiz_question in this question
	 * */	
	public void addQuizQuestion(QuizQuestionMining quizQuestion){	
		this.quizQuestions.add(quizQuestion);	
	}
	/** standard setter for the attribute question_log
	 * @param questionLog a set of entries in the question_log table which are related with the question
	 */	
	public void setQuestionLogs(Set<QuestionLogMining> questionLog) {
		this.questionLogs = questionLog;
	}
	/** standard getter for the attribute question_log
	 * @return a set of entries in the question_log table which are related with the question
	 */	
	public Set<QuestionLogMining> getQuestionLogs() {
		return questionLogs;
	}
	/** standard setter for the attribute question_log
	 * @param  questionLog this entry will be added to the list of question_log in this question
	 * */	
	public void addQuestionLog(QuestionLogMining questionLog){	
		this.questionLogs.add(questionLog);	
	}

	public Long getPlatform() {
		return platform;
	}

	public void setPlatform(Long platform) {
		this.platform = platform;
	}
}
