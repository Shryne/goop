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
package graphic.j2d.event.mouse

import graphic.j2d.shape.ShapeTarget
import logic.functional.Action
import logic.unit.PosOverlap
import java.util.concurrent.atomic.AtomicBoolean

/**
 * A combination of a press and a release. Note that a release may only happen
 * after the press on the same shape.
 * @see Press
 * @see Release
 *
 * @param press The mouse press.
 * @param release The mouse release.
 */
open class PressRelease private constructor(
    private val press: ShapeTarget,
    private val release: ShapeTarget
) : ShapeTarget {
    /**
     * @param press The action that shall be applied on press.
     * @param release The action that shall be applied on release.
     */
    constructor(press: Action, release: Action) :
        this(press, AtomicBoolean(false), release)

    /**
     * @param press The action that shall be applied on press.
     * @param release The action that shall be applied on release.
     */
    private constructor(
        press: Action,
        pressed: AtomicBoolean,
        release: Action
    ) : this(
        Press(
            Action {
                press.run()
                pressed.set(true)
            }
        ),
        Release {
            if (pressed.get()) {
                release.run()
                pressed.set(false)
            }
        }
    ) {
    }

    override fun registerFor(source: J2DMouse, overlap: PosOverlap) {
        press.registerFor(source, overlap)
        release.registerFor(source, overlap)
    }
}