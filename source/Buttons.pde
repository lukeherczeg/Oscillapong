class buttons
{
  float x,y,xWidth,yHeight,c,d,f; //c is color
  
  buttons(float _x,float _y,float _xWidth,float _yHeight,float _c,float _d,float _f)
  {
    x = _x;
    y = _y;
    xWidth = _xWidth;
    yHeight = _yHeight;
    c = _c;
    d = _d;
    f = _f;
  }
  
  
  void colorChange()
  {
    if(mouseX >= (x) && mouseX <= (x + xWidth) && mouseY >= (y) && mouseY <= (y + yHeight))
    {
      c = 0;
      d = 0;
      f = 0;
      shown = true;
    }
    else
    {
      c = 255;
      d = 255;
      f = 255;
    }
}

    
    boolean rectStart()
    {
      if((mouseX >= (x) && mouseX <= (x + xWidth) && mouseY >= (y) && mouseY <= (y + yHeight)) && shown)
      {
        return true;
      }
      else
      {
        return false;
      }
    }
    
    void show()
    {
      fill(c,d,f);
      rect(x,y,xWidth,yHeight);
      stroke(255);
      //strokeWeight();
    }
    
    void buttonText(String text, color a, color b, color c, float size)
    {
      if(mouseX >= (x) && mouseX <= (x + xWidth) && mouseY >= (y) && mouseY <= (y + yHeight))
      {
        a = 255;
        b = 255;
        c = 255;
      }
      else
      {
        a = 0;
        b = 0;
        c = 0;
      }
      fill(a, b, c);
      textSize(size);
      text(text, x + (xWidth/2), y + (yHeight/1.3));
    }
  }
  
