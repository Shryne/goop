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

import java.awt.Component;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;

/**
 * Just registers and delegates the events to the event classes.
 * <p>This class is mutable and not thread-safe, because it mutates the state
 * of the given JFrame..</p>
 * @since 12.2.0
 */
public class J2DBaseMouse implements J2DMouse {
    /**
     * The component who will get the listeners.
     */
    private final Component component;

    /**
     * Ctor.
     * @param component The component to register the events for.
     */
    public J2DBaseMouse(final Component component) {
        this.component = component;
    }

    @Override
    public final void register(final MouseListener target) {
        this.component.addMouseListener(target);
    }

    @Override
    public final void register(final MouseMotionListener target) {
        this.component.addMouseMotionListener(target);
    }

    @Override
    public final void register(final MouseWheelListener target) {
        this.component.addMouseWheelListener(target);
    }
}
