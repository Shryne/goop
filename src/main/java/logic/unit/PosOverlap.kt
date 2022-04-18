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
package logic.unit

import logic.unit.pos.Pos

/**
 * Can determine whether a given position is inside itself.
 */
interface PosOverlap {
    /**
     * Tells whether the given pos is inside itself. Example:
     * ```
     * Area(0, 0, 100, 100).contains(0, 0) => true
     * Area(0, 0, 100, 100).contains(50, 50) => true
     * Area(0, 0, 100, 100).contains(100, 100) => true
     * Area(0, 0, 100, 100).contains(101, 101) => false
     * ```
     * @param pos The position to check for.
     * @return True if the position is inside, otherwise false.
     */
    operator fun contains(pos: Pos): Boolean
}