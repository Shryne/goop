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

import graphic.j2d.event.mouse.J2DMouse;
import java.awt.Graphics;
import java.util.Optional;
import logic.graphic.color.Black;
import logic.graphic.color.Color;
import logic.unit.pos.Pos;

/**
 * A j2d line.
 * @since 19.9
 */
public class J2DLine implements J2DMouseShape {
    /**
     * The first pos.
     */
    private final Pos first;

    /**
     * The second pos.
     */
    private final Pos second;

    /**
     * The color of the line.
     */
    private final Color color;

    /**
     * The successor of this shape.
     */
    private final Optional<J2DMouseShape> successor;

    /**
     * Ctor.
     * @param first The first position of the line.
     * @param second The second position of the line.
     */
    public J2DLine(final Pos first, final Pos second) {
        this(first, second, new Black());
    }

    /**
     * Ctor.
     * @param first The first position of the line.
     * @param second The second position of the line.
     * @param color The color of the line.
     */
    public J2DLine(final Pos first, final Pos second, final Color color) {
        this.first = first;
        this.second = second;
        this.color = color;
        this.successor = Optional.of(this);
    }

    @Override
    public final void registerFor(final J2DMouse source) { }

    @Override
    public final Optional<J2DMouseShape> draw(final Graphics graphics) {
        this.color.applyOn(
            (r, g, b, a) -> graphics.setColor(new java.awt.Color(r, g, b, a))
        );
        this.first.applyOn(
            (fx, fy) -> this.second.applyOn(
                (sx, sy) -> graphics.drawLine(fx, fy, sx, sy)
            )
        );
        return this.successor;
    }

    @Override
    public final void register(final Redrawable redrawable) {
        this.first.register(redrawable);
        this.second.register(redrawable);
    }
}
