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
package graphic.j2d.shape.event

import graphic.j2d.event.mouse.J2DMouse
import graphic.j2d.shape.J2DMouseShape
import graphic.j2d.shape.J2DShapeTarget
import graphic.j2d.shape.J2DSuccessor
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

/**
 * A java 2d rect that supports mouse events.
 *
 * This class doesn't change its own state. Whether it is immutable or not,
 * depends on the given constructor arguments. Additionally whether this
 * class is thread-safe or not, depends on the given graphics instance for
 * [draw].
 *
 * @param area The area of this rect. It needs to be a PosOverlap area to check whether
 * a mouse event occurred on itself.
 * @param color The color of this rect.
 * @param successor The next shape to be drawn.
 * @param targets The targets for the mouse events.
 */
open class EventRect(
    private val area: PosOverlap,
    private val color: Color,
    private val successor: J2DSuccessor,
    private val targets: List<J2DShapeTarget>
) : J2DMouseShape {
    /**
     * Self to success.
     */
    private val self: Optional<J2DMouseShape> = Optional.of(this)

    /**
     * @param area The area of this rect.
     * @param targets The targets for the mouse events.
     */
    constructor(area: PosOverlap, vararg targets: J2DShapeTarget) :
        this(area, listOf(*targets))

    /**
     * @param area The area of this rect.
     * @param color The color of this rect.
     * @param targets The targets for the mouse events.
     */
    constructor(area: Area, color: Color, vararg targets: J2DShapeTarget) :
        this(PosOverlap2D(area), color, listOf(*targets)
    )

    /**
     * @param area The area of this rect.
     * @param color The color of this rect.
     * @param targets The targets for the mouse events.
     */
    constructor(
        area: PosOverlap,
        color: Color,
        vararg targets: J2DShapeTarget
    ) : this(area, color, listOf(*targets))

    /**
     * @param area The area of this rect.
     * @param targets The targets for the mouse events.
     */
    constructor(area: PosOverlap, targets: List<J2DShapeTarget>) :
        this(area, Black(), targets)

    /**
     * Ctor.
     * @param area The area of this rect.
     * @param color The color of this rect. The default is [Black].
     * @param targets The targets for the mouse events.
     */
    constructor(
        area: PosOverlap,
        color: Color = Black(),
        targets: List<J2DShapeTarget> = emptyList()
    ) : this(area, color, J2DSuccessor { it }, targets)

    override fun draw(graphics: Graphics): Optional<J2DMouseShape> {
        color.applyOn { r, g, b, a ->
            graphics.color = java.awt.Color(r, g, b, a)
        }
        area.applyOn { x, y, w, h -> graphics.fillRect(x, y, w, h) }
        return successor.apply(self)
    }

    override fun registerFor(source: J2DMouse) {
        targets.forEach(Consumer { it.registerFor(source, area) })
    }

    override fun register(redrawable: Redrawable) {
        area.register(redrawable)
        color.register(redrawable)
    }
}