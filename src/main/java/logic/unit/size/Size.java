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

package logic.unit.size;

import java.util.function.BiFunction;

/*
This interface is necessary (compared to using width and height each time)
because this library depends on the possibility to implement own
implementations like a scaling size
*/
/**
 * The two dimensional cartesian based size of a rectangular area.
 *
 * @since 2.1.0
 */
public interface Size {
    /**
     * Gives the given function the width and height that define this size and
     * returns the result of this function. This can be handy if for example one
     * wants to calculate something with these values and wants the result of
     * this.
     * @param target The target who gets the width and the height.
     * @param <R> The type of the result.
     * @return The result of the applied function.
     */
    <R> R result(BiFunction<Integer, Integer, R> target);
}
