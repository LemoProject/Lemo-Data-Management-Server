package de.lemo.dms.db;

public class CourseObject {
	
	private Long id;
	private Long firstRequest;
	private Long lastRequest;
	/**
	 * @return the lastRequest
	 */
	public Long getLastRequest() {
		return lastRequest;
	}
	/**
	 * @param lastRequest the lastRequest to set
	 */
	public void setLastRequest(Long lastRequest) {
		this.lastRequest = lastRequest;
	}
	/**
	 * @return the firstRequest
	 */
	public Long getFirstRequest() {
		return firstRequest;
	}
	/**
	 * @param firstRequest the firstRequest to set
	 */
	public void setFirstRequest(Long firstRequest) {
		this.firstRequest = firstRequest;
	}
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	

}
