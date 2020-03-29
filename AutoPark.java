package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */
@Autonomous(name="Basic: Linear OpMode", group="Linear Opmode")

public class AutoPark extends LinearOpMode {

    // Declare OpMode members.
    public RobotHardware robot   = new RobotHardware(); 
//declare a time tracker runtime and a timer to track time
    public ElapsedTime runtime  = new ElapsedTime();
    double timer=0;


    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
       // initialize the timer
        runtime.reset();
        timer=runtime.seconds();

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Initialized");    //

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();
        robot.pidControler.setTarget(20,20,3.14/4); //set the target
        robot.odometer.setPosition(0,0,0);

        // run until the target is hit or out of time
        while (opModeIsActive() && robot.pidControler.targetHit==false) {

            // Setup a variable for each drive wheel to save power level for telemetry
            
        if(runtime.seconds()-timer>0.05){
             robot.updateOdometer();
             robot.updatePowerControl(); // Besides updating the Odometer, the program also update the Power Control given the current position of the robot to the target
           timer=runtime.seconds();
        }
            // Show the elapsed game time and wheel power.
        telemetry.addData("location X:",  "%.2f", robot.odometer.xi);
        telemetry.addData("location Y:",  "%.2f", robot.odometer.yi);
        telemetry.addData("location Theta:",  "%.2f", Math.toDegrees(robot.odometer.thi));
        telemetry.addData("rho :",  "%.2f", robot.pidControler.rho);
        telemetry.addData("alpha  :",  "%.2f", Math.toDegrees(robot.pidControler.alpha));
        telemetry.addData("beta  :",  "%.2f", Math.toDegrees(robot.pidControler.beta));
        telemetry.addData("controlPowerRight :",  "%.2f", robot.pidControler.controlRightPower);
        telemetry.addData("controlPowerLeft  :",  "%.2f", robot.pidControler.controlLeftPower);
       // telemetry to show the status of the pidControler

        telemetry.update();
        }
    }
}
