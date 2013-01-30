/**
 * File ./main/java/de/lemo/dms/db/miningDBclass/abstractions/ICourseRatedObjectAssociation.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.db.miningDBclass.abstractions;

import de.lemo.dms.db.miningDBclass.CourseMining;

/**
 * Interface for the association between the course and an rated obbject
 * @author Boris Wenzlaff
 * @author Leonard Kappe
 * @author Sebastian Schwarzrock
 */
public interface ICourseRatedObjectAssociation {

	IRatedObject getRatedObject();

	CourseMining getCourse();

}
