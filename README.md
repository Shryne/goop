# goop
[![EO principles respected here](http://www.elegantobjects.org/badge.svg)](http://www.elegantobjects.org)
[![DevOps By Rultor.com](http://www.rultor.com/b/Shryne/goop)](http://www.rultor.com/p/Shryne/goop)  

![Sonar coverage](https://sonarcloud.io/api/project_badges/measure?project=Shryne_goo&metric=coverage)
[![PDD status](http://www.0pdd.com/svg?name=Shryne/goop)](http://www.0pdd.com/p?name=Shryne/0pdd)
[![Known Vulnerabilities](https://snyk.io/test/github/Shryne/goop/badge.svg)](https://snyk.io/test/github/Shryne/goop)
[![CodeFactor](https://www.codefactor.io/repository/github/shryne/goop/badge)](https://www.codefactor.io/repository/github/shryne/goop)
[![Reviewed by Hound](https://img.shields.io/badge/Reviewed_by-Hound-8E64B0.svg)](https://houndci.com)

Goop is an object oriented graphics library. It uses [LWJGL](https://www.lwjgl.org/) to create the windows and draw the shapes. The advantages over plain Java2D/Swing/JavaFX are:  
- It is based on decorators  
- The state of every object is set by the call of the constructor  
- No setters/getters  
- Small classes and interfaces  
- Every method is defined by an interface  

Here is an example to visualize how it will look:  
```java
new Window(
  "I am a window",
  new Area2D(
    new Pos2D(0, 0),
    new Size2D(500, 500)
  ),
  new Rect(
    new Area2D(
      new Pos2D(0, 0),
      new Size2D(500, 500)
    ),
    new Black()
  )
).show();
```
Or using the defaults and assuming that the size of the rectangle is equal to the size of the window:
```java
new Window(
  "I am a window",
  new Rect(
    new Size2D(500, 500),
    new Black()
  )
).show();
```
The JavaFX approach would look like this:
```java
public class Test extends Application {
    @Override
    public void start(Stage stage) {
        final Rectangle rectangle = new Rectangle(0, 0, 500, 500);
        final Group root = new Group(rectangle);
        final Scene scene = new Scene(root, 500, 500);
        stage.setTitle("I am a window");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String args[]){
        launch(args);
    }
}
```
In this case we are quite lucky, because the Rectangle class has a constructor that allows us to define the area of the rectangle, but this is quite rare.  
Additionally, as you can see, the JavaFX components need many getters. This is neccessary, because their state is defined outside of the object. Take a fade animation as an example. If you want a fading rectangle, you will probably create an Animation object, give the rectangle to that object and the Animation object will take control.  
They other way is to define that Animation as a special kind of Color and give that color to the rectangle. This way, you don't need any getters and the logic regarding the rectangle is right there inside the rectangle.  
