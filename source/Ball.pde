class ball
{
  float x,y,xSpeed,ySpeed,i;
  
  ball(float _x,float _y,float _xSpeed,float _ySpeed,float _i)
  {
    x = _x;
    y = _y;
    xSpeed = _xSpeed;
    ySpeed = _ySpeed;
    i = _i;
  }
  
  void Move()
  {
    if(x > width)
    {
      leftWon = true;
      p1++;
      ResetBall();
      leftPad.y = height/2-padTall/2;
      rightPad.y = height/2-padTall/2;
    }
    else if(x < 0)
    {
      leftWon = false;
      p2++;
      ResetBall();
      leftPad.y = height/2-padTall/2;
      rightPad.y = height/2-padTall/2;
    }
    float yAdd;
    if((x + xSpeed + ballSize/2 >= rightPad.x) && (y > rightPad.y && y < rightPad.y + rightPad.tall))
    {
      xSpeed *= -1;
      
      yAdd = y - rightPad.y - padTall/2;
      yAdd /= padTall/2;
      yAdd *= maxAddS;
      ySpeed += yAdd * 1.2;
      xSpeed -= .3f;
    }
    else if(x + xSpeed - ballSize/2 <= padWide && (y > leftPad.y && y < leftPad.y + leftPad.tall))
    {
      xSpeed *= -1;
      
      yAdd = y - leftPad.y - padTall/2;
      yAdd /= padTall/2;
      yAdd *= maxAddS;
      ySpeed += yAdd * 1.2;
      xSpeed += .3f;
    }
    
    /*   SIMPLE BOUNCE Y-AXIS
    if(y+ballSize/2 >= height || y-ballSize/2 <= 0)
    {                                                
      ySpeed *= -1;
    }
    */
    
    //   COMPLEX BOUNCE Y-AXIS
    // Provides more accurate ball physics.
    if(y + ballSize/2 + ySpeed >= height) // bottom
    {
      ySpeed *= -1;
      y = (2 * height) - y + ySpeed - ballSize;
    }
    else if(y + ySpeed - ballSize/2 <= 0) // top
    {
      ySpeed *= -1;
      y = -(y - ySpeed) + ballSize;
    }

    i += 0.04f; // Increment with increase of angle
    i %= 2*PI;  // Stop at 2 PI and restart increment
    
    x += xSpeed;
    
    // y speed style is based on user's choice in options
    if(classic)
    {
      y += ySpeed;  //Straight line
    }
    else if(sineWave)
    {
      y += sin(i) * ySpeed;  //Smooth sine curve
    }
    else if(stairCase)
    {
      y += abs(sq(sin(i))) * ySpeed; //StairCase curve
    }
  }
  
  void show()
  {
    ellipse(x,y,ballSize,ballSize);
  }
}
