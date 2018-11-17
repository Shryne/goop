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
 * Tests for {@link SizeCalculation}.
 * @since 9.1.1
 */
public class SizeCalculationTest {
    /*
    Normally, I would test the equality of Size2D and SizeCalculation in a
    separate method, but to create SizeCalculation, I need Size2D and it would
    somehow still test whether Size is constructed correctly. So in the end I
    would need to create a SizeCalculation object without using Size2D. Since I
    don't have a fake for Size, I can't do that so I put this test inside
    here. I don't see a point creating a fake for a class that is as simple as
    Size2D
    */
    /**
     * Tests for:
     * <p>Size(a, b) * Size(c, d) = Size(a * c, c * d)</p>
     * <p>Size(a * c, c * d) = Size(a, b) * Size(c, d)</p>
     * This test depends on {@link Size#equals(Object)} and
     * {@link SizeCalculation#equals(Object)}.
     */
    @Test
    public void sizeMultipliedEquals() {
        // @checkstyle LocalFinalVariableName (4 lines)
        final var width1 = 1320;
        final var height1 = 544;
        final var width2 = 352;
        final var height2 = 6543;
        final var expected = new Size2D(width1 * width2, height1 * height2);
        final var result = new SizeCalculation(
            new Size2D(width1, height1),
            new Size2D(width2, height2),
            (first, second) -> first * second
        );
        MatcherAssert.assertThat(
            result,
            Matchers.equalTo(expected)
        );
        MatcherAssert.assertThat(
            expected,
            Matchers.equalTo(result)
        );
    }

    /**
     * Tests whether {@link SizeCalculation#toString()}} works as expected.
     */
    @Test
    public void correctToString() {
        final var width = 313;
        final var height = 238;
        MatcherAssert.assertThat(
            new SizeCalculation(
                new Size2D(width, height),
                new Size2D(),
                Integer::sum
            ).toString(),
            Matchers.equalTo(
                String.format(
                    "Size(width=%d, height=%d)", width, height
                )
            )
        );
    }
}
