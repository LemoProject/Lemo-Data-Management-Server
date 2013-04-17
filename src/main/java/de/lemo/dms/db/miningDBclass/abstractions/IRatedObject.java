/**
 * File ./main/java/de/lemo/dms/db/miningDBclass/abstractions/IRatedObject.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.db.miningDBclass.abstractions;

/**
 * Interface for rated objects
 * @author Boris Wenzlaff
 * @author Leonard Kappe
 * @author Sebastian Schwarzrock
 */
public interface IRatedObject extends ILearningObject {

	Double getMaxGrade();

	Long getPrefix();

}
