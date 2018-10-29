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
import logic.functional.QuadConsumer;
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
     * Gives the given consumer the position and the size which define this
     * area.
     * @param target Target that gets the position and the size.
     */
    void applyOn(BiConsumer<Pos, Size> target);

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
        // @checkstyle ParameterName (4 lines)
        this.applyOn(
            (pos, size) -> pos.applyOn(
                (x, y) -> size.applyOn(
                    (w, h) -> target.accept(x, y, w, h)
                )
            )
        );
    }
}
