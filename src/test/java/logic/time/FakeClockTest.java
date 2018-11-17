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

import org.cactoos.func.ProcOf;
import org.cactoos.list.ListOf;
import org.cactoos.list.Mapped;
import org.cactoos.list.StickyList;
import org.cactoos.scalar.And;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Tests for {@link FakeClock}.
 * @since 9.2.2
 */
public class FakeClockTest {
    /**
     * Tests whether the given number will be returned when asked for it.
     */
    @Test
    public void oneInOneOut() {
        final var expected = 213L;
        MatcherAssert.assertThat(
            new FakeClock(expected).millis(),
            Matchers.equalTo(expected)
        );
    }

    /**
     * Tests whether a multiple amount of numbers will be returned right.
     */
    @Test
    public void multipleNumbersInAndOut() {
        final var expected = new ListOf<>(5L, 232L, 4324L, 42L, 423L);
        final var clock = new FakeClock(expected);
        MatcherAssert.assertThat(
            new StickyList<>(
                new Mapped<>(
                    it -> clock.millis(),
                    expected
                )
            ),
            Matchers.equalTo(expected)
        );
    }

    /**
     * Tests whether the clock stays on the last number after reaching it.
     * @throws Exception Thanks to {@link And}.
     */
    @Test
    public void staysOnLastNumber() throws Exception {
        final var last = 312L;
        final var expected = new ListOf<>(12L, 432L, 23L, 55L, last);
        final var clock = new FakeClock(expected);
        new And(
            new ProcOf<>(ignored -> clock.millis()),
            expected
        ).value();
        MatcherAssert.assertThat(last, Matchers.equalTo(clock.millis()));
        MatcherAssert.assertThat(last, Matchers.equalTo(clock.millis()));
    }
}
