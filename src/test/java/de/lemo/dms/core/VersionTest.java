/**
 * File ./src/test/java/de/lemo/dms/core/VersionTest.java
 * Lemo-Data-Management-Server for learning analytics.
 * Copyright (C) 2013
 * Leonard Kappe, Andreas Pursian, Sebastian Schwarzrock, Boris Wenzlaff
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
**/

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

import org.apache.log4j.Logger;
import org.junit.Test;

import de.lemo.dms.core.Version;

/**
 * test for Version class
 * @author Boris Wenzlaff
 *
 */
public class VersionTest {
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * check if the version != "unknown"
	 */
    @Test
    public void testDMSVersion() {
    	Version version = new Version();
    	String v = version.getServerVersion();
    	logger.debug("Version: " + v);
    	assertFalse("wrong version read", v == "unknown");
    }
    
	/**
	 * check if the version != "unknown"
	 */
    @Test
    public void testDBVersion() {
    	Version version = new Version();
    	String v = version.getDBVersion();
    	logger.debug("Version: " + v);
    	assertFalse("wrong version read", v == "unknown");
    }
}