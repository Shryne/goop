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
import kotlin.collections.MutableCollection

/**
 * An ordered collection of shapes.
 *
 * @param shapes The shapes of this collection.
 */
class MouseShapes(
    private var shapes: MutableCollection<MouseShape>
) : MouseShape {
    /**
     * The successor of this collection of shapes.
     */
    private val successor: Optional<MouseShape> = Optional.of(this)

    /**
     * Redrawable to redraw.
     */
    private lateinit var redrawable: Redrawable

    /**
     * @param shapes The shapes of this collection.
     */
    constructor(vararg shapes: MouseShape) : this(mutableListOf(*shapes))

    override fun registerFor(source: Mouse) {
        shapes.forEach(Consumer { it.registerFor(source) })
    }

    override fun draw(graphics: Graphics): Optional<MouseShape> {
        val next = shapes.filter { it.draw(graphics).isPresent }.toMutableList()
        val result = if (next.isEmpty()) {
            Optional.empty()
        } else {
            successor
        }
        shapes = next
        return result
    }

    override fun register(redrawable: Redrawable) {
        shapes.forEach { it.register(redrawable) }
        this.redrawable = redrawable
    }

    fun add(shape: MouseShape) {
        shapes.add(shape)
        shape.register(redrawable)
        redrawable.redraw { false }
    }

    fun clear() {
        shapes.clear()
    }
}