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

import java.util.function.BiFunction;
import logic.unit.size.Size;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * A matcher for {@link Size}. It compares the result and the given values of
 * the {@link Size#result(BiFunction)} method with the expectation.
 * <p>This class is immutable and thread-safe (based on the assumption that
 * {@link TypeSafeDiagnosingMatcher} is also immutable and thread-safe.</p>
 * @since 14.2.0
 */
public class RightSizeResult implements TypeSafeDiagnosingMatcher<Size> {
    /**
     * The width to compare with.
     */
    private final int width;

    /**
     * The height to compare with.
     */
    private final int height;

    /**
     * Ctor.
     * @param width The expected width. It will be compared with the first
     *  argument of the {@link Size#result(BiFunction)} method.
     * @param height The expected height. It will be compared with the second
     *  argument of the {@link Size#result(BiFunction)} method.
     */
    public RightSizeResult(final int width, final int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    protected boolean matchesSafely(
        final Size size, final Description description
    ) {
        final var check = new BiFunctionCheck<>(Integer::sum, size::result);
        final boolean first = check.checkFirst(this.width, description);
        final boolean second = check.checkSecond(this.height, description);
        final boolean third = check.checkResult(
            this.width + this.height, description
        );
        return first && second && third;
    }

    @Override
    public final void describeTo(final Description description) {
        description.appendText(
            new ExpectationText(
                "Size",
                new NamedExpectation("width", this.width),
                new NamedExpectation("height", this.height)
            ).toString()
        );
    }
}
