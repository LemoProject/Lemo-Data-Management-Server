/**
 * File ./main/java/de/lemo/dms/db/miningDBclass/abstractions/IRatedLogObject.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.db.miningDBclass.abstractions;

/**
 * Interface for rated objects as log
 * @author Boris Wenzlaff
 * @author Leonard Kappe
 * @author Sebastian Schwarzrock
 */
public interface IRatedLogObject extends ILogMining {

	Double getGrade();

	Double getFinalGrade();

	Double getMaxGrade();

}
