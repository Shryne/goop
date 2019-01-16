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

package logic.unit.size;

import java.util.function.BiFunction;
import logic.matcher.CorrectSizeResult;
import logic.time.Expiration;
import logic.time.FakeClock;
import logic.time.SystemClock;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * Tests for {@link Scaling}.
 * @since 9.2.4
 */
public class ScalingTest {
    /**
     * Tests whether the scaling object stays at the start value when
     * {@link Scaling#start()} hasn't been called. The values come from
     * {@link Scaling#result(BiFunction)}.
     */
    @Test
    public void noStartSameValue() {
        // @checkstyle LocalFinalVariableName (4 lines)
        final var width1 = 123;
        final var height1 = 4324;
        final var width2 = 428;
        final var height2 = 2348;
        final var time = 43L;
        MatcherAssert.assertThat(
            new Scaling(
                new Size2D(width1, height1),
                new Size2D(width2, height2),
                new Expiration(new SystemClock(), time)
            ),
            new CorrectSizeResult(width1, height1)
        );
    }

    /**
     * Tests whether the scaling actually happens. This method uses
     * {@link Scaling#result(BiFunction)}. The values are division friendly
     * to reduce test failures just because of rounding differences.
     */
    @Test
    public void scales() {
        // @checkstyle LocalFinalVariableName (4 lines)
        final var width1 = 500;
        final var height1 = 1000;
        final var width2 = 50;
        final var height2 = 100;
        final var scaling = new Scaling(
            new Size2D(width1, height1),
            new Size2D(width2, height2),
            new Expiration(
                new FakeClock(0, 5),
                10L
            )
        );
        scaling.start();
        MatcherAssert.assertThat(
            scaling,
            new CorrectSizeResult(
                width1 + (width2 - width1) / 2,
                height1 + (height2 - height1) / 2
            )
        );
    }

    /**
     * Tests whether the scaling ends at a certain point so that the size stays
     * there. This method uses {@link Scaling#result(BiFunction)}.
     */
    @Test
    public void reachesEndAndStays() {
        // @checkstyle LocalFinalVariableName (4 lines)
        final var width1 = 534;
        final var height1 = 321;
        final var width2 = 342;
        final var height2 = 346;
        final var scaling = new Scaling(
            new Size2D(width1, height1),
            new Size2D(width2, height2),
            new Expiration(
                new FakeClock(0, 5),
                5L
            )
        );
        scaling.start();
        MatcherAssert.assertThat(
            scaling,
            new CorrectSizeResult(width2, height2)
        );
        MatcherAssert.assertThat(
            scaling,
            new CorrectSizeResult(width2, height2)
        );
    }
}
