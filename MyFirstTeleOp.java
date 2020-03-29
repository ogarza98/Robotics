/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * MYFIRSTTELEOP.JAVA
 * OSCAR GARZA & HUNTER KIM
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;
import java.lang.Math;

/**
 * This file provides basic Telop driving for a Pushbot robot.
 * The code is structured as an Iterative OpMode
 *
 * This OpMode uses the common Pushbot hardware class to define the devices on the robot.
 * All device access is managed through the HardwarePushbot class.
 *
 * This particular OpMode executes a basic Tank Drive Teleop for a PushBot
 * It raises and lowers the claw using the Gampad Y and A buttons respectively.
 * It also opens and closes the claws slowly using the left and right Bumper buttons.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="TeleOp (A-4)", group="Pushbot")

public class MyFirstTeleOp extends OpMode{

    /* Declare OpMode members. */
    RobotHardware robot       = new RobotHardware();
    
    public ElapsedTime runtime  = new ElapsedTime(); // use the class created to define a Pushbot's hardware
    double timer = 0;
    /*int leftPosition, rightPosition;*/
    /*
     * Code to run ONCE when the driver hits INIT
     */
     
    @Override
    public void init() {
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);
        
        runtime.reset(); // reset timer
        
        timer = runtime.seconds(); // set timer
        // int leftPosition = 0, rightPosition = 0; //number of clicks
        
        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver");    //
        
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        double left;
        double right;
        // Run wheels in tank mode (note: The joystick goes negative when pushed forwards, so negate it)
        left = this.gamepad1.left_stick_y;
        right = this.gamepad1.right_stick_y;
        
        
        
        robot.leftMotor.setPower(left);
        robot.rightMotor.setPower(right);
        
        
        //work within the intervals
        if ((runtime.seconds() - timer) > 0.1){
            
        robot.updateOdometer();
        timer = runtime.seconds();
        
        }
        // if ((robot.dsr) > 0.01){
            
        // robot.updateOdometer();
        
        // }
        // telemetry.addData("Left Power", left);//
        // telemetry.addData("Right Power", right);//
        telemetry.addData("Delta X", robot.odometer.xi);//
        telemetry.addData("Delta Y", robot.odometer.yi);//
        telemetry.addData("Delta TH", Math.toDegrees(robot.odometer.thi));//
        telemetry.update();

        }
    

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }
}
