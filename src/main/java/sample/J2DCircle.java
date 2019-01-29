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

package sample;

import graphic.j2d.event.mouse.J2DMouse;
import graphic.j2d.shape.J2DMouseShape;
import graphic.j2d.shape.J2DShapeTarget;
import graphic.j2d.shape.Redrawable;
import java.awt.Graphics;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import logic.graphic.color.Black;
import logic.graphic.color.Color;
import logic.unit.area.Area;
import logic.unit.area.PosOverlap;
import logic.unit.area.PosOverlap2D;

public class J2DCircle implements J2DMouseShape {
    private final PosOverlap area;
    private final Color color;
    private final Collection<J2DShapeTarget> targets;

    public J2DCircle(final Area area) {
        this(area, new Black());
    }

    public J2DCircle(
        final Area area, final Color color, J2DShapeTarget... targets
    ) {
        this(new PosOverlap2D(area), color, List.of(targets));
    }

    public J2DCircle(
        final PosOverlap area,
        final Color color,
        final Collection<J2DShapeTarget> targets
    ) {
        this.area = area;
        this.color = color;
        this.targets = targets;
    }

    @Override
    public final Optional<J2DMouseShape> draw(final Graphics graphics) {
        this.color.applyOn(
            (r, g, b, a) -> graphics.setColor(new java.awt.Color(r, g, b, a))
        );
        this.area.applyOn(graphics::fillOval);
        return Optional.of(this);
    }

    @Override
    public final void registerFor(final J2DMouse source) {
        this.targets.forEach(target -> target.registerFor(source, this.area));
    }

    @Override
    public final void register(final Redrawable redrawable) {
        this.area.register(redrawable);
        this.color.register(redrawable);
    }
}
