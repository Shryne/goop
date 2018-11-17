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
import logic.metric.Animation;
import logic.time.Elapsable;
import logic.time.Expiration;
import lombok.ToString;

/**
 * Represents a changing size.
 * <p>This class is mutable and not thread-safe.</p>
 * @since 8.3.0
 */
@ToString(of = "size")
public class Scaling implements Size, Animation {
    /**
     * The starting size of this object.
     */
    private final Size size;

    /**
     * The watch used to get the scaling progress.
     */
    private final Elapsable watch;

    /**
     * Ctor.
     * @param origin The starting size of this object.
     * @param ending The end size of this object.
     * @param milliseconds Amount of milliseconds needed to fulfill the scaling.
     */
    public Scaling(
        final Size origin, final Size ending, final long milliseconds
    ) {
        this(origin, ending, new Expiration(milliseconds));
    }

    /**
     * Ctor.
     * @param origin The starting size of this object.
     * @param ending The end size of this object.
     * @param watch The watch used to get the scaling progress.
     */
    public Scaling(
        final Size origin, final Size ending, final Elapsable watch
    ) {
        this(
            new Sum(
                origin,
                new ScalarSizeCalculation(
                    new Diff(ending, origin),
                    value -> (int) Math.round(value * watch.elapsedPercent())
                )
            ),
            watch
        );
    }

    /**
     * Ctor.
     * @param size The size of scaling.
     * @param watch The watch used to get the scaling progress.
     */
    private Scaling(final Size size, final Elapsable watch) {
        this.size = size;
        this.watch = watch;
    }

    @Override
    public final <R> R result(final BiFunction<Integer, Integer, R> target) {
        return this.size.result(target);
    }

    @Override
    public final void start() {
        this.watch.start();
    }
}
