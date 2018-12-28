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

import logic.unit.size.Size2D;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Tests for {@link CorrectSizeResult}.
 * @since 15.2.0
 */
public class CorrectSizeResultTest {
    /**
     * Tests whether the matcher matches on a simple Size2D instance. Size2D is
     * chosen because it is the simplest implementation of the Size interface.
     */
    @Test
    public void matchesCorrectSize() {
        final int width = 142;
        final int height = 123;
        MatcherAssert.assertThat(
            new Size2D(width, height),
            new CorrectSizeResult(width, height)
        );
    }

    /**
     * Tests whether the matcher fails when the expected width isn't met by the
     * given size instance.
     */
    @Test
    public void notMatchWrongWidth() {
        final int width = 534;
        final int height = 32;
        MatcherAssert.assertThat(
            new Size2D(width + 1, height),
            Matchers.not(
                new CorrectSizeResult(width, height)
            )
        );
    }

    /**
     * Tests whether the matcher fails when the expected height isn't met by the
     * given size instance.
     */
    @Test
    public void notMatchWrongHeight() {
        final int width = 6732;
        final int height = 125;
        MatcherAssert.assertThat(
            new Size2D(width, height + 1),
            Matchers.not(
                new CorrectSizeResult(width, height)
            )
        );
    }
}
