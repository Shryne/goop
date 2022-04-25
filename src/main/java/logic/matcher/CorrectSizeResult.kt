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
package logic.matcher

import logic.unit.size.Size
import org.hamcrest.Description
import org.hamcrest.TypeSafeDiagnosingMatcher

/**
 * A matcher for [Size.result].
 * The class by itself is immutable, but mutates the incoming description
 * object which is mutable.
 *
 * @param w The width to expect from the [Size.result] method.
 * @param h The height to expect from the [Size.result] method.
 */
open class CorrectSizeResult(
    private val w: Int,
    private val h: Int
) : TypeSafeDiagnosingMatcher<Size>() {
    override fun describeTo(description: Description) {
        description.appendText("Expected width = $w and height = $h")
    }

    override fun matchesSafely(size: Size, description: Description): Boolean {
        return size.result { resW, resH ->
            description.appendText("width: $resW, height: $resH")
            w == resW && h == resH
        }
    }
}