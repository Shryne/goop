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

import graphic.j2d.event.mouse.Mouse
import graphic.j2d.event.mouse.PressRelease
import graphic.j2d.shape.event.EventRect
import logic.functional.Action
import logic.graphic.color.ButtonColor
import logic.graphic.color.DualColor
import logic.unit.area.Area
import logic.unit.area.PosOverlap2D
import java.awt.Graphics
import java.util.*

/**
 * A pressable shape.
 *
 * @param shape The shape of the button.
 */
open class Button private constructor(
    private val shape: MouseShape
) : MouseShape {
    override fun registerFor(source: Mouse) {
        shape.registerFor(source)
    }

    override fun draw(graphics: Graphics): Optional<MouseShape> {
        return shape.draw(graphics)
    }

    override fun register(redrawable: Redrawable) {
        shape.register(redrawable)
    }

    companion object {
        /**
         * @param pen The pen to create the shape of the button.
         * @param overlap The area of the button.
         * @param color The colors of the button.
         * @param action The action to be applied when the button is released.
         */
        operator fun <T> invoke(
            pen: Pen<T>,
            overlap: T,
            color: DualColor,
            action: Action
        ) = Button(
            pen.shape(
                overlap,
                color,
                PressRelease(
                    { color.swap() }
                ) {
                    action.run()
                    color.swap()
                }
            )
        )

        /**
         * @param area The area of the button.
         * @param action The action to be applied when the button is released.
         */
        operator fun invoke(area: Area, action: Action) = Button(
            { _, color, targets -> EventRect(area, color, *targets) },
            PosOverlap2D(area),
            ButtonColor(),
            action
        )
    }
}
