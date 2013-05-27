/**
 * File ./test/java/de/lemo/dms/processing/questions/QCourseUsersTest.java
 *
 * Date 2013-01-24
 *
 * Project Lemo Learning Analytics
 *
 */

package de.lemo.dms.processing.questions;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import com.google.common.collect.Lists;
import de.lemo.dms.processing.resulttype.ResultListLongObject;

public class QCourseUsersTest {

    private QCourseUsers instance;
    private List<Long> courses;
    private Long startTime;
    private Long endTime;

    @Before
    public void setUp() {
        instance = new QCourseUsers();
    }

    @Test
    public void courseSize() {

        courses = Lists.newArrayList(1L);
        startTime = 0L;
        endTime = Long.MAX_VALUE;

        ResultListLongObject result = instance.compute(courses, startTime, endTime, new ArrayList<Long>());

        assertNotNull(result);
        assertEquals(1247L, result.getElements().size());
        // TODO prove that 1247 is the correct number
        // TODO use other element list size, from testing database

    }
}
