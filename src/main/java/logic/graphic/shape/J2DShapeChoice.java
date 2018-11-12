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

package logic.graphic.shape;

import graphic.j2d.shape.J2DShape;
import graphic.lwjgl.shape.LwjShape;
import java.util.function.Supplier;
import logic.graphic.GraphicsChoice;

/**
 * Represents java 2d shape casting.
 * <p>This class is immutable and thread-safe.</p>
 * @since 7.2.0
 */
public final class J2DShapeChoice implements GraphicsChoice<J2DShape> {
    // @checkstyle ParameterNameCheck (3 lines)
    @Override
    public J2DShape chosen(
        final Supplier<LwjShape> lwj, final Supplier<J2DShape> j2d
    ) {
        return j2d.get();
    }
}
