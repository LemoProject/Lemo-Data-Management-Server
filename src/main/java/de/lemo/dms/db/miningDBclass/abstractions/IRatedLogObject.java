/**
 * File ./main/java/de/lemo/dms/db/miningDBclass/abstractions/IRatedLogObject.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.db.miningDBclass.abstractions;

public interface IRatedLogObject extends ILogMining {

	Double getGrade();

	Double getFinalGrade();

	Double getMaxGrade();

}
