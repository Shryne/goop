/*
 * Copyright 2018 Eugen Deutsch
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

package logic.matcher;

import logic.functional.QuadFunction;
import logic.metric.Insets;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * A matcher for {@link Insets}. It compares the result and the given values
 * of the {@link Insets#result(QuadFunction)} method with the expectation.
 * <p>This class is immutable and thread-safe (based on the assumption that
 * {@link TypeSafeDiagnosingMatcher} is also immutable and thread-safe.</p>
 * @since 13.3.3
 */
public class CorrectInsets extends TypeSafeDiagnosingMatcher<Insets> {
    /**
     * The first expected value. It will be compared with the top value.
     */
    private final Integer first;

    /**
     * The second expected value. It will be compared with the left value.
     */
    private final Integer second;

    /**
     * The third expected value. It will be compared with the right value.
     */
    private final Integer third;

    /**
     * The fourth expected value. It will be compared with the bottom value.
     */
    private final Integer fourth;

    /**
     * Ctor.
     * @param first The value that will be compared with top.
     * @param second The value that will be compared with left.
     * @param third The value that will be compared with right.
     * @param fourth The value that will be compared with bottom.
     * @checkstyle ParameterNumber (2 lines)
     */
    public CorrectInsets(
        final Integer first,
        final Integer second,
        final Integer third,
        final Integer fourth
    ) {
        super();
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
    }

    @Override
    public final void describeTo(final Description description) {
        description.appendText(
            String.join(
                "",
                "Insets with the following values: ",
                "top: ", Integer.toString(this.first),
                ", left: ", Integer.toString(this.second),
                ", right: ", Integer.toString(this.third),
                ", bottom: ", Integer.toString(this.fourth)
            )
        );
    }

    @Override
    protected final boolean matchesSafely(
        final Insets insets, final Description description
    ) {
        boolean result = false;
        if (insets != null) {
            // @checkstyle MethodBodyComments (1 line)
            //noinspection NonShortCircuitBooleanExpression
            result = insets.result(
                // @checkstyle ParameterName (1 line)
                (top, left, right, bottom) -> CorrectInsets.checkSide(
                    top, this.first, "top", description
                ) & CorrectInsets.checkSide(
                    left, this.second, "left", description
                ) & CorrectInsets.checkSide(
                    right, this.third, "right", description
                ) &  CorrectInsets.checkSide(
                    bottom, this.fourth, "bottom", description
                )
            ) && this.correctResult(insets, description);
        }
        return result;
    }

    /**
     * Compares the given side (one of top, left, right, bottom) with the
     * expectation (one of first, second, third, fourth) and adds a failure text
     * to the description if they aren't equal.
     * @param side The side to be compared.
     * @param expected The expected value.
     * @param name The name of the side (for example "top").
     * @param description The description object to add the failure text to.
     * @return True if the side is equal to the expected value, otherwise false.
     * @checkstyle ParameterNumber (2 lines)
     */
    private static boolean checkSide(
        final Integer side,
        final Integer expected,
        final String name,
        final Description description
    ) {
        boolean result = true;
        if (!expected.equals(side)) {
            description.appendText(
                String.join(
                    "",
                    "was ",
                    Integer.toString(side),
                    " which is not the expected value ",
                    Integer.toString(expected),
                    " for side ",
                    name,
                    "\n"
                )
            );
            result = false;
        }
        return result;
    }

    /**
     * Checks if the return of {@link Insets#result(QuadFunction)} is correct
     * and adds a text to the given description object if its not.
     * @param insets The insets to check for.
     * @param description The description object to add the failure text to.
     * @return True if the result was correct and otherwise false.
     */
    private boolean correctResult(
        final Insets insets, final Description description
    ) {
        boolean result = true;
        final int sum = insets.result(
            (top, left, right, bottom) -> top + left + right + bottom
        );
        final int expected =
            this.first + this.second + this.third + this.fourth;
        if (sum != expected) {
            description.appendText(
                String.join(
                    "",
                    "result: ",
                    Integer.toString(sum),
                    " which is not the expected value: ",
                    Integer.toString(expected)
                )
            );
            result = false;
        }
        return result;
    }
}
