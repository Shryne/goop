# goop
[![EO principles respected here](http://www.elegantobjects.org/badge.svg)](http://www.elegantobjects.org)
[![DevOps By Rultor.com](http://www.rultor.com/b/Shryne/goop)](http://www.rultor.com/p/Shryne/goop)  

![Sonar coverage](https://sonarcloud.io/api/project_badges/measure?project=Shryne_goo&metric=coverage)
[![PDD status](http://www.0pdd.com/svg?name=Shryne/goop)](http://www.0pdd.com/p?name=Shryne/goop)
[![Known Vulnerabilities](https://snyk.io/test/github/Shryne/goop/badge.svg)](https://snyk.io/test/github/Shryne/goop)
[![Reviewed by Hound](https://img.shields.io/badge/Reviewed_by-Hound-8E64B0.svg)](https://houndci.com)  

[![codebeat badge](https://codebeat.co/badges/8645442d-a265-41e0-abb6-608d64efad6a)](https://codebeat.co/projects/github-com-shryne-goop-master)
[![CodeFactor](https://www.codefactor.io/repository/github/shryne/goop/badge)](https://www.codefactor.io/repository/github/shryne/goop)

Goop is an object oriented graphics library. It uses [LWJGL](https://www.lwjgl.org/) to create the windows and draw the shapes. The advantages over plain Java2D/Swing/JavaFX are:  
- It is based on decorators  
- The state of every object is set by the call of the constructor  
- No setters/getters  
- Small classes and interfaces  
- Every method is defined by an interface  
And most important: **The object knows what happens to him**.  
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
    new Size2D(500, 500)
  )
).show();
```
This is how a moving rect would look like:  
```java
new Window(
  "I am a window with a moving rect",
  new Size(500, 500),
  new Rect(
    new Moving(
      new Pos2D(0, 0), // from
      new Pos2D(100, 100), // to
      10_000 // ms needed
    ),
    new Size2D(200, 200)
  )
).show();
```
The class Moving implements Pos and Rect takes a Pos - no getter/setter needed, because the object itself is in control.
