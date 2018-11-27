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

package logic.metric.area;

import logic.metric.pos.Pos2D;
import logic.metric.size.Size2D;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Tests for {@link PosOverlapArea}.
 * @since 12.5.0
 */
public class PosOverlapAreaTest {
    /**
     * Tests whether Area(x, y, width, height).contains(Pos(x, y)) == true.
     */
    @Test
    public void lowestPossiblePos() {
        // @checkstyle LocalFinalVariableName (2 lines)
        final var x = 1322;
        final var y = 534;
        final var width = 50;
        final var height = 100;
        MatcherAssert.assertThat(
            new PosOverlapArea(
                new Pos2D(x, y),
                new Size2D(width, height)
            ).contains(new Pos2D(x, y)),
            Matchers.is(true)
        );
    }

    /**
     * Tests whether Area(x, y, width, height)
     * .contains(Pos(x + width, y + height)) == true.
     */
    @Test
    public void highestPossiblePos() {
        // @checkstyle LocalFinalVariableName (2 lines)
        final var x = 238;
        final var y = 243;
        final var width = 324;
        final var height = 4538;
        MatcherAssert.assertThat(
            new PosOverlapArea(
                new Pos2D(x, y),
                new Size2D(width, height)
            ).contains(new Pos2D(x + width, y + height)),
            Matchers.is(true)
        );
    }

    /**
     * Tests whether Area(x, y, width, height).contains(
     * Pos(random(x, x + width), random(y, y + height)) == true.
     */
    @Test
    public void normalPos() {
        // @checkstyle LocalFinalVariableName (2 lines)
        final var x = 34;
        final var y = 543;
        final var width = 31;
        final var height = 5645;
        MatcherAssert.assertThat(
            new PosOverlapArea(
                new Pos2D(x, y),
                new Size2D(width, height)
            ).contains(
                new Pos2D(
                    x + width / 2,
                    y + height / 2
                )
            ),
            Matchers.is(true)
        );
    }

    /**
     * Tests whether a position that is outside of the area (greater) is not
     * contained by area.
     */
    @Test
    public void overPos() {
        // @checkstyle LocalFinalVariableName (2 lines)
        final var x = 432;
        final var y = 23;
        final var width = 534;
        final var height = 123;
        MatcherAssert.assertThat(
            new PosOverlapArea(
                new Pos2D(x, y),
                new Size2D(width, height)
            ).contains(
                new Pos2D(x + width + 1, y)
            ),
            Matchers.is(false)
        );
    }

    /**
     * Tests whether a position that is outside of the area (smaller) is not
     * contained by area.
     */
    @Test
    public void belowPos() {
        // @checkstyle LocalFinalVariableName (2 lines)
        final var x = 534;
        final var y = 3422;
        final var width = 432;
        final var height = 5432;
        MatcherAssert.assertThat(
            new PosOverlapArea(
                new Pos2D(x, y),
                new Size2D(width, height)
            ).contains(
                new Pos2D(x, y - 1)
            ),
            Matchers.is(false)
        );
    }

    /**
     * Tests whether a position is contained by an area that has no width and
     * no height.
     */
    @Test
    public void areaIsPos() {
        // @checkstyle LocalFinalVariableName (2 lines)
        final var x = 34;
        final var y = 543;
        final var width = 0;
        final var height = 0;
        MatcherAssert.assertThat(
            new PosOverlapArea(
                new Pos2D(x, y),
                new Size2D(width, height)
            ).contains(new Pos2D(x, y)),
            Matchers.is(true)
        );
    }
}
