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

/**
 * Prepares everything so that the user can draw something on the lwjgl window.
 * <p>Note that even though this is a canvas, nothing will be really drawn on
 * it, because lwjgl draws everything on the current global context. This
 * interface and the implementations of it offer a way to make everything around
 * the drawing, like buffer flipping, clearing the window, setting the viewport
 * and so on.</p>
 * @since 3.9.0
 */
public interface LwjCanvas {
    /**
     * Prepares everything before and after the drawing and runs the action to
     * draw something.
     * @param window The handle to the lwjgl window.
     * @param action The drawing to be ran.
     */
    void preparedDraw(long window, Action action);
}
