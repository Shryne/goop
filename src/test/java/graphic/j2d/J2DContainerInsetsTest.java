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

package graphic.j2d;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import logic.functional.QuadFunction;
import logic.metric.Insets;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Tests for {@link J2DContainerInsets}.
 * @since 13.2.2
 */
public class J2DContainerInsetsTest {
    /**
     * Tests whether the {@link J2DContainerInsets#result(QuadFunction)} method
     * returns the correct insets in the correct order.
     */
    @Test
    public void correctInsets() {
        final var top = 120;
        final var left = 23;
        final var right = 232;
        final var bottom = 234;
        final var panel = new JPanel();
        final var padding = BorderFactory.createEmptyBorder(
            top, left, bottom, right
        );
        panel.setBorder(padding);
        new J2DContainerInsets(
            panel
        ).result(
            // @checkstyle ParameterNameCheck (1 line)
            (resTop, resLeft, resRight, resBottom) -> {
                MatcherAssert.assertThat(resTop, Matchers.equalTo(top));
                MatcherAssert.assertThat(resLeft, Matchers.equalTo(left));
                MatcherAssert.assertThat(resRight, Matchers.equalTo(right));
                MatcherAssert.assertThat(resBottom, Matchers.equalTo(bottom));
                return Void.TYPE;
            }
        );
    }

    /**
     * Tests whether the object doesn't cache the
     * {@link J2DContainerInsets#result(QuadFunction)} of its result method. If
     * it does, changing the given containers insets wouldn't change the result
     * of the result method.
     */
    @Test
    public void doesNotCache() {
        // @checkstyle LocalFinalVariableName (4 lines)
        final var preTop = 413;
        final var preLeft = 232;
        final var preRight = 432;
        final var preBottom = 345;
        final var panel = new JPanel();
        panel.setBorder(
            BorderFactory.createEmptyBorder(
                preTop, preLeft, preBottom, preRight
            )
        );
        final Insets insets = new J2DContainerInsets(panel);
        insets.result(
            // @checkstyle ParameterNameCheck (1 line)
            (resTop, resLeft, resRight, resBottom) -> Void.TYPE
        );
        final var top = 45;
        final var left = 412;
        final var right = 453;
        final var bottom = 123;
        panel.setBorder(
            BorderFactory.createEmptyBorder(top, left, bottom, right)
        );
        insets.result(
            // @checkstyle ParameterNameCheck (1 line)
            (resTop, resLeft, resRight, resBottom) -> {
                MatcherAssert.assertThat(resTop, Matchers.equalTo(top));
                MatcherAssert.assertThat(resLeft, Matchers.equalTo(left));
                MatcherAssert.assertThat(resRight, Matchers.equalTo(right));
                MatcherAssert.assertThat(resBottom, Matchers.equalTo(bottom));
                return Void.TYPE;
            }
        );
    }
}
