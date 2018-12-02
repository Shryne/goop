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

package logic.graphic.color;

import logic.functional.QuadConsumer;

// @todo #7 Swing is byte based and lwjgl is integer based / can be integer based.
//  I need to adjust that.
/**
 * A simple color implementation using red, green, blue and alpha values
 * spanning from 0 to 255.
 * @since 3.1.0
 * @checkstyle AbbreviationAsWordInName (2 lines)
 */
public class RGBA implements Color {
    /**
     * The red value spanning from 0 to 255.
     */
    private final int red;

    /**
     * The green value spanning from 0 to 255.
     */
    private final int green;

    /**
     * The blue value spanning from 0 to 255.
     */
    private final int blue;

    /**
     * The alpha value spanning from 0 to 255.
     */
    private final int alpha;

    /**
     * Ctor.
     * @param red The red value from 0 (nothing) to {@link Integer#MAX_VALUE}
     *  (full).
     * @param green The green value from 0 (nothing) to
     *  {@link Integer#MAX_VALUE} (full).
     * @param blue The blue value from 0 (nothing) to {@link Integer#MAX_VALUE}
     *  (full).
     * @param alpha The alpha (transparency) value from 0 (nothing) to
     *  {@link Integer#MAX_VALUE} (full).
     * @checkstyle ParameterNumber (2 lines)
     */
    public RGBA(
        final int red,
        final int green,
        final int blue,
        final int alpha
    ) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    @Override
    public final void applyOn(
        final QuadConsumer<Integer, Integer, Integer, Integer> target
    ) {
        target.accept(this.red, this.green, this.blue, this.alpha);
    }
}
