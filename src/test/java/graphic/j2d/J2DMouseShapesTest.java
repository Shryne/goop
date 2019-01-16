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

import graphic.j2d.shape.J2DMouseShapeFake;
import graphic.j2d.shape.J2DMouseShapes;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.Optional;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Tests for {@link J2DMouseShapes}.
 * @since 18.2
 */
public final class J2DMouseShapesTest {
    /**
     * {@link J2DMouseShapes#draw(Graphics)} must return an empty optional if
     * there are no shapes left.
     */
    @Test
    public void emptyToEmptyDraw() {
        MatcherAssert.assertThat(
            new J2DMouseShapes().draw(null),
            Matchers.equalTo(Optional.empty())
        );
    }

    /**
     * {@link J2DMouseShapes#draw(Graphics)} with one element must draw one
     * element and keep that element.
     */
    @Test
    public void oneToOneDraw() {
        final var shape = new J2DMouseShapeFake();
        final var shapes = new J2DMouseShapes(shape);
        shapes.draw(null);
        MatcherAssert.assertThat(shape.wasDrawn(), Matchers.is(true));
        shape.clean();
        shapes.draw(null);
        MatcherAssert.assertThat(shape.wasDrawn(), Matchers.is(true));
    }

    /**
     * {@link J2DMouseShapes#draw(Graphics)} with multiple elements must return
     * draw multiple elements and keep them.
     */
    @Test
    public void multipleToMultipleDraw() {
        final var fakes = new J2DMouseShapeFake[]{
            new J2DMouseShapeFake(),
            new J2DMouseShapeFake(),
            new J2DMouseShapeFake(),
            new J2DMouseShapeFake(),
        };
        final var shapes = new J2DMouseShapes(fakes);
        shapes.draw(null);
        Arrays.stream(fakes).forEach(
            it -> MatcherAssert.assertThat(it.wasDrawn(), Matchers.is(true))
        );
        Arrays.stream(fakes).forEach(J2DMouseShapeFake::clean);
        shapes.draw(null);
        Arrays.stream(fakes).forEach(
            it -> MatcherAssert.assertThat(it.wasDrawn(), Matchers.is(true))
        );
    }

    /**
     * {@link J2DMouseShapes#draw(Graphics)} with one element that returns two
     * must take the two elements.
     */
    @Test
    public void oneToTwoDraw() {
        final var second = new J2DMouseShapeFake();
        final var third = new J2DMouseShapeFake();
        final var first = new J2DMouseShapeFake(
            Optional.of(
                new J2DMouseShapes(second, third)
            )
        );
        final var shapes = new J2DMouseShapes(first);
        shapes.draw(null);
        MatcherAssert.assertThat(first.wasDrawn(), Matchers.is(true));
        MatcherAssert.assertThat(second.wasDrawn(), Matchers.is(false));
        MatcherAssert.assertThat(third.wasDrawn(), Matchers.is(false));
        first.clean();
        shapes.draw(null);
        MatcherAssert.assertThat(first.wasDrawn(), Matchers.is(false));
        MatcherAssert.assertThat(second.wasDrawn(), Matchers.is(true));
        MatcherAssert.assertThat(third.wasDrawn(), Matchers.is(true));
    }

    /**
     * {@link J2DMouseShapes#draw(Graphics)} with one element that returns an
     * empty optional must be unregistered. The return of the collection must
     * be an empty optional.
     */
    @Test
    public void oneToZeroDraw() {
        final var first = new J2DMouseShapeFake(Optional.empty());
        final var shapes = new J2DMouseShapes(first);
        MatcherAssert.assertThat(
            shapes.draw(null),
            Matchers.equalTo(Optional.empty())
        );
        MatcherAssert.assertThat(first.wasDrawn(), Matchers.is(true));
        first.clean();
        MatcherAssert.assertThat(
            shapes.draw(null),
            Matchers.equalTo(Optional.empty())
        );
        MatcherAssert.assertThat(first.wasDrawn(), Matchers.is(false));
    }

    /**
     * {@link J2DMouseShapes#draw(Graphics)} with multiple elements that are
     * returning empty optionals must be unregistered.
     */
    @Test
    public void multipleToLessDraw() {
        final var first = new J2DMouseShapeFake();
        final var second = new J2DMouseShapeFake(Optional.empty());
        final var third = new J2DMouseShapeFake();
        final var fourth = new J2DMouseShapeFake(Optional.empty());
        final var fakes = new J2DMouseShapeFake[]{
            first, second, third, fourth,
        };
        final var shapes = new J2DMouseShapes(fakes);
        shapes.draw(null);
        Arrays.stream(fakes).forEach(
            it -> MatcherAssert.assertThat(it.wasDrawn(), Matchers.is(true))
        );
        Arrays.stream(fakes).forEach(J2DMouseShapeFake::clean);
        shapes.draw(null);
        MatcherAssert.assertThat(first.wasDrawn(), Matchers.is(true));
        MatcherAssert.assertThat(second.wasDrawn(), Matchers.is(false));
        MatcherAssert.assertThat(third.wasDrawn(), Matchers.is(true));
        MatcherAssert.assertThat(fourth.wasDrawn(), Matchers.is(false));
    }
}
