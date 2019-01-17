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
import logic.graphic.color.Color;
import logic.graphic.color.RGBA;
import logic.unit.area.Area;

/**
 * A shape with an attached text.
 * @since 19.4
 */
public class Labeled implements J2DMouseShape {
    /**
     * The shape to be labeled.
     */
    private final J2DMouseShape shape;

    /**
     * The text for the shape.
     */
    private final J2DMouseShape text;

    /**
     * Instance of this to return this as the successor.
     */
    private final Optional<J2DMouseShape> self;

    /**
     * Ctor.
     * @param text The text to be add.
     * @param area The area of the shape.
     * @param pen The pen to create the shape.
     */
    public Labeled(final String text, final Area area, final J2DPen<Area> pen) {
        this(
            pen.shape(area, new RGBA(200, 150, 150, 255)),
            new J2DText(text, area)
        );
    }

    /**
     * Ctor.
     * @param text The text to be add.
     * @param area The area of the shape.
     * @param color The color of the shape.
     * @param pen The pen to create the shape.
     */
    public Labeled(
        final String text,
        final Area area,
        final Color color,
        final J2DPen<Area> pen
    ) {
        this(
            pen.shape(area, color),
            new J2DText(text, area)
        );
    }

    /**
     * Ctor.
     * @param shape The shape to be labeled.
     * @param text The text to be add on the shape.
     */
    private Labeled(final J2DMouseShape shape, final J2DMouseShape text) {
        this.shape = shape;
        this.text = text;
        this.self = Optional.of(this);
    }

    @Override
    public final void registerFor(final J2DMouse source) {
        this.shape.registerFor(source);
    }

    @Override
    public final Optional<J2DMouseShape> draw(final Graphics graphics) {
        this.shape.draw(graphics);
        this.text.draw(graphics);
        return this.self;
    }

    @Override
    public final void register(final Redrawable redrawable) {
        this.shape.register(redrawable);
        this.text.register(redrawable);
    }
}
