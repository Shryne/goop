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
package sample

import graphic.j2d.event.mouse.J2DPress
import graphic.j2d.shape.*
import graphic.j2d.shape.event.EventRect
import graphic.j2d.window.event.EventWindow
import logic.functional.Action
import logic.graphic.color.Black
import logic.graphic.color.RGBA
import logic.unit.area.Area2D
import logic.unit.area.PosOverlap2D
import logic.unit.pos.Pos
import logic.unit.pos.Pos2D
import logic.unit.size.Size
import logic.unit.size.Size2D
import java.util.List
import java.util.concurrent.atomic.AtomicReference

/**
 * A form editor.
 * @since 19.11
 */
object Forms {
    private const val margin = 10
    private const val buttonW = 100
    private const val buttonH = 35
    private val buttonSize: Size = Size2D(buttonW, buttonH)
    @JvmStatic
    fun main(args: Array<String>) {
        val creation = AtomicReference(
            Creation.DOT
        )
        val shapes = J2DMouseShapes()
        val positions: MutableList<Pos> = ArrayList()
        EventWindow(
            Area2D(800, 800),
            EventRect(
                Area2D(0, 0, 800, buttonH + margin * 2),
                RGBA(230, 230, 230, 255)
            ),
            button(
                "Point",
                0
            ) {
                positions.clear()
                creation.set(Creation.DOT)
            },
            button(
                "Line",
                1
            ) {
                positions.clear()
                creation.set(Creation.LINE)
            },
            button(
                "Rect",
                2
            ) {
                positions.clear()
                creation.set(Creation.RECT)
            },
            button(
                "OVAL",
                3
            ) {
                positions.clear()
                creation.set(Creation.OVAL)
            },
            button(
                "Polygon",
                4
            ) {
                positions.clear()
                creation.set(Creation.POLYGON)
            },
            button(
                "Clear",
                5
            ) { shapes.clear() },
            EventRect(
                PosOverlap2D(
                    Area2D(0, buttonH + margin * 2, 800, 800)
                ),
                RGBA(240, 240, 240, 255),
                List.of(
                    J2DPress { pos: Pos ->
                        shapes.add(J2DDot(pos))
                        when (creation.get()) {
                            Creation.DOT -> {}
                            Creation.LINE -> if (positions.size == 1) {
                                shapes.add(
                                    Line(
                                        positions[0],
                                        pos
                                    )
                                )
                                positions.clear()
                            } else {
                                positions.add(pos)
                            }
                            Creation.RECT -> if (positions.size == 1) {
                                shapes.add(
                                    EventRect(
                                        PosOverlap2D(
                                            positions[0],
                                            positions[0].result { fx: Int, fy: Int ->
                                                pos.result { sx: Int, sy: Int ->
                                                    Size2D(
                                                        sx - fx,
                                                        sy - fy
                                                    )
                                                }
                                            }
                                        )
                                    )
                                )
                                positions.clear()
                            } else {
                                positions.add(pos)
                            }
                            Creation.OVAL -> if (positions.size == 1) {
                                shapes.add(
                                    Oval(
                                        Area2D(
                                            positions[0],
                                            positions[0].result { fx: Int, fy: Int ->
                                                pos.result { sx: Int, sy: Int ->
                                                    Size2D(
                                                        sx - fx,
                                                        sy - fy
                                                    )
                                                }
                                            }
                                        ),
                                        Black()
                                    )
                                )
                                positions.clear()
                            } else {
                                positions.add(pos)
                            }
                            Creation.POLYGON -> if (positions.size == 4) {
                                shapes.add(
                                    Polygon(
                                        Black(),
                                        positions
                                    )
                                )
                                positions.clear()
                            } else {
                                positions.add(pos)
                            }
                        }
                    }
                )
            ),
            shapes
        ).show()
    }

    private fun button(
        text: String, num: Int, action: Action
    ): J2DMouseShape {
        return J2DTextButton(
            text,
            Area2D(
                Pos2D(margin * (num + 1) + buttonW * num, margin),
                buttonSize
            ),
            action
        )
    }

    private enum class Creation {
        DOT, LINE, RECT, OVAL, POLYGON
    }
}