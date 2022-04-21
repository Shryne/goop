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

import graphic.j2d.event.mouse.PressRelease
import logic.functional.Action
import logic.graphic.color.ButtonColor
import logic.graphic.color.DualColor
import logic.unit.area.Area
import logic.unit.area.Area2D
import logic.unit.size.Size

/**
 * A clickable form that does some action when clicked and released. Note that
 * the action won't be applied if the release happens outside of the button.
 *
 * @param area The area of the button.
 * @param action The action to be applied.
 * @param color The color of the button.
 */
open class Button private constructor(
    area: Area,
    action: Action,
    color: DualColor
) : Circle(
    area,
    color,
    PressRelease({ color.swap() }) {
        color.swap()
        action.run()
    }
) {
    /**
     * Creates a button using the [ButtonColor].
     * @param area The area of the button.
     * @param action The action to be applied.
     */
    constructor(area: Area, action: Action) : this(
        area,
        action,
        ButtonColor()
    )

    /**
     * Creates a button using the [ButtonColor] and with a position of (0,0).
     * @param size The size of the button.
     * @param action The action to be applied.
     */
    constructor(size: Size, action: Action) : this(Area2D(size), action)
}