package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import java.util.List;



@TeleOp(name="AStartest 2", group="Linear Opmode")

public class PrimaryActive2 extends LinearOpMode {
    
    int Start_X = 2;
    int Start_Y = 0;
    int End_X = 1;
    int End_Y = 1;
    int currentX;
    int currentY;
    int currentZ;
    int nextX;
    int nextY;
    int nextZ;
 //   int StartDirection = 0; //INPUT START DIRECTION (0, 90, 180, 270)
//    int FinalMove;
    
    double timer;
    double targetAngle;
    // Declare OpMode members.
    private ElapsedTime runtime             = new ElapsedTime();
    
    RobotHardware robot = new RobotHardware(this);

    @Override
    public void runOpMode() {
        
        
     /*   int[][]maze = 
        {
        {0,    0,      0,    0},
        {0,    0,      0,    0},
        {0,  100,    100,    0},
        {0,    0,      0,    0},
        {0,    0,      0,    0}
        };
        */
         int[][]maze = 
        {
        {0,    0},
        {100,    0},
        {0,    0}
    
        };
        double[][]mazeCoordinatesX=
        {
        {0,    12},
        {0,   12},
        {0,    12}
    
        };
         double[][]mazeCoordinatesY=
        {
        {24,    24},
        {12,    12},
        {0,    0}
    
        };
    

        
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        //telemetry.addData("Status", "Resetting Encoders");    //
        //telemetry.update();

        timer=0;
       
        waitForStart();
        
         AStarClass as = new AStarClass(maze, Start_X, Start_Y, false, this);
         
         List<Node> path = as.findPathTo(End_X, End_Y);
         robot.odometer.setPosition(mazeCoordinatesX[Start_X][Start_Y],mazeCoordinatesY[Start_X][Start_Y],0);
         while(opModeIsActive() && robot.pidControler.targetHit==false){
             
         
         for(int i = 0; i < path.size()-1; i++){
             if(i<path.size()-1){
                    currentX = path.get(i).x;
                    currentY = path.get(i).y;
                    nextX    = path.get(i+1).x;
                    nextY    = path.get(i+1).y;
                    targetAngle = Math.atan2(mazeCoordinatesY[nextX][nextY]-mazeCoordinatesY[currentX][currentY],mazeCoordinatesX[nextX][nextY]-mazeCoordinatesX[currentX][currentY]);
             }          
            telemetry.addData("Maze Position x:", nextX);
            telemetry.addData("Maze Position y:", nextY);
            telemetry.addData("TargetAngle:", targetAngle);

            
             robot.pidControler.setTarget(mazeCoordinatesX[nextX][nextY],mazeCoordinatesY[nextX][nextY],targetAngle);

        robot.pidControler.targetHit=false;
        // run until the end of the match (driver presses STOP)
        while (opModeIsActive() && robot.pidControler.targetHit==false) {

            // Setup a variable for each drive wheel to save power level for telemetry
            
           if(runtime.seconds()-timer>0.05){
               robot.updateOdometer();
               robot.updatePowerControl();
               timer=runtime.seconds();
               telemetry.update();
           }
           
        } 
    }
    telemetry.update();
    //sleep(5000);
 
        
        
}
}
}