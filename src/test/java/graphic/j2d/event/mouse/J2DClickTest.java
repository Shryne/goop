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

package graphic.j2d.event.mouse;

import graphic.j2d.window.event.J2DWindow;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.JFrame;
import logic.metric.area.PosOverlap2D;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Tests for {@link J2DClick}.
 * @since 13.0.2
 */
public class J2DClickTest {
    /**
     * Tests whether the click object applies the given action. This test
     * uses a {@link javax.swing.JFrame} instead of a
     * {@link J2DWindow} to isolate the click object as much as possible.
     * {@link Robot} is used to click on the window.
     * @throws Exception because of {@link Robot} and because of
     *  {@link Thread#sleep(long)}.
     */
    @Test
    public void clickAppliesAction() throws Exception {
        final var clicked = new AtomicBoolean(false);
        // @checkstyle LocalFinalVariableName (2 lines)
        final var x = 0;
        final var y = 0;
        final var width = 100;
        final var height = 80;
        final var robot = new Robot();
        final var frame = new JFrame();
        frame.setSize(width, height);
        new J2DClick(
            () -> clicked.set(true)
        ).registerFor(
            new J2DBaseMouse(frame),
            new PosOverlap2D(width, height)
        );
        frame.setVisible(true);
        final var shift = 20;
        robot.mouseMove(x + shift, y + frame.getInsets().top + shift);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        final var wait = 250L;
        Thread.sleep(wait);
        MatcherAssert.assertThat(
            clicked.get(),
            Matchers.is(true)
        );
    }
}
