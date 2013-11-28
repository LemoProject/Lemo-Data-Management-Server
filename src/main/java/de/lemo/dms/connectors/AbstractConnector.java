/**
 * File ./src/main/java/de/lemo/dms/connectors/AbstractConnector.java
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
 * File ./main/java/de/lemo/dms/connectors/AbstractConnector.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors;

import java.util.Collections;
import java.util.List;

/**
 * Abstract class for connector implementations
 * @author Sebastian Schwarzrock
 *
 */
public abstract class AbstractConnector implements IConnector {

	private Long id;
	private String name;
	private Long prefix;
	private ESourcePlatform platform;
	private List<Long> courseIdFilter = Collections.emptyList();
	private List<String> courseLoginFilter = Collections.emptyList();
	
	@Override
	public Long getPlatformId() {
		return this.id;
	}

	public void setPlatformId(final Long id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return this.name;
	}

	protected void setName(final String name) {
		this.name = name;
	}

	@Override
	public Long getPrefix() {
		return this.prefix;
	}

	protected void setPrefix(final Long prefix) {
		this.prefix = prefix;
	}

	@Override
	public ESourcePlatform getPlattformType() {
		return this.platform;
	}

	protected void setPlatformType(final ESourcePlatform platformType) {
		this.platform = platformType;
	}

	@Override
	public String toString() {
		return this.id + "-" + this.platform + "-" + this.name;
	}

	/**
	 * @return true if connectors should only load specific courses
	 */
	public boolean isFilterEnabled() {
		return !courseIdFilter.isEmpty();
	}

	/**
	 * The list of course IDs to be loaded by a connectors.
	 * 
	 * @return a list of course IDs
	 */
	public List<Long> getCourseIdFilter() {
		return courseIdFilter;
	}
	
	/**
	 * The list of course IDs to be loaded by a connectors.
	 * 
	 * @return a list of course IDs
	 */
	public List<String> getCourseLoginFilter() {
		return courseLoginFilter;
	}

	/**
	 * @param courseIdFilter
	 *            a list of course IDs
	 */
	public void setCourseIdFilter(List<Long> courseIdFilter) {
		this.courseIdFilter = courseIdFilter;
	}
	
	/**
	 * @param courseLoginFilter
	 *            a list of course logins
	 */
	public void setCourseLoginFilter(List<String> courseLoginFilter) {
		this.courseLoginFilter = courseLoginFilter;
	}
	
}
