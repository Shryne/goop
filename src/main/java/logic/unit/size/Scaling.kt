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

import com.google.common.math.DoubleMath
import graphic.j2d.shape.Redrawable
import logic.functional.Lazy
import logic.functional.Value
import logic.time.Elapsable
import logic.time.Expiration
import kotlin.math.roundToInt

/**
 * Represents a changing size.
 * This class is mutable and not thread-safe.
 *
 * @param size The size of scaling.
 * @param watch The watch used to get the scaling progress.
 */
open class Scaling private constructor(
    size: Value<Size>,
    private val watch: Elapsable
) : SizeEnvelope(size), AnimatedSize {
    /**
     * The redrawable to notify about the change.
     */
    private lateinit var redrawable: Redrawable

    /**
     * @param origin The starting size of this object.
     * @param ending The end size of this object.
     * @param milliseconds Amount of milliseconds needed to fulfill the scaling.
     */
    constructor(origin: Size, ending: Size, milliseconds: Long) :
        this(origin, ending, Expiration(milliseconds))

    /**
     * @param origin The starting size of this object.
     * @param ending The end size of this object.
     * @param watch The watch used to get the scaling progress.
     */
    constructor(origin: Size, ending: Size, watch: Elapsable) : this(
        Lazy<Size> {
            Sum(
                origin,
                ScalarSizeCalculation(Diff(ending, origin)) {
                    (it * watch.elapsedPercent()).roundToInt()
                }
            )
        },
        watch
    )

    override fun register(redrawable: Redrawable) {
        this.redrawable = redrawable
    }

    override fun start() {
        watch.start()
        val done = 1.0
        val epsilon = 0.00001
        redrawable.redraw {
            DoubleMath.fuzzyEquals(
                watch.elapsedPercent(), done, epsilon
            )
        }
    }
}