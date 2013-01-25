/**
 * File ./main/java/de/lemo/dms/db/miningDBclass/abstractions/ICourseRatedObjectAssociation.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.db.miningDBclass.abstractions;

import de.lemo.dms.db.miningDBclass.CourseMining;

public interface ICourseRatedObjectAssociation {

	public IRatedObject getRatedObject();

	public CourseMining getCourse();

}
