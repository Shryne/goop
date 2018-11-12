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

package graphic.lwjgl.shape;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import logic.functional.Lazy;
import logic.functional.Value;
import logic.graphic.GraphicsChoice;
import logic.graphic.shape.Shape;

/**
 * An iterator for {@link LwjLazyShape}s. It is used to lazily transform a
 * collection of shapes to a {@link LwjShape}.
 * @since 7.2.0
 */
public class LwjLazyShapes implements Iterable<LwjShape> {
    /**
     * The construction of the iterable containing the j2d shapes.
     */
    private final Value<Iterable<LwjShape>> iterable;

    /**
     * Ctor.
     * @param choice The choice of the j2d shape.
     * @param shapes The shapes that offer the creation of the lwjgl shape
     *  version of themselves.
     */
    public LwjLazyShapes(
        final GraphicsChoice<LwjShape> choice, final Shape... shapes
    ) {
        this(choice, List.of(shapes));
    }

    /**
     * Ctor.
     * @param choice The choice of the j2d shape.
     * @param shapes The shapes that offer the creation of the lwjgl shape
     *  version of themselves.
     */
    public LwjLazyShapes(
        final GraphicsChoice<LwjShape> choice,
        final Collection<Shape> shapes
    ) {
        this(
            new Lazy<>(
                () -> shapes
                    .stream()
                    .map(it -> new LwjLazyShape(choice, it))
                    .collect(Collectors.toList())
            )
        );
    }

    /**
     * Ctor.
     * @param iterable The iterable containing the lwjgl shapes.
     */
    private LwjLazyShapes(final Value<Iterable<LwjShape>> iterable) {
        this.iterable = iterable;
    }

    @Override
    public final Iterator<LwjShape> iterator() {
        return this.iterable.content().iterator();
    }
}
