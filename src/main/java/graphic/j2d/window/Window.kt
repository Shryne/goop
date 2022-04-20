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
package graphic.j2d.window

import graphic.j2d.shape.J2DShape
import logic.unit.area.Area
import logic.unit.area.Area2D
import org.cactoos.iterable.Joined
import javax.swing.WindowConstants

/**
 * The default implementation of a java 2d window. It uses a
 * [J2DBaseWindow] and adds the following settings to it:
 * - sets the title
 * - sets the default close operation (end application on close)
 *
 * This class mutates its state when [J2DBaseWindow.show] is called.
 * Since show isn't synchronized, this class isn't thread-safe. Additionally,
 * the features can change its state.
 *
 * @param title The title that is shown at the top of the window.
 * @param area The area of the window.
 * @param features Certain features regarding the window.
 * @param shapes The shapes that are drawn on the window.
 */
open class Window(
    title: String,
    area: Area,
    features: Iterable<J2DWindowFeature>,
    shapes: Iterable<J2DShape<*>>
) : J2DBaseWindow(
    area,
    Joined(
        listOf(
            J2DWindowFeature { it.title = title },
            J2DWindowFeature {
                it.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
            }
        ),
        features
    ),
    shapes
) {
    /**
     * @param width The width of the window.
     * @param height The height of the window.
     * @param shapes The shapes that are drawn on the window.
     */
    constructor(
        width: Int,
        height: Int,
        vararg shapes: J2DShape<*>
    ) : this("", Area2D(width, height), listOf(*shapes))

    /**
     * @param title The title that is shown at the top of the window.
     * @param area The area of the window.
     * @param shapes The shapes that are drawn on the window.
     * @param features Certain features regarding the window.
     */
    constructor(
        title: String = "",
        area: Area,
        shapes: Iterable<J2DShape<*>>,
        vararg features: J2DWindowFeature
    ) : this(title, area, listOf(*features), shapes)
}