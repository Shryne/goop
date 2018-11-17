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

package logic.time;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;
import test.Delta;

/**
 * Tests for {@link Expiration}. These tests depend on {@link FakeClock} to
 * inject certain needed times and calculate whether the methods do their job
 * right.
 * @since 9.2.2
 */
public class ExpirationTest {
    /*
    I check the method multiple times to see whether the elapsedPercent method
    triggers the start
     */
    /**
     * Tests whether the return of a not started Expiration is 0.0 (
     * {@link Expiration#elapsedPercent()}).
     */
    @Test
    public void notStartedReturnsZero() {
        final var expiration = new Expiration(
            new FakeClock(100, 200, 300, 400),
            10L
        );
        final var first = expiration.elapsedPercent();
        MatcherAssert.assertThat(
            first,
            Matchers.closeTo(0.0, new Delta(first).value())
        );
        final var second = expiration.elapsedPercent();
        MatcherAssert.assertThat(
            second,
            Matchers.closeTo(0.0, new Delta(second).value())
        );
    }

    /**
     * Tests whether a given clock that returns a 0 as time will result in a
     * {@link NullPointerException}.
     */
    @Test
    public void zeroTime() {
        final var expiration = new Expiration(
            new FakeClock(0),
            42L
        );
        expiration.start();
        final var result = expiration.elapsedPercent();
        MatcherAssert.assertThat(
            result,
            Matchers.closeTo(0.0, new Delta(result).value())
        );
    }

    /**
     * Tests whether a given toElapse time of 0 will result in a
     * {@link NullPointerException}.
     */
    @Ignore
    @Test(expected = NullPointerException.class)
    public void zeroElapse() {
        final var expiration = new Expiration(0L);
        expiration.start();
        expiration.elapsedPercent();
    }

    /**
     * Tests whether the call of {@link Expiration#start()} does restart the
     * time.
     */
    @Test
    public void reset() {
        final var expiration = new Expiration(
            new FakeClock(10, 10, 20, 20),
            10L
        );
        expiration.start();
        final var first = expiration.elapsedPercent();
        MatcherAssert.assertThat(
            first,
            Matchers.closeTo(0.0, new Delta(first).value())
        );
        expiration.start();
        final var second = expiration.elapsedPercent();
        MatcherAssert.assertThat(
            second,
            Matchers.closeTo(0.0, new Delta(second).value())
        );
    }

    /**
     * Tests whether the calculation of the elapsed time percentage is right
     * in the method {@link Expiration#elapsedPercent()}.
     */
    @Test
    public void calculatesElapsedCorrectly() {
        final var expiration = new Expiration(
            new FakeClock(0, 5, 8),
            10L
        );
        expiration.start();
        final var first = expiration.elapsedPercent();
        final double expected = 0.5;
        MatcherAssert.assertThat(
            first,
            Matchers.closeTo(expected, new Delta(first).value())
        );
    }

    /**
     * Tests whether {@link Expiration#elapsedPercent()} does calculate 1.0
     * as the final percentage and stays there.
     */
    @Test
    public void percentageAfterElapsing() {
        final var expiration = new Expiration(
            new FakeClock(0, 50, 100),
            5
        );
        expiration.start();
        final var first = expiration.elapsedPercent();
        MatcherAssert.assertThat(
            first,
            Matchers.closeTo(1.0, new Delta(first).value())
        );
        final var second = expiration.elapsedPercent();
        MatcherAssert.assertThat(
            second,
            Matchers.closeTo(1.0, new Delta(second).value())
        );
    }
}
