/**
 * File ./src/main/java/de/lemo/dms/connectors/lemo_0_8/mapping/QuizQuestionLMS.java
 * Lemo-Data-Management-Server for learning analytics.
 * Copyright (C) 2015
 * Leonard Kappe, Andreas Pursian, Sebastian Schwarzrock, Boris Wenzlaff
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
**/

/**
 * File ./src/main/java/de/lemo/dms/connectors/lemo_0_8/mapping/QuizQuestionLMS.java
 * Date 2015-01-05
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.lemo_0_8.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/** This class represents the relationship between quiz and questions. */
@Entity
@Table(name = "quiz_question")
public class QuizQuestionLMS{

	private long id;
	private long quiz;
	private long question;
	private Long platform;

	
	
	/**
	 * standard getter for the attribute id
	 * 
	 * @return the identifier for the association between quiz and question
	 */
	@Id
	public long getId() {
		return this.id;
	}

	/**
	 * standard setter for the attribute id
	 * 
	 * @param id
	 *            the identifier for the association between quiz and question
	 */
	public void setId(final long id) {
		this.id = id;
	}

	/**
	 * standard getter for the attribute quiz
	 * 
	 * @return the quiz which is associated
	 */
	@Column(name="quiz_id")
	public long getQuiz() {
		return this.quiz;
	}

	/**
	 * standard setter for the attribute quiz
	 * 
	 * @param quiz
	 *            the quiz which is associated
	 */
	public void setQuiz(final long quiz) {
		this.quiz = quiz;
	}

	/**
	 * standard getter for the attribute question
	 * 
	 * @return the question which is associated
	 */
	@Column(name="question_id")
	public long getQuestion() {
		return this.question;
	}

	/**
	 * standard setter for the attribute question
	 * 
	 * @param question
	 *            the question which is associated
	 */
	public void setQuestion(final long question) {
		this.question = question;
	}

	@Column(name="platform")
	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}
}
