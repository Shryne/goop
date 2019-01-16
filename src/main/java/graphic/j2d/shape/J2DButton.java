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
import graphic.j2d.event.mouse.J2DPressRelease;
import graphic.j2d.shape.event.J2DEventRect;
import java.awt.Graphics;
import java.util.Optional;
import logic.functional.Action;
import logic.graphic.color.ButtonColor;
import logic.graphic.color.DualColor;
import logic.unit.PosOverlap;
import logic.unit.area.Area;
import logic.unit.area.PosOverlap2D;

/**
 * A pressable shape.
 * @since 18.6
 */
public class J2DButton implements J2DMouseShape {
    /**
     * The shape of the button.
     */
    private final J2DMouseShape shape;

    /**
     * Ctor. Creates a rectangular button.
     * @param area The area of the button.
     * @param action The action to be applied when the button is released.
     */
    public J2DButton(final Area area, Action action) {
        this(
            J2DEventRect::new,
            new PosOverlap2D(area),
            new ButtonColor(),
            action
        );
    }

    /**
     * Ctor.
     * @param pen The pen to create the shape of the button.
     * @param overlap The area of the button.
     * @param color The colors of the button.
     * @param action The action to be applied when the button is released.
     */
    public <T extends PosOverlap> J2DButton(
        final J2DPen<T> pen,
        final T overlap,
        final DualColor color,
        final Action action
    ) {
        this(
            pen.shape(
                overlap,
                color,
                new J2DPressRelease(
                    color::swap,
                    () -> {
                        action.run();
                        color.swap();
                    }
                )
            )
        );
    }

    private J2DButton(final J2DMouseShape shape) {
        this.shape = shape;
    }

    @Override
    public final void registerFor(final J2DMouse source) {
        this.shape.registerFor(source);
    }

    @Override
    public final Optional<J2DMouseShape> draw(final Graphics graphics) {
        return this.shape.draw(graphics);
    }

    @Override
    public final void register(final Redrawable redrawable) {
        this.shape.register(redrawable);
    }
}
