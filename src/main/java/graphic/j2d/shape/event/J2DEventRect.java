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

package graphic.j2d.shape.event;

import graphic.j2d.event.mouse.J2DMouse;
import graphic.j2d.shape.J2DMouseShape;
import graphic.j2d.shape.J2DShapeTarget;
import graphic.j2d.shape.J2DSuccessor;
import graphic.j2d.shape.Redrawable;
import java.awt.Graphics;
import java.util.List;
import java.util.Optional;
import logic.graphic.color.Black;
import logic.graphic.color.Color;
import logic.unit.area.Area;
import logic.unit.area.PosOverlap;
import logic.unit.area.PosOverlap2D;
import org.cactoos.list.ListOf;

/**
 * A java 2d rect that supports mouse events.
 * <p>This class doesn't change its own state. Whether it is immutable or not,
 * depends on the given constructor arguments. Additionally whether this
 * class is thread-safe or not, depends on the given graphics instance for
 * {@link this#draw(Graphics)}.</p>
 * @since 13.0.0
 */
public class J2DEventRect implements J2DMouseShape {
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
    private final List<J2DShapeTarget> targets;

    /**
     * Self to success.
     */
    private final Optional<J2DMouseShape> self;

    /**
     * The next shape to be drawn.
     */
    private final J2DSuccessor successor;

    /**
     * Ctor.
     * @param area The area of the rect.
     */
    public J2DEventRect(final PosOverlap area) {
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
    public J2DEventRect(
        final PosOverlap area, final J2DShapeTarget... targets
    ) {
        this(
            area,
            List.of(targets)
        );
    }

    /**
     * Ctor.
     * @param area The area of this rect.
     * @param color The color of this rect.
     * @param targets The targets for the mouse events.
     */
    public J2DEventRect(
        final Area area,
        final Color color,
        final J2DShapeTarget... targets
    ) {
        this(
            new PosOverlap2D(area),
            color,
            List.of(targets)
        );
    }

    /**
     * Ctor.
     * @param area The area of this rect.
     * @param color The color of this rect.
     * @param targets The targets for the mouse events.
     */
    public J2DEventRect(
        final PosOverlap area,
        final Color color,
        final J2DShapeTarget... targets
    ) {
        this(
            area,
            color,
            List.of(targets)
        );
    }

    /**
     * Ctor.
     * @param area The area of this rect.
     * @param targets The targets for the mouse events.
     */
    public J2DEventRect(
        final PosOverlap area, final List<J2DShapeTarget> targets
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
    public J2DEventRect(final PosOverlap area, final Color color) {
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
    public J2DEventRect(
        final PosOverlap area,
        final Color color,
        final List<J2DShapeTarget> targets
    ) {
        this(area, color, succ -> succ, targets);
    }

    /**
     * Ctor.
     * @param area The area of this rect.
     * @param color The color of this rect.
     * @param successor The successor of the rect.
     * @param targets The targets for the mouse events.
     */
    public J2DEventRect(
        final PosOverlap area,
        final Color color,
        final J2DSuccessor successor,
        final List<J2DShapeTarget> targets
    ) {
        this.area = area;
        this.color = color;
        this.targets = targets;
        this.self = Optional.of(this);
        this.successor = successor;
    }

    @Override
    public final Optional<J2DMouseShape> draw(final Graphics graphics) {
        this.color.applyOn(
            (red, green, blue, alpha) -> graphics.setColor(
                new java.awt.Color(red, green, blue, alpha)
            )
        );
        this.area.applyOn(graphics::fillRect);
        return this.successor.apply(this.self);
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
