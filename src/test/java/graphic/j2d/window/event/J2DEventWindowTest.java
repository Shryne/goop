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

import graphic.j2d.J2DContainerInsets;
import graphic.j2d.event.mouse.Click;
import graphic.j2d.shape.event.EventRect;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import logic.unit.area.PosOverlap2D;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Tests for {@link EventWindow}.
 * @since 13.0.1
 */
public class J2DEventWindowTest {
    /**
     * Tests if the full specified rectangular area is clickable. This tests the
     * {@link EventWindow} and {@link EventRect}. Additionally, them
     * {@link Robot}  is used. The robot will click somewhere at the top of the
     * area so this test will probably succeed even if the insets of the window
     * aren't taken into account.
     * @throws Exception Thanks to the used {@link Robot} and to
     *  {@link Thread#sleep(long)}. I need to wait for the window to be created
     *  until I can start the robot and unfortunately I don't know how to let
     *  the window tell me when it's built up.
     * @todo #6 This test belongs to somewhere else because it aims to check
     *  multiple components and not just {@link EventWindow}.
     */
    @Test
    public void fullClickableAreaTop() throws Exception {
        final var clicked = new AtomicBoolean(false);
        // @checkstyle LocalFinalVariableName (2 lines)
        final var x = 0;
        final var y = 0;
        final var width = 200;
        final var height = 200;
        final var robot = new Robot();
        new EventWindow(
            "",
            new PosOverlap2D(width, height),
            List.of(
                frame -> new J2DContainerInsets(frame).result(
                    (top, left, right, bottom) -> {
                        robot.mouseMove(x + left, y + top);
                        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                        return Void.TYPE;
                    }
                )
            ),
            List.of(
                new EventRect(
                    new PosOverlap2D(width, height),
                    new Click(() -> clicked.set(true))
                )
            )
        ).show();
        final var wait = 50L;
        Thread.sleep(wait);
        MatcherAssert.assertThat(
            clicked.get(),
            Matchers.is(true)
        );
    }

    /**
     * Tests if the full specified rectangular area is clickable. This tests the
     * {@link EventWindow} and {@link EventRect}. Additionally, the
     * {@link Robot} is used. The robot will click somewhere near the bottom to
     * ensure that the bar size of the window is taken into account.
     * @throws Exception Thanks to the used {@link Robot} and to
     *  {@link Thread#sleep(long)}. I need to wait for the window to be created
     *  until I can start the robot and unfortunately I don't know how to let
     *  the window tell me when it's built up.
     * @todo #6 This test belongs to somewhere else because it aims to check
     *  multiple components and not just {@link EventWindow}.
     */
    @Test
    public void fullClickableAreaBottom() throws Exception {
        final var clicked = new AtomicBoolean(false);
        // @checkstyle LocalFinalVariableName (2 lines)
        final var x = 0;
        final var y = 0;
        final var width = 150;
        final var height = 300;
        final var robot = new Robot();
        new EventWindow(
            "",
            new PosOverlap2D(width, height),
            List.of(
                frame -> new J2DContainerInsets(frame).result(
                    // @checkstyle ParameterNameCheck (1 line)
                    (top, left, right, bottom) -> {
                        robot.mouseMove(
                            x + left + width - 1,
                            y + top + height - 1
                        );
                        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                        return Void.TYPE;
                    }
                )
            ),
            List.of(
                new EventRect(
                    new PosOverlap2D(width, height),
                    new Click(() -> clicked.set(true))
                )
            )
        ).show();
        final var wait = 50L;
        Thread.sleep(wait);
        MatcherAssert.assertThat(
            clicked.get(),
            Matchers.is(true)
        );
    }
}
