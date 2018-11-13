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

package logic.time;

/**
 * Defines a watch that expires after a certain time. Calling {@link #start()}
 * will restart the elapsing. If {@link #start} hasn't been called,
 * {@link #elapsedPercent()} will return 0.0 every time.
 * <p>This class is mutable and not thread-safe, because {@link #start()}
 * mutates its state.</p>
 * @since 8.3.0
 */
public class Expiration implements Elapsable {
    /**
     * The upper bound of the percentage that will be returned by
     * {@link #elapsedPercent()}.
     */
    private static final double MAX_PERCENT = 1.0;

    /**
     * The amount of time needed to elapse.
     * @checkstyle MemberName (2 lines)
     */
    private final long toElapse;

    /**
     * The beginning of the start measurement.
     */
    private long beginning;

    /**
     * Ctor.
     * @param toElapse The amount of time needed to elapse.
     * @checkstyle ParameterName (2 lines)
     */
    public Expiration(final long toElapse) {
        this.toElapse = toElapse;
        this.beginning = 0L;
    }

    @Override
    public final double elapsedPercent() {
        return Math.max(
            (double)
                (System.currentTimeMillis() - this.beginning)
                / (double) this.toElapse,
            Expiration.MAX_PERCENT
        );
    }

    @Override
    public final void start() {
        this.beginning = System.currentTimeMillis();
    }
}
