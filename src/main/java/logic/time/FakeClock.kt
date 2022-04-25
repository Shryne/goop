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

import org.cactoos.list.Joined
import org.cactoos.list.ListOf
import kotlin.math.min

/**
 * A clock that will return the given numbers in consecutive calls starting from
 * the first. After the clock has reached the last number, it will stick on this
 * number and return it every time.
 * Example:
 * ```
 * Input: (1, 2, 3, 4, 5) -> millis() => 1, millis() => 2, ...,
 * n5: millis() => 5, millis() => 5
 * ```
 * This class takes [Number] instances to be more flexible. Note that it
 * will call [Number.toLong] to retrieve the value for [millis].
 *
 * This class is mutable and not thread-safe, because [millis]
 * mutates its state.
 * @param times The times to be returned from [millis].
 */
open class FakeClock(private val times: List<Number>) : Clock {
    /**
     * The cursor pointing on the element that will be returned with the next
     * call of [millis].
     */
    private var cursor: Int = -1

    /**
     * @param first The first time that will be returned. This is necessary,
     * because otherwise millis() would throw an exception when asked for a
     * time.
     * @param times The given times that will be returned one after another with
     * each call of [millis].
     */
    constructor(first: Number, vararg times: Number) : this(
        Joined(listOf(first), listOf(*times))
    )

    /**
     * @param times The given times that will be returned one after another with
     * each call of [millis].
     */
    constructor(times: Collection<Number>) : this(ArrayList(times))

    override fun millis(): Long {
        cursor = min(cursor + 1, times.size - 1)
        return times[cursor].toLong()
    }
}