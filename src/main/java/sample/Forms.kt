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

import graphic.j2d.event.mouse.J2DPress;
import graphic.j2d.shape.J2DDot;
import graphic.j2d.shape.J2DLine;
import graphic.j2d.shape.J2DMouseShape;
import graphic.j2d.shape.J2DMouseShapes;
import graphic.j2d.shape.J2DOval;
import graphic.j2d.shape.J2DPolygon;
import graphic.j2d.shape.J2DTextButton;
import graphic.j2d.shape.event.J2DEventRect;
import graphic.j2d.window.event.J2DEventWindow;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import logic.functional.Action;
import logic.graphic.color.Black;
import logic.graphic.color.RGBA;
import logic.unit.area.Area2D;
import logic.unit.area.PosOverlap2D;
import logic.unit.pos.Pos;
import logic.unit.pos.Pos2D;
import logic.unit.size.Size;
import logic.unit.size.Size2D;

/**
 * A form editor.
 * @since 19.11
 */
public class Forms {
    /**
     * Ctor.
     */
    private Forms() { }

    private enum Creation {
        DOT,
        LINE,
        RECT,
        OVAL,
        POLYGON
    }

    private static final int margin = 10;
    private static final int buttonW = 100;
    private static final int buttonH = 35;
    private static final Size buttonSize = new Size2D(buttonW, buttonH);

    public static void main(String[] args) {
        final AtomicReference<Creation> creation = new AtomicReference<>(
            Creation.DOT
        );

        final J2DMouseShapes shapes = new J2DMouseShapes();
        final List<Pos> positions = new ArrayList<>();
        //noinspection NestedAssignment
        new J2DEventWindow(
            new Area2D(800, 800),
            new J2DEventRect(
                new Area2D(0, 0, 800, buttonH + margin * 2),
                new RGBA(230, 230, 230, 255)
            ),
            button(
                "Point",
                0,
                () -> {
                    positions.clear();
                    creation.set(Creation.DOT);
                }
            ),
            button(
                "Line",
                1,
                () -> {
                    positions.clear();
                    creation.set(Creation.LINE);
                }
            ),
            button(
                "Rect",
                2,
                () -> {
                    positions.clear();
                    creation.set(Creation.RECT);
                }
            ),
            button(
                "OVAL",
                3,
                () -> {
                    positions.clear();
                    creation.set(Creation.OVAL);
                }
            ),
            button(
                "Polygon",
                4,
                () -> {
                    positions.clear();
                    creation.set(Creation.POLYGON);
                }
            ),
            button(
                "Clear",
                5,
                () -> shapes.clear()
            ),
            new J2DEventRect(
                new PosOverlap2D(
                    new Area2D(0, buttonH + margin * 2, 800, 800)
                ),
                new RGBA(240, 240, 240, 255),
                List.of(
                    new J2DPress(
                        pos -> {
                            shapes.add(new J2DDot(pos));
                            switch (creation.get()) {
                                case DOT:
                                    break;
                                case LINE:
                                    if (positions.size() == 1) {
                                        shapes.add(
                                            new J2DLine(
                                                positions.get(0),
                                                pos
                                            )
                                        );
                                        positions.clear();
                                    } else {
                                        positions.add(pos);
                                    }
                                    break;
                                case RECT:
                                    if (positions.size() == 1) {
                                        shapes.add(
                                            new J2DEventRect(
                                                new PosOverlap2D(
                                                    positions.get(0),
                                                    positions.get(0).result(
                                                        (fx, fy) -> pos.result(
                                                            (sx, sy) -> new Size2D(sx - fx, sy - fy)
                                                        )
                                                    )
                                                )
                                            )
                                        );
                                        positions.clear();
                                    } else {
                                        positions.add(pos);
                                    }
                                    break;
                                case OVAL:
                                    if (positions.size() == 1) {
                                        shapes.add(
                                            new J2DOval(
                                                new Area2D(
                                                    positions.get(0),
                                                    positions.get(0).result(
                                                        (fx, fy) -> pos.result(
                                                            (sx, sy) -> new Size2D(sx - fx, sy - fy)
                                                        )
                                                    )
                                                ),
                                                new Black()
                                            )
                                        );
                                        positions.clear();
                                    } else {
                                        positions.add(pos);
                                    }
                                    break;
                                case POLYGON:
                                    if (positions.size() == 4) {
                                        shapes.add(
                                            new J2DPolygon(
                                                new Black(),
                                                positions
                                            )
                                        );
                                        positions.clear();
                                    } else {
                                        positions.add(pos);
                                    }
                                    break;
                            }
                        }
                    )
                )
            ),
            shapes

        ).show();
    }

    private static J2DMouseShape button(
        final String text, final int num, final Action action
    ) {
        return new J2DTextButton(
            text,
            new Area2D(
                new Pos2D(margin * (num + 1) + buttonW * num, margin),
                buttonSize
            ),
            action
        );
    }
}
