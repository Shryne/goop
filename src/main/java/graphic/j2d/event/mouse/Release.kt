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
package graphic.j2d.event.mouse

import graphic.j2d.shape.ShapeTarget
import logic.functional.Action
import logic.unit.Overlap
import logic.unit.pos.Pos2D
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseListener

/**
 * A mouse button release bound on a component to apply some action on
 * activation.
 * This class is immutable, but does mutate the state of the mouse.
 *
 * @param action The action to be applied when the mouse release occurred.
 */
open class Release(private val action: Action) : ShapeTarget {
    override fun registerFor(source: Mouse, overlap: Overlap) {
        source.register(
            object : MouseAdapter() {
                override fun mouseReleased(event: MouseEvent) {
                    if (overlap.contains(Pos2D(event.x, event.y))) {
                        action.run()
                    }
                }
            } as MouseListener
        )
    }
}