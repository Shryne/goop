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
import logic.graphic.color.Black
import logic.graphic.color.Color
import logic.unit.area.Area2D
import logic.unit.pos.Pos
import logic.unit.size.Size2D
import java.util.List

/**
 * A small oval.
 *
 * @param pos The pos of the dot.
 * @param color The color of the dot.
 * @param events The events of the dot.
 */
open class Dot(
    pos: Pos,
    color: Color,
    events: Collection<J2DMouseTarget>
) : Oval(Area2D(pos, Size2D(5, 5)), color, events) {
    /**
     * Creates a black dot.
     * @param pos The pos of the dot.
     * @param events The events of the dot.
     */
    constructor(pos: Pos, vararg events: J2DMouseTarget) :
        this(pos, Black(), listOf(*events))

    /**
     * @param pos The pos of the dot.
     * @param color The color of the dot.
     * @param events The events of the dot.
     */
    constructor(pos: Pos, color: Color, vararg events: J2DMouseTarget) :
        this(pos, color, listOf(*events))
}