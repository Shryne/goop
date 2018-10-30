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
 * Represents the simplest java 2d window implementation. Offers a window on
 * a certain position with a certain size and some shapes on it. The window is
 * not resizable.
 * <p>This class is not thread-safe, because it mutates its state when
 * {@link #show} is called.</p>
 * @since 3.5.0
 */
public class J2DBaseWindow implements Showable {
    /**
     * A value containing the actual frame. This is necessary, because the
     * JFrame will probably be lazily constructed.
     */
    private final Value<JFrame> window;

    /**
     * Secondary constructor. Uses the arguments to define how a JFrame will be
     * constructed using them. The primary constructor is private.
     * @param area The area of this window.
     * @param shapes The shapes that are on this window.
     */
    public J2DBaseWindow(final Area area, final Iterable<J2DShape> shapes) {
        this(
            new Lazy<>(
                () -> {
                    final var frame = new JFrame();
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
     * @param window The construction of the window
     */
    private J2DBaseWindow(final Value<JFrame> window) {
        this.window = window;
    }

    @Override
    public final void show() {
        this.window.content();
    }
}
