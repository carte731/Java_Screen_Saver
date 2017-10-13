/*
Corey Carter
carte731@umn.edu
ID: 5026487
CSCI_1933, Sec-001
Project 2:Object-oriented geometry and graphics - Ball screen saver class
*/

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.sql.Timestamp;

public class BallScreenSaver extends AnimationFrame{
    private double xSpeed, ySpeed; //Creates private variables, need methods to change or use.
	private int ballSize=18;
	private final int speed=300;
	public final int borderWalls=30;
    private int windowFrameWidth;
    private int windowFrameHeight;
    private String appWindowName;
    private int numOfBalls; 
    private static final Ball balls[] = new Ball[100]; //creates nested int lists
    private CollisionLogger collisionLogger; //Allows for collisionLogger instances
    private static final int bucketSize = 10; //bucket size

    public BallScreenSaver(int framewidth, int frameheight, String name, int ballsnum){
        super(); //grabs methods from AnimationFrame class
        Color colors = Color.green; //color default
        windowFrameWidth = framewidth; //Assigning constuctor variables
        windowFrameHeight = frameheight;
        appWindowName = name;
        numOfBalls = ballsnum;
        for(int counter = 0; counter < numOfBalls; counter++){ //This creates ball instances
            double newBallXPos = randdouble(borderWalls,getWidth()-(borderWalls+20)); //+20 is added to make sure balls aren't generate on the border. The border plus 10 pixals inward 
            double newBallYPos = randdouble(borderWalls,getHeight()-(borderWalls+20));
            if(counter == 1){ //Creates red ball on second index point
                colors = Color.red;
            }
            Ball ball = new Ball(newBallXPos, newBallYPos, ballSize, colors); //Creates ball object            
            setRandDir(speed, ball); //randomly assigns speed accepts recently created ball object
            balls[counter] = ball; //Adds them into empty list at counter index position
            colors = Color.green; //resets object to green if red, safety measure
        }
        setFPS(30); //default FPS is 30
        collisionLogger = new CollisionLogger(this.getWidth(), this.getHeight(), bucketSize); //Creates collisionLogger instance
    }

    public void action(){
        for(int ballcount = 0; ballcount < numOfBalls; ballcount++){ //Goes thourgh entire ball listings
            balls[ballcount].updatePosition(getFPS()); //Uses ball updatePosition method to change speed
            if (balls[ballcount].getXPos() < borderWalls || balls[ballcount].getXPos() + ballSize>getHeight()-borderWalls){ //If ball bounces of wall on X axis
                double newXSpeed = -1 * balls[ballcount].getSpeedX(); //Negative 1 causes the ball to change direction
                balls[ballcount].setSpeedX(newXSpeed); //uses ball set speed method to change speed on the ball index
            }
            if (balls[ballcount].getYPos() < borderWalls || balls[ballcount].getYPos() + ballSize>getWidth()-borderWalls){ //If ball bounces of wall on Y axis
                double newYSpeed = -1 * balls[ballcount].getSpeedY();
                balls[ballcount].setSpeedY(newYSpeed);
            }
            for(int collisionChecker = 0; collisionChecker < numOfBalls; collisionChecker++){ //Checks entire list for collision, FOR looping through same list twice
                if(balls[ballcount] != balls[collisionChecker]){ //Checks if both balls aren't the same by comparing memory addresses, this prevents a ball from colliding with itself
                    if(balls[ballcount].intersect(balls[collisionChecker])){ //If they intersect, do this
                        balls[ballcount].collide(balls[collisionChecker]); //calls ball collide method
                        collisionLogger.collide(balls[ballcount],balls[collisionChecker]); //inputs balls into collisionLogger collide method
                    }
                }
            }
        }
    }

    public void draw(Graphics g){
        g.setColor(Color.black); //Sets background color
		g.fillRect(0,0,getWidth(),getHeight()); //set fill depth
		g.setColor(Color.yellow); //sets border color
        g.drawRect(borderWalls,borderWalls,getWidth()-borderWalls*2,getHeight()-borderWalls*2); //creates border
        for(int ballcounter = 0; ballcounter < numOfBalls; ballcounter++){ //FOR loops trhough ball list and draws them
            g.setColor(balls[ballcounter].getColor()); //Colors ball
            g.fillOval((int)balls[ballcounter].getXPos(), (int)balls[ballcounter].getYPos(), ballSize, ballSize); //Draws ball based off of ball object values           
        }
    }

    public void setRandDir(double speed, Ball ball){ //Accepts default speed and ball instances
        double dir=randdouble(0,Math.PI*2); //random direction
        xSpeed = Math.cos(dir)*speed; //Sets speed along X and Y
        ySpeed = Math.sin(dir)*speed;
		ball.setSpeedX(xSpeed); //Saves speed to ball object
		ball.setSpeedY(ySpeed);
	}
	
	public int randInt(int min, int max){ //Random int genterator
		return (int)(Math.random()*(max-min)+min);
	}
	
	public double randdouble(double min, double max){ //Random double genterator
        return (Math.random()*(max-min)+min);
    }

    protected void processKeyEvent(KeyEvent e){ //receives user inputs
	    int keyCode = e.getKeyCode(); //input from user
	    if(e.getID() == KeyEvent.KEY_PRESSED && keyCode == KeyEvent.VK_LEFT){ //If left key is press reduce speed by .2
            double currentFPS = getFPS() * .2; //grabs 20% of current FPS, I chose 20% and not 10% because it looked better
            currentFPS =getFPS() + currentFPS; //Add FPS percentage to total FPS speed which slows it down. More frames to render
            setFPS((int)currentFPS); //type casting double current FPS value to int.
	    }else if(e.getID() == KeyEvent.KEY_PRESSED && keyCode == KeyEvent.VK_RIGHT){
            double currentFPS = getFPS() * .2;
            currentFPS = getFPS() - currentFPS; //subtracts FPS by 20%, less render which means it goes faster
            if(currentFPS > 0){ //Makes sure value never goes below 1, double safety precausion since Animation Class already has IF statement preventing 0 FPS
                setFPS((int)currentFPS);
            }
	    }else if(keyCode == KeyEvent.VK_UP){ //Default FPS speed reset
            setFPS(30);
        }else if (e.getID() == KeyEvent.KEY_PRESSED && keyCode == KeyEvent.VK_P){ //Takes heatmap image
                EasyBufferedImage image = EasyBufferedImage.createImage(collisionLogger.getNormalizedHeatMap()); //Allows for heatmap image saving
                try {
                    Timestamp time = new Timestamp(System.currentTimeMillis()); //Creates Timestamps, current date and time
                    image.save("CoreyCarterHeatMap_" + time + ".png", EasyBufferedImage.PNG); //saves image with my name and current dat and time
                } catch (IOException exc) {
                    exc.printStackTrace();
                    System.exit(1);
                }
                
            }else if(keyCode == KeyEvent.VK_Q){ //Allows user to quit by pressing q
                System.out.println("Thank you for using my app.");
                System.out.println("GOODBYE...\n");
                System.exit(0); //quits
            }
	}

}