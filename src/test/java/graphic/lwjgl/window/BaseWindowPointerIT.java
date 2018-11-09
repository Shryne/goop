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

package graphic.lwjgl.window;

import graphic.lwjgl.window.information.PosOfWindow;
import logic.metric.pos.Pos2D;
import logic.metric.size.Size2D;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Contains tests for the {@link BaseWindowPointer} class.
 * @since 6.0.1
 */
public class BaseWindowPointerIT {
    /**
     * Creates a lwjgl window by using {@link BaseWindowPointer} and checks if
     * the position of that window meets the expectations.
     * <p>This test needs {@link BaseWindowPointer} and {@link PosOfWindow} to
     * run correctly.</p>
     * @throws Exception Because closing the window pointer object can fail.
     */
    @Test
    public void hasCorrectPosition() throws Exception {
        final var pos = new Pos2D(123, 432);
        final var width = 120;
        final var height = 232;
        try (
            var window = new BaseWindowPointer(
                pos,
                new Size2D(width, height)
            )
        ) {
            MatcherAssert.assertThat(
                new PosOfWindow(window),
                Matchers.equalTo(
                    pos
                )
            );
        }
    }
}
