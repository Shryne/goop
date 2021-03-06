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

import logic.functional.Action;
import logic.functional.Lazy;
import logic.functional.Value;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLCapabilities;

/**
 * This buffer takes care of the correct swap interval, the color cleaning
 * and the swapping of the buffer.
 * <p>This class is not thread-safe, because it mutates the global OpenGL
 * state.</p>
 * @since 3.9.0
 */
public class BaseViewBuffer implements ViewBuffer {
    /**
     * The amount of buffer swapping per second.
     */
    private final int interval;

    /**
     * The OpenGL related capabilities contained by a value. This is necessary,
     * because the capabilities can only be created after a {@link GlfwInit}
     * implementation has been used.
     */
    private final Value<GLCapabilities> capabilities;

    /**
     * Ctor.
     */
    public BaseViewBuffer() {
        this(0, new Lazy<>(GL::createCapabilities));
    }

    /**
     * Ctor.
     * @param interval The interval of the swapping. A 60 would mean that the
     *  buffer would be swapped 60 times per second.
     * @param capabilities The OpenGL related capabilities.
     */
    public BaseViewBuffer(
        final int interval, final Value<GLCapabilities> capabilities
    ) {
        this.interval = interval;
        this.capabilities = capabilities;
    }

    @Override
    public final void drawBuffered(final long window, final Action action) {
        GL.setCapabilities(this.capabilities.content());
        GLFW.glfwSwapInterval(this.interval);
        GL11.glClearColor(
            1.0f, 1.0f, 1.0f, 1.0f
        );
        GL11.glClear(
            GL11.GL_COLOR_BUFFER_BIT
                | GL11.GL_DEPTH_BUFFER_BIT
        );
        action.run();
        GLFW.glfwSwapBuffers(window);
    }
}
