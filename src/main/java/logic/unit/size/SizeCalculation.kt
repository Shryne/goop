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
import java.util.function.IntBinaryOperator

/**
 * Defines a size that is calculated from two other sizes based on a given
 * operation. The order of the given sizes will be hold when the given operation
 * is called on them.
 *
 * This class reuses the objects and doesn't create new ones when the
 * calculation is applied.
 *
 * This class is immutable and thread-safe.
 *
 * @param first The first size for the calculation.
 * @param second The second size for the calculation.
 * @param operation The operation to be applied on the widths and heights of
 * the given sizes.
 */
open class SizeCalculation(
    private val first: Size,
    private val second: Size,
    private val operation: IntBinaryOperator
) : Size {
    override fun <R> result(target: BiFunction<Int, Int, R>): R {
        // @checkstyle MethodBodyComments (1 line)
        return first.result { firstW: Int, firstH: Int ->
            second.result { secondW, secondH ->
                target.apply(
                    operation.applyAsInt(firstW, secondH),
                    operation.applyAsInt(firstH, secondH)
                )
            }
        }
    }

    override fun register(redrawable: Redrawable) {
        first.register(redrawable)
        second.register(redrawable)
    }

    override fun toString() = result { w, h -> "Size(width=$w, height=$h)" }
}