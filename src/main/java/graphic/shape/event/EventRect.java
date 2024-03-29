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

package graphic.shape.event;

import graphic.event.mouse.J2DMouse;
import graphic.shape.MouseShape;
import graphic.shape.ShapeTarget;
import java.awt.Graphics;
import java.util.List;
import logic.graphic.color.Black;
import logic.graphic.color.Color;
import logic.unit.area.PosOverlap;
import org.cactoos.list.ListOf;

/**
 * A java 2d rect that supports mouse events.
 * <p>This class doesn't change its own state. Whether it is immutable or not,
 * depends on the given constructor arguments. Additionally whether this
 * class is thread-safe or not, depends on the given graphics instance for
 * {@link this#draw(Graphics)}.</p>
 * @since 13.0.0
 */
public class EventRect implements MouseShape {
    /**
     * The area of this rect. It needs to be a PosOverlap area to check whether
     * a mouse event occurred on itself.
     */
    private final PosOverlap area;

    /**
     * The color of this rect.
     */
    private final Color color;

    /**
     * The targets for the mouse events.
     */
    private final List<ShapeTarget> targets;

    /**
     * Ctor.
     * @param area The area of the rect.
     */
    public EventRect(final PosOverlap area) {
        this(
            area,
            new Black()
        );
    }

    /**
     * Ctor.
     * @param area The area of this rect.
     * @param targets The targets for the mouse events.
     */
    public EventRect(
        final PosOverlap area, final ShapeTarget... targets
    ) {
        this(
            area,
            List.of(targets)
        );
    }

    /**
     * Ctor.
     * @param area The area of this rect.
     * @param targets The targets for the mouse events.
     */
    public EventRect(
        final PosOverlap area, final List<ShapeTarget> targets
    ) {
        this(
            area,
            new Black(),
            targets
        );
    }

    /**
     * Ctor.
     * @param area The area of this rect.
     * @param color The color of this rect.
     */
    public EventRect(final PosOverlap area, final Color color) {
        this(
            area,
            color,
            new ListOf<>()
        );
    }

    /**
     * Ctor.
     * @param area The area of this rect.
     * @param color The color of this rect.
     * @param targets The targets for the mouse events.
     */
    public EventRect(
        final PosOverlap area,
        final Color color,
        final List<ShapeTarget> targets
    ) {
        this.area = area;
        this.color = color;
        this.targets = targets;
    }

    @Override
    public final void draw(final Graphics graphics) {
        this.color.applyOn(
            (red, green, blue, alpha) -> graphics.setColor(
                new java.awt.Color(red, green, blue, alpha)
            )
        );
        this.area.applyOn(graphics::fillRect);
    }

    @Override
    public final void registerFor(final J2DMouse source) {
        this.targets.forEach(target -> target.registerFor(source, this.area));
    }
}
