/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * ROBOTHARDWARE.JAVA
 * OSCAR GARZA & HUNTER KIM
 *
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.lang.Math;

/**
 * This is NOT an opmode.
 *
 * This class can be used to define all the specific hardware for a single robot.
 * In this case that robot is a Pushbot.
 * See PushbotTeleopTank_Iterative and others classes starting with "Pushbot" for usage examples.
 *
 * This hardware class assumes the following device names have been configured on the robot:
 * Note:  All names are lower case and some have single spaces between words.
 *
 * Motor channel:  Left  drive motor:        "left_drive"
 * Motor channel:  Right drive motor:        "right_drive"
 * Motor channel:  Manipulator drive motor:  "left_arm"
 * Servo channel:  Servo to open left claw:  "left_hand"
 * Servo channel:  Servo to open right claw: "right_hand"
 */
public class RobotHardware
{
    /* Public OpMode members. */
    public DcMotor  leftMotor   = null;
    public DcMotor  rightMotor  = null;
    public double dsr;
    public double dsl;
    int leftPosition, rightPosition;
    public LinearOpMode currentOpMode;
    static final double     COUNTS_PER_MOTOR_REV    = 288 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 3.5 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
                                                      (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = .5;
    static final double     TURN_SPEED              = .5;
    static final double     WHEEL_DISTANCE          = 3;

    static final double     KPPA_RHO =0.038;
    static final double     KPPA_ALPHA = 0.15;
    static final double     KPPA_BETA =-0.1;
    //These are parameters for controlling the robot. You may fine tune them

    
    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime runtime  = new ElapsedTime();
    public Odometer odometer = new Odometer();
    public PIDControler pidControler = new PIDControler(); // install the new PID controller by declaring it as a new asset
    //public MotionController motion_controller = new MotionController();

    /* Constructor */
    public RobotHardware(LinearOpMode opMode){
        currentOpMode = opMode;

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        leftMotor  = hwMap.get(DcMotor.class, "leftMotor");
        rightMotor = hwMap.get(DcMotor.class, "rightMotor");
        leftMotor.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        rightMotor.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors

        // Set all motors to zero power
        leftMotor.setPower(0);
        rightMotor.setPower(0);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.

        // leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        // rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        
        int leftPosition = leftMotor.getCurrentPosition(), rightPosition = rightMotor.getCurrentPosition(); //number of clicks
        
        odometer.setPosition(0,0,0);
        //motion_controller.setPostion(20, 20, 3.14/4);
        
        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    
        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // Define and initialize ALL installed servos.
        
    }
    
    
    public void encoderDrive(double speed,
                             double leftInches, double rightInches,
                             double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (currentOpMode.opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = leftMotor.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newRightTarget = rightMotor.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            leftMotor.setTargetPosition(newLeftTarget);
            rightMotor.setTargetPosition(newRightTarget);

            // Turn On RUN_TO_POSITION
            leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            leftMotor.setPower(Math.abs(speed));
            rightMotor.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (currentOpMode.opModeIsActive() &&
                  (runtime.seconds() < timeoutS) &&
                  (leftMotor.isBusy() && rightMotor.isBusy())) {

                // Display it for the driver.
                //currentOpMode.telemetry.addData("Path1",  "Running to LT: %7d | RT: %7d", newLeftTarget,  newRightTarget);
                //currentOpMode.telemetry.addData("Path2",  "Running at LP: %7d | RP: %7d", leftMotor.getCurrentPosition(), rightMotor.getCurrentPosition());
                                            
                currentOpMode.telemetry.update();
            }

            // Stop all motion;
            leftMotor.setPower(0);
            rightMotor.setPower(0);

            // Turn off RUN_TO_POSITION
            leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
    }
    
    // public void motionControl(double x_target, double y_target, double th_target) {
    //     int newLeftTarget;
    //     int newRightTarget;

    //     // Ensure that the opmode is still active
    //     if (currentOpMode.opModeIsActive()) {

    //         // Determine new target position, and pass to motor controller
    //         newLeftTarget = leftMotor.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
    //         newRightTarget = rightMotor.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
    //         leftMotor.setTargetPosition(newLeftTarget);
    //         rightMotor.setTargetPosition(newRightTarget);

    //         // Turn On RUN_TO_POSITION
    //         leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    //         rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    //         // reset the timeout time and start motion.
    //         runtime.reset();
    //         leftMotor.setPower(Math.abs(speed));
    //         rightMotor.setPower(Math.abs(speed));

    //         // keep looping while we are still active, and there is time left, and both motors are running.
    //         // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
    //         // its target position, the motion will stop.  This is "safer" in the event that the robot will
    //         // always end the motion as soon as possible.
    //         // However, if you require that BOTH motors have finished their moves before the robot continues
    //         // onto the next step, use (isBusy() || isBusy()) in the loop test.
    //         while (currentOpMode.opModeIsActive() &&
    //               (runtime.seconds() < timeoutS) &&
    //               (leftMotor.isBusy() && rightMotor.isBusy())) {

    //             // Display it for the driver.
    //             currentOpMode.telemetry.addData("Path1",  "Running to LT: %7d | RT: %7d", newLeftTarget,  newRightTarget);
    //             currentOpMode.telemetry.addData("Path2",  "Running at LP: %7d | RP: %7d", leftMotor.getCurrentPosition(), rightMotor.getCurrentPosition());
                                            
    //             currentOpMode.telemetry.update();
    //         }

    //         // Stop all motion;
    //         leftMotor.setPower(0);
    //         rightMotor.setPower(0);

    //         // Turn off RUN_TO_POSITION
    //         leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    //         rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    //         //  sleep(250);   // optional pause after each move
    //     }
    // }


    public void updateOdometer(){
        
        double dsl = find_sl(leftMotor.getCurrentPosition() - leftPosition);
        
        double dsr = find_sr(rightMotor.getCurrentPosition() - rightPosition);
        
        odometer.updatePosition(dsl, dsr, WHEEL_DISTANCE);
        
        leftPosition = leftMotor.getCurrentPosition();
        
        rightPosition = rightMotor.getCurrentPosition();
        
    }

    //Added interface for the PID controller on the robot
    public void updatePowerControl(){
        pidControler.calculateControlPower(odometer.xi, odometer.yi, odometer.thi,KPPA_RHO, KPPA_ALPHA, KPPA_BETA, WHEEL_DIAMETER_INCHES/2, WHEEL_DISTANCE);
    
        leftMotor.setPower(pidControler.controlLeftPower);
        rightMotor.setPower(pidControler.controlRightPower);
    } 

    
      // fill in the math (clicks = the interval of clicks (ticks))
    public double find_sr(int clicks){
        
        return 3.1415*3.5*clicks/288; // INCHES
            
        }
        
    public double find_sl(int clicks){
        
        return 3.1415*3.5*clicks/288;

        
        }
    
    
 }

