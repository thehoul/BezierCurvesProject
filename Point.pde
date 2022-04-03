class Point {
  final float x;
  final float y;
  final static float circleRadius = 10.0;
  
  Point(float x, float y){
    this.x = x;
    this.y = y;
  }
  
  void display(){
    fill(niceRed);
    stroke(0); 
    strokeWeight(2);
    circle(x, y, circleRadius);
    noStroke();
  }
  
  float getX(){
    return x; 
  }
  float getY(){
   return y; 
  }
  
  Point time(float s){
   return new Point(x * s, y * s); 
  }
  
  Point plus(Point that){
   return new Point(this.x + that.x, this.y + that.y); 
  }
  
}
