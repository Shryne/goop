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
package graphic.j2d.window

import graphic.j2d.shape.J2DShape
import logic.functional.Lazy
import logic.functional.Value
import logic.graphic.window.Showable
import logic.unit.area.Area
import logic.unit.area.applyOn
import logic.unit.pos.applyOn
import java.awt.Dimension
import java.awt.Graphics
import java.awt.event.ActionEvent
import java.util.function.Consumer
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.Timer

/*
Passing the JFrame to the feature isn't beautiful but necessary, because the
window can't be decorated without using the JFrame. At least by using the
feature I made the window pass the JFrame to something instead of providing a
direct getter to the state
*/
/**
 * Represents a simple window. To apply some settings on this window,
 * one has to use a constructor that takes a feature. This feature gets
 * the JFrame of this window to apply the needed settings or to get information
 * from it. By using this, one can bind the feature on a certain event like a
 * click on a button. **Don't capture the JFrame to use it elsewhere!**
 *
 * This class is not thread-safe, because it mutates its state when
 * [show] is called. Additionally, the features can change its state.
 *
 * @param window A value containing the actual frame. This is necessary, because the
 * JFrame will probably be lazily constructed.
 */
open class J2DBaseWindow private constructor(
    private val window: Value<JFrame>
) : Showable {
    /**
     * @param area The area of this window.
     * @param shapes The shapes that are on this window.
     */
    constructor(area: Area, shapes: Iterable<J2DShape<*>>) :
        this(area, emptyList<J2DWindowFeature>(), shapes)

    /**
     * @param area The area of this window.
     * @param features Certain  features regarding the window.
     * @param shapes The shapes that are on this window.
     */
    constructor(
        area: Area,
        features: Iterable<J2DWindowFeature>,
        shapes: Iterable<J2DShape<*>>
    ) : this(
        Lazy<JFrame> {
            val frame = JFrame()
            val panel: JPanel = object : JPanel() {
                override fun paintComponent(
                    graphics: Graphics
                ) {
                    super.paintComponent(graphics)
                    shapes.forEach { shape: J2DShape<*>? ->
                        shape!!.draw(
                            graphics
                        )
                    }
                }
            }
            area.applyOn { pos, size ->
                pos.applyOn(frame::setLocation)
                size.result { width, height ->
                    panel.preferredSize = Dimension(width, height)
                    null
                }
            }
            frame.add(panel)
            frame.pack()
            frame.isVisible = true
            features.forEach(Consumer { it: J2DWindowFeature -> it.accept(frame) })
            val timer = Timer(25) { e: ActionEvent? -> frame.repaint() }
            timer.isRepeats = true
            timer.start()
            frame
        }
    )

    override fun show() {
        window.content()
    }
}