package de.lemo.dms.processing.resulttype;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResultListRoleObject {
	private List<RoleObject> roles;

	public ResultListRoleObject() {}
	
	public ResultListRoleObject(List<RoleObject> roles) {
		this.roles = roles;
	}
	
	public List<RoleObject> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleObject> roles) {
		this.roles = roles;
	}
}
