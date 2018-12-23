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
import org.hamcrest.Description;

/**
 * The expectation of a matcher in a text form. It is supposed to be used in
 * the overridden version of
 * {@link org.hamcrest.TypeSafeDiagnosingMatcher#describeTo(Description)}.
 * <p>Example:</p>
 * <p>Class: Size(width = 15, height = 34)</p>
 * <p>ExpectationString(name = "Size", namedExpectation = [NameExpectation(
 * "width", 15), NamedExpectation("height", 34)]</p>
 * <p>Result of {@link #toString()}: "Size with the following values: width: 15,
 * height: 34"</p>
 * <p>This class uses {@link Lazy} and is therefore mutable and not thread-safe.
 * </p>
 * @see NamedExpectation
 * @since 14.2.0
 */
public class ExpectationText extends CharSequenceEnvelope {
    public ExpectationText(
        final String name, final CharSequence... expectations
    ) {
        super(
            new Lazy<>(
                () -> String.join(
                    "",
                    name,
                    "with the following values: ",
                    new NamedExpectations(expectations)
                )
            )
        );
    }
}
