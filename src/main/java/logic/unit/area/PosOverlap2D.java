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

package logic.unit.area;

import logic.unit.pos.Pos;
import logic.unit.pos.Pos2D;
import logic.unit.size.Size;
import logic.unit.size.Size2D;

/**
 * An area that knows when a point overlaps with itself.
 * @since 12.5.0
 */
public class PosOverlap2D extends Area2D implements PosOverlap {
    /**
     * Ctor. Uses (0|0) as its position.
     * @param width The width of the area.
     * @param height The height of the area.
     */
    public PosOverlap2D(final int width, final int height) {
        this(new Size2D(width, height));
    }

    /**
     * Ctor. Uses (0|0) as its position.
     * @param size The size of the area.
     */
    public PosOverlap2D(final Size size) {
        super(new Pos2D(), size);
    }

    /**
     * Ctor.
     * @param pos The position of the area.
     * @param size The size of the area.
     */
    public PosOverlap2D(final Pos pos, final Size size) {
        super(pos, size);
    }

    @Override
    public final boolean contains(final Pos pos) {
        return this.result(
            // @checkstyle ParameterName (1 line)
            (x, y, width, height) -> pos.result(
                // @checkstyle ParameterName (1 line)
                (posX, posY) -> x <= posX && y <= posY
                    && posX <= x + width && posY <= y + height
                )
        );
    }
}
