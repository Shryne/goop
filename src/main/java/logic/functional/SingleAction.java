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

/**
 * An action that will be applied only once. Further calls of {@link #run()}
 * won't be executed.
 * <p>This class is mutable and not thread-safe, because it mutates its
 * state when {@link #run()} is called.</p>
 * @since 9.0.0
 */
public class SingleAction implements Action {
    /**
     * The action to be called.
     */
    private final Action action;

    /**
     * Boolean whether the action has been called or not.
     */
    private boolean called;

    /**
     * Ctor.
     * @param action The action to be called.
     */
    public SingleAction(final Action action) {
        this(action, false);
    }

    /**
     * Ctor.
     * @param action The action to be called.
     * @param called Boolean whether the action has been called or not. Passing
     *  true as a value would mean that the action won't be called.
     */
    public SingleAction(final Action action, final boolean called) {
        this.action = action;
        this.called = called;
    }

    @Override
    public final void run() {
        if (!this.called) {
            this.action.run();
            this.called = true;
        }
    }
}
