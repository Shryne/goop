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

import graphic.lwjgl.shape.LwjShape;
import java.util.Collections;
import java.util.List;
import logic.graphic.window.Showable;
import logic.unit.area.Area;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

// @todo #3 A window seems to have a minimum width of 120. I should be at least
//  be able to retrieve that size
/**
 * A window using lwjgl to show itself. Note that this class only represents the
 * window without any threads. One has to consecutively call {@link #show()}
 * to show and sustain the window.
 * <p>This class is not thread-safe.</p>
 * @since 3.9.0
 */
public class LwjBaseWindow implements Showable, AutoCloseable, Failable {
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
     * The shapes to be drawn on this window.
     */
    private final Iterable<LwjShape> shapes;

    /**
     * Ctor.
     * @param area The area of the window.
     * @param shapes The shapes to be drawn on this window.
     */
    public LwjBaseWindow(final Area area, final LwjShape... shapes) {
        this(area, List.of(shapes));
    }

    /**
     * Ctor.
     * @param area The area of this window.
     * @param shapes The shapes to be drawn on this window.
     */
    public LwjBaseWindow(final Area area, final Iterable<LwjShape> shapes) {
        this(area, new LwjBaseCanvas(area), shapes);
    }

    /**
     * Ctor.
     * @param area The area of this window.
     * @param canvas The canvas that contains the background information for the
     *  drawing and applies it accordingly.
     */
    public LwjBaseWindow(final Area area, final LwjCanvas canvas) {
        this(area, canvas, Collections.emptyList());
    }

    /**
     * Ctor.
     * @param area The area of this window.
     * @param canvas The canvas that contains the background information for the
     *  drawing and applies it accordingly.
     * @param shapes The shapes to be drawn on the window.
     */
    public LwjBaseWindow(
        final Area area,
        final LwjCanvas canvas,
        final Iterable<LwjShape> shapes
    ) {
        this(
            new BaseWindowPointer(area),
            canvas,
            shapes
        );
    }

    /**
     * Ctor.
     * @param pointer The value containing the long lwjgl handle to the window.
     * @param canvas The canvas that contains the background information for the
     *  drawing and applies it accordingly.
     * @param shapes The shapes to be drawn on the window.
     */
    private LwjBaseWindow(
        final WindowPointer pointer,
        final LwjCanvas canvas,
        final Iterable<LwjShape> shapes
    ) {
        this.pointer = pointer;
        this.canvas = canvas;
        this.shapes = shapes;
    }

    // @todo #5 It should be tried to merge hasFailed and show because
    //  currently, they are temporally coupled. One has to call hasFailed,
    //  check it and then run show or do something else.
    @Override
    public final void show() {
        while (!GLFW.glfwWindowShouldClose(this.pointer.content())) {
            GLFW.glfwMakeContextCurrent(this.pointer.content());
            this.shapes.forEach(
                shape -> this.canvas.preparedDraw(
                    this.pointer.content(), shape::draw
                )
            );
            GLFW.glfwPollEvents();
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
