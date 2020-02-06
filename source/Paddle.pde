class paddle
{
  public float tall, wide, x, y;
  paddle(float _x,float _y,float _tall,float _wide)
  {
    x = _x;
    y = _y;
    tall = _tall;
    wide = _wide;
  }
  
  void move(float c)
  {
    if(y + c + tall <= height + (height * 0.01302083333333f) && y + c >= 0)
    {
      y += c;
    }
  }
  
  void show()
  {
    rect(x,y,wide,tall);
  }
}
