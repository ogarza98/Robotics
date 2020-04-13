/*A star class*/
package org.firstinspires.ftc.teamcode;

import java.util.List;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import java.util.ArrayList;
import java.util.Collections;

// The A* Class
public class AStarClass {
    public final List <Node> open;
    public final List <Node> closed;
    public final List <Node> path;
    private final int[][] maze;
    private Node now; // The current node
    private final int xstart;
    private final int ystart;
    private int xend, yend;
    private final boolean diag;
    public LinearOpMode currentOpMode; //Optional, mainly used for debugging
    
    // Constructor, the opmode is imported as a parameter so that it’s member function like telemetry can be used. This argument //is optional. 
    AStarClass(int[][] maze, int xstart, int ystart, boolean diag, LinearOpMode currentOpMode){
        this.open = new ArrayList<>();
        this.closed = new ArrayList<>();
        this.path = new ArrayList<>();
        this.maze = maze;
        this.now = new Node(null, xstart, ystart, 0, 0);
        this.xstart = xstart;
        this.ystart = ystart;
        this.diag = diag;
        this.currentOpMode = currentOpMode;
// For debugging purpose
        /*currentOpMode.telemetry.addData("maze", maze[0][0]);
        currentOpMode.telemetry.addData("maze", maze[0][1]);
        currentOpMode.telemetry.addData("maze", maze[1][0]);
        currentOpMode.telemetry.addData("maze", maze[1][1]);
        currentOpMode.telemetry.update();
        */
    }

public List<Node> findPathTo(int xend, int yend){
    this.xend = xend;
    this.yend = yend;
    this.closed.add(this.now);
    addNeighborsToOpenList();
    
    
/* This segment is for debugging, we keep it here to show you how it’s possible to take a look of what’s going on
for(int i = 0; i < this.open.size(); i++){
        currentOpMode.telemetry.addData("open Position x:", this.closed.get(i).x);
        currentOpMode.telemetry.addData("open Position y:", this.closed.get(i).y);
         }
    currentOpMode.telemetry.update();
    currentOpMode.telemetry.addData("Node added to open list:", this.now.x);
    currentOpMode.telemetry.addData("Node added to open list:", this.now.y);
    */
    
    while(this.now.x != this.xend || this.now.y != this.yend ){
        if (this.open.isEmpty()){
            return null;
    }    

    this.now = this.open.get(0);
    this.open.remove(0);
    
    this.closed.add(this.now);
    addNeighborsToOpenList();
    currentOpMode.telemetry.update();
    }
    this.path.add(0, this.now);
        
    while (this.now.x != this.xstart || this.now.y != this.ystart){
            this.now = this.now.parent;
            this.path.add(0, this.now);
}
return this.path;
}

private static boolean findNeighborInList(List <Node> array, Node node){
    for (int z = 0; z < array.size(); z++){
        if (array.get(z).x == node.x && array.get(z).y == node.y){
            return true;
        }
    }
    return false;
}

private double distance(int dx, int dy){
    if(this.diag){
        return Math.hypot(this.now.x + dx - this.xend, this.now.y + dy - this.yend);
    } 
            else {
                return Math.abs(this.now.x +dx - this.xend) + Math.abs(this.now.y + dy - this.yend);
    }
}

private void addNeighborsToOpenList(){
    Node node;
    for (int x = -1; x <= 1; x++){
        for (int y = -1; y <= 1; y++){

            if (!this.diag && x != 0 && y != 0){
                continue;
        }
        node = new Node(this.now, this.now.x + x, this.now.y + y, this.now.g, this.distance(this.now.x + x, this.now.y + y));
        
        if ((x != 0 || y != 0)
            && (this.now.x + x) >= 0 && (this.now.x + x) < this.maze.length
            && (this.now.y + y) >= 0 && (this.now.y + y) < this.maze[0].length
            && this.maze[this.now.x + x][this.now.y + y] != 100 // Note it’s [X] [Y]
            && !findNeighborInList(this.open, node) && !findNeighborInList(this.closed, node)) {
               node.g = node.parent.g + 1;
               // diagonal cost = sqrt(hor_cost^2 + vert_cost^2)
               // in this example the cost would be 12.2 instead of 11
              // if (diag && x != 0 && y != 0){
              //    node.g += 0.4;

               this.open.add(node);    
                }
            }
        }
        //Collections.sort(this.open);
    }
}
