/**
 * File ./test/java/de/lemo/dms/core/VersionTest.java
 *
 * Date 2013-01-24
 *
 * Project Lemo Learning Analytics
 *
 */

package de.lemo.dms.core;

import static org.junit.Assert.*;
import org.junit.Test;
import de.lemo.dms.core.Version;

/**
 * test for Version class
 * @author Boris Wenzlaff
 *
 */
public class VersionTest {
	
	/**
	 * check if the version != "unknown"
	 */
    @Test
    public void testDMSVersion() {
    	Version version = new Version();
    	String v = version.getServerVersion();
    	System.out.println("Version: " + v);
    	assertFalse("wrong version read", v == "unknown");
    }
    
	/**
	 * check if the version != "unknown"
	 */
    @Test
    public void testDBVersion() {
    	Version version = new Version();
    	String v = version.getDBVersion();
    	System.out.println("Version: " + v);
    	assertFalse("wrong version read", v == "unknown");
    }
}