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

import graphic.j2d.event.J2DMouseTarget
import graphic.j2d.event.mouse.Mouse
import logic.graphic.color.Black
import logic.graphic.color.Color
import logic.unit.area.Area
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
 * @param events The events of the circle.
 */
open class Oval(
    private val area: Area,
    private val color: Color,
    private val events: Collection<J2DMouseTarget>
) : MouseShape {
    /**
     * The successor of this shape.
     */
    private val successor: Optional<MouseShape> = Optional.of(this)

    /**
     * @param area The area of the circle.
     * @param color The color of the circle. The default is [Black].
     * @param events The events of the circle.
     */
    constructor(
        area: Area,
        color: Color = Black(),
        vararg events: J2DMouseTarget
    ) : this(area, color, listOf<J2DMouseTarget>(*events))

    override fun registerFor(source: Mouse) {
        events.forEach(Consumer { it.registerFor(source) })
    }

    override fun draw(graphics: Graphics): Optional<MouseShape> {
        color.applyOn { r, g, b, a ->
            graphics.color = java.awt.Color(r, g, b, a)
        }
        area.applyOn { x, y, w, h -> graphics.fillOval(x, y, w, h) }
        return successor
    }

    override fun register(redrawable: Redrawable) {
        area.register(redrawable)
        color.register(redrawable)
    }
}