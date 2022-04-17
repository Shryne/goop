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

package graphic.shape;

import java.awt.Graphics;
import logic.functional.Lazy;
import logic.functional.Value;

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
     * @param shape The construction of the j2d based shape.
     */
    public J2DLazyShape(final Value<J2DShape> shape) {
        this.shape = shape;
    }

    @Override
    public final void draw(final Graphics graphics) {
        this.shape.content().draw(graphics);
    }
}
