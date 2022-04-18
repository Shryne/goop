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
package logic.graphic.color

import graphic.j2d.shape.Redrawable
import logic.functional.QuadConsumer

/**
 * The default color of a button.
 */
open class ButtonColor : DualColor {
    /**
     * The color of a released button.
     */
    private val release: Color = RGBA(100, 100, 100, 255)

    /**
     * The color of a pressed button.
     */
    private val press: Color = RGBA(200, 200, 200, 255)

    /**
     * The current color of the button.
     */
    private var current: Color = release

    /**
     * The redrawable to notify about the change.
     */
    private lateinit var redrawable: Redrawable

    override fun swap() {
        if (current === release) {
            current = press
        } else {
            current = release
        }
        redrawable.redraw { false }
    }

    override fun applyOn(target: QuadConsumer<Int, Int, Int, Int>) {
        current.applyOn(target)
    }

    override fun register(redrawable: Redrawable) {
        this.redrawable = redrawable
    }
}