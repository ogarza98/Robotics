/*OpMode*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import java.util.List;



@TeleOp(name="Basic: Linear OpMode", group="Linear Opmode")

public class PrimaryActive extends LinearOpMode {
    
    int Start_X = 3;
    int Start_Y = 1;
    double Xpos;
    double Ypos;
    int End_X = 0;
    int End_Y = 2;
    int currentX;
    int currentY;
    int currentZ;
    int nextX;
    int nextY;
    int nextZ;
    int StartDirection = 0; //INPUT START DIRECTION (0, 90, 180, 270)
    int FinalMove;
    
    
    
    // Declare OpMode members.
    private ElapsedTime runtime             = new ElapsedTime();
    
    DemoHardware robot = new DemoHardware(this);

    @Override
    public void runOpMode() {
        
        
        int[][]maze = 
        {
        {0,    0,      0,    0},
        {0,    0,      0,    0},
        {0,  100,    100,    0},
        {0,    0,      0,    0},
        {0,    0,      0,    0}
        };
        
   
        robot.init(hardwareMap);


        waitForStart();
        
         AStarClass as = new AStarClass(maze, Start_X, Start_Y, false, this);
         
         List<Node> path = as.findPathTo(End_X, End_Y);
         for(int i = 0; i < path.size()-1; i++){
             
                    currentX = path.get(i).x;
                    currentY = path.get(i).y;
                    nextX    = path.get(i+1).x;
                    nextY    = path.get(i+1).y; 
            telemetry.addData("Maze Position x:", path.get(i).x);
            telemetry.addData("Maze Position y:", path.get(i).y);
                    

          // This is a long and convoluted implementation, please try to implement it using the two functions I suggested in class.

            //if(StartDirection == 0){ //Looping statement for start facing 0 degrees
            // Direction is clockwise from 0 to 270
            if(StartDirection == 0){
            
                if(nextX == (currentX - 1) && nextY == currentY && currentZ == 0){        // if x decreases & Y doesnt change & direction is 0; 
                        robot.encoderDrive(robot.DRIVE_SPEED,  -5,    -5,    1.5);        //Forward 12 Inches
                        currentZ = 0;
                        telemetry.addData("Direction", currentZ);
                        //telemetry.update();
                    }
                if(nextX == currentX && nextY == (currentY + 1) && currentZ == 0){        //if x doesnt change & Y increases & direction is 0;  
                        robot.encoderDrive(robot.TURN_SPEED,   -3,     3,    1.75);        //Turn Right 90 degrees
                        robot.encoderDrive(robot.DRIVE_SPEED, -10,   -10,    1.5);        //Forward 12 Inches
                        currentZ = 90;
                        telemetry.addData("Direction", currentZ);
                        //telemetry.update();
                    }
                if(currentX == nextX && currentY == nextY + 1 && currentZ == 90){
                    robot.encoderDrive(robot.DRIVE_SPEED,  -5,    -5,    1.5);         //Forward 12 Inches
                    currentZ = 90;
                    telemetry.addData("Direction", currentZ);
                }

                if((nextX == currentX) && (nextY == (currentY - 1)) && currentZ == 90 ){  // if x doesnt change & Y decreases & direction is 90; 
                       robot.encoderDrive(robot.TURN_SPEED,    6,    -6,   1.55);         //Turn left 180 degrees to 270 degrees
                       robot.encoderDrive(robot.DRIVE_SPEED,  -5,    -5,    1.5);         //Forward 12 Inches
                       currentZ = 270;
                       telemetry.addData("Direction", currentZ);
                       //telemetry.update();
                    }
                if(nextX == (currentX - 1) && nextY == currentY && currentZ == 270){      //if x decreases & Y doesnt change & direction is 180;
                       robot.encoderDrive(robot.TURN_SPEED,   -3,     3,    1.5);         //Turn Right 90 degrees
                       robot.encoderDrive(robot.DRIVE_SPEED, -10,   -10,    1.5);         //Forward 12 Inches
                       currentZ = 0;
                       telemetry.addData("Direction", currentZ);
                       //telemetry.update();
                }
                if(nextX == (currentX - 1) && nextY == currentY && currentZ == 90){       //if x decreases & Y doesnt change & direction is 90; 
                       robot.encoderDrive(robot.TURN_SPEED,    3,    -3,   1.55);         //Turn left 90 degrees
                       robot.encoderDrive(robot.DRIVE_SPEED, -10,   -10,    1.5);         //Forward 12 Inches
                       currentZ = 0;
                       telemetry.addData("Direction", currentZ);
                       //telemetry.update();
                }
                if((nextX == currentX) && (nextY == (currentY - 1)) && currentZ == 0 ){   // if x doesnt change & Y decreases & direction is 0;  
                       robot.encoderDrive(robot.TURN_SPEED,    3,    -3,   1.55);         //Turn left 90 degrees to 270
                       robot.encoderDrive(robot.DRIVE_SPEED,  -5,    -5,    1.5);         //Forward 12 Inches
                       currentZ = 270;
                       telemetry.addData("Direction", currentZ);
                       //telemetry.update();
                    }
                if(currentX == End_X && currentY == End_Y){
                    FinalMove = i;
                    break;
                }
                
            }
        

         }
         if(currentZ == 90){
             robot.encoderDrive(robot.DRIVE_SPEED,  -5,    -5,    1.5);         //Forward 12 Inches
         }
                telemetry.update();
                sleep(10000);
}
}
