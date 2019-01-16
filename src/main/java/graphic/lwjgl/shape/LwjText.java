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

import java.nio.ByteBuffer;
import logic.functional.Lazy;
import logic.functional.Value;
import logic.graphic.color.Black;
import logic.graphic.color.Color;
import logic.unit.pos.Pos;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.STBEasyFont;

/**
 * A lwjgl based text.
 * @since 18.0
 */
public class LwjText implements LwjShape {
    /**
     * Some number for the quads. Should find that out.
     */
    private static final int QUAD_NUMBER = 4;

    /**
     * Probably the amount of characters in the used char set, or at least a
     * number that is high enough to initialize the correct buffer.
     */
    private static final int CHAR_NUMBER = 270;

    /**
     * No idea what that is. Should find that out.
     */
    private static final int STRIDE = 16;

    /**
     * The buffer for the text.
     */
    private final Value<ByteBuffer> buffer;

    /**
     * The quads of the text.
     */
    private final Value<Integer> quads;

    /**
     * The color of the text.
     */
    private final Color color;

    /**
     * Ctor.
     * @param content The string of the text.
     * @param pos The position of the text.
     */
    public LwjText(final String content, final Pos pos) {
        this(
            content,
            new Lazy<>(
                () -> BufferUtils.createByteBuffer(
                    content.length() * LwjText.CHAR_NUMBER
                )
            )
        );
    }

    /**
     * Ctor.
     * @param content The actual text.
     * @param buffer The buffer for the text.
     */
    private LwjText(final String content, final Value<ByteBuffer> buffer) {
        this(
            content,
            buffer,
            new Black()
        );
    }

    /**
     * Ctor.
     * @param content The actual text.
     * @param buffer The buffer for the text.
     * @param color The color of the text.
     */
    private LwjText(
        final String content, final Value<ByteBuffer> buffer, final Color color
    ) {
        this(
            buffer,
            new Lazy<>(
                () -> STBEasyFont.stb_easy_font_print(
                    0F,
                    0F,
                    content,
                    null,
                    buffer.content()
                )
            ),
            color
        );
    }

    /**
     * Ctor.
     * @param buffer The buffer for the text.
     * @param quads The quads of the text.
     * @param color The color of the text.
     */
    private LwjText(
        final Value<ByteBuffer> buffer,
        final Value<Integer> quads,
        final Color color
    ) {
        this.buffer = buffer;
        this.quads = quads;
        this.color = color;
    }

    @Override
    public final void draw() {
        GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
        GL11.glVertexPointer(
            2, GL11.GL_FLOAT, LwjText.STRIDE, this.buffer.content()
        );
        this.color.applyOn(GL11::glColor4i);
        GL11.glPushMatrix();
        GL11.glDrawArrays(
            GL11.GL_QUADS, 0, this.quads.content() * LwjText.QUAD_NUMBER
        );
        GL11.glPopMatrix();
    }
}
