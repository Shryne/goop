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
import java.util.function.ObjIntConsumer;
import logic.functional.Value;

/**
 * An envelope that delegates everything to the given size instance.
 * @since 6.1.1
 */
public abstract class SizeEnvelope implements Size {
    /**
     * The size to delegate the calls to.
     */
    private final Value<Size> size;

    /**
     * Ctor.
     * @param size The size to delegate the calls to.
     */
    public SizeEnvelope(final Value<Size> size) {
        this.size = size;
    }

    @Override
    public final <R> R result(final BiFunction<Integer, Integer, R> target) {
        return this.size.content().result(target);
    }

    @Override
    public final void applyOn(final ObjIntConsumer<Integer> target) {
        this.size.content().applyOn(target);
    }

    @Override
    public final int hashCode() {
        return this.size.content().hashCode();
    }

    @Override
    public final boolean equals(final Object obj) {
        return this.size.content().equals(obj);
    }

    @Override
    public final String toString() {
        return this.size.content().toString();
    }
}
