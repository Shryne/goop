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

package graphic.event.mouse;

import graphic.shape.ShapeTarget;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import logic.functional.Action;
import logic.unit.PosOverlap;
import logic.unit.pos.Pos2D;

/**
 * A mouse button release bound on a component to apply some action on
 * activation.
 * <p>This class is immutable, but does mutate the state of the mouse.</p>
 * @since 13.1.2
 */
public class Release implements ShapeTarget {
    /**
     * The action to be applied when the release occurs.
     */
    private final Action action;

    /**
     * Ctor.
     * @param action The action to be applied when the mouse release occurred.
     */
    public Release(final Action action) {
        this.action = action;
    }

    @Override
    public final void registerFor(
        final J2DMouse source,
        final PosOverlap overlap
    ) {
        source.register(
            (MouseListener) new MouseAdapter() {
                @Override
                public void mouseReleased(final MouseEvent event) {
                    if (overlap.contains(
                        new Pos2D(
                            event.getX(),
                            event.getY()
                        )
                    )) {
                        Release.this.action.run();
                    }
                }
            }
        );
    }
}
