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

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Tests for {@link NamedExpectations}. Even though it provides multiple methods
 * because of {@link CharSequenceEnvelope}, only
 * {@link NamedExpectations#toString()} is tested, because that's the method
 * whose result matters.
 * @since 14.2.0
 */
public class NamedExpectationsTest {
    /**
     * Tests whether the returned value is "" when no NamedExpectations where
     * given.
     */
    @Test
    public void noExpectations() {
        MatcherAssert.assertThat(
            new NamedExpectations().toString(),
            Matchers.equalTo("")
        );
    }

    /**
     * Tests whether one expectation is returned how it is (without "," as the
     * delimiter).
     */
    @Test
    public void oneExpectation() {
        final var name = "Some name";
        final var value = "Some value";
        MatcherAssert.assertThat(
            new NamedExpectations(
                new NamedExpectation(name, value)
            ),
            Matchers.equalTo(
                String.join("", name, ": ", value)
            )
        );
    }

    /**
     * Tests whether multiple expectations are correctly divided by a ", ".
     */
    @Test
    public void someExpectations() {
        final var name1 = "A";
        final var value1 = "1";
        final var name2 = "B";
        final var value2 = "2";
        final var name3 = "C";
        final var value3 = "3";
        MatcherAssert.assertThat(
            new NamedExpectations(
                new NamedExpectation(name1, value1),
                new NamedExpectation(name2, value2),
                new NamedExpectation(name3, value3)
            ),
            Matchers.equalTo(
                String.join(
                    "",
                    name1, ": ", value1, ", ",
                    name2, ": ", value2, ", ",
                    name3, ": ", value3
                )
            )
        );
    }
}
