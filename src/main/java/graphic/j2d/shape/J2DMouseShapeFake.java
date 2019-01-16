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

/**
 * A fake for {@link J2DMouseShape}.
 * @since 18.2
 */
@SuppressWarnings("PMD.OnlyOneConstructorShouldDoInitialization")
public class J2DMouseShapeFake implements J2DMouseShape {
    /**
     * The successor of this shape.
     */
    private final Optional<J2DMouseShape> successor;

    /**
     * Whether {@link #draw(Graphics)} was called.
     */
    private boolean drawn;

    /**
     * Whether {@link #registerFor(J2DMouse)} was called.
     */
    private boolean registered;

    /**
     * Ctor. Creates the object with itself as the successor.
     */
    public J2DMouseShapeFake() {
        this.successor = Optional.of(this);
        this.drawn = false;
        this.registered = false;
    }

    /**
     * Ctor.
     * @param successor The successor for {@link #draw(Graphics)}.
     */
    public J2DMouseShapeFake(final Optional<J2DMouseShape> successor) {
        this.successor = successor;
        this.drawn = false;
        this.registered = false;
    }

    @Override
    public final void registerFor(final J2DMouse source) {
        this.registered = true;
    }

    @Override
    public final Optional<J2DMouseShape> draw(final Graphics graphics) {
        this.drawn = true;
        return this.successor;
    }

    /**
     * Whether it was drawn.
     * @return Whether it was drawn.
     */
    public final boolean wasDrawn() {
        return this.drawn;
    }

    /**
     * Whether it was registered.
     * @return Whether it was registered.
     */
    public final boolean wasRegistered() {
        return this.registered;
    }

    /**
     * Resets the flags.
     */
    public final void clean() {
        this.drawn = false;
        this.registered = false;
    }
}
