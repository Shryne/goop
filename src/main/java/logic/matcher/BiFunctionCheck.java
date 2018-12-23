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

import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import logic.functional.Lazy;
import logic.functional.Value;
import org.hamcrest.Description;

/**
 * The check of a method that takes {@link BiFunction}. It takes the method
 * through the constructor, gets the values and offers the methods to check it
 * against an expectation. This class is intended to be used in Matcher for
 * classes that have such methods.
 * <p>This doesn't mutate it's state, but changes the state of the description
 * object given in the check methods (for example
 * {@link #checkFirst(Object, Description)})</p>
 * @see RightSizeResult
 * @see RightInsetsResult
 * @since 14.2.0
 */
public class BiFunctionCheck<A, B, R>
    implements TwoValueCheck<A, B>, ResultCheck<R> {
    /**
     * The method that takes the BiFunction instance. It will get an instance
     * from this class and it will be used to get the values from the object
     * to check them.
     */
    private final Consumer<BiFunction<A, B, R>> method;

    /**
     * Ctor. Use the method reference to shorten the creation. Example:
     * <pre>{@code new BiFunctionCheck(size::result)}</pre> instead of:
     * <pre>{@code new BiFunctionCheck(func -> size.result(func)}</pre>
     * @param method The method to check.
     */
    public BiFunctionCheck(final Consumer<BiFunction<A, B, R>> method) {
        this(new Lazy<>(() -> method.));
    }

    private BiFunctionCheck(final Value<Consumer<BiFunction<A, B, R>>> method) {
        this.method = method;
    }

    @Override
    public final boolean checkResult(final R expected, final Description description) {

    }

    @Override
    public final boolean checkSecond(final B expected, final Description description) {
        throw new UnsupportedOperationException("#checkSecond()");
    }

    @Override
    public final boolean checkFirst(
        final A expected, final String name, final Description description
    ) {
        final var result = new ArrayList<Boolean>(1);
        this.method.accept(
            (first, ignored) -> {
                final CharSequence text = new EqualsFailText(
                    first, expected, name
                );
                description.appendText(text.toString());
                // @todo #? text.length() == 0 is very uninformative. I should
                //  use something better
                result.add(text.length() == 0);
                return null;
            }
        );
        return result.get(0);
    }
}
