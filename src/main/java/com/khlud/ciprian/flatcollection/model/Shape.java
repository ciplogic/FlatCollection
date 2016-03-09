package com.khlud.ciprian.flatcollection.model;

public class Shape {

    Line[] Lines = new Line[200]; //200 x 40 = 8 000
    static Shape shape;

    static void fun() {
        shape.Lines[10].begin.x = 3;
    }
}
