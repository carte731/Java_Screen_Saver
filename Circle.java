/*
Corey Carter
carte731@umn.edu
ID: 5026487
CSCI_1933, Sec-001
Project 2:Object-oriented geometry and graphics - Circle Method
*/

import java.awt.*;

public class Circle{

    private double xPos; //Creates private variables, need methods to change or use.
    private double yPos;
    private double radius;
    private Color shapeColor;

    public Circle(double x, double y, double rad){
        xPos = x; //Constutor class used for default values
        yPos = y;
        radius = rad;
        shapeColor = Color.green;
    }
    
    public double calculatePerimeter(){
            double perimeter = 2 * (Math.PI * radius); //formula for circle perimeter
            return(perimeter);
    }
    
    public double calculateArea(){
            double area = Math.PI * Math.pow(radius, 2); //formula for a circles area
            return(area);
    }

    public void setColor(Color newShapeColor){
            shapeColor = newShapeColor; //Reassigns shape color
    }

    public void setPos(double newXPos, double newYPos){
            xPos = newXPos; //gives new X and Y cor
            yPos = newYPos;
    }

    public void setRadius(double newRadius){
            radius = newRadius; //changes circles radius
    }

    public Color getColor(){
            return(shapeColor); //gets the shapes color
    }

    public double getXPos(){
            return(xPos); //gets the shapes X position
    }

    public double getYPos(){
            return(yPos); //gets the shapes Y position
    }

    public double getRadius(){
            return(radius); //gets the shapes radius
    }

}