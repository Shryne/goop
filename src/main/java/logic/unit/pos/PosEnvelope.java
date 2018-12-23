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
import java.util.function.ObjIntConsumer;
import logic.functional.Value;

/**
 * An envelope that delegates everything to the given pos instance.
 * @since 5.13.3
 */
public abstract class PosEnvelope implements Pos {
    /**
     * The pos to delegate the calls to.
     */
    private final Value<Pos> pos;

    /**
     * Ctor.
     * @param pos The pos to delegate the calls to.
     */
    public PosEnvelope(final Value<Pos> pos) {
        this.pos = pos;
    }

    @Override
    public final <R> R result(final BiFunction<Integer, Integer, R> target) {
        return this.pos.content().result(target);
    }

    @Override
    public final void applyOn(final ObjIntConsumer<Integer> target) {
        this.pos.content().applyOn(target);
    }

    @Override
    public final int hashCode() {
        return this.pos.content().hashCode();
    }

    @Override
    public final boolean equals(final Object obj) {
        return this.pos.content().equals(obj);
    }

    @Override
    public final String toString() {
        return this.pos.content().toString();
    }
}
