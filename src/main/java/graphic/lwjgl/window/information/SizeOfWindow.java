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

import graphic.j2d.shape.Redrawable;
import graphic.lwjgl.window.WindowPointer;
import java.nio.IntBuffer;
import logic.functional.Lazy;
import logic.unit.size.Size2D;
import logic.unit.size.SizeEnvelope;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryStack;

/**
 * A size that can construct itself by retrieving the size of a window.
 * This size doesn't includes the frame of the window. Example: If the frame has
 * a height of 10 and the client area has a height of 100 then the height value
 * of this object will be 100.
 * <p>This class is mutable and not thread-safe, because it uses a lazy object
 * that changes its state when a method is called.</p>
 * @since 6.1.1
 */
public class SizeOfWindow extends SizeEnvelope {
    /**
     * Primary constructor.
     * @param window The window to get the size from.
     */
    public SizeOfWindow(final WindowPointer window) {
        super(
            new Lazy<>(
                () -> {
                    try (MemoryStack stack = MemoryStack.stackPush()) {
                        final IntBuffer width = stack.mallocInt(1);
                        final IntBuffer height = stack.mallocInt(1);
                        GLFW.glfwGetWindowSize(window.content(), width, height);
                        return new Size2D(
                            width.get(0),
                            height.get(0)
                        );
                    }
                }
            )
        );
    }

    @Override
    public final void register(final Redrawable redrawable) { }
}
