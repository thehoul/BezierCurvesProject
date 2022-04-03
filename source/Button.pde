class Button {
  
  final float x;
  final float y;
  final float width;
  final float height;
  final color col;
  final float radius;
  
  String txt = "";
  boolean enabled;
 
  Button(float x, float y, float w, float h, color col, float radius){
    this.x = x;
    this.y = y;
    this.width = w;
    this.height = h;
    this.col = col;
    this.radius = radius;
    this.enabled = true;
  }
  
  void setText(String newText){
   this.txt = newText; 
  }
  
  String getText(){
   return txt; 
  }
  
  boolean isMouseOver(){
    if(!enabled){
     return false; 
    }
    if(mouseX <= x + width/2.0 && mouseX >= x - width/2.0){
     if(mouseY <= y + height/2.0 && mouseY >= y - height/2.0){
      return true; 
     }
    }
   // TODO : test is the mouse is over the button 
   return false;
  }
  
  void setEnabled(boolean enabled){
     this.enabled = enabled; 
  }
  
  void display(){
    rectMode(CENTER);
    fill(col);
    rect(x, y, width, height, radius);
    textAlign(CENTER, CENTER);
    fill(255);
    textSize(30);
    text(txt, x, y);
  }
}
