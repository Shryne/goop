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

package logic.graphic;

import graphic.j2d.shape.J2DShape;
import graphic.lwjgl.shape.LwjShape;
import java.util.function.Supplier;

/**
 * Represents the choice of the shape based on the currently used graphics
 * library.
 * @param <T> The type of the shape interface, depending on the chosen graphics
 *  library. Example: {@link LwjShape}.
 * @since 7.2.0
 */
public interface GraphicsChoice<T> {
    /*
    This method will change, if a new graphics library will be added or if
    a graphics library will be removed. It will break every shape using this
    interface. However, since the shape link between the different graphics
    library shapes, this is what to be expected. The alternative would be a
    runtime error
     */
    /**
     * Takes all the possible available shapes (one per graphics library) and
     * returns the one that corresponds to the currently used graphics library.
     * @param lwj The lwjgl based version of the shape.
     * @param j2d The java 2d based version of the shape.
     * @return The chosen shape.
     * @checkstyle ParameterNameCheck (2 lines)
     */
    T chosen(Supplier<LwjShape> lwj, Supplier<J2DShape> j2d);
}
