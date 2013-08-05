/**
 * File ./src/main/java/de/lemo/dms/connectors/clix2010/clixDBClass/TQtiTestPlayer.java
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
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/TQtiTestPlayer.java
 * Date 2013-03-07
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.clix2010.clixDBClass;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

/**
 * Mapping class for table TQtiTestPlayer.
 * 
 * @author S.Schwarzrock
 *
 */
@Entity
@Table(name = "T_QTI_TESTPLAYER")
public class TQtiTestPlayer implements IClixMappingClass {

	private TQtiTestPlayerPK id;
	private Long candidate;
	private Long container;
	private Long content;
	private Double maxScore;
	private String started;
	private String saved;
	private String evaluated;
	private Double evaluatedScore;
	private Long resultStatus;
	private Long remainingDuration;
	private Long runtimeStatus;
	private String created;
	
	@EmbeddedId
	public TQtiTestPlayerPK getId() {
		return id;
	}
	
	public void setId(TQtiTestPlayerPK id) {
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
	
	@Column(name="MAX_SCORE")
	public Double getMaxScore() {
		return maxScore;
	}
	
	public void setMaxScore(Double maxScore) {
		this.maxScore = maxScore;
	}
	
	@Column(name="STARTED")
	public String getStarted() {
		return started;
	}
	
	public void setStarted(String started) {
		this.started = started;
	}
	
	@Column(name="SAVED")
	public String getSaved() {
		return saved;
	}
	
	public void setSaved(String saved) {
		this.saved = saved;
	}
	
	@Column(name="EVALUATED")
	public String getEvaluated() {
		return evaluated;
	}
	
	public void setEvaluated(String evaluated) {
		this.evaluated = evaluated;
	}
	
	@Column(name="EVALUATED_SCORE")
	public Double getEvaluatedScore() {
		return evaluatedScore;
	}
	
	public void setEvaluatedScore(Double evaluatedScore) {
		this.evaluatedScore = evaluatedScore;
	}
	
	@Column(name="RESULT_STATUS")
	public Long getResultStatus() {
		return resultStatus;
	}
	
	public void setResultStatus(Long resultStatus) {
		this.resultStatus = resultStatus;
	}
	
	@Column(name="REMAINING_DURATION")
	public Long getRemainingDuration() {
		return remainingDuration;
	}
	
	public void setRemainingDuration(Long remainingDuration) {
		this.remainingDuration = remainingDuration;
	}

	@Column(name="RUNTIME_STATUS")
	public Long getRuntimeStatus() {
		return runtimeStatus;
	}

	public void setRuntimeStatus(Long runtimeStatus) {
		this.runtimeStatus = runtimeStatus;
	}

	@Column(name="CREATED")
	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}
	
	
	
}
