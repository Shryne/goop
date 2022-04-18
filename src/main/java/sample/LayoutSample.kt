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

import graphic.j2d.shape.J2DGrid
import graphic.j2d.shape.J2DMouseShape
import graphic.j2d.shape.J2DRect
import graphic.j2d.shape.event.J2DEventRect
import graphic.j2d.window.J2DWindow
import graphic.j2d.window.event.J2DEventWindow
import logic.unit.area.Area2D
import logic.unit.area.PosOverlap2D
import logic.unit.size.Size2D

fun main() {
    J2DEventWindow(
        Area2D(500, 500),
        J2DGrid(
            listOf(
                listOf(J2DEventRect(PosOverlap2D(Area2D(100, 100)))),
                listOf(J2DEventRect(PosOverlap2D(Area2D(100, 100))))
            ),
            1, 1
        )
    ).show()
}