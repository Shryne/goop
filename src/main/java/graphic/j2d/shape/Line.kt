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
import logic.graphic.color.Black
import logic.graphic.color.Color
import logic.unit.pos.Pos
import logic.unit.pos.applyOn
import java.awt.Graphics
import java.util.*

/**
 * A line.
 *
 * @param first The first pos.
 * @param second The second pos.
 * @param color The color of the line.
 */
open class Line constructor(
    private val first: Pos,
    private val second: Pos,
    private val color: Color = Black()
) : MouseShape {
    /**
     * The successor of this shape.
     */
    private val successor: Optional<MouseShape> = Optional.of(this)

    override fun registerFor(source: J2DMouse) {}

    override fun draw(graphics: Graphics): Optional<MouseShape> {
        color.applyOn { r, g, b, a ->
            graphics.color = java.awt.Color(r, g, b, a)
        }
        first.applyOn { fx, fy ->
            second.applyOn { sx, sy ->
                graphics.drawLine(fx, fy, sx, sy)
            }
        }
        return successor
    }

    override fun register(redrawable: Redrawable) {
        first.register(redrawable)
        second.register(redrawable)
    }
}