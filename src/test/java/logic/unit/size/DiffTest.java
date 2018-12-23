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

import java.util.function.ObjIntConsumer;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Tests for {@link Diff}.
 * @since 9.1.1
 */
public class DiffTest {
    /*
    Normally, I would test the equality of Size2D and Diff in a separate method,
    but to create Diff, I need Size2D and it would somehow still test whether
    Size is constructed correctly. So in the end I would need to create a Diff
    object without using Size2D. Since I don't have a fake for Size, I can't do
    that so I put this test inside here. I don't see a point creating a fake
    for a class that is as simple as Size2D
     */
    /**
     * Tests for:
     * <p>size1 - Size(0, 0) = size1</p>
     * This test depends on {@link SizeCalculation#applyOn(ObjIntConsumer)}.
     */
    @Test
    public void sizeMinusZeroEqualsSize() {
        final var width = 54;
        final var height = 325;
        new Diff(new Size2D(width, height), new Size2D()).applyOn(
            // @checkstyle ParameterName (1 line)
            (diffWidth, diffHeight) -> {
                MatcherAssert.assertThat(diffWidth, Matchers.equalTo(width));
                MatcherAssert.assertThat(diffHeight, Matchers.equalTo(height));
            }
        );
    }

    /**
     * Test for:
     * <p>size1 - size2 = size3</p>
     * This test depends on {@link SizeCalculation#applyOn(ObjIntConsumer)}.
     */
    @Test
    public void sizeMinusSizeResult() {
        // @checkstyle LocalFinalVariableName (4 lines)
        final var width1 = 43;
        final var height1 = 215;
        final var width2 = 1534;
        final var height2 = 123;
        new Diff(
            new Size2D(width1, height1),
            new Size2D(width2, height2)
        ).applyOn(
            // @checkstyle ParameterName (1 line)
            (diffWidth, diffHeight) -> {
                MatcherAssert.assertThat(
                    diffWidth,
                    Matchers.equalTo(width1 - width2)
                );
                MatcherAssert.assertThat(
                    diffHeight,
                    Matchers.equalTo(height1 - height2)
                );
            }
        );
    }
}
