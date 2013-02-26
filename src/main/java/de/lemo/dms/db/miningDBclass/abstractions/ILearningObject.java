/**
 * File ./main/java/de/lemo/dms/db/miningDBclass/abstractions/ILearningObject.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.db.miningDBclass.abstractions;

/**
 * Interface to get the title and id for an learning object
 * @author Boris Wenzlaff
 * @author Leonard Kappe
 * @author Sebastian Schwarzrock
 */
public interface ILearningObject {

	long getId();

	String getTitle();

}
