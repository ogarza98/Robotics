/*OpMode*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.Hardware;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import java.util.List;



@TeleOp(name="Primary Active", group="Linear Opmode")

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
    private ElapsedTime runtime = new ElapsedTime();
    
    public RobotHardware robot = new RobotHardware(this);

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

         currentZ = StartDirection;
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
            encoderDriveWrapper(currentX, currentY, nextX, nextY, currentZ);

          }
         
                telemetry.update();
                sleep(10000);
        
}

public int determineDegreeToTurn(int currentX, int currentY, int nextX, int nextY, int currentDegree){

    int degreeToTurn = 0;
    int targetDegree = 0;


    if(currentY==nextY)
    {

        if(nextX>currentX) {
            targetDegree=270;
        }

        else {
            targetDegree=90;
        }
        
    }

    else if (nextX==currentX) 
    {
         if(nextY>currentY) {
            targetDegree=180;
        }

         else {
            targetDegree=0;
        }
         
    }

    degreeToTurn = (targetDegree-currentDegree);


    if (degreeToTurn > 180) {
        degreeToTurn -= 360;
    }
    if(degreeToTurn < -180) {
        degreeToTurn += 360;
    }

    return degreeToTurn;

    }

public void moveRobot(int degreeToTurn, int currentDegree){


        switch (degreeToTurn)
        
        {
            case 0:
            robot.encoderDrive(robot.DRIVE_SPEED, -10.0, -10.0, 1.5);
            currentZ = currentDegree;
            telemetry.addData("Direction", currentZ);
            break;


            case 90:
            robot.encoderDrive(robot.TURN_SPEED, -5.5, 5.5, 1.75);
            robot.encoderDrive(robot.DRIVE_SPEED, -10.0, -10.0, 1.5);
            currentZ = currentDegree + 90;
            telemetry.addData("Direction", currentZ);
            break;


            case -90:
            robot.encoderDrive(robot.TURN_SPEED, 5.5, -5.5, 1.75);
            robot.encoderDrive(robot.DRIVE_SPEED, -10.0, -10.0, 1.5);
            currentZ = currentDegree - 90;
            telemetry.addData("Direction", currentZ);
            break;


            case 180:
            robot.encoderDrive(robot.TURN_SPEED, -11.0, 11.0, 1.75);
            robot.encoderDrive(robot.DRIVE_SPEED, -10.0, -10.0, 1.5);
            currentZ = currentDegree + 180;
            telemetry.addData("Direction", currentZ);
            break;


            case -180:
            robot.encoderDrive(robot.TURN_SPEED, 11.0, -11.0, 1.75);
            robot.encoderDrive(robot.DRIVE_SPEED, -10.0, -10.0, 1.5);
            currentZ = currentDegree - 180;
            telemetry.addData("Direction", currentZ);
            break;

            default:
            telemetry.addData("Error", "Error");


        }
        if(currentZ >= 360)
        {
            currentZ -= 360;
        }
        
        if(currentZ < 0)
        {
            currentZ += 360;
        }
        
        telemetry.update();

}
public void encoderDriveWrapper(int currentX, int currentY, int nextX, int nextY, int currentDegree)
    {
        int degreeToTurn = determineDegreeToTurn(currentX, currentY, nextX, nextY, currentDegree);

        moveRobot(degreeToTurn, currentDegree);
        
    }


}


