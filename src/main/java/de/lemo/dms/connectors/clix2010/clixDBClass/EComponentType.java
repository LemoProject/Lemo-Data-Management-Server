/**
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/EComponentType.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
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
	private final static String DOLLAR = "$$$";

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
		return "EComponentType" + DOLLAR
				+ this.getUploadDir() + DOLLAR
				+ this.getCharacteristic() + DOLLAR
				+ this.getCharacteristicId() + DOLLAR
				+ this.getComponent() + DOLLAR
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
