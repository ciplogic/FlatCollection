package com.khlud.ciprian.flatcollection.utils;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Ciprian on 4/27/2016.
 */
public class StringUtilsTest {
    @Test
    public void splitTest() {
        List<String> joined= StringUtils.split("Abc, def", ',', true);

    }

    @Test
    public void joinTest() {
        String joined= StringUtils.join(", ", Arrays.asList("Abc", "def"));
        assertEquals("Abc, def", joined);
    }

}