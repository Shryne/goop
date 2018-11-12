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

package graphic.lwjgl.window.information;

import graphic.lwjgl.window.WindowPointer;
import java.nio.IntBuffer;
import logic.functional.Lazy;
import logic.metric.pos.Pos2D;
import logic.metric.pos.PosEnvelope;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryStack;

/**
 * A position that can construct itself by retrieving the position of a window.
 * This position includes the frame of the window. Example: If the frame has a
 * height of 10 and a y value of 0 the y coordinate will still be 0.
 * <p>This class is mutable and not thread-safe, because it uses a lazy object
 * that changes its state when a method is called.</p>
 * @since 5.13.3
 */
public class PosOfWindow extends PosEnvelope {
    /**
     * Primary constructor.
     * @param window The window to get the position from.
     */
    public PosOfWindow(final WindowPointer window) {
        super(
            new Lazy<>(
                () -> {
                    try (MemoryStack stack = MemoryStack.stackPush()) {
                        // @checkstyle LocalFinalVariableName (2 lines)
                        final IntBuffer x = stack.mallocInt(1);
                        final IntBuffer y = stack.mallocInt(1);
                        GLFW.glfwGetWindowPos(window.content(), x, y);
                        final IntBuffer top = stack.mallocInt(1);
                        GLFW.glfwGetWindowFrameSize(
                            window.content(),
                            null,
                            top,
                            null,
                            null
                        );
                        return new Pos2D(
                            x.get(0), y.get(0) - top.get(0)
                        );
                    }
                }
            )
        );
    }
}
