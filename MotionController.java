package org.firstinspires.ftc.teamcode;

import java.lang.Math;


public class MotionController {
    
   /* Constructor */
    public MotionController(){

    }
    
    public double xi,yi,thi;
    private double delta_xi, delta_yi, delta_thi;
    
    public void setPosition(double x, double y, double th){
        
        xi = x;
        yi = y;
        th = th
        
    }
    
    public void updatePosition(double dx, double dy, double dth){
        
        solve_ro();
        solve_alpha();
        solve_beta();
        
    }
    
    public voide solve_ro(double x_target, double, y_target){
        delta_x = x_target - xi;
        delta_y = y_target
    }


    // todo: write your code here
}
