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

import logic.functional.Lazy;
import logic.metric.area.Area;
import logic.metric.area.Area2D;
import logic.metric.pos.Pos;
import logic.metric.size.Size;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryUtil;

/**
 * This class offers the creation of a window with a certain area. Since this
 * class extends lazy, it will create itself only when {@link Lazy#content()}
 * is called.
 * <p>This class is mutable and not thread-safe. There aren't any additional
 * synchronization mechanics compared to the parent class.</p>
 * @since 5.12.2
 */
public class BaseWindowPointer extends Lazy<Long> implements WindowPointer {
    /**
     * The initialization of glfw needed to use the glfw methods.
     */
    private final GlfwInit glfw;

    /**
     * Secondary constructor. The position will be (0|0).
     * @param size The size of the window that will be pointed by this object.
     */
    public BaseWindowPointer(final Size size) {
        this(new Area2D(size));
    }

    /**
     * Secondary constructor.
     * @param pos The position of window that will be pointed by this object.
     * @param size The size of the window that will be pointed by this object.
     */
    public BaseWindowPointer(final Pos pos, final Size size) {
        this(new Area2D(pos, size));
    }

    // @todo #1 The static initialization of GlfwBaseInit is dangerous. If a
    //  user uses this constructor, he won't be able to tell which GlfwInit
    //  implementation was used and could then use another. The glfw
    //  implementation should know somehow that there is already another glfw
    //  instance
    /**
     * Secondary constructor. Uses the static initialization of
     * {@link GlfwBaseInit}.
     * @param area The area of the window that will be pointed by this object.
     */
    public BaseWindowPointer(final Area area) {
        this(
            area,
            GlfwBaseInit.instance()
        );
    }

    /**
     * Primary constructor.
     * @param glfw This is needed to use the glfw methods to create the window.
     * @param area The area of the window that will be pointed by this object.
     */
    public BaseWindowPointer(final Area area, final GlfwInit glfw) {
        super(
            () -> {
                glfw.acquire();
                return area.result(
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
            }
        );
        this.glfw = glfw;
    }

    @Override
    public final void close() throws Exception {
        // @checkstyle MethodBodyCommentsCheck (2 lines)
        // @todo #2 It should be checked whether it's really fine to add methods
        //  to an extended class
        this.glfw.close();
    }

    @Override
    public final boolean hasFailed() {
        return this.content().equals(0L);
    }
}
