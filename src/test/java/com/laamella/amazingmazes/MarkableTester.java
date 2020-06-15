package com.laamella.amazingmazes;

import com.laamella.amazingmazes.mazemodel.Marker;
import com.laamella.amazingmazes.mazemodel.Markable.ObservableMarkable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MarkableTester {
    @Test
    void test1() {
        Marker key = Marker.singletonInstance();
        ObservableMarkable observableMarkable = new ObservableMarkable();
        observableMarkable.mark(key);
        assertTrue(observableMarkable.isMarked(key));
    }

    @Test
    void test2() {
        Marker key = Marker.singletonInstance();
        ObservableMarkable observableMarkable = new ObservableMarkable();
        observableMarkable.markNumber(key, 15);
        assertTrue(observableMarkable.isMarked(key));
        assertEquals(15, observableMarkable.getNumberMarker(key).intValue());
    }
}
