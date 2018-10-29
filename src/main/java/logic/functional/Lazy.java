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

import java.util.function.Supplier;

/**
 * Offers a lazy initialization of the given value. The value will only be
 * calculated once.
 * <p>This class is not immutable, because the calculated value will be cached
 * by calling {@link #content()}. Additionally this class is not thread-safe,
 * because there isn't any synchronization.</p>
 * @param <T> The type of the lazily constructed value.
 * @since 3.2.0
 */
public class Lazy<T> implements Value<T> {
    /**
     * By using this, the value will be constructed.
     */
    private final Supplier<T> init;

    /*
    The null may not be in favor of everybody, but I chose it for the following
    reasons:
    1. Lazy is/will be used in many classes. Additional objects as an
    alternative to the null would only raise the amount of objects without
    real benefit
    2. Objects instead of null would also require a check (if value has been
    set, use the value, else call init)
    3. This class is small and value is only needed for the content method. So
    the impact of using null is rather small
    */
    /**
     * The cached value. After one call of {@link #content()}, it will be set
     * to the result of {@link #init}.
     */
    private T value;

    /**
     * Primary constructor.
     * @param init The initialization function to constructor the
     *  desired value.
     */
    public Lazy(final Supplier<T> init) {
        this.init = init;
    }

    @Override
    public final T content() {
        if (this.value == null) {
            this.value = this.init.get();
        }
        return this.value;
    }
}
