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
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Collections;
import javax.swing.JFrame;
import javax.swing.JPanel;
import logic.functional.Lazy;
import logic.functional.Value;
import logic.graphic.window.Showable;
import logic.unit.area.Area;

/*
Passing the JFrame to the feature isn't beautiful but necessary, because the
window can't be decorated without using the JFrame. At least by using the
feature I made the window pass the JFrame to something instead of providing a
direct getter to the state
*/
/**
 * Represents a simple java 2d window. To apply some settings on this window,
 * one has to use a constructor that takes a feature. This feature gets
 * the JFrame of this window to apply the needed settings or to get information
 * from it. By using this, one can bind the feature on a certain event like a
 * click on a button. <b>Don't capture the JFrame to use it elsewhere!</b>
 * <p>This class is not thread-safe, because it mutates its state when
 * {@link #show} is called. Additionally the features can change its state.</p>
 * @since 3.5.0
 */
public class J2DBaseWindow implements Showable {
    /**
     * A value containing the actual frame. This is necessary, because the
     * JFrame will probably be lazily constructed.
     */
    private final Value<JFrame> window;

    /**
     * Ctor.
     * @param area The area of this window.
     * @param shapes The shapes that are on this window.
     */
    public J2DBaseWindow(
        final Area area,
        final Iterable<? extends J2DShape> shapes
    ) {
        this(area, Collections.emptyList(), shapes);
    }

    /**
     * Ctor.
     * @param area The area of this window.
     * @param features Certain  features regarding the window.
     * @param shapes The shapes that are on this window.
     */
    public J2DBaseWindow(
        final Area area,
        final Iterable<J2DWindowFeature> features,
        final Iterable<? extends J2DShape> shapes
    ) {
        this(
            new Lazy<>(
                () -> {
                    final var frame = new JFrame();
                    final var panel = new JPanel() {
                        @Override
                        protected void paintComponent(
                            final Graphics graphics
                        ) {
                            super.paintComponent(graphics);
                            shapes.forEach(shape -> shape.draw(graphics));
                        }
                    };
                    area.applyOn(
                        (pos, size) -> {
                            pos.applyOn(frame::setLocation);
                            size.result(
                                (width, height) -> {
                                    panel.setPreferredSize(
                                        new Dimension(width, height)
                                    );
                                    return null;
                                }
                            );
                        }
                    );
                    frame.add(panel);
                    frame.pack();
                    frame.setVisible(true);
                    features.forEach(it -> it.accept(frame));
                    return frame;
                }
            )
        );
    }

    /**
     * Ctor.
     * @param window The construction of the window
     */
    J2DBaseWindow(final Value<JFrame> window) {
        this.window = window;
    }

    @Override
    public final void show() {
        this.window.content();
    }
}
