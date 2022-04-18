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

package sample;

import graphic.j2d.window.event.J2DEventWindow;
import logic.unit.area.Area2D;
import logic.unit.pos.Pos2D;
import logic.unit.size.Scaling;
import logic.unit.size.Size2D;

public class Sample {
    public static void main(final String[] args) {
        new J2DEventWindow(
            new Area2D(100, 100, 300, 300),
            new Button(
                new Area2D(
                    new Pos2D(0, 0),
                    new Immediate(
                        new Scaling(
                            new Size2D(0, 0),
                            new Size2D(300, 300),
                            10_000
                        )
                    )
                ),
                () -> System.out.println("released")
            )
        ).show();
    }
}
