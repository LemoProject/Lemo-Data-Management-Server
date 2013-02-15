/**
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/EComponentType.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.connectors.clix2010.clixDBClass;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

/**
 * Mapping class for table EComponentType.
 * 
 * @author S.Schwarzrock
 *
 */
public class EComponentType implements IClixMappingClass {

	private EComponentTypePK id;

	public EComponentTypePK getId() {
		return this.id;
	}

	public void setId(final EComponentTypePK id) {
		this.id = id;
	}

	private Long component;
	private Long characteristic;
	private Long language;
	private String uploadDir;

	public String getString()
	{
		return "EComponentType$$$"
				+ this.getUploadDir() + "$$$"
				+ this.getCharacteristic() + "$$$"
				+ this.getCharacteristicId() + "$$$"
				+ this.getComponent() + "$$$"
				+ this.getLanguage();
	}

	public String getUploadDir() {
		return this.uploadDir;
	}

	public void setUploadDir(final String uploadDir) {
		this.uploadDir = uploadDir;
	}

	public Long getCharacteristic() {
		return this.characteristic;
	}

	public void setCharacteristic(final Long characteristic) {
		this.characteristic = characteristic;
	}

	public Long getLanguage() {
		return this.language;
	}

	public void setLanguage(final Long language) {
		this.language = language;
	}

	public EComponentType()
	{

	}

	public Long getComponent() {
		return this.component;
	}

	public void setComponent(final Long component) {
		this.component = component;
	}

	public Long getCharacteristicId() {
		return this.characteristic;
	}

	public void setCharacteristicId(final Long characteristicId) {
		this.characteristic = characteristicId;
	}

}
