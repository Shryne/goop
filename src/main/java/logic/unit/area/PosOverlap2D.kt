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

/**
 * An area that knows when a point overlaps with itself.
 *
 * @param area The area to delegate the calls to.
 */
open class PosOverlap2D(private val area: Area) : PosOverlap {
    /**
     * Uses (0|0) as its position.
     * @param width The width of the area.
     * @param height The height of the area.
     */
    constructor(width: Int, height: Int) : this(Size2D(width, height))

    /**
     * Uses (0|0) as its position.
     * @param size The size of the area.
     */
    constructor(size: Size) : this(Pos2D(), size)

    /**
     * @param pos The position of the area.
     * @param size The size of the area.
     */
    constructor(pos: Pos, size: Size) : this(Area2D(pos, size))

    override fun contains(pos: Pos): Boolean =
        result { x, y, w, h ->
            pos.result { posX, posY ->
                x <= posX && y <= posY && posX <= x + w && posY <= y + h
            }
        }

    override fun <R> result(target: BiFunction<Pos, Size, R>): R =
        area.result(target)

    override fun register(redrawable: Redrawable) {
        area.register(redrawable)
    }
}