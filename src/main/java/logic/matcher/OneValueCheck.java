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

import org.hamcrest.Description;

/**
 * A check of a single value. This is intentioned to be used for
 * {@link java.util.function.Function} and {@link java.util.function.Consumer}
 * related checks.
 * @param <T> The type of the value that is expected.
 * @see BiFunctionCheck
 * @since 14.2.0
 */
public interface OneValueCheck<T> {
    /**
     * Checks whether the given expectation is equal (according to
     * {@link java.util.Objects#equals(Object, Object)}) to the value that this
     * object holds.
     * @param expected The expected value.
     * @param description The description that is used to write what went wrong.
     *  It is only used if the values aren't equal.
     * @return True if the values are equal and false otherwise.
     */
    boolean checkFirst(T expected, Description description);
}
