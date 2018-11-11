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

package graphic.lwjgl.window;

import logic.graphic.window.Showable;
import logic.metric.area.Area;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

/*
@todo #6 A window seems to have a minimum width of 120. I should be at least be
able to retrieve that size
 */
/**
 * A window using lwjgl to show itself. Note that this class only represents the
 * window without any threads. It's enough to start the window, but one has to
 * consecutively call {@link #update()} to sustain it.
 * <p>This class is not thread-safe.</p>
 * @since 3.9.0
 */
public class LwjBaseWindow implements Updateable, Showable, AutoCloseable,
    Failable {
    /**
     * The creation of the window that results in the lwjgl long handle to it.
     */
    private final WindowPointer pointer;

    /**
     * Needed for buffer swapping, camera, viewport and everything else around
     * the drawing.
     */
    private final LwjCanvas canvas;

    /**
     * Secondary constructor. Uses the parameters to define the initialization
     * of the actual window. The initialization will be executed when
     * {@link #show()} is called the first time. The primary constructor is
     * private.
     * @param area The area of this window.
     * @param canvas The canvas that contains the background information for the
     *  drawing and applies it accordingly.
     */
    public LwjBaseWindow(
        final Area area,
        final LwjCanvas canvas
    ) {
        this(
            new BaseWindowPointer(area),
            canvas
        );
    }

    /**
     * Primary constructor.
     * @param pointer The value containing the long lwjgl handle to the window.
     * @param canvas The canvas that contains the background information for the
     *  drawing and applies it accordingly.
     */
    private LwjBaseWindow(
        final WindowPointer pointer,
        final LwjCanvas canvas
    ) {
        this.pointer = pointer;
        this.canvas = canvas;
    }

    @Override
    public final void show() {
        GLFW.glfwShowWindow(this.pointer.content());
    }

    @Override
    public final void update() {
        if (!GLFW.glfwWindowShouldClose(this.pointer.content())) {
            GLFW.glfwMakeContextCurrent(this.pointer.content());
            this.canvas.preparedDraw(this.pointer.content(), () -> { });
        }
    }

    @Override
    public final void close() throws Exception {
        Callbacks.glfwFreeCallbacks(this.pointer.content());
        GLFW.glfwDestroyWindow(this.pointer.content());
        GL.setCapabilities(null);
        this.pointer.close();
    }

    @Override
    public final boolean hasFailed() {
        return this.pointer.hasFailed();
    }
}
