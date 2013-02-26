/**
 * File ./main/java/de/lemo/dms/db/miningDBclass/abstractions/IMappingClass.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.db.miningDBclass.abstractions;

/**
 * Interface for mapping class to control equality
 * @author Boris Wenzlaff
 * @author Leonard Kappe
 * @author Sebastian Schwarzrock
 */
public interface IMappingClass {

	long getId();

	boolean equals(IMappingClass o);

}
