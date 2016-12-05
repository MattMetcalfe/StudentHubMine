package com.example.studenthub;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import com.google.api.client.util.DateTime;

/**
 * Created by michaelspearing on 12/4/16.
 */

public class EventUnitTest {
    @Test
    public void testTitle() throws Exception {
        mEvent event = new mEvent();
        String title = "This is a title";
        event.setTitle(title);
        assertEquals(event.getTitle(), title);
    }

    @Test
    public void testStart() throws Exception {
        mEvent event = new mEvent();
        DateTime start = new DateTime(System.currentTimeMillis());
        event.setStart(start);
        assertEquals(event.getStart(), start);
    }

    @Test
    public void testEnd() throws Exception {
        mEvent event = new mEvent();
        DateTime end = new DateTime(System.currentTimeMillis());
        event.setEnd(end);
        assertEquals(event.getEnd(), end);
    }

    @Test
    public void testLocation() throws Exception {
        mEvent event = new mEvent();
        String location = "This is a Loction";
        event.setLocation(location);
        assertEquals(event.getLocation(), location);
    }

    @Test
    public void testTimeTill() throws Exception{
        mEvent event = new mEvent();
        DateTime timeTill = new DateTime(System.currentTimeMillis() + 10000);
        event.setStart(timeTill);
        String strTimeTill = "";
        assertEquals(strTimeTill, event.getTimeTill());
    }
}