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

package graphic.lwjgl.shape;

import logic.graphic.color.Black;
import logic.graphic.color.Color;
import logic.unit.area.Area;
import logic.unit.area.Area2D;
import logic.unit.size.Size;
import org.lwjgl.opengl.GL11;

/**
 * A lwjgl based rectangle.
 * <p>This class is immutable, but uses the gl methods to draw itself which
 * aren't thread-safe..</p>
 * @since 7.1.0
 */
public class LwjRect implements LwjShape {
    /**
     * The area of this rect.
     */
    private final Area area;

    /**
     * The color of this rect.
     */
    private final Color color;

    /**
     * Ctor.
     * @param size The size of this rect.
     */
    public LwjRect(final Size size) {
        this(new Area2D(size));
    }

    /**
     * Ctor.
     * @param size The size of this rect.
     * @param color The color of this rect.
     */
    public LwjRect(final Size size, final Color color) {
        this(new Area2D(size), color);
    }

    /**
     * Ctor.
     * @param area The area of this rect.
     */
    public LwjRect(final Area area) {
        this(area, new Black());
    }

    /**
     * Ctor.
     * @param area The area of this rect.
     * @param color The color of this rect.
     */
    public LwjRect(final Area area, final Color color) {
        this.area = area;
        this.color = color;
    }

    @Override
    public final void draw() {
        GL11.glBegin(GL11.GL_QUADS);
        this.color.applyOn(GL11::glColor4i);
        this.area.applyOn(
            // @checkstyle ParameterNameCheck (1 line)
            (x, y, width, height) -> {
                GL11.glVertex2i(x, y);
                GL11.glVertex2i(x + width, y);
                GL11.glVertex2i(x + width, y  + height);
                GL11.glVertex2i(x, y  + height);
            }
        );
        GL11.glEnd();
    }
}
