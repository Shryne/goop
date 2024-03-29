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
import java.util.function.ObjIntConsumer;

import logic.functional.Action;
import logic.unit.PosOverlap;
import logic.unit.pos.Pos2D;

/**
 * A mouse press bound on a component to apply some action on activation.
 * <p>This class is immutable, but does mutate the state of the mouse.</p>
 * @since 13.1.2
 */
public class Press implements ShapeTarget {
    /**
     * The target action to be applied with the x and y coordinates of the
     * press.
     */
    private final ObjIntConsumer<Integer> target;

    /**
     * Ctor.
     * @param action The action to be applied when the press occurs.
     */
    public Press(final Action action) {
        this(
            // @checkstyle ParameterName (1 line)
            (x, y) -> action.run()
        );
    }

    /**
     * Ctor.
     * @param target The target with the action, who gets the x and y
     *  coordinates of the press.
     */
    public Press(final ObjIntConsumer<Integer> target) {
        this.target = target;
    }

    @Override
    public final void registerFor(
        final J2DMouse source,
        final PosOverlap overlap
    ) {
        source.register(
            (MouseListener) new MouseAdapter() {
                @Override
                public void mousePressed(final MouseEvent event) {
                    // @checkstyle LocalFinalVariableName (2 lines)
                    final var x = event.getX();
                    final var y = event.getY();
                    if (overlap.contains(new Pos2D(x, y))) {
                        Press.this.target.accept(x, y);
                    }
                }
            }
        );
    }
}
