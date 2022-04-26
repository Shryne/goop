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
package graphic.j2d.window.information

import graphic.j2d.shape.Redrawable
import logic.functional.Lazy
import logic.unit.size.Size
import logic.unit.size.Size2D
import logic.unit.size.SizeEnvelope
import javax.swing.JFrame

/**
 * The size of a java 2d window bar. This class is intended to be used like
 * this:
 * ```
 * `Window(
 * // ...
 *      listOf(
 *          BarSize(it).applyOn {
 *              (w, h) -> // The action to be applied
 *          }
 *      )
 * );
 * ```
 * In this case, the action will be applied when
 * [graphic.j2d.window.Window.show] is called.
 * This class is immutable and thread-safe.
 *
 * @param frame The frame to get the bar size from.
 */
class BarSize(frame: JFrame) : Size2D(
    {}, {}
        /*
    Lazy {

        Size2D(
            frame.width,
            insets.top
        )
    }*/
)