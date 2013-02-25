/**
 * File ./main/java/de/lemo/dms/db/miningDBclass/abstractions/ILogMining.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.db.miningDBclass.abstractions;

import de.lemo.dms.db.miningDBclass.CourseMining;
import de.lemo.dms.db.miningDBclass.UserMining;

/**
 * Interface to make an ILogMining class comparable
 * @author Boris Wenzlaff
 * @author Leonard Kappe
 * @author Sebastian Schwarzrock
 */
public interface ILogMining extends Comparable<ILogMining> {

	long getId();

	long getTimestamp();

	UserMining getUser();

	CourseMining getCourse();

	String getAction();

	String getTitle();

	Long getLearnObjId();

	Long getDuration();

	void setDuration(Long duration);

	Long getPrefix();

	void setId(long id);

}
