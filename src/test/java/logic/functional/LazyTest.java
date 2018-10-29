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

package logic.functional;

import java.util.concurrent.atomic.AtomicInteger;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Tests for {@link Lazy}.
 *
 * @since 3.2.0
 */
public class LazyTest {
    /**
     * A simple test to determine whether lazy returns any value.
     */
    @Test
    public void returnsValue() {
        final var result = 15;
        MatcherAssert.assertThat(
            "Should return the specified value.",
            new Lazy<>(() -> result).content(),
            Matchers.is(result)
        );
    }

    /**
     * Tests if the value is being cached.
     */
    @Test
    public void cachesValue() {
        final var value = 100;
        final var lazy = new Lazy<>(
            () -> new AtomicInteger(value).getAndAdd(1)
        );
        MatcherAssert.assertThat(
            "First call should return the specified value.",
            lazy.content().intValue(),
            Matchers.is(value)
        );
        MatcherAssert.assertThat(
            String.join(
                "Second call should return the same value again,",
                "even though the backing object has been mutated."
            ),
            lazy.content().intValue(),
            Matchers.is(value)
        );
    }
}
