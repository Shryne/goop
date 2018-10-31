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

package logic.metric.area;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import logic.functional.QuadConsumer;
import logic.functional.QuadFunction;
import logic.metric.position.Pos;
import logic.metric.size.Size;

/*
This interface is needed (compared to using the position and the size classes
alone) because some implementations need both like an overlappable area
*/
/**
 * Defines a cartesian two dimensional rectangular area of a shape.
 *
 * @since 2.1.0
 */
public interface Area {
    /**
     * Gives the given function the position and the size which define this
     * area and returns the result of that. This can be handy if for example
     * one wants to calculate something based on these values and needs the
     * result of it.
     * @param target The target that gets the position and the size.
     * @param <R> The type of the result.
     * @return The result.
     */
    <R> R result(BiFunction<Pos, Size, R> target);

    /*
    The apply(BiConsumer<Position, Size>) method is not enough, because the
    concrete graphic libraries are based on single values (x, y), instead of
    the classes of this library. Without this method, one would've to use
    two lambdas in multiple places. Additionally I don't want to use this method
    alone, because my classes depend on Position, Size and the others and I
    would've to convert them back and forth there
    */
    /**
     * Gives the given function the position and the size which define this
     * area. This method shall offer a more convenient way to use the area
     * classes compared to {@link this#result(BiFunction)}.
     * <p>This method uses {@link this#result(BiFunction)} to gets it's values
     * and it doesn't mutate the state by itself.</p>
     * @param target Target that gets the position and the size as four integer
     *  values.
     * @param <R> The type of the result.
     * @return The result.
     */
    default <R> R result(
        final QuadFunction<Integer, Integer, Integer, Integer, R> target
    ) {
        return this.result(
            (pos, size) -> pos.result(
                // @checkstyle ParameterName (1 line)
                (x, y) -> size.result(
                    (width, height) -> target.apply(x, y, width, height)
                )
            )
        );
    }

    /**
     * Gives the given consumer the position and the size which define this
     * area.
     * @param target Target that gets the position and the size.
     */
    default void applyOn(final BiConsumer<Pos, Size> target) {
        this.result(
            (pos, size) -> {
                target.accept(pos, size);
                return Void.TYPE;
            }
        );
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
     * Gives the given consumer the position and the size which define this
     * area. This method shall offer a more convenient way to use the area
     * classes compared to {@link this#applyOn(BiConsumer)}.
     * <p>This method uses {@link this#applyOn(BiConsumer)} to gets it's values
     * and it doesn't mutate the state by itself.</p>
     * @param target Target that gets the position and the size as four integer
     *  values.
     */
    default void applyOn(
        final QuadConsumer<Integer, Integer, Integer, Integer> target) {
        // @checkstyle ParameterName (2 lines)
        this.result(
            (x, y, width, height) -> {
                target.accept(x, y, width, height);
                return Void.TYPE;
            }
        );
    }
}
