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

package logic.graphic.shape;

import graphic.j2d.shape.J2DRect;
import graphic.lwjgl.shape.LwjRect;
import logic.graphic.GraphicsChoice;
import logic.graphic.color.Color;
import logic.unit.area.Area;

/**
 * This rect chooses the concrete rect version depending on the previously
 * chosen graphics library (by window).
 * <p>This class is immutable and thread-safe.</p>
 * @since 7.2.0
 */
public class Rect implements Shape {
    /**
     * The area of the rectangle.
     */
    private final Area area;

    /**
     * The color of the rectangle.
     */
    private final Color color;

    /**
     * Ctor.
     * @param area The area of the rectangle.
     * @param color The color of the rectangle.
     */
    public Rect(final Area area, final Color color) {
        this.area = area;
        this.color = color;
    }

    @Override
    public final <T> T concrete(final GraphicsChoice<T> library) {
        return library.chosen(
            () -> new LwjRect(this.area),
            () -> new J2DRect(this.area, this.color)
        );
    }
}
