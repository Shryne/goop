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

package logic.matcher;

import java.util.function.BiFunction;
import logic.unit.size.Size;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * A matcher for {@link Size#result(BiFunction)}.
 * <p>The class by itself is immutable, but mutates the incoming description
 * object which is mutable.</p>
 * @since 15.1.0
 */
public class CorrectSizeResult extends TypeSafeDiagnosingMatcher<Size> {
    /**
     * The expected width.
     */
    private final Integer width;

    /**
     * The expected height.
     */
    private final Integer height;

    /**
     * Ctor.
     * @param width The width to expect from the
     *  {@link Size#result(BiFunction)} method.
     * @param height The height to expect from the
     *  {@link Size#result(BiFunction)} method.
     */
    public CorrectSizeResult(final Integer width, final Integer height) {
        super();
        this.width = width;
        this.height = height;
    }

    @Override
    public final void describeTo(final Description description) {
        description.appendText(
            String.format(
                "Expected width = %d and height = %d",
                this.width,
                this.height
            )
        );
    }

    @Override
    protected final boolean matchesSafely(
        final Size size, final Description description
    ) {
        return size.result(
            // @checkstyle ParameterNameCheck (1 line)
            (resWidth, resHeight) -> {
                description.appendText(
                    String.format("width: %d, height: %d", resWidth, resHeight)
                );
                return this.width.equals(resWidth)
                    && this.height.equals(resHeight);
            }
        );
    }
}
