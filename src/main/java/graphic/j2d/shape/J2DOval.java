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
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import logic.graphic.color.Color;
import logic.unit.area.Area;

/**
 * A j2d circle.
 * @since 19.6
 */
public class J2DOval implements J2DMouseShape {
    /**
     * The area of the circle.
     */
    private final Area area;

    /**
     * The color of the circle.
     */
    private final Color color;

    /**
     * The events of the circle.
     */
    private final Collection<J2DMouseTarget> events;

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
    public J2DOval(
        final Area area,
        final Color color,
        final J2DMouseTarget... events
    ) {
        this(area, color, List.of(events));
    }

    /**
     * Ctor.
     * @param area The area of the circle.
     * @param color The color of the circle.
     * @param events The events of the circle.
     */
    public J2DOval(
        final Area area,
        final Color color,
        final Collection<J2DMouseTarget> events
    ) {
        this.area = area;
        this.color = color;
        this.events = events;
        this.successor = Optional.of(this);
    }

    @Override
    public final void registerFor(final J2DMouse source) {
        this.events.forEach(it -> it.registerFor(source));
    }

    @Override
    public final Optional<J2DMouseShape> draw(final Graphics graphics) {
        this.color.applyOn(
            (r, g, b, a) -> graphics.setColor(new java.awt.Color(r, g, b, a))
        );
        this.area.applyOn(graphics::drawOval);
        return this.successor;
    }

    @Override
    public void register(final Redrawable redrawable) {
        this.area.register(redrawable);
        this.color.register(redrawable);
    }
}
