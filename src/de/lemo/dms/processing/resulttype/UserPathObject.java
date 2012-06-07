package de.lemo.dms.processing.resulttype;


public class UserPathObject implements Comparable{
	
	private Long userId;
	private Long timestamp;
	private String title;
	private Long objectId;
	private Long group;
	private String type;
	private String info;
	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getGroup() {
		return group;
	}

	public void setGroup(Long group) {
		this.group = group;
	}

	public UserPathObject()
	{
		
	}
	
	public UserPathObject(Long userId, long timestamp, String title, Long objectId, String type, Long group, String info)
	{
		this.userId = userId;
		this.timestamp = timestamp;
		this.objectId = objectId;
		this.title = title;
		this.group = group;
		this.type = type;
		this.setInfo(info);
		
	}
	
	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Override
	public int compareTo(Object arg0) {
		UserPathObject s;
		try{
			s = (UserPathObject)arg0;
		}catch(Exception e)
		{
			return 0;
		}
		if(this.getUserId() > s.getUserId())
			return 1;
		if(this.getUserId() < s.getUserId())
			return -1;
		if(this.getUserId() == s.getUserId())
		{
			if(this.getTimestamp() > s.getTimestamp())
				return 1;
			if(this.getTimestamp() < s.getTimestamp())
				return -1;
		}
		return 0;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	

}
