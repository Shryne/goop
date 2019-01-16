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

package graphic.j2d.window.information;

import graphic.j2d.shape.Redrawable;
import graphic.j2d.window.J2DWindow;
import javax.swing.JFrame;
import logic.functional.Lazy;
import logic.unit.size.Size2D;
import logic.unit.size.SizeEnvelope;

/**
 * The size of a java 2d window bar. This class is intended to be used like
 * this:
 * <pre>
 *     {@code new J2DWindow(
 *          // ...
 *          new ListOf<J2DWindowFeatures>(
 *              jframe -> new BarSize(jframe).applyOn {
 *                  (width, height) -> // The action to be applied
 *              }
 *          );
 *     );}
 * </pre>
 * In this case, the action will be applied when {@link J2DWindow#show()} is
 * called.
 * <p>This class is immutable and thread-safe.</p>
 * @since 13.0.1
 */
public class BarSize extends SizeEnvelope {
    /**
     * Ctor.
     * @param frame The frame to get the bar size from.
     */
    public BarSize(final JFrame frame) {
        super(
            new Lazy<>(
                () -> {
                    final var insets = frame.getInsets();
                    return new Size2D(
                        frame.getWidth(),
                        insets.top
                    );
                }
            )
        );
    }

    @Override
    public final void register(final Redrawable redrawable) { }
}
