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
import java.util.Collection;
import java.util.List;
import logic.graphic.color.Black;
import logic.graphic.color.Color;
import logic.unit.area.Area2D;
import logic.unit.pos.Pos;
import logic.unit.size.Size2D;

/**
 * A small oval.
 * @since 19.8
 */
public class J2DDot extends J2DOval {
    /**
     * Ctor. Creates a black dot.
     * @param pos The pos of the dot.
     * @param events The events of the dot.
     */
    public J2DDot(
        final Pos pos,
        final J2DMouseTarget... events
    ) {
        this(pos, new Black(), List.of(events));
    }

    /**
     * Ctor.
     * @param pos The pos of the dot.
     * @param color The color of the dot.
     * @param events The events of the dot.
     */
    public J2DDot(
        final Pos pos,
        final Color color,
        final J2DMouseTarget... events
    ) {
        this(pos, color, List.of(events));
    }

    /**
     * Ctor.
     * @param pos The pos of the dot.
     * @param color The color of the dot.
     * @param events The events of the dot.
     */
    public J2DDot(
        final Pos pos,
        final Color color,
        final Collection<J2DMouseTarget> events
    ) {
        super(new Area2D(pos, new Size2D(10, 10)), color, events);
    }
}
