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
 * Tests for {@link Size}.
 * @since 4.9.0
 */
public class SizeTest {
    /**
     * Tests whether to sizes with different values aren't equal.
     */
    @Test
    public void notEqualSizes() {
        final var first = new Size2D(1434, 213);
        final var second = new Size2D(4325, 4654);
        MatcherAssert.assertThat(
            first,
            Matchers.not(
                Matchers.equalTo(second)
            )
        );
        MatcherAssert.assertThat(
            second,
            Matchers.not(
                Matchers.equalTo(first)
            )
        );
    }

    /**
     * Aims to test, whether the correct result is returned.
     */
    @Test
    public void correctResult() {
        final var width = 3445;
        final var height = 432;
        MatcherAssert.assertThat(
            new Size2D(width, height).result(
                Integer::sum
            ),
            Matchers.is(width + height)
        );
    }
}
