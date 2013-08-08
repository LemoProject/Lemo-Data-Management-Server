/**
 * File ./src/main/java/de/lemo/dms/connectors/clix2010/mapping/TQtiTestPlayerResp.java
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
 * File ./main/java/de/lemo/dms/connectors/clix2010/mapping/TQtiTestPlayerResp.java
 * Date 2013-03-07
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.clix2010.mapping;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import de.lemo.dms.connectors.clix2010.mapping.abstractions.IClixMappingClass;

/**
 * Mapping class for table TQtiTestPlayerResp.
 * 
 * @author S.Schwarzrock
 *
 */
@Entity
@Table(name = "T_QTI_TESTPLAYER_RESP")
public class TQtiTestPlayerResp implements IClixMappingClass {

	private TQtiTestPlayerRespPK id;
	private Long candidate;
	private Long container;
	private Long content;
	private Long response;
	private Long subitemPosition;
	private Long testItem;
	private String evaluationDate;
	private Double evaluatedScore;
	private Long itemResultStatus;
	private Long itemProcessStatus;
	private Long processStatus;
	private String text;
	
	@EmbeddedId
	public TQtiTestPlayerRespPK getId() {
		return id;
	}
	
	public void setId(TQtiTestPlayerRespPK id) {
		this.id = id;
	}
	
	@Column(name="CANDIDATE_ID")
	public Long getCandidate() {
		return candidate;
	}
	
	public void setCandidate(Long candidate) {
		this.candidate = candidate;
	}
	
	@Column(name="CONTAINER_ID")
	public Long getContainer() {
		return container;
	}
	
	public void setContainer(Long container) {
		this.container = container;
	}
	
	@Column(name="CONTENT_ID")
	public Long getContent() {
		return content;
	}
	
	public void setContent(Long content) {
		this.content = content;
	}
	
	@Column(name="RESPONSE_ID")
	public Long getResponse() {
		return response;
	}
	
	public void setResponse(Long response) {
		this.response = response;
	}
	
	@Column(name="SUBITEM_POSITION")
	public Long getSubitemPosition() {
		return subitemPosition;
	}
	
	public void setSubitemPosition(Long subitemPosition) {
		this.subitemPosition = subitemPosition;
	}
	
	@Column(name="TESTITEM_ID")
	public Long getTestItem() {
		return testItem;
	}
	
	public void setTestItem(Long testItem) {
		this.testItem = testItem;
	}

	@Column(name="EVALUATION_DATE")
	public String getEvaluationDate() {
		return evaluationDate;
	}

	public void setEvaluationDate(String evaluationDate) {
		this.evaluationDate = evaluationDate;
	}

	@Column(name="EVALUATED_SCORE")
	public Double getEvaluatedScore() {
		return evaluatedScore;
	}

	public void setEvaluatedScore(Double evaluatedScore) {
		this.evaluatedScore = evaluatedScore;
	}

	@Column(name="ITEM_RESULT_STATUS")
	public Long getItemResultStatus() {
		return itemResultStatus;
	}

	public void setItemResultStatus(Long itemResultStatus) {
		this.itemResultStatus = itemResultStatus;
	}

	@Column(name="ITEM_PROCESS_STATUS")
	public Long getItemProcessStatus() {
		return itemProcessStatus;
	}

	public void setItemProcessStatus(Long itemProcessStatus) {
		this.itemProcessStatus = itemProcessStatus;
	}
	
	@Column(name="PROCESS_STATUS")
	public Long getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(Long processStatus) {
		this.processStatus = processStatus;
	}

	@Column(name="RESPONSE")
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
}
