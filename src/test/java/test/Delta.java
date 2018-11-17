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

package test;

import org.cactoos.Scalar;

/**
 * Represents the delta according to:
 * https://stackoverflow.com/a/50547044/7563350
 * <p>It serves as the delta argument for <b>double values</b>.</p>
 * <p>This class is immutable and thread-safe.</p>
 * @since 9.2.2
 */
public class Delta implements Scalar<Double> {
    /**
     * A value that will multiply the nextAfter value.
     */
    private static final double MULTIPLIER = 0x1.0000000000001p-51;

    /**
     * The value to test. It is needed to calculate the value after it.
     */
    private final double test;

    /**
     * Ctor.
     * @param test The value to test. It is needed to calculate the value after
     *  it.
     */
    public Delta(final double test) {
        this.test = test;
    }

    @Override
    public final Double value() {
        return Math.nextAfter(this.test, Double.POSITIVE_INFINITY)
            * Delta.MULTIPLIER;
    }
}
