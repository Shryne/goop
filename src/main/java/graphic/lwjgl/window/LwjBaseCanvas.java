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
import logic.metric.area.Area;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;

/**
 * The basic canvas for lwjgl. It will apply the given buffer and the camera.
 * <p>This class is not thread-safe, because it mutes the OpenGL state.</p>
 * @since 3.9.0
 */
public class LwjBaseCanvas implements LwjCanvas {
    /**
     * Tbe buffer of the canvas/window.
     */
    private final ViewBuffer buffer;

    /**
     * The camera of the canvas.
     */
    private final LwjCamera camera;

    /**
     * The OpenGL related capabilities.
     */
    private final GLCapabilities capabilities;

    /**
     * Secondary constructor.
     * @param area The area of the camera used by the canvas.
     */
    public LwjBaseCanvas(final Area area) {
        this(
            new BaseViewBuffer(),
            new LwjBaseCamera(area),
            GL.createCapabilities()
        );
    }

    /**
     * Primary constructor.
     * @param buffer The buffer to be used for the drawing.
     * @param camera The camera to be applied.
     * @param capabilities The OpenGL related capabilities.
     */
    public LwjBaseCanvas(
        final ViewBuffer buffer,
        final LwjCamera camera,
        final GLCapabilities capabilities
    ) {
        this.buffer = buffer;
        this.camera = camera;
        this.capabilities = capabilities;
    }

    @Override
    public final void preparedDraw(final long window, final Action action) {
        this.buffer.drawBuffered(
            window, () -> {
                GL.setCapabilities(this.capabilities);
                this.camera.apply();
                action.run();
            }
        );
    }
}
