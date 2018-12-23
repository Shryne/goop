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

import java.util.List;
import logic.functional.Lazy;

/**
 * A list of expectations. Takes multiple {@link CharSequence} implementations
 * and joins them with ", " as the delimiter. {@link NamedExpectation} is a good
 * class to be used as a {@link CharSequence}.
 * <p>Example:</p>
 * <p>Expectations: (name: "A", value: 12), (name: "B", value: 232)</p>
 * <p>Result of {@link #toString()}: "A: 12, B: 232"</p>
 * If no expectations are provided, {@link #toString()} returns "".
 * <p>This class uses {@link Lazy} and is therefore mutable and not thread-safe.
 * </p>
 * @see NamedExpectation
 * @see ExpectationText
 * @since 14.2.0
 */
public class NamedExpectations extends CharSequenceEnvelope {
    /**
     * Ctor.
     * @param expectations A string representation of the expected values.
     *  {@link NamedExpectation} is a good class for that.
     */
    public NamedExpectations(final CharSequence... expectations) {
        this(List.of(expectations));
    }

    /**
     * Ctor.
     * @param expectations A string representation of the expected values.
     *  {@link NamedExpectation} is a good class for that.
     */
    public NamedExpectations(final Iterable<CharSequence> expectations) {
        super(new Lazy<>(() -> String.join(", ", expectations)));
    }
}
