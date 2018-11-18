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

package logic.metric.pos;

import java.util.function.BiFunction;
import java.util.function.ObjIntConsumer;

/*
I am not happy about this naming, but my other idea's regarding the interface
name and the basic implementation weren't good either (impl or simple for
example). So I took the more annoying name for the implementation to lower the
chance that a user might use the class name as a parameter type
*/
/**
 * Basic concrete implementation of {@link Pos}.
 * <p>This class is immutable and thread-safe.</p>
 * @since 3.4.0
 */
public class Pos2D implements Pos {
    /**
     * The x coordinate.
     * @checkstyle MemberName (2 lines)
     */
    private final int x;

    /**
     * The y coordinate.
     * @checkstyle MemberName (2 lines)
     */
    private final int y;

    /**
     * Ctor.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @checkstyle ParameterName (2 lines)
     */
    public Pos2D(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public final <R> R result(final BiFunction<Integer, Integer, R> target) {
        return target.apply(this.x, this.y);
    }

    @Override
    public final void applyOn(final ObjIntConsumer<Integer> target) {
        Pos.super.applyOn(target);
    }
}
