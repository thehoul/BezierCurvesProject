class Line{
  final Point start;
  final Point end;
  
  Line(Point start, Point end){
    this.start = start;
    this.end = end;
  }
  
  
  void display(){
   stroke(0);
   strokeWeight(2);
   line(start.getX(), start.getY(), end.getX(), end.getY());
   noStroke();
  }
  
}
