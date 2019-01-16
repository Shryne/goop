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
import logic.graphic.color.Color;
import logic.unit.area.Area;

/**
 * A rectangle using java2d.
 * <p>This class doesn't change its own state. Whether it is immutable or not,
 * depends on the given constructor arguments. Additionally whether this
 * class is thread-safe or not, depends on the given graphics instance for
 * {@link this#draw(Graphics)}.</p>
 * @since 2.1.0
 */
public class J2DRect implements J2DShape {
    /**
     * The area of this rect.
     */
    private final Area area;

    /**
     * The color of this rect.
     */
    private final Color color;

    /**
     * The next shape to be drawn.
     */
    private final Optional<J2DShape> successor;

    /**
     * Ctor.
     * @param area The area of this rect.
     * @param color The color of this rect.
     */
    public J2DRect(final Area area, final Color color) {
        this.area = area;
        this.color = color;
        this.successor = Optional.of(this);
    }

    @Override
    public final Optional<J2DShape> draw(final Graphics graphics) {
        this.color.applyOn(
            (red, green, blue, alpha) -> graphics.setColor(
                new java.awt.Color(red, green, blue, alpha)
            )
        );
        this.area.applyOn(graphics::fillRect);
        return this.successor;
    }
}
