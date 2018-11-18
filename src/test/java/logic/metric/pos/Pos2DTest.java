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

package logic.metric.pos;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Tests for {@link Pos}.
 * @since 4.9.0
 */
public class Pos2DTest {
    /**
     * Aims to test, whether the correct result is returned.
     */
    @Test
    public void correctResult() {
        // @checkstyle LocalFinalVariableName (2 lines)
        final var x = 3445;
        final var y = 432;
        MatcherAssert.assertThat(
            x + y,
            Matchers.is(
                new Pos2D(x, y).result(
                    Integer::sum
                )
            )
        );
    }

    /**
     * Tests whether {@link Pos2D#toString()}} works as expected.
     */
    @Test
    public void correctToString() {
        final var x = 31453;
        final var y = 4538;
        MatcherAssert.assertThat(
            // @checkstyle LocalFinalVariableName (1 line)
            new Pos2D(x, y).toString(),
            Matchers.equalTo(
                String.format(
                    "Pos(x=%d, y=%d)", x, y
                )
            )
        );
    }
}
