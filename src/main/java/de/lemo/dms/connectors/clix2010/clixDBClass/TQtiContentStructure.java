/**
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/TQtiContentStructure.java
 * Date 2013-03-07
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.clix2010.clixDBClass;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

/**
 * Mapping class for table TQtiContentStructure.
 * 
 * @author S.Schwarzrock
 *
 */
public class TQtiContentStructure implements IClixMappingClass {

	private Long id;
	private Long container;
	private Long content;
	private String created;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
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
	
	public String getCreated() {
		return created;
	}
	
	public void setCreated(String created) {
		this.created = created;
	}


}
