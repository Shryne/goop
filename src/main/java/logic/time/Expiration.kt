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
package logic.time

import kotlin.math.min

/**
 * Defines a watch that expires after a certain time. Calling [start]
 * will restart the elapsing. If [start] hasn't been called,
 * [elapsedPercent] will return 0.0 every time.
 * This class is mutable and not thread-safe, because [start]
 * mutates its state.
 *
 * @param clock The clock to retrieve the time from.
 * @param toElapse The amount of time needed to elapse.
 */
open class Expiration(
    private val clock: Clock,
    private val toElapse: Long
) : Elapsable {
    /**
     * The beginning of the start measurement.
     */
    private var beginning = 0L

    /**
     * This variable is true when [start] has been called at least once
     * and it then stays true forever.
     */
    private var started = false

    /**
     * @param toElapse The amount of time needed to elapse.
     */
    constructor(toElapse: Long) : this(SystemClock(), toElapse)

    override fun elapsedPercent(): Double =
        if (started) {
            min(
                (clock.millis() - beginning).toDouble() / toElapse.toDouble(),
                MAX_PERCENT
            )
        } else {
            0.0
        }

    override fun start() {
        started = true
        beginning = clock.millis()
    }

    companion object {
        /**
         * The upper bound of the percentage that will be returned by
         * [elapsedPercent].
         */
        private const val MAX_PERCENT = 1.0
    }
}