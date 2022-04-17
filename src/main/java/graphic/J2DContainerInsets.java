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

package graphic;

import java.awt.Container;
import logic.functional.QuadFunction;
import logic.unit.Insets;

/**
 * The insets of a java 2d based container ({@link Container} to be
 * precise).
 * <p><b>This class is lazy.</b></p> It will use the given container only when
 * {@link #result(QuadFunction)} is called. Additionally, it <b>doesn't
 * cache</b>. Every call of {@link #result(QuadFunction)} will ask the given
 * container for its insets.
 * <p>This class is immutable by itself, but whether it really is and whether
 * it's thread-safe, depends on the implementation of
 * {@link Container#getInsets()} of the given container.</p>
 * @since 13.2.2
 */
public class J2DContainerInsets implements Insets {
    /**
     * The container to get the insets from. Note that it will be used lazy and
     * every time {@link #result(QuadFunction)} is called.
     */
    private final Container container;

    /**
     * Ctor.
     * @param container The container to get the insets from. Note that
     */
    public J2DContainerInsets(final Container container) {
        this.container = container;
    }

    @Override
    public final <T> T result(
        final QuadFunction<Integer, Integer, Integer, Integer, T> target
    ) {
        final var insets = this.container.getInsets();
        return target.apply(
            insets.top,
            insets.left,
            insets.right,
            insets.bottom
        );
    }
}
