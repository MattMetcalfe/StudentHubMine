package com.example.studenthub;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Bailey on 11/30/2016.
 */
public class TaskUnitTest {
    @Test
    public void idIntGetSetTest() throws Exception {
        Task expect = new Task("label", 16);
        Task setter = new Task();
        setter.setId(16);

        assertEquals(expect.getId(), setter.getId());
    }
    @Test
    public void idStrGetSetTest() throws Exception {
        Task expect = new Task("label", 7);
        Task setter = new Task();
        setter.setId(String.valueOf(7));

        assertEquals(expect.getId(), setter.getId());
    }

    @Test
    public void labelGetSetTest() throws Exception {
        Task expect = new Task("label", 16);
        Task setter = new Task();
        setter.setLabel("label");

        assertEquals(expect.getLabel(), setter.getLabel());
    }

}
