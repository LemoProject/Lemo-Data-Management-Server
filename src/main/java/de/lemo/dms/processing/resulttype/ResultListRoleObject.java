/**
 * File ./main/java/de/lemo/dms/processing/resulttype/ResultListRoleObject.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.processing.resulttype;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * represents a list for RoleObject which is use to transfer data from
 * the dms to the app-server
 * @author Sebastian Schwarzrock
 *
 */
@XmlRootElement
public class ResultListRoleObject {

	private List<RoleObject> roles;

	public ResultListRoleObject() {
	}

	public ResultListRoleObject(final List<RoleObject> roles) {
		this.roles = roles;
	}

	public List<RoleObject> getRoles() {
		return this.roles;
	}

	public void setRoles(final List<RoleObject> roles) {
		this.roles = roles;
	}
}
