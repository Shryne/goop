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

package graphic.lwjgl.window;

import org.lwjgl.glfw.GLFW;

/**
 * This class is used to help releasing glfw at the end of the usage.
 * Counts the acquisitions of the initialization and the releases. If it's
 * even, meaning that nobody needs glfw anymore, then it releases glfw.
 * It's a singleton, so be wary in using this.
 *
 * <p>Singletons aren't good and this one is even a mutable singleton. However,
 * it's necessary, because glfw uses methods that are global. This class uses
 * synchronization to be thread-safe.</p>
 * @since 3.9.0
 */
public final class GlfwBaseInit implements GlfwInit {
    /**
     * Used for synchronization of {@link #instance()}.
     */
    private static final Object LOCK = new Object();

    /**
     * The private instance of GlfwBaseInit. This is necessary, because the
     * glfw initialization is global and it's not possible to do the right
     * release of resources without a global overview of that.
     */
    private static GlfwInit glfwInit;

    /**
     * Locking for {@link #acquire()} and {@link #close()}.
     */
    private final Object lock;

    /**
     * The amount of objects that tried to initialize glfw. The same amount has
     * to release glfw before it really needs to be released.
     */
    private int amount;

    /**
     * Private constructor because this is a singleton.
     */
    private GlfwBaseInit() {
        this.lock = new Object();
    }

    /**
     * Creates the instance of GlfwInit if there isn't one and returns it.
     * @return The singleton instance of GlfwInit.
     */
    @SuppressWarnings("PMD.PublicStaticMethods")
    public static GlfwInit instance() {
        synchronized (GlfwBaseInit.LOCK) {
            if (GlfwBaseInit.glfwInit == null) {
                GlfwBaseInit.glfwInit = new GlfwBaseInit();
            }
            return GlfwBaseInit.glfwInit;
        }
    }

    @Override
    public void acquire() {
        synchronized (this.lock) {
            if (this.amount == 0 && !GLFW.glfwInit()) {
                throw new IllegalStateException("Unable to initialize GLFW");
            }
            this.amount += 1;
        }
    }

    @SuppressWarnings("PMD.NullAssignment")
    @Override
    public void close() {
        this.amount -= 1;
        if (this.amount == 0) {
            GLFW.glfwTerminate();
            GlfwBaseInit.glfwInit = null;
        }
    }
}
