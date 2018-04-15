package test.pl.lodz.p.pl;

import pl.lodz.p.pl.SudokuConstants;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;
import static pl.lodz.p.pl.SudokuConstants.*;

public class SudokuConstantsTest {

    @Test
    public void Constants_IndexBounds_Test()
    {
        assertFalse(isIndexInBounds(-1));
        assertTrue(isIndexInBounds(0));
        assertFalse(isIndexInBounds(9));
    }
    @Test
    public void Constants_ValueBounds_Test()
    {
        assertFalse(isFieldValueInBounds(-1));
        assertTrue(isFieldValueInBounds(0));
        assertTrue(isFieldValueInBounds(9));
        assertFalse(isFieldValueInBounds(10));
    }
}
