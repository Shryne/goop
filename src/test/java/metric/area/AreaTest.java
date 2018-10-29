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

package metric.area;

import logic.metric.area.Area2D;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Tests for {@link logic.metric.area.Area}.
 * @since 3.4.0
 */
public class AreaTest {
    /**
     * A test whether the applyOn method does correctly divide the position
     * and size for the QuadConsumer.
     */
    @Test
    public void correctDistribution() {
        // @checkstyle LocalFinalVariableName (4 lines)
        final var resX = 203;
        final var resY = 132;
        final var resW = 3231;
        final var resH = 32;
        new Area2D(resX, resY, resW, resH).applyOn(
            // @checkstyle ParameterName (1 line)
            (x, y, width, height) -> {
                MatcherAssert.assertThat(x, Matchers.is(resX));
                MatcherAssert.assertThat(y, Matchers.is(resY));
                MatcherAssert.assertThat(width, Matchers.is(resW));
                MatcherAssert.assertThat(height, Matchers.is(resH));
            }
        );
    }
}
