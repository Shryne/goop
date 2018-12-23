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

package logic.matcher;

import logic.functional.Lazy;
import logic.functional.Value;

/**
 * An envelope for the {@link CharSequence}. Forwards all calls to the
 * given {@link Value} object. This value object will probably be
 * {@link Lazy}.
 * <p>This class doesn't mutate any state by itself. However, the given Value
 * object is probably mutable.</p>
 * <p>Used for example in:</p>
 * @see NamedExpectation
 * @see NamedExpectations
 * @since 14.2.0
 */
public abstract class CharSequenceEnvelope implements CharSequence {
    /**
     * The value object to forward the calls to.
     */
    private final Value<String> value;

    /**
     * Ctor.
     * @param value The string to forward the method calls to.
     */
    public CharSequenceEnvelope(final String value) {
        this(new Lazy<>(() -> value));
    }

    /**
     * Ctor.
     * @param value The value object to forward the method calls to.
     *  {@link logic.functional.Lazy} can be used for this.
     */
    public CharSequenceEnvelope(final Value<String> value) {
        this.value = value;
    }

    @Override
    public final int length() {
        return this.value.content().length();
    }

    @Override
    public final char charAt(final int index) {
        return this.value.content().charAt(index);
    }

    @Override
    public final CharSequence subSequence(final int start, final int end) {
        return this.value.content().subSequence(start, end);
    }

    @Override
    public final boolean equals(final Object obj) {
        return this.value.content().equals(obj);
    }

    @Override
    public final int hashCode() {
        return this.value.content().hashCode();
    }

    @Override
    public final String toString() {
        return this.value.content();
    }
}
