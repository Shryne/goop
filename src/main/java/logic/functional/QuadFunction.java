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

package logic.functional;

/*
Personally, I may've called this class differently, but in this case I thought
it may be better to follow the standard libraries way. Otherwise users might
be confused
*/
/**
 * This interface defines a function like {@link java.util.function.Function}.
 * The difference is that this function takes four arguments.
 * @param <A> The type of the first argument.
 * @param <B> The type of the second argument.
 * @param <C> The type of the third argument.
 * @param <D> The type of the fourth argument.
 * @param <R> The type of the result of that function.
 * @since 3.9.0
 */
@FunctionalInterface
public interface QuadFunction<A, B, C, D, R> {
    /**
     * Accepts the given arguments and returns its result.
     * @param a The first argument.
     * @param b The second argument.
     * @param c The third argument.
     * @param d The fourth argument.
     * @return The result of the call.
     * @checkstyle ParameterNumber (3 lines)
     * @checkstyle ParameterNameCheck (2 lines)
     */
    R apply(A a, B b, C c, D d);
}
