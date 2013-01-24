/**
 * File ./test/java/de/lemo/dms/core/DMSMainTest.java
 *
 * Date 2013-01-24
 *
 * Project Lemo Learning Analytics
 *
 * Copyright TODO (INSERT COPYRIGHT)
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
import de.lemo.dms.db.miningDBclass.ResourceLogMining;

class TestUserThread extends Thread {

	Exception exeption;
    private IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();

    @Override
    public void run() {
        Session session = dbHandler.getMiningSession();
        try {
            List<?> result = session.createCriteria(ResourceLogMining.class).setMaxResults(5000).list();
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
