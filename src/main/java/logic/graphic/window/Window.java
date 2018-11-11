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

package logic.graphic.window;

import graphic.j2d.window.J2DWindow;
import graphic.lwjgl.window.LwjBaseWindow;
import java.util.Collections;
import logic.functional.Lazy;
import logic.functional.Value;
import logic.metric.area.Area;

/**
 * This window checks which graphics library is supported on the current
 * platform and creates the best concrete window object for that. The actual
 * construction/choosing of the windows will happen, when the user calls
 * {@link #show} the first time. Every additional call is a no-op.
 * <p>This class is not thread-safe, because {@link #show} mutates its state.
 * </p>
 * @since 5.11.1
 */
public class Window implements Showable {
    /**
     * The value that contains the construction of the actual window.
     */
    private final Value<Showable> concrete;

    /**
     * Secondary constructor. Uses the arguments to lazily create the concrete
     * window. The primary constructor is private.
     * @param title The title of the window.
     * @param area The area of the window.
     */
    public Window(final String title, final Area area) {
        this(
            new Lazy<>(
                () -> {
                    final var lwjgl = new LwjBaseWindow(area);
                    Showable result = lwjgl;
                    if (lwjgl.hasFailed()) {
                        result = new J2DWindow(
                            title,
                            area,
                            Collections.emptyList()
                        );
                    }
                    return result;
                }
            )
        );
    }

    /**
     * Primary constructor.
     * @param concrete The value containing/creating the window using a specific
     *  graphics library.
     */
    private Window(final Value<Showable> concrete) {
        this.concrete = concrete;
    }

    @Override
    public final void show() {
        this.concrete.content().show();
    }
}
