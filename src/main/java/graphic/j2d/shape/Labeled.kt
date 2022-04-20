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
import logic.graphic.color.RGBA
import logic.unit.area.Area
import java.awt.Graphics
import java.util.*

/**
 * A shape with an attached text.
 *
 * @param shape The shape to be labeled.
 * @param text The text for the shape.
 */
open class Labeled private constructor(
    private val shape: J2DMouseShape,
    private val text: J2DMouseShape
) : J2DMouseShape {
    /**
     * Instance of this to return this as the successor.
     */
    private val self: Optional<J2DMouseShape> = Optional.of(this)

    /**
     * @param text The text to be add.
     * @param area The area of the shape.
     * @param pen The pen to create the shape.
     */
    constructor(text: String, area: Area, pen: J2DPen<Area>) : this(
        pen.shape(area, RGBA(200, 150, 150, 255)),
        Text(text, area)
    )

    /**
     * @param text The text to be added.
     * @param area The area of the shape.
     * @param color The color of the shape.
     * @param pen The pen to create the shape.
     */
    constructor(text: String, area: Area, color: Color, pen: J2DPen<Area>) :
        this(pen.shape(area, color), Text(text, area))

    override fun registerFor(source: J2DMouse) {
        shape.registerFor(source)
    }

    override fun draw(graphics: Graphics): Optional<J2DMouseShape> {
        shape.draw(graphics)
        text.draw(graphics)
        return self
    }

    override fun register(redrawable: Redrawable) {
        shape.register(redrawable)
        text.register(redrawable)
    }
}