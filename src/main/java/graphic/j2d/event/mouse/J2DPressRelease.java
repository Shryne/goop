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

package graphic.j2d.event.mouse;

import graphic.j2d.shape.J2DShapeTarget;
import java.util.concurrent.atomic.AtomicBoolean;
import logic.functional.Action;
import logic.unit.PosOverlap;

/**
 * A combination of a press and a release. Note that a release may only happen
 * after the press on the same shape.
 * @see J2DPress
 * @see J2DRelease
 * @since 18.6
 */
public class J2DPressRelease implements J2DShapeTarget {
    /**
     * The mouse press.
     */
    private final J2DShapeTarget press;

    /**
     * The mouse release.
     */
    private final J2DShapeTarget release;

    /**
     * Ctor.
     * @param press The action that shall be applied on press.
     * @param release The action that shall be applied on release.
     */
    public J2DPressRelease(
        final Action press,
        final Action release
    ) {
        this(
            press,
            new AtomicBoolean(false),
            release
        );
    }

    /**
     * Ctor.
     * @param press The action that shall be applied on press.
     * @param release The action that shall be applied on release.
     */
    private J2DPressRelease(
        final Action press,
        final AtomicBoolean pressed,
        final Action release
    ) {
        this(
            new J2DPress(
                () -> {
                    press.run();
                    pressed.set(true);
                }
            ),
            new J2DRelease(
                () -> {
                    if (pressed.get()) {
                        release.run();
                        pressed.set(false);
                    }
                }
            )
        );
    }

    /**
     * Ctor.
     * @param press The mouse press.
     * @param release The mouse release.
     */
    private J2DPressRelease(
        final J2DShapeTarget press, final J2DShapeTarget release
    ) {
        this.press = press;
        this.release = release;
    }

    @Override
    public final void registerFor(
        final J2DMouse source,
        final PosOverlap overlap
    ) {
        this.press.registerFor(source, overlap);
        this.release.registerFor(source, overlap);
    }
}
