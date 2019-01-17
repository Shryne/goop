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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * An ordered collection of shapes.
 * @since 18.2
 */
public class J2DMouseShapes implements J2DMouseShape {
    /**
     * The shapes of this collection.
     */
    private Collection<J2DMouseShape> shapes;

    /**
     * The successor of this collection of shapes.
     */
    private final Optional<J2DMouseShape> successor;

    /**
     * Redrawable to redraw.
     */
    private Redrawable redrawable;

    /**
     * Ctor.
     * @param shapes The shapes of this collection.
     */
    public J2DMouseShapes(final J2DMouseShape... shapes) {
        this(List.of(shapes));
    }

    /**
     * Ctor.
     * @param shapes The shapes of this collection.
     */
    public J2DMouseShapes(final Collection<J2DMouseShape> shapes) {
        this.shapes = shapes;
        this.successor = Optional.of(this);
    }

    @Override
    public final void registerFor(final J2DMouse source) {
        this.shapes.forEach(it -> it.registerFor(source));
    }

    @Override
    public final Optional<J2DMouseShape> draw(final Graphics graphics) {
        final List<J2DMouseShape> next = new ArrayList<>(this.shapes.size());
        this.shapes.forEach(
            shape -> shape.draw(graphics).ifPresent(next::add)
        );
        final Optional<J2DMouseShape> result;
        if (next.isEmpty()) {
            result = Optional.empty();
        } else {
            result = this.successor;
        }
        this.shapes = next;
        return result;
    }

    @Override
    public final void register(final Redrawable redrawable) {
        this.shapes.forEach(it -> it.register(redrawable));
        this.redrawable = redrawable;
    }

    public final void add(final J2DMouseShape shape) {
        this.shapes.add(shape);
        shape.register(this.redrawable);
        this.redrawable.redraw(() -> false);
    }

    public final void clear() {
        this.shapes.clear();
    }
}
