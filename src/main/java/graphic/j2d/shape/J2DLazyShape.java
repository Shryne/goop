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

package graphic.j2d.shape;

import java.awt.Graphics;
import java.util.Optional;
import logic.functional.Lazy;
import logic.functional.Value;
import logic.graphic.GraphicsChoice;
import logic.graphic.shape.Shape;

/**
 * Represents a java 2d shape that will be constructed lazily from a
 * {@link Shape}.
 * <p>This class is mutable and not thread-safe, because it uses a
 * {@link Lazy} to initialize itself.</p>
 * @since 7.2.0
 */
public class J2DLazyShape implements J2DShape {
    /**
     * The construction of the j2d shape.
     */
    private final Value<J2DShape> shape;

    /**
     * Ctor.
     * @param choice The choice of the j2d shape.
     * @param shape The shape that offers the creation of the j2d shape version
     *  of itself.
     */
    public J2DLazyShape(
        final GraphicsChoice<J2DShape> choice, final Shape shape
    ) {
        this(new Lazy<>(() -> shape.concrete(choice)));
    }

    /**
     * Ctor.
     * @param shape The construction of the j2d based shape.
     */
    public J2DLazyShape(final Value<J2DShape> shape) {
        this.shape = shape;
    }

    @Override
    public final Optional<J2DShape> draw(final Graphics graphics) {
        return this.shape.content().draw(graphics);
    }

    @Override
    public final void register(final Redrawable redrawable) {
        this.shape.content().register(redrawable);
    }
}
