/**
 * File ./src/test/java/de/lemo/dms/core/DMSMainTest.java
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
 * File ./test/java/de/lemo/dms/core/DMSMainTest.java
 *
 * Date 2013-01-24
 *
 * Project Lemo Learning Analytics
 *
 */

package de.lemo.dms.core;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.junit.Test;

import com.google.common.collect.Lists;

import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.mapping.AccessLog;

class TestUserThread extends Thread {

	Exception exeption;
    private IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();

    @Override
    public void run() {
        Session session = dbHandler.getMiningSession();
        try {
            List<?> result = session.createCriteria(AccessLog.class).setMaxResults(5000).list();
            assertNotNull(result);
            assertFalse(result.isEmpty());
        } catch (Exception e) {
            exeption = e;
        } finally {
            dbHandler.closeSession(session);
        }
    }

}

public class DMSMainTest {

    private static final int CONNECTIONS = 1;

     @Test
    public void concurrentMiningQueries() {

        ArrayList<TestUserThread> threads = Lists.newArrayList();
        for(int i = 0; i < CONNECTIONS; i++) {
            threads.add(new TestUserThread());
        }

        for(TestUserThread thread : threads) {
            thread.start();
        }

        for(;;) {
            boolean allRunning = false;
            for(TestUserThread thread : threads) {
                assertNull(thread.exeption);
                if(allRunning = thread.isAlive())
                    break;
            }
            if(!allRunning)
                break;
        }

    }
}
