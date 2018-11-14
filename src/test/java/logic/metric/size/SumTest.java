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
    /*
    Normally, I would test the equality of Size2D and Sum in a separate method,
    but to create Sum, I need Size2D and it would somehow still test whether
    Size is constructed correctly. So in the end I would need to create a Sum
    object without using Size2D. Since I don't have a fake for Size, I can't do
    that so I put this test inside here. I don't see a point creating a fake
    for a class that is as simple as Size2D
     */
    /**
     * Tests for:
     * <p>size1 + Size(0, 0) = size1</p>
     * <p>size1 = size1 + Size(0, 0)</p>
     * This test depends on {@link Size#equals(Object)} and
     * {@link Sum#equals(Object)}.
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
        MatcherAssert.assertThat(
            size,
            Matchers.equalTo(sum)
        );
    }

    /**
     * Tests whether {@link Sum#toString()}} works as expected.
     */
    @Test
    public void correctToString() {
        final var width = 313;
        final var height = 238;
        MatcherAssert.assertThat(
            new Sum(
                new Size2D(width, height),
                new Size2D()
            ).toString(),
            Matchers.equalTo(
                String.format(
                    "Size(width=%d, height=%d)", width, height
                )
            )
        );
    }
}
