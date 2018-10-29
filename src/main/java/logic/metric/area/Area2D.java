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

package logic.metric.area;

import java.util.function.BiConsumer;
import logic.functional.QuadConsumer;
import logic.metric.position.Pos;
import logic.metric.position.Pos2D;
import logic.metric.size.Size;
import logic.metric.size.Size2D;

/*
I am not happy about this naming, but my other idea's regarding the interface
name and the basic implementation weren't good either (impl or simple for
example). So I took the more annoying name for the implementation to lower the
chance that a user might use the class name as a parameter type
*/
/**
 * Basic concrete implementation of {@link Area}.
 * <p>Whether this class is immutable or thread-safe, depends on the given
 * {@link Pos} and {@link Size}. This class
 * doesn't mutate state by itself. Additionally note that the
 * {@link #applyOn(BiConsumer)} provides the actual objects without any
 * safe-copies for performance reasons.</p>
 * @since 3.3.0
 */
public class Area2D implements Area {
    /**
     * The position of this area.
     */
    private final Pos pos;

    /**
     * The size of this area.
     */
    private final Size size;

    /**
     * Secondary constructor. Uses an immutable implementation of {@link Pos}
     * and {@link Size}.
     * @param x The x coordinate for the position.
     * @param y The y coordinate for the position.
     * @param width The width for the size.
     * @param height The height for the size.
     * @checkstyle ParameterNumber (3 lines)
     * @checkstyle ParameterName (2 lines)
     */
    public Area2D(final int x, final int y, final int width, final int height) {
        this(
            new Pos2D(x, y),
            new Size2D(width, height)
        );
    }

    /**
     * Primary constructor.
     * @param pos The position of the area.
     * @param size The size of the area.
     */
    public Area2D(final Pos pos, final Size size) {
        this.pos = pos;
        this.size = size;
    }

    @Override
    public final void applyOn(final BiConsumer<Pos, Size> target) {
        target.accept(this.pos, this.size);
    }

    @Override
    public final void applyOn(
        final QuadConsumer<Integer, Integer, Integer, Integer> target
    ) {
        Area.super.applyOn(target);
    }
}
