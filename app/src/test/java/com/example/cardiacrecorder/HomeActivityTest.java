package com.example.cardiacrecorder;

import static com.example.cardiacrecorder.Measurement.measurementArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class HomeActivityTest {
    @Test
    public void testAddMes() {
        Measurement mes = new Measurement("12-07-22", "03:26", "130", "85", "72", "Dummy Comment");
        measurementArrayList.add(mes);
        assertEquals(1, measurementArrayList.size());
        assertTrue(measurementArrayList.contains(mes));

        Measurement mes1 = new Measurement("13-07-22", "02:26", "120", "85", "72", "Dummy Comment");
        measurementArrayList.add(mes1);
        assertEquals(2, measurementArrayList.size());
        assertTrue(measurementArrayList.contains(mes));
        assertTrue(measurementArrayList.contains(mes1));
        measurementArrayList.clear();
    }

    /**
     * This test method is for testing whether a health record has been got which is added
     */
    @Test
    public void testGetMes() {
        Measurement mes = new Measurement("12-07-22", "03:26", "130", "85", "72", "Dummy Comment");
        measurementArrayList.add(mes);
        assertEquals(true, measurementArrayList.get(0).equals(mes));
        measurementArrayList.clear();
    }

    /**
     * This test method is for testing whether a health record is deleted or not
     */
    @Test
    public void testDelMes() {
        Measurement mes = new Measurement("12-07-22", "03:26", "130", "85", "72", "Dummy Comment");
        measurementArrayList.add(mes);
        assertTrue(measurementArrayList.contains(mes));

        measurementArrayList.remove(mes);
        assertFalse(measurementArrayList.contains(mes));
        measurementArrayList.clear();
    }

    /**
     * This test method is for testing whether a health record is replaced or not
     */
    @Test
    public void testEditMes() {
        Measurement mes = new Measurement("12-07-22", "03:26", "130", "85", "72", "Dummy Comment");
        measurementArrayList.add(mes);
        assertTrue(measurementArrayList.contains(mes));

        Measurement mes1 = new Measurement("13-07-22", "02:26", "120", "85", "72", "Dummy Comment");
        measurementArrayList.set(0, mes1);
        assertFalse(measurementArrayList.contains(mes));
        assertTrue(measurementArrayList.contains(mes1));
        measurementArrayList.clear();
    }

}
