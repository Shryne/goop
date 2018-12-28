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

package logic.unit.area;

import java.util.function.BiFunction;
import java.util.function.ObjIntConsumer;
import logic.unit.pos.Pos2D;
import logic.unit.size.Size2D;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Tests for {@link Area}.
 * @since 3.4.0
 */
public class AreaTest {
    /**
     * A test whether the applyOn method gives the right values into the
     * consumer. This test depends on {@link Pos2D#applyOn(ObjIntConsumer)} and
     * {@link Size2D#result(BiFunction)}.
     */
    @Test
    public void correctApply() {
        // @checkstyle LocalFinalVariableName (4 lines)
        final var resX = 42;
        final var resY = 563;
        final var resW = 34324;
        final var resH = 233;
        new Area2D(
            new Pos2D(resX, resY),
            new Size2D(resW, resH)
        ).applyOn(
            (pos, size) -> {
                pos.applyOn(
                    // @checkstyle ParameterName (1 line)
                    (x, y) -> {
                        MatcherAssert.assertThat(x, Matchers.is(resX));
                        MatcherAssert.assertThat(y, Matchers.is(resY));
                    }
                );
                size.result(
                    (width, height) -> {
                        MatcherAssert.assertThat(width, Matchers.is(resW));
                        MatcherAssert.assertThat(height, Matchers.is(resH));
                        return null;
                    }
                );
            }
        );
    }

    /**
     * A test whether the applyOn method does correctly divide the pos
     * and size for the QuadConsumer.
     */
    @Test
    public void correctDistributionOnApply() {
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

    /**
     * Aims to test, whether the correct result is returned. This test depends
     * on {@link Pos2D#result(BiFunction)} and
     * {@link Size2D#result(BiFunction)}.
     */
    @Test
    public void correctResult() {
        // @checkstyle LocalFinalVariableName (4 lines)
        final var resX = 342;
        final var resY = 328;
        final var resW = 31284;
        final var resH = 4585;
        MatcherAssert.assertThat(
            resX + resY + resW + resH,
            Matchers.is(
                (Integer) new Area2D(resX, resY, resW, resH).result(
                    (pos, size) -> pos.result(
                        // @checkstyle ParameterName (1 line)
                        (x, y) -> x + y + size.result(Integer::sum)
                    )
                )
            )
        );
    }

    /**
     * Tests whether the result method splits the values correctly.
     */
    @Test
    public void correctDistributionOnResult() {
        // @checkstyle LocalFinalVariableName (4 lines)
        final var resX = 52;
        final var resY = 75;
        final var resW = 4234;
        final var resH = 123;
        MatcherAssert.assertThat(
            resX + resY + resW + resH,
            Matchers.is(
                (Integer) new Area2D(resX, resY, resW, resH).result(
                    // @checkstyle ParameterName (1 line)
                    (x, y, width, height) -> x + y + width + height
                )
            )
        );
    }
}
