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
import logic.metric.pos.Pos2D;
import logic.metric.size.Size2D;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryStack;

/**
 * Contains tests for the {@link WindowPointer} class.
 * @since 5.12.1
 */
public class WindowPointerTest {
    /**
     * Creates a lwjgl window by using {@link WindowPointer} and checks if the
     * position of that window meets the expectations.
     */
    @Test
    public void hasCorrectPosition() {
        final var pos = new Pos2D(123, 432);
        final var window = new WindowPointer(
            pos,
            new Size2D(32, 234)
        );
        try (MemoryStack stack = MemoryStack.stackPush()) {
            final IntBuffer top = stack.mallocInt(1);
            GLFW.glfwGetWindowPos(window.content(), top, top);
            MatcherAssert.assertThat(
                pos,
                Matchers.equalTo(
                    new Pos2D(
                        top.get(0), top.get(1)
                    )
                )
            );
        }
    }
}
