package org.firstinspires.ftc.teamcode;

import java.lang.Math;


public class Odometer {
    
   /* Constructor */
    public Odometer(){

    }
    
    public double xi,yi,thi;
    private double delta_xi, delta_yi, delta_thi;
    
    public void setPosition(double x, double y, double th){
        
        xi = x;
        yi = y;
        thi = th;
        
    }
    
    public void updatePosition(double dsr, double dsl, double L){
        
        double delta_s = (dsr + dsl) / 2;
        
        delta_thi = ((dsr - dsl) / (2*L)); //-0.114
        // if (delta_thi != 0){
        //     delta_thi = delta_thi-0.007;
        // }
        delta_xi = -(delta_s * Math.cos(thi + (delta_thi/2)));
        
        delta_yi = -(delta_s * Math.sin(thi + (delta_thi/2)));
        
        xi = xi + delta_xi;
        yi = yi + delta_yi;
        thi = thi + delta_thi;
        
        
    }


    // todo: write your code here
}
