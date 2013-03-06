/**
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/EComposing.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.clix2010.clixDBClass;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

/**
 * Mapping class for table EComposing.
 * 
 * @author S.Schwarzrock
 *
 */
public class TQtiContentComposing implements IClixMappingClass {

	private TQtiContentComposingPK id;
	private Long candidate;
	private Long content;
	private Long container;
	
	
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
	
	public Long getCandidate() {
		return candidate;
	}
	
	public void setCandidate(Long candidate) {
		this.candidate = candidate;
	}
	
	public TQtiContentComposingPK getId() {
		return id;
	}
	
	public void setId(TQtiContentComposingPK id) {
		this.id = id;
	}
	
	
}
