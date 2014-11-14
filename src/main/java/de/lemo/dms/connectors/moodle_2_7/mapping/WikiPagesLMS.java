/**
 * File ./src/main/java/de/lemo/dms/connectors/moodle_2_7/mapping/WikiPagesLMS.java
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
 * File ./main/java/de/lemo/dms/connectors/moodle_2_7/mapping/WikiPages_LMS.java
 * Date 2014-11-13
 * Project Lemo Learning Analytics
 */
package de.lemo.dms.connectors.moodle_2_7.mapping;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Mapping class for table Wiki.
 * 
 * @author S.Schwarzrock
 *
 */
@Entity
@Table(name = "mdl_wiki_pages")
public class WikiPagesLMS {
	
	private long id;
	private long subwikiid;
	private String title;
	/**
	 * @return the title
	 */
	@Column(name="title")
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the subwikiid
	 */
	@Column(name="subwikiid")
	public long getSubwikiid() {
		return subwikiid;
	}
	/**
	 * @param subwikiid the subwikiid to set
	 */
	public void setSubwikiid(long subwikiid) {
		this.subwikiid = subwikiid;
	}
	/**
	 * @return the id
	 */
	@Id
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	

}
