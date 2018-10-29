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

package graphic.j2d.window;

import graphic.j2d.shape.J2DShape;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import logic.functional.Lazy;
import logic.functional.Value;
import logic.metric.area.Area;
import logic.window.Showable;

/**
 * The standard implementation of a java 2d window. It uses a
 * {@link javax.swing.JFrame} for this.
 * <p>This class mutates its state when {@link this#show()} is called. Since
 * show isn't synchronized, this class isn't thread-safe.</p>
 * @since 3.2.0
 */
public class J2DWindow implements Showable {
    /**
     * The Window. Because the constructor(s) only set fields, this lazy is used
     * to postpone the actual creation, setting and actions regarding the
     * JFrame.
     */
    private final Value<JFrame> window;

    /**
     * Secondary constructor. The primary constructor is private.
     * @param title The title that is shown at the top of the window.
     * @param area The area of the window.
     * @param shapes The shapes that are drawn on the window.
     */
    public J2DWindow(final String title,
        final Area area, final Iterable<J2DShape> shapes) {
        this(
            new Lazy<>(
                () -> {
                    final var frame = new JFrame(title);
                    area.applyOn(frame::setBounds);
                    final var panel = new JPanel() {
                        @Override
                        protected void paintComponent(final Graphics graphics) {
                            super.paintComponent(graphics);
                            shapes.forEach(shape -> shape.draw(graphics));
                        }
                    };
                    frame.add(panel);
                    frame.setVisible(true);
                    return frame;
                }
            )
        );
    }

    /**
     * Primary constructor.
     * @param window The construction of the frame.
     */
    private J2DWindow(final Value<JFrame> window) {
        this.window = window;
    }

    @Override
    public final void show() {
        this.window.content();
    }
}
