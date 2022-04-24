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
import java.awt.Graphics
import java.util.*
import java.util.function.Consumer

/**
 * A grid.
 *
 * @param shapes The shapes on the grid.
 * @param rows The number of rows.
 * @param columns The number of columns.
 */
open class Grid(
    private val shapes: List<List<MouseShape>>,
    private val rows: Int,
    private val columns: Int
) : MouseShape {
    override fun registerFor(source: Mouse) {
        throw UnsupportedOperationException("#registerFor()")
    }

    override fun draw(graphics: Graphics): Optional<MouseShape> {
        throw UnsupportedOperationException("#draw()")
    }

    /*
    public final Optional<J2DMouseShape> draw(
        final Graphics graphics, final Adjustment adjustment
    ) {
        for (int y = 0; y < this.shapes.size(); ++y) {
            final var row = this.shapes.get(y);
            for (int x = 0; x < row.size(); ++x) {
                this.shapes.get(y).get(x).draw(
                    graphics,
                    (area) -> adjusted(area)
                );
            }
        }
    }*/
    override fun register(redrawable: Redrawable) {
        shapes.forEach(
            Consumer {
                it.forEach(Consumer { shape -> shape.register(redrawable) })
            }
        )
    }
}