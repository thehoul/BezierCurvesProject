State state;
ArrayList<Point> ctrlPoints;
ArrayList<Line> lines;
ArrayList<Point> animPoints;
ArrayList<Point> bezierCurve;

// ---------- BUTTONS ---------- // 
Button drawB;
Button resetB;
float padding = 5.0;
int nbButtons = 2;
float buttonHeight = 60.0;
// ---------- BUTTONS ---------- //

color niceRed = color(255, 0, 65);

int nbFrames = 20;
int frame = 0;

float t = 0.0;
 
 void setup(){
   size(1000, 1000);
   rectMode(CENTER);
   noStroke();
   state = State.Points;
   ctrlPoints = new ArrayList<Point>();
   bezierCurve = new ArrayList<Point>();
   lines = new ArrayList<Line>();
   animPoints = new ArrayList<Point>();
   makeGUI();
   
 }
 
 void draw(){
   background(100,100,100);
   
   switch(state){
     case Points:
       break;
     case Draw:
       
       getBezierLines();
       t += 0.005;
       if(t >= 1.0){
        t = 0.0;
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
    text("Click anywhere on the canvas to place a control point", width/2, height/2 - 20.0); 
    text("then click Draw to start the animation", width/2, height/2 + 20.0); 
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
 
 void makeGUI(){
   float buttonWidth = width / nbButtons - (nbButtons) * padding;
   float xOffset = padding;
   drawB = new Button(xOffset + buttonWidth / 2.0, buttonHeight / 2.0 + padding, buttonWidth, buttonHeight, niceRed, 6.0);
   drawB.setText("Draw");
   xOffset += padding + buttonWidth;
   resetB = new Button(xOffset + buttonWidth / 2.0 , buttonHeight / 2.0 + padding, buttonWidth, buttonHeight, niceRed, 6.0); 
   resetB.setText("Reset");
 }
 
 void mouseReleased(){
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
 
 void getBezierLines(){
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
      
      Point linePoint = lastPoint.time(1.0 -t).plus(newPoint.time(t)); 
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
