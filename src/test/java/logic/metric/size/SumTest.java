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
 * Tests for {@link Sum}.
 * @since 9.1.0
 */
public class SumTest {
    /**
     * Tests for:
     * <p>size1 + Size(0, 0) = size1</p>
     * This test depends on {@link Size#equals(Object)} and
     * {@link SizeCalculation#equals(Object)}.
     */
    @Test
    public void sizePlusZeroEqualsSize() {
        final var width = 1342;
        final var height = 2341;
        final var size = new Size2D(width, height);
        final var sum = new Sum(size, new Size2D());
        MatcherAssert.assertThat(
            sum,
            Matchers.equalTo(size)
        );
    }

    /**
     * Test for:
     * <p>size1 + size2 = size3</p>
     * This test depends on {@link Size#equals(Object)} and
     * {@link SizeCalculation}.
     */
    @Test
    public void sizePlusSizeResult() {
        // @checkstyle LocalFinalVariableName (4 lines)
        final var width1 = 234;
        final var height1 = 345;
        final var width2 = 438;
        final var height2 = 4399;
        MatcherAssert.assertThat(
            new Size2D(
                width1 + width2,
                height1 + height2
            ),
            Matchers.equalTo(
                new Sum(
                    new Size2D(width1, height1),
                    new Size2D(width2, height2)
                )
            )
        );
    }
}
