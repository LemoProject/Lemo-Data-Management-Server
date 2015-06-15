package de.lemo.dms.dp.umed.adapters;

import java.util.List;

import de.lemo.dms.dp.ED_Object;
import de.lemo.dms.dp.umed.entities.LearningObject;
import de.lemo.dms.dp.umed.interfaces.IObject;

public class EDI_Object extends LearningObject implements ED_Object, IObject{

	@Override
	public String getName() {
		return this.getName();
	}

	@Override
	public ED_Object getParentObject() {
		return (EDI_Object) this.getParent();
	}

	@Override
	public List<ED_Object> getChildren() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getType() {
		return this.getType();
	}

}
