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

import java.util.Objects;
import logic.functional.Lazy;

/**
 * The message used for {@link org.hamcrest.Description} when two objects aren't
 * equal.
 * @see BiFunctionCheck
 * @since 14.2.0
 */
public class EqualsFailText extends CharSequenceEnvelope {
    /**
     * Ctor.
     * @param result The resulting object from the test.
     * @param expected The expected object.
     * @param name The name of the tested object.
     */
    public EqualsFailText(
        final Object result, final Object expected, final String name
    ) {
        super(
            new Lazy<>(
                () -> {
                    final String message;
                    if (Objects.equals(result, expected)) {
                        message = "";
                    } else {
                        message = String.join(
                            "",
                            "was ",
                            Objects.toString(result),
                            " which is not the expected value ",
                            Objects.toString(expected),
                            " for ",
                            name,
                            "\n"
                        );
                    }
                    return message;
                }
            )
        );
    }
}
