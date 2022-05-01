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

import graphic.j2d.event.Event
import graphic.j2d.event.mouse.Mouse
import logic.graphic.color.Color
import logic.unit.area.Area
import logic.unit.area.applyOn
import java.awt.Graphics

/**
 * A rectangle.
 *
 * This class doesn't change its own state. Whether it is immutable or not,
 * depends on the given constructor arguments. Additionally, whether this
 * class is thread-safe or not, depends on the given graphics instance for
 * [draw].
 *
 * @param area The area of this rect.
 * @param color The color of this rect.
 */
open class Rect(
    private val area: Area,
    private val color: Color,
    private val targets: List<Event>
) : Shape {
    override fun draw(graphics: Graphics): Shape? {
        color.applyOn { r, g, b, a ->
            graphics.color = java.awt.Color(r, g, b, a)
        }
        area.applyOn { x, y, w, h -> graphics.fillRect(x, y, w, h) }
        return this
    }

    override fun registerFor(mouse: Mouse) {
        targets.forEach { it.registerFor(mouse, area) }
    }

    override fun register(redrawable: Redrawable) {
        area.register(redrawable)
        color.register(redrawable)
    }
}