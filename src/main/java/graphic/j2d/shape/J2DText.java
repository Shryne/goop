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
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.Optional;
import logic.graphic.color.Black;
import logic.graphic.color.Color;
import logic.unit.area.Area;
import logic.unit.area.Area2D;
import logic.unit.pos.Pos;
import logic.unit.pos.Pos2D;
import logic.unit.size.Size2D;

/**
 * Java 2d based text.
 * @since 19.2
 */
public class J2DText implements J2DMouseShape {
    /**
     * The text to show.
     */
    private final String text;

    /**
     * The max area of the text.
     */
    private final Area area;

    /**
     * The color of the text.
     */
    private final Color color;

    /**
     * The font of the text.
     */
    private Font font;

    /**
     * The height of the font.
     */
    private int height;

    /**
     * The successor of this text.
     */
    private final Optional<J2DMouseShape> successor;

    /**
     * Ctor.
     * @param text The text to show.
     * @param x The x coordinate of the text.
     * @param y The y coordinate of the text.
     */
    public J2DText(final String text, final int x, final int y) {
        this(text, new Pos2D(x, y));
    }

    /**
     * Ctor.
     * @param text The text to show.
     * @param pos The position of the text.
     */
    public J2DText(final String text, final Pos pos) {
        this(text, pos, new Black());
    }

    /**
     * Ctor.
     * @param text The text to show.
     * @param area The area of the text.
     */
    public J2DText(final String text, final Area area) {
        this(text, area, new Black());
    }

    /**
     * Ctor.
     * @param text The text to show.
     * @param pos The position of the text.
     * @param color The color of the text.
     */
    public J2DText(final String text, final Pos pos, final Color color) {
        this(
            text,
            new Area2D(
                pos,
                new Size2D(
                    text.length()
                        * Toolkit.getDefaultToolkit().getScreenSize().width
                        / 150,
                    Toolkit.getDefaultToolkit().getScreenSize().height / 100
                )
            ),
            color
        );
    }

    /**
     * Ctor.
     * @param text The text to show.
     * @param area The area of the text.
     * @param color The color of the text.
     */
    public J2DText(
        final String text, final Area area, final Color color
    ) {
        this.text = text;
        this.area = area;
        this.color = color;
        this.successor = Optional.of(this);
    }

    @Override
    public final void registerFor(final J2DMouse source) { }

    @Override
    public final Optional<J2DMouseShape> draw(final Graphics graphics) {
        this.color.applyOn(
            (r, g, b, a) -> graphics.setColor(new java.awt.Color(r, g, b, a))
        );
        if (this.font == null) {
            int fontSize = 10;
            do {
                this.font = new Font("Arial", Font.PLAIN, fontSize);
                var metrics = graphics.getFontMetrics(this.font);
                int width = metrics.stringWidth(this.text);
                this.height = metrics.getMaxAscent();
                if (this.area.result(
                    (pos, size) -> size.result(
                        (w, h) -> w < width || h < this.height
                    )
                )) {
                    break;
                }
                ++fontSize;
            } while(true);
            this.font = new Font("Arial", Font.PLAIN, fontSize - 1);
        }
        graphics.setFont(this.font);
        this.area.applyOn((x, y, w, h) -> graphics.drawString(
            this.text,
            x,
            y + this.height
        ));
        return this.successor;
    }

    @Override
    public final void register(final Redrawable redrawable) {
        this.area.register(redrawable);
    }
}
