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

package logic.graphic;

import logic.graphic.color.Black;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Tests for {@link logic.graphic.color.Black}.
 * @since 9.1.2
 */
public class BlackTest {
    /**
     * Tests whether the constructor uses the correct values for the black
     * color when the {@link Black#Black(int)} constructor is used.
     */
    @Test
    public void blackWithAlphaCreation() {
        new Black(0).applyOn(
            // @checkstyle ParameterName (1 line)
            (r, g, b, a) -> {
                MatcherAssert.assertThat(r, Matchers.equalTo(0));
                MatcherAssert.assertThat(g, Matchers.equalTo(0));
                MatcherAssert.assertThat(b, Matchers.equalTo(0));
                MatcherAssert.assertThat(a, Matchers.equalTo(0));
            }
        );
    }

    /**
     * Tests whether the constructor uses the correct values for the black
     * color when the {@link Black#Black()} constructor is used.
     */
    @Ignore
    @Test
    public void defaultCreation() {
        new Black().applyOn(
            // @checkstyle ParameterName (1 line)
            (r, g, b, a) -> {
                MatcherAssert.assertThat(r, Matchers.equalTo(0));
                MatcherAssert.assertThat(g, Matchers.equalTo(0));
                MatcherAssert.assertThat(b, Matchers.equalTo(0));
                MatcherAssert.assertThat(
                    a,
                    Matchers.equalTo(Integer.MAX_VALUE)
                );
            }
        );
    }
}
