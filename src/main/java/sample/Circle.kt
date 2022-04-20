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
package sample

import graphic.j2d.event.mouse.J2DMouse
import graphic.j2d.shape.J2DMouseShape
import graphic.j2d.shape.J2DShapeTarget
import graphic.j2d.shape.Redrawable
import logic.graphic.color.Black
import logic.graphic.color.Color
import logic.unit.area.Area
import logic.unit.area.PosOverlap
import logic.unit.area.PosOverlap2D
import logic.unit.area.applyOn
import java.awt.Graphics
import java.util.*
import java.util.function.Consumer
import kotlin.collections.Collection

/**
 * A circle.
 *
 * @param area The area of the circle.
 * @param color The color of the circle.
 * @param targets The targets that shall receive events that may happen to them.
 */
open class Circle(
    private val area: PosOverlap,
    private val color: Color = Black(),
    private val targets: Collection<J2DShapeTarget> = emptyList()
) : J2DMouseShape {
    /**
     * @param area The area of the circle.
     * @param color The color of the circle.
     * @param targets The targets that shall receive events that may happen to them.
     */
    constructor(area: Area, color: Color, vararg targets: J2DShapeTarget) :
        this(PosOverlap2D(area), color, listOf<J2DShapeTarget>(*targets))

    override fun draw(graphics: Graphics): Optional<J2DMouseShape> {
        color.applyOn { r, g, b, a ->
            graphics.color = java.awt.Color(r, g, b, a)
        }
        area.applyOn { x, y, w, h -> graphics.fillOval(x, y, w, h) }
        return Optional.of(this)
    }

    override fun registerFor(source: J2DMouse) {
        targets.forEach(
            Consumer { target -> target.registerFor(source, area) }
        )
    }

    override fun register(redrawable: Redrawable) {
        area.register(redrawable)
        color.register(redrawable)
    }
}