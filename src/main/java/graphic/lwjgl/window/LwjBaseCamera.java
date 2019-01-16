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

import logic.unit.area.Area;
import logic.unit.area.Area2D;
import logic.unit.size.Size;
import org.lwjgl.opengl.GL11;

/**
 * The camera/view of a lwjgl based window.
 * <p>This class is not thread-safe, because {@link #apply()} mutates the gl
 * state which is not thread-safe.</p>
 * @since 3.9.0
 */
public class LwjBaseCamera implements LwjCamera {
    /**
     * The area of the camera. This will be the part which will be seen
     * on the window.
     */
    private final Area area;

    /**
     * Secondary constructor. The camera will have its view on pos (0|0).
     * @param size The size of the camera/the part you can see through it.
     */
    public LwjBaseCamera(final Size size) {
        this(new Area2D(size));
    }

    /**
     * Primary constructor.
     * @param area The area of the camera/the part you can see through it.
     */
    public LwjBaseCamera(final Area area) {
        this.area = area;
    }

    @Override
    public final void apply() {
        this.area.applyOn(GL11::glViewport);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        this.area.applyOn(
            (pos, size) -> size.result(
                (width, height) -> {
                    GL11.glOrtho(
                        0.0,
                        width,
                        height,
                        0.0,
                        0.0,
                        1.0
                    );
                    return null;
                }
            )
        );
    }
}
