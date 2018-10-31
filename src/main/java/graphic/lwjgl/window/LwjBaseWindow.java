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

import java.nio.IntBuffer;
import logic.functional.Lazy;
import logic.functional.Value;
import logic.metric.area.Area;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

/**
 * A window using lwjgl to show itself.
 * <p>This class is not thread-safe, because it mutates its state when
 * {@link #show()} is called.</p>
 * @since 3.9.0
 */
public class LwjBaseWindow implements LwjWindow, AutoCloseable {
    /**
     * The creation of the window that results in the lwjgl long handle to it.
     */
    private final Value<Long> handle;

    /**
     * The initialization for glfw. This is necessary to create a window.
     */
    private final GlfwInit glfw;

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
     * @param glfw The glfw initialization. This is needed to create windows and
     *  most of the functions on them.
     * @param canvas The canvas that contains the background information for the
     *  drawing and applies it accordingly.
     */
    public LwjBaseWindow(
        final Area area,
        final GlfwInit glfw,
        final LwjCanvas canvas
    ) {
        this(
            new Lazy<>(
                () -> {
                    glfw.acquire();
                    GLFW.glfwDefaultWindowHints();
                    final long result = area.result(
                        (pos, size) -> size.result(
                            (width, height) -> GLFW.glfwCreateWindow(
                                width,
                                height,
                                "",
                                MemoryUtil.NULL,
                                MemoryUtil.NULL
                            )
                        )
                    );
                    GLFW.glfwMakeContextCurrent(result);
                    try (MemoryStack stack = MemoryStack.stackPush()) {
                        final IntBuffer top = stack.mallocInt(1);
                        GLFW.glfwGetWindowFrameSize(
                            result, null, top, null, null
                        );
                        area.applyOn(
                            // @checkstyle ParameterName (1 line)
                            (x, y, width, height) -> GLFW.glfwSetWindowPos(
                                result, x, y + top.get(0)
                            )
                        );
                    }
                    return result;
                }
            ),
            glfw,
            canvas
        );
    }

    /**
     * Primary constructor.
     * @param handle The value containing the long lwjgl handle to the window.
     * @param glfw The glfw initialization. This is needed to create windows and
     *  most of the functions on them.
     * @param canvas The canvas that contains the background information for the
     *  drawing and applies it accordingly.
     */
    private LwjBaseWindow(
        final Value<Long> handle,
        final GlfwInit glfw,
        final LwjCanvas canvas
    ) {
        this.handle = handle;
        this.glfw = glfw;
        this.canvas = canvas;
    }

    @Override
    public final void show() {
        GLFW.glfwShowWindow(this.handle.content());
    }

    @Override
    public final void update() {
        if (!GLFW.glfwWindowShouldClose(this.handle.content())) {
            GLFW.glfwMakeContextCurrent(this.handle.content());
            this.canvas.preparedDraw(this.handle.content(), () -> { });
        }
    }

    @Override
    public final void close() throws Exception {
        Callbacks.glfwFreeCallbacks(this.handle.content());
        GLFW.glfwDestroyWindow(this.handle.content());
        GL.setCapabilities(null);
        this.glfw.close();
    }
}
