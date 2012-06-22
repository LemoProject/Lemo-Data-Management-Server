package de.lemo.dms.connectors.clix2010.clixDBClass;

public class EComponentType {

	private EComponentTypePK id;
	
	public EComponentTypePK getId() {
		return id;
	}

	public void setId(EComponentTypePK id) {
		this.id = id;
	}

	private long component;
	private long characteristic;
	private long language;
	
	public long getCharacteristic() {
		return characteristic;
	}

	public void setCharacteristic(long characteristic) {
		this.characteristic = characteristic;
	}

	public long getLanguage() {
		return language;
	}

	public void setLanguage(long language) {
		this.language = language;
	}

	public EComponentType()
	{
		
	}

	public long getComponent() {
		return component;
	}

	public void setComponent(long component) {
		this.component = component;
	}

	public long getCharacteristicId() {
		return characteristic;
	}

	public void setCharacteristicId(long characteristicId) {
		this.characteristic = characteristicId;
	}
	
	
	
}
