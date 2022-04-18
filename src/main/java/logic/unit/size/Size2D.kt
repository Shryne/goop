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
package logic.unit.size

import graphic.j2d.shape.Redrawable
import java.util.function.BiFunction

/*
I am not happy about this naming, but my other idea's regarding the interface
name and the basic implementation weren't good either (impl or simple for
example). So I took the more annoying name for the implementation to lower the
chance that a user might use the class name as a parameter type
*/
/**
 * Basic concrete implementation of [Size].
 *
 * This class is immutable and thread-safe.
 *
 * @constructor Creates a size with width = 0 and height = 0.
 * @param w The width of the size.
 * @param h The height of the size.
 */
open class Size2D(private val w: Int = 0, private val h: Int = 0) : Size {
    /**
     * @param width The width for the size.
     * @param height The height for the size.
     */
    override fun <R> result(target: BiFunction<Int, Int, R>): R {
        return target.apply(w, h)
    }

    override fun register(redrawable: Redrawable) {}

    override fun toString(): String {
        return StringBuilder("Size")
            .append("(width=").append(w)
            .append(", height=").append(h)
            .append(')')
            .toString()
    }
}