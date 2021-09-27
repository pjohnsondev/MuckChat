package muck.server;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProximityFilterTest {

    @Test
    public void testDistFuncction1() {
        ProximityFilter PF = new ProximityFilter();
        assertTrue(PF.dist(0,0,0,0) == 0.0);
    }
    @Test
    public void testDistFuncction2() {
        ProximityFilter PF = new ProximityFilter();
        assertTrue(PF.dist(2,2,2,-4) == 6.0);
    }
    @Test
    public void testDistFuncction3() {
        ProximityFilter PF = new ProximityFilter();
        assertTrue(PF.dist(0,3,0,5) == 2.0);
    }
    @Test
    public void testDistFuncction4() {
        ProximityFilter PF = new ProximityFilter();
        assertTrue(PF.dist(-2,1,-2,3) == 2.0);
    }
}