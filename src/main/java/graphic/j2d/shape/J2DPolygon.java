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

import graphic.j2d.event.J2DMouseTarget;
import graphic.j2d.event.mouse.J2DMouse;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import logic.graphic.color.Color;
import logic.unit.area.Area;
import logic.unit.pos.Pos;

/**
 * A j2d polygon.
 * @since 19.10
 */
public class J2DPolygon implements J2DMouseShape {
    /**
     * The positions of the polygon.
     */
    private final Polygon positions;

    /**
     * The color of the circle.
     */
    private final Color color;

    /**
     * The successor of this shape.
     */
    private final Optional<J2DMouseShape> successor;

    /**
     * Ctor.
     * @param area The area of the circle.
     * @param color The color of the circle.
     * @param events The events of the circle.
     */
    public J2DPolygon(final Color color, final Pos... positions) {
        this(color, List.of(positions));
    }

    /**
     * Ctor.
     * @param area The area of the circle.
     * @param color The color of the circle.
     * @param events The events of the circle.
     */
    public J2DPolygon(final Color color, final List<Pos> positions) {
        this.color = color;
        final int[] x = new int[positions.size()];
        final int[] y = new int[positions.size()];
        for (int i = 0; i < positions.size(); ++i) {
            final int index = i;
            positions.get(i).applyOn(
                (posx, posy) -> {
                    x[index] = posx;
                    y[index] = posy;
                }
            );
        }
        this.positions = new Polygon(x, y, positions.size());
        this.successor = Optional.of(this);
    }

    @Override
    public final void registerFor(final J2DMouse source) { }

    @Override
    public final Optional<J2DMouseShape> draw(final Graphics graphics) {
        this.color.applyOn(
            (r, g, b, a) -> graphics.setColor(new java.awt.Color(r, g, b, a))
        );
        graphics.fillPolygon(this.positions);
        return this.successor;
    }

    @Override
    public final void register(final Redrawable redrawable) {
        this.color.register(redrawable);
    }
}
