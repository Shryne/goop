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
import logic.metric.area.Area;
import logic.metric.area.Area2D;
import logic.metric.pos.Pos;
import logic.metric.size.Size;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

/*
 * @todo #2 Check whether this class is thread-safe or not and document that
 */
/**
 * Represents the long value that holds the reference to the lwjgl window. This
 * class offers only the creation of a window with a certain area. Since this
 * class extends lazy, it will create itself only when {@link Lazy#content()}
 * is called.
 * @since 5.12.1
 */
public class WindowPointer extends Lazy<Long> {
    /**
     * Secondary constructor. The position will be (0|0).
     * @param size The size of window that will be pointed by this object.
     */
    public WindowPointer(final Size size) {
        this(new Area2D(size));
    }

    /**
     * Secondary constructor.
     * @param pos The position of window that will be pointed by this object.
     * @param size The size of window that will be pointed by this object.
     */
    public WindowPointer(final Pos pos, final Size size) {
        this(new Area2D(pos, size));
    }

    /*
     * @todo Check for null when creating the window
     */
    /**
     * Primary constructor.
     * @param area The area of window that will be pointed by this object.
     */
    public WindowPointer(final Area area) {
        super(
            () -> {
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
        );
    }
}
