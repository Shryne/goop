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

package logic.metric.size;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Tests for {@link ScalarSizeCalculation}.
 * @since 10.0.1
 */
public class ScalarSizeCalculationTest {
    /**
     * Tests whether a size addition with 0 will result in the given size.
     */
    @Test
    public void identityMultiplication() {
        final var width = 234;
        final var height = 3243;
        new ScalarSizeCalculation(
            new Size2D(width, height),
            value -> value
        ).applyOn(
            // @checkstyle ParameterName (1 line)
            (resWidth, resHeight) -> {
                MatcherAssert.assertThat(resWidth, Matchers.equalTo(width));
                MatcherAssert.assertThat(resHeight, Matchers.equalTo(height));
            }
        );
    }

    /**
     * Tests whether a size multiplied with 0 will result in size(0|0).
     */
    @Test
    public void sizeMultipliedWithZero() {
        final var width = 45;
        final var height = 4324;
        new ScalarSizeCalculation(
            new Size2D(width, height),
            value -> 0
        ).applyOn(
            // @checkstyle ParameterName (1 line)
            (resWidth, resHeight) -> {
                MatcherAssert.assertThat(resWidth, Matchers.equalTo(0));
                MatcherAssert.assertThat(resHeight, Matchers.equalTo(0));
            }
        );
    }

    /**
     * Tests whether a size multiplied with some value will result in
     * size(width * value|height * value).
     */
    @Test
    public void normalMultiplication() {
        final var width = 564;
        final var height = 342;
        final var multiplier = 44;
        new ScalarSizeCalculation(
            new Size2D(width, height),
            value -> value * multiplier
        ).applyOn(
            // @checkstyle ParameterName (1 line)
            (resWidth, resHeight) -> {
                MatcherAssert.assertThat(
                    resWidth, Matchers.equalTo(width * multiplier)
                );
                MatcherAssert.assertThat(
                    resHeight, Matchers.equalTo(height * multiplier)
                );
            }
        );
    }
}
