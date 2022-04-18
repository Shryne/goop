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
package logic.unit.size

import graphic.j2d.shape.Redrawable
import java.util.function.BiFunction
import java.util.function.ObjIntConsumer

/**
 * A decorator containing convenience methods that are based on the methods of
 * the interface.
 *
 * @param size The size to delegate the calls to.
 */
open class ConvenientSize(private val size: Size) : Size {
    override fun <R> result(target: BiFunction<Int, Int, R>): R {
        return size.result(target)
    }

    /**
     * Gives the given consumer the width and height that define this size.
     *
     * @param target Target that gets the width and height.
     */
    fun applyOn(target: ObjIntConsumer<Int>) {
        result<Any> { w, h -> target.accept(w, h) }
    }

    override fun register(redrawable: Redrawable) {}
}