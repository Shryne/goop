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

package graphic.window.event;

import graphic.event.mouse.J2DBaseMouse;
import graphic.event.mouse.J2DMouse;
import graphic.shape.J2DMouseShape;
import graphic.shape.J2DShape;
import graphic.window.J2DBaseWindow;
import graphic.window.J2DWindow;
import graphic.window.J2DWindowFeature;
import java.util.Collections;
import logic.unit.area.Area;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Joined;

/**
 * The default implementation of a java 2d window that supports events. It uses
 * a {@link J2DBaseWindow} and adds the following settings to it:
 * <ul>
 *     <li>sets the title</li>
 *     <li>sets the default close operation (end application on close)</li>
 * </ul>
 * Additionally, it uses {@link J2DMouseShape} instead of
 * {@link J2DShape}.
 * <p>This class mutates its state when {@link J2DBaseWindow#show()} is called.
 * Since show isn't synchronized, this class isn't thread-safe. Additionally,
 * the features can change its state.</p>
 * @since 13.0.0
 */
public class J2DEventWindow extends J2DWindow {
    /**
     * Ctor.
     * @param area The area of the window.
     * @param shapes The mouse event shapes that are drawn on the window.
     * @checkstyle ParameterNumberCheck (2 lines)
     */
    public J2DEventWindow(
        final Area area,
        final J2DMouseShape... shapes
    ) {
        this(
            "",
            area,
            shapes
        );
    }

    /**
     * Ctor.
     * @param area The area of the window.
     * @param shapes The mouse event shapes that are drawn on the window.
     * @checkstyle ParameterNumberCheck (2 lines)
     */
    public J2DEventWindow(
        final Area area,
        final Iterable<J2DMouseShape> shapes
    ) {
        this(
            "",
            area,
            Collections.emptyList(),
            shapes
        );
    }

    /**
     * Ctor.
     * @param title The title that is shown at the top of the window.
     * @param area The area of the window.
     * @param shapes The mouse event shapes that are drawn on the window.
     * @checkstyle ParameterNumberCheck (2 lines)
     */
    public J2DEventWindow(
        final String title,
        final Area area,
        final J2DMouseShape... shapes
    ) {
        this(
            title,
            area,
            Collections.emptyList(),
            new IterableOf<>(shapes)
        );
    }

    /**
     * Ctor.
     * @param title The title that is shown at the top of the window.
     * @param area The area of the window.
     * @param features Certain features regarding the window.
     * @param shapes The mouse event shapes that are drawn on the window.
     * @checkstyle ParameterNumberCheck (2 lines)
     */
    public J2DEventWindow(
        final String title,
        final Area area,
        final Iterable<J2DWindowFeature> features,
        final Iterable<J2DMouseShape> shapes
    ) {
        super(
            title,
            area,
            new Joined<>(
                new IterableOf<>(
                    frame -> {
                        final J2DMouse mouse = new J2DBaseMouse(
                            frame.getContentPane()
                        );
                        shapes.forEach(it -> it.registerFor(mouse));
                    }
                ),
                features
            ),
            shapes
        );
    }
}
