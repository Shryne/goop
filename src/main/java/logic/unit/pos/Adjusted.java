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

package logic.unit.pos;

import java.util.function.BiFunction;
import java.util.function.IntFunction;

/**
 * An adjusted pos.
 * @since 18.4
 */
public class Adjusted implements Pos {
    /**
     * The pos to adjust.
     */
    private final Pos pos;

    /**
     * The adjustment of x.
     * @checkstyle MemberName (2 lines)
     */
    private final IntFunction<Integer> x;

    /**
     * The adjustment of y.
     * @checkstyle MemberName (2 lines)
     */
    private final IntFunction<Integer> y;

    /**
     * Ctor.
     * @param pos The position to adjust.
     * @param x The adjustment of x.
     * @param y The adjustment of y.
     * @checkstyle ParameterName (5 lines)
     */
    public Adjusted(
        final Pos pos,
        final IntFunction<Integer> x,
        final IntFunction<Integer> y
    ) {
        this.pos = pos;
        this.x = x;
        this.y = y;
    }

    @Override
    public final <R> R result(final BiFunction<Integer, Integer, R> target) {
        return this.pos.result(
            (first, second) -> target.apply(
                this.x.apply(first),
                this.y.apply(second)
            )
        );
    }
}
