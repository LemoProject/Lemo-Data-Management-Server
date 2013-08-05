/**
 * File ./src/main/java/de/lemo/dms/connectors/clix2010/clixDBClass/TQtiEvalAssessment.java
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
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/TQtiEvalAssessment.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.clix2010.clixDBClass;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

/**
 * Mapping class for table TQtiEvalAssessment.
 * 
 * @author S.Schwarzrock
 *
 */
@Entity
@Table(name = "T_QTI_EVAL_ASSESSMENT")
public class TQtiEvalAssessment implements IClixMappingClass {

	private Long id;
	private Long component;
	private Long candidate;
	private Long assessment;
	private Long evaluatedScore;
	private Long evalCount;
	private String lastInvocation;

	@Id
	@Column(name="EVALASS_ID")
	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	@Column(name="COMPONENT_ID")
	public Long getComponent() {
		return this.component;
	}

	public void setComponent(final Long component) {
		this.component = component;
	}

	@Column(name="CANDIDATE_ID")
	public Long getCandidate() {
		return this.candidate;
	}

	public void setCandidate(final Long candidate) {
		this.candidate = candidate;
	}

	@Column(name="ASSESSMENT_ID")
	public Long getAssessment() {
		return this.assessment;
	}

	public void setAssessment(final Long assessment) {
		this.assessment = assessment;
	}

	@Column(name="EVALUATED_SCORE")
	public Long getEvaluatedScore() {
		return this.evaluatedScore;
	}

	public void setEvaluatedScore(final Long evaluatedScore) {
		this.evaluatedScore = evaluatedScore;
	}

	@Column(name="EVAL_COUNT")
	public Long getEvalCount() {
		return this.evalCount;
	}

	public void setEvalCount(final Long evalCount) {
		this.evalCount = evalCount;
	}

	@Column(name="LASTINVOCATION")
	public String getLastInvocation() {
		return this.lastInvocation;
	}

	public void setLastInvocation(final String lastInvocation) {
		this.lastInvocation = lastInvocation;
	}

	public TQtiEvalAssessment()
	{

	}
}
