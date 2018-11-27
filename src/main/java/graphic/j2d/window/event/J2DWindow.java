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

import graphic.j2d.event.mouse.J2DBaseMouse;
import graphic.j2d.event.mouse.J2DMouse;
import graphic.j2d.shape.J2DMouseShape;
import graphic.j2d.window.J2DBaseWindow;
import java.util.Collections;
import java.util.function.Consumer;
import javax.swing.JFrame;
import logic.metric.area.Area;
import logic.metric.area.Area2D;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Joined;

/**
 * The default implementation of a java 2d window that supports events. It uses
 * a {@link J2DBaseWindow} and adds the following settings to it:
 * <ul>
 *     <li>sets the title</li>
 *     <li>sets the default close operation (end application on close)</li>
 * </ul>
 * Additionally, it uses {@link graphic.j2d.shape.J2DMouseShape} instead of
 * {@link graphic.j2d.shape.J2DShape}.
 * <p>This class mutates its state when {@link J2DBaseWindow#show()} is called.
 * Since show isn't synchronized, this class isn't thread-safe. Additionally,
 * the setting can change its state.</p>
 * @since 13.0.0
 */
public class J2DWindow extends graphic.j2d.window.J2DWindow {
    /**
     * Ctor.
     * @param width The width of the window.
     * @param height The height of the window.
     * @param shapes The mouse event shapes that are drawn on the window.
     */
    public J2DWindow(
        final int width,
        final int height,
        final J2DMouseShape... shapes
    ) {
        this(
            "",
            new Area2D(width, height),
            Collections.emptyList(),
            new IterableOf<>(shapes)
        );
    }

    /**
     * Ctor.
     * @param title The title that is shown at the top of the window.
     * @param area The area of the window.
     * @param shapes The mouse event shapes that are drawn on the window.
     * @param settings Certain settings regarding the window.
     * @checkstyle ParameterNumberCheck (2 lines)
     */
    public J2DWindow(
        final String title,
        final Area area,
        final Iterable<J2DMouseShape> shapes,
        final Consumer<JFrame>... settings
    ) {
        this(
            title,
            area,
            new IterableOf<>(settings),
            shapes
        );
    }

    /**
     * Ctor.
     * @param title The title that is shown at the top of the window.
     * @param area The area of the window.
     * @param settings Certain settings regarding the window.
     * @param shapes The mouse event shapes that are drawn on the window.
     * @checkstyle ParameterNumberCheck (2 lines)
     */
    public J2DWindow(
        final String title,
        final Area area,
        final Iterable<Consumer<JFrame>> settings,
        final Iterable<J2DMouseShape> shapes
    ) {
        super(
            title,
            area,
            new Joined<>(
                new IterableOf<>(
                    frame -> {
                        final J2DMouse mouse = new J2DBaseMouse(frame);
                        shapes.forEach(it -> it.registerFor(mouse));
                    }
                ),
                settings
            ),
            shapes
        );
    }
}
