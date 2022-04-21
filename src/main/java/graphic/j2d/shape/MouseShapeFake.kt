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
import java.awt.Graphics
import java.util.*

/**
 * A fake for [MouseShape].
 */
open class MouseShapeFake : MouseShape {
    /**
     * The successor of this shape.
     */
    private val successor: Optional<MouseShape>

    /**
     * Whether [draw] was called.
     */
    private var drawn: Boolean = false

    /**
     * Whether [registerFor] was called.
     */
    private var registered: Boolean = false

    /**
     * Creates the object with itself as the successor.
     */
    constructor() {
        successor = Optional.of(this)
    }

    /**
     * @param successor The successor for [draw].
     */
    constructor(successor: Optional<MouseShape>) {
        this.successor = successor
    }

    override fun registerFor(source: J2DMouse) {
        registered = true
    }

    override fun draw(graphics: Graphics): Optional<MouseShape> {
        drawn = true
        return successor
    }

    override fun register(redrawable: Redrawable) {}

    /**
     * @return Whether it was drawn.
     */
    fun wasDrawn(): Boolean {
        return drawn
    }

    /**
     * @return Whether it has been registered.
     */
    fun hasBeenRegistered(): Boolean {
        return registered
    }

    /**
     * Resets the flags.
     */
    fun clean() {
        drawn = false
        registered = false
    }
}