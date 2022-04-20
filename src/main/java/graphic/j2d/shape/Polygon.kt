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
package graphic.j2d.shape

import graphic.j2d.event.mouse.J2DMouse
import logic.graphic.color.Color
import logic.unit.pos.Pos
import logic.unit.pos.applyOn
import java.awt.Graphics
import java.awt.Polygon
import java.util.*

typealias J2DPolygon = java.awt.Polygon

/**
 * A polygon.
 *
 * @param color The color of the circle.
 * @param positions The positions of this polygon's edges.
 */
open class Polygon(
    private val color: Color,
    positions: List<Pos>
) : J2DMouseShape {
    /**
     * The positions of this polygon's edges.
     */
    private val positions: J2DPolygon = positions.run {
        val x = IntArray(positions.size)
        val y = IntArray(positions.size)
        for (i in positions.indices) {
            positions[i].applyOn { posX, posY ->
                x[i] = posX
                y[i] = posY
            }
        }
        Polygon(x, y, positions.size)
    }

    /**
     * The successor of this shape.
     */
    private val successor: Optional<J2DMouseShape> = Optional.of(this)

    /**
     * @param color The color of the circle.
     * @param positions The positions of this polygon's edges.
     */
    constructor(color: Color, vararg positions: Pos) : this(
        color,
        listOf<Pos>(*positions)
    )

    override fun registerFor(source: J2DMouse) {}

    override fun draw(graphics: Graphics): Optional<J2DMouseShape> {
        color.applyOn { r: Int, g: Int, b: Int, a: Int ->
            graphics.color = java.awt.Color(
                r, g, b, a
            )
        }
        graphics.fillPolygon(positions)
        return successor
    }

    override fun register(redrawable: Redrawable) {
        color.register(redrawable)
    }
}