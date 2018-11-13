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

package logic.metric.size;

import java.util.function.BiFunction;
import java.util.function.ObjIntConsumer;

/**
 * Defines a size that is calculated from two other sizes based on a given
 * operation. The order of the given sizes will be hold when the given operation
 * is called on them.
 * <p>This class is immutable and thread-safe.</p>
 * @since 8.3.0
 */
public class SizeCalculation implements Size {
    /**
     * The first size for the calculation.
     */
    private final Size first;

    /**
     * The second size for the calculation.
     */
    private final Size second;

    /**
     * The operation to be applied on the widths and heights of the given sizes.
     */
    private final BiFunction<Integer, Integer, Integer> operation;

    /**
     * Ctor.
     * @param first The first size for the calculation.
     * @param second The second size for the calculation.
     * @param operation The operation to be applied on the widths and heights of
     *  the given sizes.
     */
    public SizeCalculation(
        final Size first,
        final Size second,
        final BiFunction<Integer, Integer, Integer> operation
    ) {
        this.first = first;
        this.second = second;
        this.operation = operation;
    }

    @Override
    public final <R> R result(final BiFunction<Integer, Integer, R> target) {
        return this.first.result(
            // @checkstyle ParameterName (2 lines)
            (firstWidth, firstHeight) -> this.second.result(
                (secondWidth, secondHeight) -> target.apply(
                    this.operation.apply(firstWidth, secondWidth),
                    this.operation.apply(firstHeight, secondHeight)
                )
            )
        );
    }

    @Override
    public final void applyOn(final ObjIntConsumer<Integer> target) {
        Size.super.applyOn(target);
    }
}