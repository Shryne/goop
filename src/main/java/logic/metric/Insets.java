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

package logic.metric;

import logic.functional.QuadFunction;

/**
 * Defines the size of the border sides of a java 2d based element. It consists
 * of a top, left, right and bottom side.
 * @since 13.2.2
 */
public interface Insets {
    /**
     * Gives the given function the size of the top, left, right and bottom side
     * (in this order) and returns the result of this function.
     * @param target The target that gets the top, left, right and bottom sizes
     *  (in this order).
     * @param <T> The type of the result.
     * @return The result of the given function.
     * @checkstyle ParameterCount (2 lines)
     */
    <T> T result(QuadFunction<Integer, Integer, Integer, Integer, T> target);
}
