/*
Corey Carter
carte731@umn.edu
ID: 5026487
CSCI_1933, Sec-001
Project 2:Object-oriented geometry and graphics - Collision Logger Class
*/

public class CollisionLogger {

    int screenWidthWindow; //Creates private variables, need methods to change or use.
    int screenHeightWindow;
    int bucketWidthWindow;
    int[][] masterList = new int[80][80]; //Allows for 10 rows and columns buckets for heatmaping a 800x800 grid
    
    public CollisionLogger(int screenWidth, int screenHeight, int bucketWidth){
        screenWidthWindow = screenWidth; 
        screenHeightWindow = screenHeight;
        bucketWidthWindow = bucketWidth; //bucket width allows 1/10th of total grid size
        for(int window = 0; window < screenHeight/bucketWidth; window++){ //creates nested heatmap
            int subList[] = new int[screenWidth/bucketWidth]; //creates sub list used for nesting list, or cleans out list
            for(int subwindow = 0; subwindow < bucketWidth/bucketWidth; subwindow++){ //assigning nested list values, not really needed since empty list default to 0, but I wanted to make sure for certain.
                subList[window] = 0; //saves default value of 0
            }
            masterList[window] = subList; //Saves the nested sub list to master list  
        }
    }

    public void collide(Ball one, Ball two) {
        int avgYPos = ((int)one.getXPos() + (int)two.getXPos()) / 2; //gets average postion between balls
        int avgXPos = ((int)one.getYPos() + (int)two.getYPos()) / 2;
        int collisionYGrid = avgYPos / bucketWidthWindow; //removes remainder from number by deiving by bucket amount, the leading int assigns it to the proper nested X and column Y
        int collisionXGrid = avgXPos / bucketWidthWindow;
        collisionYGrid = Math.abs(collisionYGrid); //Grabs the absolute value incase a ball escapes the play pen
        collisionXGrid = Math.abs(collisionXGrid);
        if(collisionXGrid < (masterList.length-1)){ //Checks if ball is in play pen border
            if(collisionYGrid < (masterList.length-1)){
                masterList[collisionYGrid][collisionXGrid] += 1; //Saves 1 to nested location  
            }
        }
    }

    public int[][] getNormalizedHeatMap() {
        int indexYPos = 0; //largest X position
        int indexXPos = 0; //largest Y position
        int maxValue = 0; //largest value used fro comparison
        int maxYDimensions = 0; //Max window size
        int maxXDimensions = 0;
    	for(int maxValYScanner = 0; maxValYScanner < masterList.length; maxValYScanner++){ //scans through columns
            for(int subScanner = 0; subScanner < masterList[maxValYScanner].length; subScanner++){ //scans through nested X values
                if(masterList[maxValYScanner][subScanner] > maxValue){ //if FOR loop element is greater than saved max value save over it
                    maxValue = masterList[maxValYScanner][subScanner]; //reassigns max value to new max value
                    indexYPos = maxValYScanner; //saves Max values positions
                    indexXPos = subScanner;
                }
            }
            maxYDimensions = masterList.length; //saves max Y length
            maxXDimensions = masterList[maxValYScanner].length; //saves max X lengths
        }
        masterList[indexYPos][indexXPos] = 255; //Changes max value from masterlist to 255
        int[][] normalizedHeatMap = new int[maxYDimensions][maxXDimensions]; //Creates Heatmap nesetd list
        for(int sweep = 0; sweep < masterList.length; sweep++){ //This copies masterlist values to new Heatmap neste lst
            for(int subSweep = 0; subSweep < masterList[sweep].length; subSweep++){
                normalizedHeatMap[sweep][subSweep] = masterList[sweep][subSweep];
            }
        }
        return(normalizedHeatMap); //return heatmap
    }
}
