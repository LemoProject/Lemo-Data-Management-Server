/**
 * File ./main/java/de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Role_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.connectors.moodleNumericId.moodleDBclass;

/**
 * Mapping class for table Role.
 * 
 * @author S.Schwarzrock, B.Wolf
 *
 */
public class RoleLMS {

	private long id;
	private String name;
	private String shortname;
	private String description;// text
	private long sortorder;

	public void setId(final long id) {
		this.id = id;
	}

	public long getId() {
		return this.id;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public String getShortname() {
		return this.shortname;
	}

	public void setShortname(final String shortname) {
		this.shortname = shortname;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public long getSortorder() {
		return this.sortorder;
	}

	public void setSortorder(final long sortorder) {
		this.sortorder = sortorder;
	}

}
