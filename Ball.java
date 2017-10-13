/*
Corey Carter
carte731@umn.edu
ID: 5026487
CSCI_1933, Sec-001
Project 2:Object-oriented geometry and graphics - Ball class
*/

import java.awt.*;

public class Ball extends Circle{

    private double xspeed; //Creates private variables, need methods to change or use.
    private double yspeed;
    private double xpos;
    private double ypos;
    
    public Ball(double xpos, double ypos, double radius, Color color){
        super(xpos, ypos, radius); //pulls method constructors from circle parent class
        super.setColor(color); //assigns color
    }

    public void setSpeedX(double newXspeed){ //sets X speed
        xspeed = newXspeed;
    }
    
    public void setSpeedY(double newYspeed){ //sets Y speed
        yspeed = newYspeed;
    }

    public double getSpeedX(){ //grabs X speed
        return(xspeed);
    }

    public double getSpeedY(){ //grabs Y speed
        return(yspeed);
    }

    public void updatePosition(double timePassed){
        xpos = this.getXPos() + this.getSpeedX() / timePassed; //updates balls location based off of speed, position and FPS
        ypos = this.getYPos() + this.getSpeedY() / timePassed;
        this.setPos(xpos, ypos);
    }

    public boolean intersect(Ball ballObject){
        double x = this.getXPos(); //ball one X and Y
        double y = this.getYPos();
        double radius = this.getRadius(); //ball one radius
        double xCollide = ballObject.getXPos();//ball two X and Y
        double yCollide = ballObject.getYPos();
        double radiusCollide = ballObject.getRadius(); //ball two radius
        double xDiff = x - xCollide; //ball one and two X plane difference
        double yDiff = y - yCollide; //ball one and two Y plane difference
        double radDiff = this.getRadius() + ballObject.getRadius(); //adds radius for comparison
        double distance = Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2)); //distance formula
        if(distance < radDiff){ //if combined radius is less than distance, they have intersected
            return(true);
        }
        return(false); //otherwise they didn't
    }

    public void collide(Ball ballObject){
        boolean colliding = intersect(ballObject); //Calls intersect() and saves value to boolean variable
        if(colliding == true){ //Checks it they interected
            double xi = this.getXPos(); //This copies the formulas from the class PDF
            double yi = this.getYPos();
            double xj = ballObject.getXPos();
            double yj = ballObject.getYPos();
            double vix = this.getSpeedX();
            double viy = this.getSpeedY();
            double vjx = ballObject.getSpeedX();
            double vjy = ballObject.getSpeedY();
            double xDiff = xi - xj;
            double yDiff = yi - yj;
            double distance = Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));
            double deltaX = xDiff / distance;
            double deltaY = yDiff / distance;
            double iNewXSpeed = (((vjx*deltaX) + (vjy*deltaY))*deltaX) - (((-vix*deltaY) + (viy*deltaX))*deltaY);
            double iNewYSpeed = (((vjx*deltaX) + (vjy*deltaY))*deltaY) + (((-vix*deltaY) + (viy*deltaX))*deltaX);
            double jNewXSpeed = (((vix*deltaX) + (viy*deltaY))*deltaX) - (((-vjx*deltaY) + (vjy*deltaX))*deltaY);
            double jNewYSpeed = (((vix*deltaX) + (viy*deltaY))*deltaY) + (((-vjx*deltaY) + (vjy*deltaX))*deltaX);
            this.setSpeedX(iNewXSpeed); //sets speed for ball one
            this.setSpeedY(iNewYSpeed);
            ballObject.setSpeedX(jNewXSpeed); //sets speed for ball two
            ballObject.setSpeedY(jNewYSpeed);
            if(this.getColor() == Color.red){ //If ball one is red, spread it to ball two
                ballObject.setColor(Color.red);
            }else if(ballObject.getColor() == Color.red){ //If ball two is red, spread it to ball one
                this.setColor(Color.red);
            }
        }
    }


}