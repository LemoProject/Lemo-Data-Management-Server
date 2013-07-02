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

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

/**
 * Mapping class for table TQtiTestPlayer.
 * 
 * @author S.Schwarzrock
 *
 */
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
	
	public TQtiTestPlayerPK getId() {
		return id;
	}
	
	public void setId(TQtiTestPlayerPK id) {
		this.id = id;
	}
	
	public Long getCandidate() {
		return candidate;
	}
	
	public void setCandidate(Long candidate) {
		this.candidate = candidate;
	}
	
	public Long getContainer() {
		return container;
	}
	
	public void setContainer(Long container) {
		this.container = container;
	}
	
	public Long getContent() {
		return content;
	}
	
	public void setContent(Long content) {
		this.content = content;
	}
	
	public Double getMaxScore() {
		return maxScore;
	}
	
	public void setMaxScore(Double maxScore) {
		this.maxScore = maxScore;
	}
	
	public String getStarted() {
		return started;
	}
	
	public void setStarted(String started) {
		this.started = started;
	}
	
	public String getSaved() {
		return saved;
	}
	
	public void setSaved(String saved) {
		this.saved = saved;
	}
	
	public String getEvaluated() {
		return evaluated;
	}
	
	public void setEvaluated(String evaluated) {
		this.evaluated = evaluated;
	}
	
	public Double getEvaluatedScore() {
		return evaluatedScore;
	}
	
	public void setEvaluatedScore(Double evaluatedScore) {
		this.evaluatedScore = evaluatedScore;
	}
	
	public Long getResultStatus() {
		return resultStatus;
	}
	
	public void setResultStatus(Long resultStatus) {
		this.resultStatus = resultStatus;
	}
	
	public Long getRemainingDuration() {
		return remainingDuration;
	}
	
	public void setRemainingDuration(Long remainingDuration) {
		this.remainingDuration = remainingDuration;
	}

	public Long getRuntimeStatus() {
		return runtimeStatus;
	}

	public void setRuntimeStatus(Long runtimeStatus) {
		this.runtimeStatus = runtimeStatus;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}
	
	
	
}
