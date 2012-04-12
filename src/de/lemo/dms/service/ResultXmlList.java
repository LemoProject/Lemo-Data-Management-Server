package de.lemo.dms.service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Result List for WebService with Objects and Type
 * @author Boris Wenzlaff
 *
 */
@XmlRootElement
public class ResultXmlList {
	private List<String> resultList;
	
	public List<String> getResultList() {
		return resultList;
	}
	
	public void setResultList(List<String> resultList) {
		this.resultList = resultList;
	}
	
	public void setResultList(Set<String> resultList) {
		this.resultList = new ArrayList<String>();
		for(Object key : resultList) {
			this.resultList.add(key.toString());
		}
	}
}
