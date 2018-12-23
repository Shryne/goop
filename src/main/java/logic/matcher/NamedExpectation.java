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

/**
 * A combination of an expectation and the name of it. <p>Example: "width" as
 * the name of the expectation and 16 as the value of it.</p>
 * <p>Result of {@link #toString()}: "width: 16"</p>
 * This class is intended to be used with {@link ExpectationText}.
 * <p>This class uses {@link Lazy} and is therefore mutable and not thread-safe.
 * </p>
 * @see NamedExpectations
 * @since 14.2.0
 */
public class NamedExpectation extends CharSequenceEnvelope {
    /**
     * Ctor.
     * @param name The name of the expected value.
     * @param value The expected value.
     */
    public NamedExpectation(final String name, final Object value) {
        super(
            new Lazy<>(() -> String.join(": ", name, String.valueOf(value)))
        );
    }
}
