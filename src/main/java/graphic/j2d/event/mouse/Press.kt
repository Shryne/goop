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
import logic.unit.PosOverlap
import logic.unit.pos.Pos
import logic.unit.pos.Pos2D
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.util.function.Consumer

/**
 * A mouse press bound on a component to apply some action on activation.
 * This class is immutable, but does mutate the state of the mouse.
 *
 * @param target The target with the action, who gets the x and y
 * coordinates of the press.
 */
class Press(private val target: Consumer<Pos>) : ShapeTarget {
    /**
     * @param action The action to be applied when the press occurs.
     */
    constructor(action: Action) : this(Consumer<Pos> { action.run() })

    override fun registerFor(source: Mouse, overlap: PosOverlap) {
        source.register(
            object : MouseAdapter() {
                override fun mousePressed(event: MouseEvent) {
                    val pos = Pos2D(event.x, event.y)
                    if (pos in overlap) {
                        target.accept(pos)
                    }
                }
            } as MouseListener
        )
    }
}