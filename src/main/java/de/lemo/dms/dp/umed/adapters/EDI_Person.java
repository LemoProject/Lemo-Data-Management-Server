package de.lemo.dms.dp.umed.adapters;

import de.lemo.dms.dp.ED_Person;
import de.lemo.dms.dp.umed.entities.Person;
import de.lemo.dms.dp.umed.interfaces.IPerson;

public class EDI_Person extends Person implements ED_Person, IPerson {

	@Override
	public String getName() {
		return this.getName();
	}

}
