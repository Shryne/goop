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

package graphic.j2d.window.event;

import graphic.j2d.event.mouse.J2DClick;
import graphic.j2d.shape.event.J2DRect;
import graphic.j2d.window.information.BarSize;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import logic.metric.area.PosOverlap2D;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Tests for {@link J2DWindow}.
 * @since 13.0.1
 */
public class J2DWindowTest {
    /**
     * Tests if the full specified rectangular area is clickable. This is
     * necessary because swing tends to take the bar size into the calculation.
     * This tests the {@link J2DWindow} and {@link J2DRect}. Additionally, the
     * {@link Robot} is used.
     * @throws Exception Thanks to the used {@link Robot} and to
     *  {@link Thread#sleep(long)}. I need to wait for the window to be created
     *  until I can start the robot and unfortunately I don't know how to let
     *  the window tell me when it's built up.
     * @todo #6 This test belongs to somewhere else because it aims to check
     *  multiple components and not just {@link J2DWindow}.
     */
    @Test
    public void fullClickableArea() throws Exception {
        final var clicked = new AtomicBoolean(false);
        // @checkstyle LocalFinalVariableName (2 lines)
        final var x = 0;
        final var y = 0;
        final var width = 200;
        final var height = 200;
        final var shift = 20;
        final var robot = new Robot();
        new J2DWindow(
            "",
            new PosOverlap2D(width, height),
            List.of(
                frame -> new BarSize(frame).applyOn(
                    // @checkstyle ParameterNameCheck (1 line)
                    (w, h) -> {
                        robot.mouseMove(x + shift, y + h + shift);
                        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    }
                )
            ),
            List.of(
                new J2DRect(
                    new PosOverlap2D(width, height),
                    new J2DClick(() -> clicked.set(true))
                )
            )
        ).show();
        final var wait = 250L;
        Thread.sleep(wait);
        MatcherAssert.assertThat(
            clicked.get(),
            Matchers.is(true)
        );
    }
}
