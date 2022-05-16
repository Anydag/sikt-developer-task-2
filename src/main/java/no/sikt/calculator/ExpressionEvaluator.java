package no.sikt.calculator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.nonNull;

public class ExpressionEvaluator {

    private static final String UNKNOWN_OPERATION_ERROR = "Supported operations are +\\-*/";
    private static final String ILLEGAL_DIVIDER_ERROR = "Division by zero is not supported";
    private static final String UNKNOWN_EXPRESSION_FORMAT = "Provided syntax is not valid";

    private static final Pattern OPERATION_PATTERN = Pattern.compile("(\\d+) (\\d+) ([+\\-*/])");
    private static final Pattern VALIDATION_PATTERN = Pattern.compile(
        "^|(\\d+) (\\d+) ([+\\-*/])"
            + "|(\\d+) (\\d+) ([+\\-*/]) (\\d+) ([+\\-*/])"
            + "|(\\d+) (\\d+) (\\d+) ([+\\-*/]) ([+\\-*/])"
            + "|(\\d+) (\\d+) (\\d+) ([+\\-*/]) (\\d+) ([+\\-*/]) ([+\\-*/])|$");

    public ExpressionEvaluator() {}

    public static String evaluate(String expression) {
        assert nonNull(expression);

        Matcher validationMatcher = VALIDATION_PATTERN.matcher(expression.trim());
        if (!validationMatcher.matches())
            // TODO: create custom ValidationException
            throw new IllegalArgumentException(UNKNOWN_EXPRESSION_FORMAT);

        StringBuilder parsed = new StringBuilder();
        Matcher operationMatcher = OPERATION_PATTERN.matcher(expression);

        if (!operationMatcher.find()) {
            throw new IllegalArgumentException(UNKNOWN_EXPRESSION_FORMAT);
        }

        String evaluatedFragment = calculateArithmeticOperation(operationMatcher);
        // found fragment is identical to original
        if (expression.equalsIgnoreCase(operationMatcher.group()))
            return evaluatedFragment;

        // replace found fragment with evaluated
        parsed.append(expression, 0, operationMatcher.start())
                .append(evaluatedFragment)
                .append(expression, operationMatcher.end(), expression.length());

        // process recursively
        return evaluate(parsed.toString());
    }

    private static String calculateArithmeticOperation(Matcher twoOperandMatcher) {
        int first = Integer.parseInt(twoOperandMatcher.group(1));
        int second = Integer.parseInt(twoOperandMatcher.group(2));
        // TODO: declare enum
        switch (twoOperandMatcher.group(3)) {
            case "+":
                return String.valueOf(first + second);
            case "-":
                return String.valueOf(first - second);
            case "*":
                return String.valueOf(first * second);
            case "/":
                if (second == 0)
                    throw new IllegalArgumentException(ILLEGAL_DIVIDER_ERROR);
                return  String.valueOf(first / second);
            default: {
                throw new IllegalArgumentException(UNKNOWN_OPERATION_ERROR);
            }
        }
    }
}