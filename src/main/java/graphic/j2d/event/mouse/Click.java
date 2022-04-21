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

import graphic.j2d.shape.ShapeTarget;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.function.Consumer;
import logic.functional.Action;
import logic.unit.PosOverlap;
import logic.unit.pos.Pos;
import logic.unit.pos.Pos2D;

/**
 * A mouse click bound on a component to apply some action on activation. The
 * action (thus the click) will only be applied when the mouse has been pressed
 * and released.
 * <p>This class is immutable, but does mutate the state of the mouse.</p>
 * @since 12.5.0
 */
public class Click implements ShapeTarget {
    /**
     * The action to be applied when the click occurs.
     */
    private final Consumer<Pos> action;

    /**
     * Ctor.
     * @param action The action to be applied when the click occurs.
     */
    public Click(final Action action) {
        this(pos -> action.run());
    }

    /**
     * Ctor.
     * @param action The action that gets the coordinates of the click.
     */
    public Click(final Consumer<Pos> action) {
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
                public void mouseClicked(final MouseEvent event) {
                    final Pos pos = new Pos2D(event.getX(), event.getY());
                    if (overlap.contains(pos)) {
                        Click.this.action.accept(pos);
                    }
                }
            }
        );
    }
}
