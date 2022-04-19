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
package logic.unit.area

import graphic.j2d.shape.Redrawable
import logic.unit.pos.Pos
import logic.unit.pos.Pos2D
import logic.unit.size.Size
import logic.unit.size.Size2D
import java.util.function.BiFunction

/*
I am not happy about this naming, but my other idea's regarding the interface
name and the basic implementation weren't good either (impl or simple for
example). So I took the more annoying name for the implementation to lower the
chance that a user might use the class name as a parameter type
*/
/**
 * Basic concrete implementation of [Area].
 *
 * Whether this class is immutable or thread-safe, depends on the given
 * [Pos] and [Size]. This class
 * doesn't mutate state by itself. Additionally note that the methods provide
 * the actual objects without any
 * safe-copies for performance reasons.
 *
 * @param pos The pos of the area.
 * @param size The size of the area.
 */
open class Area2D(private val pos: Pos, private val size: Size) : Area {
    /**
     * Uses (0|0) as its pos.
     * @param width The width for the size.
     * @param height The height for the size.
     */
    constructor(width: Int, height: Int) : this(0, 0, width, height)

    /**
     * @param x The x coordinate for the pos.
     * @param y The y coordinate for the pos.
     * @param width The width for the size.
     * @param height The height for the size.
     */
    constructor(x: Int, y: Int, width: Int, height: Int) : this(
        Pos2D(x, y),
        Size2D(width, height)
    )

    /**
     * Uses (0|0) as its pos.
     * @param size The size of the area.
     */
    constructor(size: Size) : this(Pos2D(0, 0), size)

    override fun <R> result(target: BiFunction<Pos, Size, R>): R =
        target.apply(pos, size)

    override fun register(redrawable: Redrawable) {
        pos.register(redrawable)
        size.register(redrawable)
    }
}