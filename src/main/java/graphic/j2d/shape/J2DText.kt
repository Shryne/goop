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
import logic.graphic.color.Color.applyOn
import logic.unit.area.Area
import logic.unit.area.Area2D
import logic.unit.pos.Pos
import logic.unit.pos.Pos2D
import logic.unit.size.Size
import logic.unit.size.Size2D
import java.awt.Font
import java.awt.Graphics
import java.awt.Toolkit
import java.util.*

/**
 * Java 2d based text.
 * @since 19.2
 */
class J2DText @JvmOverloads constructor(
    /**
     * The text to show.
     */
    private val text: String,
    /**
     * The max area of the text.
     */
    private val area: Area,
    /**
     * The color of the text.
     */
    private val color: Color = Black()
) : J2DMouseShape {
    /**
     * The font of the text.
     */
    private var font: Font? = null

    /**
     * The height of the font.
     */
    private var height = 0

    /**
     * The successor of this text.
     */
    private val successor: Optional<J2DMouseShape>

    /**
     * Ctor.
     * @param text The text to show.
     * @param x The x coordinate of the text.
     * @param y The y coordinate of the text.
     */
    constructor(text: String, x: Int, y: Int) : this(text, Pos2D(x, y)) {}
    /**
     * Ctor.
     * @param text The text to show.
     * @param pos The position of the text.
     * @param color The color of the text.
     */
    /**
     * Ctor.
     * @param text The text to show.
     * @param pos The position of the text.
     */
    @JvmOverloads
    constructor(text: String, pos: Pos?, color: Color = Black()) : this(
        text,
        Area2D(
            pos!!,
            Size2D(
                text.length
                    * Toolkit.getDefaultToolkit().screenSize.width
                    / 150,
                Toolkit.getDefaultToolkit().screenSize.height / 100
            )
        ),
        color
    ) {
    }
    /**
     * Ctor.
     * @param text The text to show.
     * @param area The area of the text.
     * @param color The color of the text.
     */
    /**
     * Ctor.
     * @param text The text to show.
     * @param area The area of the text.
     */
    init {
        successor = Optional.of(this)
    }

    override fun registerFor(source: J2DMouse) {}
    override fun draw(graphics: Graphics): Optional<J2DMouseShape> {
        color.applyOn { r: Int?, g: Int?, b: Int?, a: Int? ->
            graphics.color = java.awt.Color(
                r!!, g!!, b!!, a!!
            )
        }
        if (font == null) {
            var fontSize = 10
            do {
                font = Font("Arial", Font.PLAIN, fontSize)
                val metrics = graphics.getFontMetrics(font)
                val width = metrics.stringWidth(text)
                height = metrics.ascent
                if (area.result { pos: Pos?, size: Size -> size.result { w: Int, h: Int -> w < width || h < height } }) {
                    break
                }
                ++fontSize
            } while (true)
            font = Font("Arial", Font.PLAIN, fontSize - 1)
        }
        graphics.font = font
        area.applyOn { x, y, w, h ->
            graphics.drawString(
                text,
                x,
                y + height
            )
        }
        return successor
    }

    override fun register(redrawable: Redrawable) {
        area.register(redrawable)
    }
}