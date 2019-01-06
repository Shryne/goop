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
import logic.unit.pos.Pos;
import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBEasyFont;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnableClientState;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glVertexPointer;

/**
 * A lwjgl based text.
 * @since 18.0
 */
public class LwjText implements LwjShape {
    private final Value<ByteBuffer> buffer;

    private final Value<Integer> quads;

    /**
     * Ctor.
     * @param content The string of the text.
     * @param pos The position of the text.
     */
    public LwjText(final String content, final Pos pos) {
        this(
            content,
            new Lazy<>(
                () -> BufferUtils.createByteBuffer(content.length() * 270)
            )
        );
    }

    private LwjText(final String content, final Value<ByteBuffer> buffer) {
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
            )
        );
    }

    private LwjText(
        final Value<ByteBuffer> buffer,
        final Value<Integer> quads
    ) {
        this.buffer = buffer;
        this.quads = quads;
    }

    @Override
    public final void draw() {
        glEnableClientState(GL_VERTEX_ARRAY);
        glVertexPointer(2, GL_FLOAT, 16, buffer.content());
        glColor3f(169f / 255f, 183f / 255f, 198f / 255f); // Text color
        glPushMatrix();
        glDrawArrays(GL_QUADS, 0, quads.content() * 4);
        glPopMatrix();
    }
}
