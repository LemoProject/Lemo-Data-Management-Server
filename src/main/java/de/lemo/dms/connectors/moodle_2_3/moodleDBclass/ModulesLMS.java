/**
 * File ./main/java/de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Modules_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.moodle_2_3.moodleDBclass;

/**
 * Mapping class for table Modules.
 * 
 * @author S.Schwarzrock, B.Wolf
 *
 */
public class ModulesLMS {

	private long id;
	private String name;

	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

}
