package org.firstinspires.ftc.teamcode;
// Here, we construct a new class called PIDControler. It has a set of fields and functions
public class PIDControler {
    public double targetX;
    public double targetY;
    public double targetTheta;
    public double controlRightPower;
    public double controlLeftPower;
    public boolean targetHit;
    public double rho ;
    public double alpha ;
    public double beta;
    
    //The constructor
    public void PIDControler(){        
}
// The setter function
    public void setTarget(double tx, double ty, double ttheta){
        targetX=tx;
        targetY=ty;
        targetTheta=ttheta;
        targetHit=false;
    }
    //The main function of the PIDcontroler for calculating the power that need to be applied to the left and right wheel
public void calculateControlPower(double x, double y, double theta,double kappaRho, double kappaAlpha, double kappaBeta,      double wheelRadius, double wheelDistance ){
//Calculate the error to the target
         rho = Math.sqrt((targetX-x)*(targetX-x)+(targetY-y)*(targetY-y));        
         alpha = -theta+Math.atan2(targetY-x,targetX-x);
         beta = -theta - alpha;
 //Stop the robot if the target is hit
        if( Math.abs(rho)<5 ){
            controlRightPower=0;
            controlLeftPower=0;
            targetHit=true;
        }
        else{ // If the target is not hit
            controlRightPower=-(kappaRho*rho/wheelRadius + (kappaAlpha*alpha+kappaBeta*beta)*wheelDistance/wheelRadius);
            controlLeftPower=-(kappaRho*rho/wheelRadius - (kappaAlpha*alpha+kappaBeta*beta)*wheelDistance/wheelRadius);
        //have to ensure that the power is within a range
            double maxPower=Math.max(Math.abs(controlRightPower),Math.abs(controlLeftPower));
            if (maxPower>1){
                controlRightPower = controlRightPower/maxPower;
                controlLeftPower = controlLeftPower/maxPower;
            }
    // Set  a lower limit on the power. This is not very good here in the sense that it’s not proportional to the controls, you may modify this
            if(Math.abs(controlRightPower)<0.2){
                if(controlRightPower>0) controlRightPower=0.2;
                else controlRightPower=-0.2;
            }
            if(Math.abs(controlLeftPower)<0.2){
                if(controlLeftPower>0) controlLeftPower=0.2;
                else controlLeftPower=-0.2;
            }
        }
        
    }
}
