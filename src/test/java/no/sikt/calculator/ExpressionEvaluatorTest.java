package no.sikt.calculator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ExpressionEvaluatorTest {
    // TODO: read resources file to get expected values

    @Test
    void shouldValidateAndEvaluateTwoOperands() {
        var expected = "3";
        var expression = "6 2 /";

        var actual = ExpressionEvaluator.evaluate(expression);
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void shouldValidateAndEvaluateThreeOperands() {
        var expected = "4";
        var expression = "7 1 + 2 /";

        var actual = ExpressionEvaluator.evaluate(expression);
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void shouldValidateAndEvaluateThreeInARowOperands() {
        var expected = "-1";
        var expression = "6 5 2 + -";

        var actual = ExpressionEvaluator.evaluate(expression);
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void shouldValidateAndEvaluateFourOperands() {
        var expected = "-9";
        var expression = "2 6 2 + 3 + -";

        var actual = ExpressionEvaluator.evaluate(expression);
        assertNotNull(actual);
        assertEquals(expected, actual);
    }
}
