/**
 * File ./src/main/java/de/lemo/dms/connectors/clix2010/clixDBClass/TQtiTestItemD.java
 * Lemo-Data-Management-Server for learning analytics.
 * Copyright (C) 2013
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
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/TQtiTestItemD.java
 * Date 2013-03-07
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.clix2010.clixDBClass;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

/**
 * Mapping class for table TQtiTestItemD.
 * 
 * @author S.Schwarzrock
 *
 */
public class TQtiTestItemD implements IClixMappingClass {

	private TQtiTestItemDPK id;
	private Long content;
	private String question;
	private Long questionType;
	private Long language;
	
	public TQtiTestItemDPK getId() {
		return id;
	}
	
	public void setId(TQtiTestItemDPK id) {
		this.id = id;
	}
	
	public Long getContent() {
		return content;
	}
	
	public void setContent(Long content) {
		this.content = content;
	}
	
	public String getQuestion() {
		return question;
	}
	
	public void setQuestion(String question) {
		this.question = question;
	}
	
	public Long getQuestionType() {
		return questionType;
	}
	
	public void setQuestionType(Long questionType) {
		this.questionType = questionType;
	}
	
	public Long getLanguage() {
		return language;
	}
	
	public void setLanguage(Long language) {
		this.language = language;
	}
	
	
}
