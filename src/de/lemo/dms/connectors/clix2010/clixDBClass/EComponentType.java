package de.lemo.dms.connectors.clix2010.clixDBClass;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

public class EComponentType  implements IClixMappingClass{

	private EComponentTypePK id;
	
	public EComponentTypePK getId() {
		return id;
	}

	public void setId(EComponentTypePK id) {
		this.id = id;
	}

	private Long component;
	private Long characteristic;
	private Long language;
	private String uploadDir;
	
	
	public String getString()
	{
		return "EComponentTypeä$"
				+this.getUploadDir()+"ä$"
				+this.getCharacteristic()+"ä$"
				+this.getCharacteristicId()+"ä$"
				+this.getComponent()+"ä$"
				+this.getLanguage();
	}
	
	public String getUploadDir() {
		return uploadDir;
	}

	public void setUploadDir(String uploadDir) {
		this.uploadDir = uploadDir;
	}

	public Long getCharacteristic() {
		return characteristic;
	}

	public void setCharacteristic(Long characteristic) {
		this.characteristic = characteristic;
	}

	public Long getLanguage() {
		return language;
	}

	public void setLanguage(Long language) {
		this.language = language;
	}

	public EComponentType()
	{
		
	}

	public Long getComponent() {
		return component;
	}

	public void setComponent(Long component) {
		this.component = component;
	}

	public Long getCharacteristicId() {
		return characteristic;
	}

	public void setCharacteristicId(Long characteristicId) {
		this.characteristic = characteristicId;
	}
	
	
	
}
