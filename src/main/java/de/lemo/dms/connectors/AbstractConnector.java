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
	 * @param courseIdFilter
	 *            a list of course IDs
	 */
	public void setCourseIdFilter(List<Long> courseIdFilter) {
		this.courseIdFilter = courseIdFilter;
	}
	
}
