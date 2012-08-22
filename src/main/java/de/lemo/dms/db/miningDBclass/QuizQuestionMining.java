package de.lemo.dms.db.miningDBclass;


import java.util.HashMap;

import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

/**This class represents the relationship between quiz and questions.*/
public class QuizQuestionMining  implements IMappingClass{

	private long id;
	private QuizMining quiz;
	private	QuestionMining question;

	public boolean equals(IMappingClass o)
	{
		if(o == null || !(o instanceof QuizQuestionMining))
			return false;
		if(o.getId() == this.getId() && (o instanceof QuizQuestionMining))
			return true;
		return false;
	}
	
	/** standard getter for the attribut id
	 * @return the identifier for the assoziation between quiz and question
	 */	
	public long getId() {
		return id;
	}
	/** standard setter for the attribut id
	 * @param id the identifier for the assoziation between quiz and question
	 */	
	public void setId(long id) {
		this.id = id;
	}
	
	/** standard getter for the attribut quiz
	 * @return the quiz which is assoziated
	 */	
	public QuizMining getQuiz() {
		return quiz;
	}
	/** standard setter for the attribut quiz
	 * @param quiz the quiz which is assoziated
	 */	
	public void setQuiz(QuizMining quiz) {
		this.quiz = quiz;
	}
	/** parameterized setter for the attribut quiz
	 * @param quiz the id of the quiz which is assoziated
	 * @param quizMining a list of new added quiz, which is searched for the quiz with the id submitted in the quiz parameter
	 * @param oldQuizMining a list of quiz in the miningdatabase, which is searched for the quiz with the id submitted in the quiz parameter
	 */	
	public void setQuiz(long quiz, HashMap<Long, QuizMining> quizMining, HashMap<Long, QuizMining> oldQuizMining) {		
		
		if(quizMining.get(quiz) != null)
		{
			this.quiz = quizMining.get(quiz);
			quizMining.get(quiz).addQuiz_question(this);
		}
		if(this.quiz == null && oldQuizMining.get(quiz) != null)
		{
			this.quiz = oldQuizMining.get(quiz);
			oldQuizMining.get(quiz).addQuiz_question(this);
		}
	}
	
	/** standard getter for the attribut question
	 * @return the question which is assoziated
	 */	
	public QuestionMining getQuestion() {
		return question;
	}
	/** standard setter for the attribut question
	 * @param question the question which is assoziated
	 */	
	public void setQuestion(QuestionMining question) {
		this.question = question;
	}
	/** parameterized setter for the attribut question
	 * @param question the quiz which is assoziated
	 * @param questionMining a list of new added question, which is searched for the question with the id submitted in the question parameter
	 * @param oldQuestionMining a list of question in the miningdatabase, which is searched for the question with the id submitted in the question parameter
	 */	
	public void setQuestion(long question, HashMap<Long, QuestionMining> questionMining, HashMap<Long, QuestionMining> oldQuestionMining) {	
		
		if(questionMining.get(question) != null)
		{
			this.question = questionMining.get(question);
			questionMining.get(question).addQuiz_question(this);
		}
		if(this.question == null && oldQuestionMining.get(question) != null)
		{
			this.question = oldQuestionMining.get(question);
			oldQuestionMining.get(question).addQuiz_question(this);
		}
	}
}
