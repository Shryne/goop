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
package logic.unit.area

import logic.Changeable
import logic.functional.QuadConsumer
import logic.functional.QuadFunction
import logic.unit.pos.Pos
import logic.unit.size.Size
import java.util.function.BiConsumer
import java.util.function.BiFunction

/*
This interface is needed (compared to using the pos and the size classes
alone) because some implementations need both like an overlappable area
*/
/**
 * Defines a cartesian two-dimensional rectangular area of a shape.
 */
interface Area : Changeable {
    /**
     * Gives the given function the pos and the size which define this
     * area and returns the result of that. This can be handy if for example
     * one wants to calculate something based on these values and needs the
     * result of it.
     * @param target The target that gets the pos and the size.
     * @param <R> The type of the result.
     * @return The result.
     */
    fun <R> result(target: BiFunction<Pos, Size, R>): R
}

/*
The apply(BiConsumer<Position, Size>) method is not enough, because the
concrete graphic libraries are based on single values (x, y), instead of
the classes of this library. Without this method, one would've to use
two lambdas in multiple places. Additionally I don't want to use this method
alone, because my classes depend on Position, Size and the others and I
would've to convert them back and forth there
*/
/**
 * Gives the given function the pos and the size which define this
 * area. This method shall offer a more convenient way to use the area
 * classes compared to [this.result].
 *
 * This method uses [this.result] to gets it's values
 * and it doesn't mutate the state by itself.
 * @param target Target that gets the pos and the size as four integer
 * values.
 * @param <R> The type of the result.
 * @return The result.
</R> */
fun <R> Area.result(target: QuadFunction<Int, Int, Int, Int, R>): R  =
    result { pos, size ->
        pos.result { x, y ->
            size.result { w, h ->
                target.apply(x, y, w, h)
            }
        }
    }

/**
 * Gives the given consumer the pos and the size which define this
 * area.
 * @param target Target that gets the pos and the size.
 */
fun Area.applyOn(target: BiConsumer<Pos, Size>) {
    result { pos, size -> target.accept(pos, size) }
}
/*
The apply(BiConsumer<Position, Size>) method is not enough, because the
concrete graphic libraries are based on single values (x, y), instead of
the classes of this library. Without this method, one would've to use
two lambdas in multiple places. Additionally I don't want to use this method
alone, because my classes depend on Position, Size and the others and I
would've to convert them back and forth there
*/
/**
 * Gives the given consumer the pos and the size which define this
 * area. This method shall offer a more convenient way to use the area
 * classes compared to [this.applyOn].
 *
 * This method uses [this.applyOn] to gets it's values
 * and it doesn't mutate the state by itself.
 * @param target Target that gets the pos and the size as four integer
 * values.
 */
fun Area.applyOn(target: QuadConsumer<Int, Int, Int, Int>) {
    result { x, y, width, height -> target.accept(x, y, width, height) }
}