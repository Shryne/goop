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

import java.util.function.IntUnaryOperator;

/**
 * Defines a calculation of a size object with a scalar value by using a given
 * operation:
 * <pre>{@code Size(10, 20) + 15 = Size(25, 35)}</pre>
 * <p>This class is immutable and thread-safe.</p>
 * @since 9.0.0
 */
public class ScalarSizeCalculation extends SizeCalculation {
    /**
     * Ctor.
     * @param size The size of the calculation.
     * @param operation The operation to be applied.
     */
    public ScalarSizeCalculation(
        final Size size,
        final IntUnaryOperator operation
    ) {
        super(
            size,
            new Size2D(0, 0),
            (first, second) -> operation.applyAsInt(first)
        );
    }
}
