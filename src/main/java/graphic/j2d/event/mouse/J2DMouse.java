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

package graphic.j2d.event.mouse;

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;

/*
The alternative to this would be to pass the actual JFrame to the classes,
but because they just need "addMouseListener" and the other mouse methods,
it would give the client much more control than he needs. Besides, one could
argue that it would be simpler to add a getter "mouseListener" to the
event classes but that would mean that each class has to define at least an
empty MouseListener and the Window would add them all to the JFrame. Using the
J2DMouse, I can omit the empty MouseListeners
*/
/**
 * Serves as a layer between a {@link java.awt.Component} and the concrete
 * event classes.
 * @since 12.2.0
 */
public interface J2DMouse {
    /**
     * Registers a MouseListener.
     * @param target The MouseListener who will get the events.
     */
    void register(MouseListener target);

    /**
     * Registers a MouseMotionListener.
     * @param target The MouseMotionListener who will get the events.
     */
    void register(MouseMotionListener target);

    /**
     * Registers a MouseWheelListener.
     * @param target The MouseWheelListener who will get the events.
     */
    void register(MouseWheelListener target);
}
