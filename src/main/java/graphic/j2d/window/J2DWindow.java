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
import java.util.List;
import javax.swing.WindowConstants;
import logic.metric.area.Area;

/**
 * The default implementation of a java 2d window. It uses a
 * {@link J2DBaseWindow} and adds the following settings to it:
 * <ul>
 *     <li>sets the title</li>
 *     <li>sets the default close operation (end application on close)</li>
 * </ul>
 * <p>This class mutates its state when {@link J2DBaseWindow#show()} is called.
 * Since show isn't synchronized, this class isn't thread-safe. Additionally,
 * the setting can change its state.</p>
 * @since 3.2.0
 */
public class J2DWindow extends J2DBaseWindow {
    /**
     * Primary constructor.
     * @param title The title that is shown at the top of the window.
     * @param area The area of the window.
     * @param shapes The shapes that are drawn on the window.
     */
    public J2DWindow(
        final String title, final Area area, final Iterable<J2DShape> shapes
    ) {
        super(
            area,
            List.of(
                frame -> frame.setTitle(title),
                frame -> frame.setDefaultCloseOperation(
                    WindowConstants.EXIT_ON_CLOSE
                )
            ),
            shapes
        );
    }
}
