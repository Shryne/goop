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

/**
 * The default color of a button.
 * @since 18.6
 */
public class ButtonColor implements DualColor {
    /**
     * The color of a released button;
     */
    private final Color release;

    /**
     * The color of a pressed button.
     */
    private final Color press;

    /**
     * The current color of the button.
     */
    private Color current;

    public ButtonColor() {
        this.release = new RGBA(100, 100, 100, 255);
        this.press = new RGBA(200, 200, 200, 255);
        this.current = this.release;
    }

    @Override
    public final void swap() {
        if (this.current == this.release) {
            this.current = this.press;
        } else {
            this.current = this.release;
        }
    }

    @Override
    public final void applyOn(
        final QuadConsumer<Integer, Integer, Integer, Integer> target
    ) {
        this.current.applyOn(target);
    }
}
