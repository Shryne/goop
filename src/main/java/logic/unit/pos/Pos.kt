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
package logic.unit.pos

import logic.Changeable
import java.util.function.BiFunction
import java.util.function.ObjIntConsumer

/*
This interface is necessary (compared to using x and y each time) because this
library depends on the possibility to implement own implementations like a
moving pos

I changed the name from Position to Pos, because that's a class that will be
used in many places and I think this cut will safe some characters without
sacrificing readability
*/
/**
 * A cartesian two dimensional pos.
 */
interface Pos : Changeable {
    /**
     * Gives the given function the x and y coordinates of this pos and
     * returns the result of the function. This can be handy if you want to
     * calculate something with the pos and want to have the result.
     *
     * @param target The target who gets the coordinates.
     * @param <R> The type of the result of the applied function.
     * @return The result of the applied function.
     */
    fun <R> result(target: BiFunction<Int, Int, R>): R
}

/**
 * Gives the given consumer the x and y coordinates of this [Pos].
 *
 * @param target Target that gets the Coordinates.
 */
fun Pos.applyOn(target: ObjIntConsumer<Int>) {
    result { x: Int, y: Int -> target.accept(x, y) }
}