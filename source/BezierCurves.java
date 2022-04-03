import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class BezierCurves extends PApplet {

State state;
ArrayList<Point> ctrlPoints;
ArrayList<Line> lines;
ArrayList<Point> animPoints;
ArrayList<Point> bezierCurve;

// ---------- BUTTONS ---------- // 
Button drawB;
Button resetB;
float padding = 5.0f;
int nbButtons = 2;
float buttonHeight = 60.0f;
// ---------- BUTTONS ---------- //

int niceRed = color(255, 0, 65);

int nbFrames = 20;
int frame = 0;

float t = 0.0f;
 
 public void setup(){
   
   rectMode(CENTER);
   noStroke();
   state = State.Points;
   ctrlPoints = new ArrayList<Point>();
   bezierCurve = new ArrayList<Point>();
   lines = new ArrayList<Line>();
   animPoints = new ArrayList<Point>();
   makeGUI();
   
 }
 
 public void draw(){
   background(100,100,100);
   
   switch(state){
     case Points:
       break;
     case Draw:
       
       getBezierLines();
       t += 0.005f;
       if(t >= 1.0f){
        t = 0.0f;
        bezierCurve.clear();
       }
       
       
       break;
     default:
       break;
   }
   noFill();
   stroke(255, 0, 0);
   strokeWeight(5);
   beginShape();
   for(Point p : bezierCurve){
    vertex(p.getX(), p.getY()); 
   }
   endShape();
   noStroke();
   
   for(Line l : lines){
    l.display(); 
   }
   for(Point p : ctrlPoints){
    p.display(); 
   }
   for(Point p : animPoints){
    p.display(); 
   }
   drawB.display();
   resetB.display();
   
   if(ctrlPoints.size() == 0){
    text("Click anywhere on the canvas to place a control point", width/2, height/2 - 20.0f); 
    text("then click Draw to start the animation", width/2, height/2 + 20.0f); 
   }
   
   /* // Uncomment to save frames to do a gif (only does it the first iteration)
   if(frame <= nbFrames){
     if(t >=  (1.0 / float(nbFrames) * frame)){
      saveFrame("gif/frame_" + frame + ".png");
      frame++;
     }    
   }
   */
   
 }
 
 public void makeGUI(){
   float buttonWidth = width / nbButtons - (nbButtons) * padding;
   float xOffset = padding;
   drawB = new Button(xOffset + buttonWidth / 2.0f, buttonHeight / 2.0f + padding, buttonWidth, buttonHeight, niceRed, 6.0f);
   drawB.setText("Draw");
   xOffset += padding + buttonWidth;
   resetB = new Button(xOffset + buttonWidth / 2.0f , buttonHeight / 2.0f + padding, buttonWidth, buttonHeight, niceRed, 6.0f); 
   resetB.setText("Reset");
 }
 
 public void mouseReleased(){
   if(drawB.isMouseOver()){
     if(ctrlPoints.size() < 2){
      println("Can't have less than 2 points"); 
     } else {
       state = State.Draw;
       drawB.setEnabled(false);
       frame = 0;
       t = 0;  
     }
   } else if(resetB.isMouseOver()){
     ctrlPoints.clear();
     bezierCurve.clear();
     drawB.setEnabled(true);
     state = State.Points;
     animPoints.clear();
     lines.clear();
     bezierCurve.clear();
   } else{
     ctrlPoints.add(new Point(mouseX, mouseY));
   }
 }
 
 public void getBezierLines(){
   lines.clear();
   animPoints.clear();
   
   ArrayList<Point> subPoints = new ArrayList<Point>(ctrlPoints);
   ArrayList<Point> newSubPoints;
   
   while(subPoints.size() > 1){
     Point lastPoint = subPoints.get(0);
     newSubPoints = new ArrayList<Point>();
     for(int i = 1; i < subPoints.size(); i++){
      Point newPoint = subPoints.get(i);
      stroke(0);
      strokeWeight(2);
      lines.add(new Line(lastPoint, newPoint));
      
      Point linePoint = lastPoint.time(1.0f -t).plus(newPoint.time(t)); 
      animPoints.add(linePoint);
      newSubPoints.add(linePoint);
      lastPoint = newPoint;
     } 
     subPoints = newSubPoints;
   }
   bezierCurve.add(subPoints.get(0));
   
   noStroke();
 }
 
 enum State {
   Points, Draw
 };
class Button {
  
  final float x;
  final float y;
  final float width;
  final float height;
  final int col;
  final float radius;
  
  String txt = "";
  boolean enabled;
 
  Button(float x, float y, float w, float h, int col, float radius){
    this.x = x;
    this.y = y;
    this.width = w;
    this.height = h;
    this.col = col;
    this.radius = radius;
    this.enabled = true;
  }
  
  public void setText(String newText){
   this.txt = newText; 
  }
  
  public String getText(){
   return txt; 
  }
  
  public boolean isMouseOver(){
    if(!enabled){
     return false; 
    }
    if(mouseX <= x + width/2.0f && mouseX >= x - width/2.0f){
     if(mouseY <= y + height/2.0f && mouseY >= y - height/2.0f){
      return true; 
     }
    }
   // TODO : test is the mouse is over the button 
   return false;
  }
  
  public void setEnabled(boolean enabled){
     this.enabled = enabled; 
  }
  
  public void display(){
    rectMode(CENTER);
    fill(col);
    rect(x, y, width, height, radius);
    textAlign(CENTER, CENTER);
    fill(255);
    textSize(30);
    text(txt, x, y);
  }
}
class Line{
  final Point start;
  final Point end;
  
  Line(Point start, Point end){
    this.start = start;
    this.end = end;
  }
  
  
  public void display(){
   stroke(0);
   strokeWeight(2);
   line(start.getX(), start.getY(), end.getX(), end.getY());
   noStroke();
  }
  
}
class Point {
  final float x;
  final float y;
  final static float circleRadius = 10.0f;
  
  Point(float x, float y){
    this.x = x;
    this.y = y;
  }
  
  public void display(){
    fill(niceRed);
    stroke(0); 
    strokeWeight(2);
    circle(x, y, circleRadius);
    noStroke();
  }
  
  public float getX(){
    return x; 
  }
  public float getY(){
   return y; 
  }
  
  public Point time(float s){
   return new Point(x * s, y * s); 
  }
  
  public Point plus(Point that){
   return new Point(this.x + that.x, this.y + that.y); 
  }
  
}
  public void settings() {  size(1000, 1000); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "BezierCurves" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
