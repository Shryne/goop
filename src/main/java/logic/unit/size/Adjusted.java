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

package logic.unit.size;

import java.util.function.BiFunction;
import java.util.function.IntFunction;

/**
 * An adjusted size.
 * @since 18.5
 */
public class Adjusted implements Size {
    /**
     * The size to adjust.
     */
    private final Size size;

    /**
     * The adjustment of the width.
     */
    private final IntFunction<Integer> width;

    /**
     * The adjustment of the height.
     */
    private final IntFunction<Integer> height;

    /**
     * Ctor.
     * @param size The size to adjust.
     * @param width The adjustment of the width.
     * @param height The adjustment of the height.
     */
    public Adjusted(
        final Size size,
        final IntFunction<Integer> width,
        final IntFunction<Integer> height
    ) {
        this.size = size;
        this.width = width;
        this.height = height;
    }

    @Override
    public final <R> R result(final BiFunction<Integer, Integer, R> target) {
        return this.size.result(
            (first, second) -> target.apply(
                this.width.apply(first),
                this.height.apply(second)
            )
        );
    }
}
