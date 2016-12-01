package com.example.studenthub;

/**
 * Created by Bailey on 11/30/2016.
 */
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;


public class NoteUnitTest{
    @Test
    public void bodyGetSetTest() throws Exception {
        Note expect = new Note("title", "body", 4);
        Note setter = new Note();
        setter.setBody("body");

        assertEquals(expect.getBody(), setter.getBody());
    }

    @Test
    public void titleGetSetTest() throws Exception {
        Note expect = new Note("title", "body", 4);
        Note setter = new Note();
        setter.setTitle("title");

        assertEquals(expect.getTitle(), setter.getTitle());
    }
    @Test
    public void idGetSetTest() throws Exception {
        Note expect = new Note("title", "body", 4);
        Note setter = new Note();
        setter.setId(String.valueOf(4));

        assertEquals(expect.getId(), setter.getId());
    }
}
