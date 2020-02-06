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

public class Oscillapong extends PApplet {

//float ballSpeed = 8;          //Fullscreen
//float scrollSpeed = 10;
float ballSpeed = 6;        //1000/1000
float scrollSpeed = 10;
float ballSize = 15;
float padWide = width * .15f;
float padTall = height;
float theta;
PFont titlefont;
PFont buttonsfont;

float maxYS = 15;
float maxAddS = 5;

int p1,p2;
int endScore = 5;
float buttonTextSize = 40;

boolean[] keys = new boolean[9];
boolean gameIsOver = false, leftWon = false, shown = false, multiPlayer = true, AIMenu = false;
boolean classic = false, sineWave = true, stairCase = false, physicsMenu = false, canStart = false, p1MouseControl = false, p2MouseControl = false, p1MouseControlMenu = false, p2MouseControlMenu = false;
boolean hard, medium, easy;

buttons button1, button2, button3, button4, button5, button6, button7, button8, button9, button10, button11, button12, button13, button14, button15, button16, button17, button18, button19, button20;

ball theBall;
paddle leftPad;
paddle rightPad;

public void setup()
{
  //fullScreen();
  
  background(0);
  frameRate(1000);
  fill(255);
  noStroke();
  textSize(60);
  textAlign(CENTER);
  timer();
  
  
  if(!p1MouseControl)
    leftPad = new paddle(0,height/2-padTall/2,padTall,padWide);      //Player 1 Paddle KEYS
  
  if(multiPlayer && !p2MouseControl)
    rightPad = new paddle(width-padWide,height/2-padTall/2,padTall,padWide);  //Player 2 Paddle KEYS    
  
  ResetBall();
  p1 = 0;
  p2 = 0;
 
  titlefont = loadFont("Candara-Bold-200.vlw");
  buttonsfont = loadFont("CenturyGothic-Bold-64.vlw");
}

public void draw()
{
  scene1();
  scene2();
  scene3();
}

// CODE FOR INTERACTION

public void keysPressed()
{
  if(keys[0] || keys[4])
  {
    leftPad.move(-scrollSpeed);
  }
  else if(keys[1] || keys[5])
  {
    leftPad.move(scrollSpeed);
  }
  
  if(keys[2] || keys[6])
  {
    rightPad.move(-scrollSpeed);
  }
  else if(keys[3] || keys[7])
  {
    rightPad.move(scrollSpeed);
  }
 
}

public void keyPressed()
{
  if(key == 'w') keys[0] = true;
  if(key == 's') keys[1] = true;
  if(key == 'i') keys[2] = true;
  if(key == 'k') keys[3] = true;
  if(key == 'W') keys[4] = true;
  if(key == 'S') keys[5] = true;
  if(key == 'I') keys[6] = true;
  if(key == 'K') keys[7] = true;
  if(key == ' ') keys[8] = true;
}

public void keyReleased()
{
  if(key == 'w') keys[0] = false;
  if(key == 's') keys[1] = false;
  if(key == 'i') keys[2] = false;
  if(key == 'k') keys[3] = false;
  if(key == 'W') keys[4] = false;
  if(key == 'S') keys[5] = false;
  if(key == 'I') keys[6] = false;
  if(key == 'K') keys[7] = false;
  if(key == ' ') keys[8] = false;
}


public void ResetBall()
{
  float xS,yS;
  yS = random(ballSpeed/2);
  if(!leftWon)
  {
    xS = ballSpeed;
  }
  else
  {
    xS = -ballSpeed;
  }
  
  if(p1 == endScore || p2 == endScore)
  {
    xS = 0;
    yS = 0;
    theBall = new ball(width/2,height/2,xS,yS,0);
  }
  else
  {
    theBall = new ball(width/2,height/2,xS,yS,0);
  }
  
  if (keys[8])
  {
    p1 = 0;
    p2 = 0;
    xS = ballSpeed;
    theBall = new ball(width/2,height/2,xS,yS,0);
  }
}
// CODE TO END THE GAME
public void scores()
{
  fill(0);
  textSize(60);
  text(p1, width * .25f, 100);
  fill(0,255,255);
  text(p2, width * .75f, 100);
}


public void gameOver()
{
  if (p1 == endScore)
  {
    startCount = false;
    gameOverText("P1 Wins!", 0, 0, 0);
    gameIsOver = true;
  }
  else if (p2 == endScore)
  {
    startCount = false;
    gameOverText("P2 Wins!", 0,255,255);
    gameIsOver = true;
  } 
}

public void gameOverText(String text, int c, int b, int a)
{
  fill(c, b, a);
  textSize(60);
  text("GAME!", width/2, height/4);
  text(text, width/2, height/3);
  text("Hit space to play again!", width/2, height/1.2f); 
  
  
  
  button3 = new buttons(width/2 - 300,height/1.15f,600,height * .05f,255,255,255);
  button3.colorChange();
  button3.show();
  textFont(buttonsfont);
  button3.buttonText("Options",0,0,0,buttonTextSize);
  
  button4 = new buttons(width/2 - 300,height/1.07f,600,height * .05f,255,255,255);
  button4.colorChange();
  button4.show();
  textFont(buttonsfont);
  button4.buttonText("Back",0,0,0,buttonTextSize);
  
  if(mousePressed && button3.rectStart())
  {
    scene = 2;
  }
  else if(mousePressed && button4.rectStart())
  {
    scene = 0;
  }
    
  if (keys[8] || scene != 1)
  {  
    timer();
    p1 = 0;
    p2 = 0;
    ResetBall();
    leftPad.y = height/2-padTall/2;
    rightPad.y = height/2-padTall/2;
    canStart = false;
  }
}
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
  
  public void Move()
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
      ySpeed += yAdd * 1.2f;
      xSpeed -= .3f;
    }
    else if(x + xSpeed - ballSize/2 <= padWide && (y > leftPad.y && y < leftPad.y + leftPad.tall))
    {
      xSpeed *= -1;
      
      yAdd = y - leftPad.y - padTall/2;
      yAdd /= padTall/2;
      yAdd *= maxAddS;
      ySpeed += yAdd * 1.2f;
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
  
  public void show()
  {
    ellipse(x,y,ballSize,ballSize);
  }
}
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
  
  
  public void colorChange()
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

    
    public boolean rectStart()
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
    
    public void show()
    {
      fill(c,d,f);
      rect(x,y,xWidth,yHeight);
      stroke(255);
      //strokeWeight();
    }
    
    public void buttonText(String text, int a, int b, int c, float size)
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
      text(text, x + (xWidth/2), y + (yHeight/1.3f));
    }
  }
  
public void dashline(float x0, float y0, float x1, float y1, float[ ] spacing)
{
  float distance = dist(x0, y0, x1, y1);
  float [ ] xSpacing = new float[spacing.length];
  float [ ] ySpacing = new float[spacing.length];
  float drawn = 0.0f;  // amount of distance drawn
 
  if (distance > 0)
  {
    int i;
    boolean drawLine = true; // alternate between dashes and gaps
    for (i = 0; i < spacing.length; i++)
    {
      xSpacing[i] = lerp(0, (x1 - x0), spacing[i] / distance);
      ySpacing[i] = lerp(0, (y1 - y0), spacing[i] / distance);
    }
 
    i = 0;
    while (drawn < distance)
    {
      if (drawLine)
      {
        line(x0, y0, x0 + xSpacing[i], y0 + ySpacing[i]);
      }
      x0 += xSpacing[i];
      y0 += ySpacing[i];
      /* Add distance "drawn" by this line or gap */
      drawn = drawn + mag(xSpacing[i], ySpacing[i]);
      i = (i + 1) % spacing.length;  // cycle through array
      drawLine = !drawLine;  // switch between dash and gap
    }
  }
}

public void dashline(float x0, float y0, float x1, float y1, float dash, float gap)
{
  float [ ] spacing = { dash, gap };
  dashline(x0, y0, x1, y1, spacing);
}
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
  
  public void move(float c)
  {
    if(y + c + tall <= height + (height * 0.01302083333333f) && y + c >= 0)
    {
      y += c;
    }
  }
  
  public void show()
  {
    rect(x,y,wide,tall);
  }
}
float scene = 0;

public void scene1()
{
  if(scene == 0)
  {
    fill(0,0,255);
    rect((0 - width * .01f), (0 - height * .01f), (width + (width * .01f)), (height + (height * .01f)));
    button2 = new buttons(width/2 - 300,height/1.29f,600,height * .05f,255,255,255);
    button1 = new buttons(width/2 - 300,height/1.4f,600,height * .05f,255,255,255);
  
    button2.colorChange();
    button1.colorChange();
    
    button2.show();
    button1.show();
    
    textFont(buttonsfont);
    button1.buttonText("Play",0,0,0,buttonTextSize);
    button2.buttonText("Options",0,0,0,buttonTextSize);
    fill(176,224,230);
    textFont(titlefont);
    textSize(height * .1f);
    text("OSCILLAPONG", width/2, height/3); 
    if(mousePressed && button1.rectStart())
    {
      timer();
      scene = 1;
    }
    else if(mousePressed && button2.rectStart())
    {
      scene = 2;
    }
  }
}


public void scene2()
{
  if(scene == 1)
  {
    textFont(buttonsfont);
    textSize(60);
    frameRate(150);
    fill(0,0,255);
    rect((0 - width * .01f), (0 - height * .01f), (width + (width * .01f)), (height + (height * .01f)));
    fill(255);
    float [ ] dashes = {25, 25, 25, 25};
    stroke(255);
    dashline(width / 2, 0, width / 2, height, dashes);
    
    
    if(p1MouseControl)
      leftPad = new paddle(0,mouseY - padTall/2,padTall,padWide);
      
      
    if(multiPlayer && p2MouseControl)
        rightPad = new paddle(width - padWide,mouseY - padTall/2,padTall,padWide);
   
    if(!multiPlayer)
    {
      if(easy)
      {
        rightPad = new paddle(width - padWide,theBall.y - padTall/2.001f,padTall,padWide); //MultiPlayer Paddle EASY
      }
      else if(medium)
      {
        rightPad = new paddle(width - padWide,theBall.y - padTall/5,padTall,padWide);     //MultiPlayer Paddle MEDIUM
      }
      else if(hard)
      {
        rightPad = new paddle(width - padWide,theBall.y - padTall/25,padTall,padWide);    //MultiPlayer Paddle HARD
      }
    }
    
    if(canStart)
    {
      keysPressed();
      theBall.show();
      theBall.Move();
      
      display();  
      
      gameOver();
    }
    
    fill(0);
    stroke(0);
    leftPad.show();
    stroke(0,255,255);
    fill(0,255,255);
    rightPad.show();
    scores();
    
    
    displayStart();
    calculate();
  }
}

public void scene3()
{
  if(scene == 2)
  {
    fill(0,0,255);
    rect((0 - width * .01f), (0 - height * .01f), (width + (width * .01f)), (height + (height * .01f)));
    fill(176,224,230);
    textFont(titlefont);
    textSize(height * .1f);
    text("OPTIONS", width/2, height/3); 
    
    textFont(buttonsfont);
    button5 = new buttons(width/2 - 300,height/1.09f,600,height * .05f,255,255,255);
    button5.colorChange();
    button5.show();
    button5.buttonText("Back",0,0,0,buttonTextSize);
    
    button6 = new buttons(width/2 - 157,height/2.3f,300,height * .05f,255,255,255);
    button6.colorChange();
    button6.show();
    if (multiPlayer){
      button6.buttonText("Multiplayer (On)",0,0,0,buttonTextSize/1.3f);
    }
    else{
      button6.buttonText("Multiplayer (Off)",0,0,0,buttonTextSize/1.3f);
    }
    
    button7 = new buttons(width/3 - 150,height/2,300,height * .05f,255,255,255);
    button7.colorChange();
    button7.show();
    button7.buttonText("Vs. AI",0,0,0,buttonTextSize);
    
    button8 = new buttons(width/3 + 89,height/1.77f,170,height * .05f,255,255,255);
    button8.colorChange();
    
    button9 = new buttons(width/3 - 85,height/1.77f,170,height * .05f,255,255,255);
    button9.colorChange();
    
    button10 = new buttons(width/3 - 259, height/1.77f,170,height * .05f,255,255,255);
    button10.colorChange();
    
    button11 = new buttons(width/2,height/2,300,height * .05f,255,255,255);
    button11.colorChange();
    button11.show();
    button11.buttonText("Ball Physics",0,0,0,buttonTextSize);
    
    button12 = new buttons(width/3 + 385,height/1.77f,170,height * .05f,255,255,255);
    button12.colorChange();
    
    button13 = new buttons(width/3 + 211,height/1.77f,170,height * .05f,255,255,255);
    button13.colorChange();
    
    button14 = new buttons(width/3 + 37, height/1.77f,170,height * .05f,255,255,255);
    button14.colorChange();
    
    button15 = new buttons(width/3 - 150,height/1.5f,300,height * .05f,255,255,255);
    button15.colorChange();
    button15.show();
    button15.buttonText("P1 Controls",0,0,0,buttonTextSize);
    
    button16 = new buttons(width/3,height/1.37f,170,height * .05f,255,255,255);
    button16.colorChange();
    
    button17 = new buttons(width/3 - 175, height/1.37f,170,height * .05f,255,255,255);
    button17.colorChange();
    
    button18 = new buttons(width/2,height/1.5f,300,height * .05f,255,255,255);
    button18.colorChange();
    button18.show();
    button18.buttonText("P2 Controls",0,0,0,buttonTextSize);
    
    button19 = new buttons(width/3 + 315,height/1.37f,170,height * .05f,255,255,255);
    button19.colorChange();
    
    button20 = new buttons(width/3 + 140, height/1.37f,170,height * .05f,255,255,255);
    button20.colorChange();
    
///////////////// ORIGINAL BUTTONS IN MENU /////////////////
    
    if(mousePressed && button5.rectStart())
    {
      scene = 0;
      AIMenu = false;
      p2MouseControlMenu = false;
      physicsMenu = false;
      p1MouseControlMenu = false;
    }
    if(mousePressed && button6.rectStart())
    {
      multiPlayer = true;
      AIMenu = false;
      p2MouseControlMenu = false;
      physicsMenu = false;
    }
    if(mousePressed && button7.rectStart())
    {
      physicsMenu = false;
      p1MouseControlMenu = false;
      p2MouseControlMenu = false;
      AIMenu = true;
    }
    if(mousePressed && button11.rectStart())
    { 
      AIMenu = false;
      p1MouseControlMenu = false;
      p2MouseControlMenu = false;
      physicsMenu = true;
    }
    if(mousePressed && button15.rectStart()){
      physicsMenu = false;
      AIMenu = false;
      p2MouseControlMenu = false;
      p1MouseControlMenu = true;
    }
    
    if(mousePressed && button18.rectStart()){
      physicsMenu = false;
      AIMenu = false;
      p1MouseControlMenu = false;
      p2MouseControlMenu = true;
    }
    
///////////////// POPOUT BUTTONS: AI MENU /////////////////
    
    if(AIMenu)
    {
      button8.show();
      button8.buttonText("Hard",0,0,0,buttonTextSize/1.1f);
      button9.show();
      button9.buttonText("Medium",0,0,0,buttonTextSize/1.2f);
      button10.show();
      button10.buttonText("Easy",0,0,0,buttonTextSize/1.1f);
    }
    if(mousePressed && button8.rectStart())
    {
      hard = true;
      medium = false;
      easy = false;
      AIMenu = false;
      multiPlayer = false;
    }
    if(mousePressed && button9.rectStart())
    {
      medium = true;
      hard = false;
      easy = false;
      AIMenu = false;
      multiPlayer = false;
    }
    if(mousePressed && button10.rectStart())
    {
      easy = true;
      medium = false;
      hard = false;
      AIMenu = false;
      multiPlayer = false;
    }
    
    
///////////////// POPOUT BUTTONS: BALL PHYSICS MENU /////////////////
    
    if(physicsMenu){
      textSize(height/10);
      button12.show();
      button12.buttonText("Staircase",0,0,0,buttonTextSize/1.2f);
      button13.show();
      button13.buttonText("Sine Wave",0,0,0,buttonTextSize/1.3f);
      button14.show();
      button14.buttonText("Classic",0,0,0,buttonTextSize/1.3f);
    }
    if(mousePressed && button12.rectStart())
    {
      classic = true;
      sineWave = false;
      stairCase = false;
      physicsMenu = false;
    }
    if(mousePressed && button13.rectStart())
    {
      classic = false;
      sineWave = true;
      stairCase = false;
      physicsMenu = false;
    }
    if(mousePressed && button14.rectStart())
    {
      classic = false;
      sineWave = false;
      stairCase = true;
      physicsMenu = false;
    }
    
///////////////// POPOUT BUTTONS:  P1 CONTROL MENU /////////////////
    
    if(p1MouseControlMenu){
      textSize(height/10);
      button16.show();
      button16.buttonText("Mouse",0,0,0,buttonTextSize/1.2f);
      button17.show();
      button17.buttonText("W/S Keys",0,0,0,buttonTextSize/1.2f);
    }
    if(mousePressed && button16.rectStart())
    {
      p1MouseControl = true;
      p2MouseControl = false;
      p1MouseControlMenu = false;
    }
    if(mousePressed && button17.rectStart())
    {
      p1MouseControl = false;
      p1MouseControlMenu = false;
    }
    
    
///////////////// POPOUT BUTTONS: P2 CONTROL MENU /////////////////
    
    if(p2MouseControlMenu){
      textSize(height/10);
      button19.show();
      button19.buttonText("Mouse",0,0,0,buttonTextSize/1.2f);
      button20.show();
      button20.buttonText("I/K Keys",0,0,0,buttonTextSize/1.2f);
    }
    if(mousePressed && button19.rectStart())
    {
      p2MouseControl = true;
      p1MouseControl = false;
      p2MouseControlMenu = false;
    }
    if(mousePressed && button20.rectStart())
    {
      p2MouseControl = false;
      p2MouseControlMenu = false;
    }
  }
}
int startSECOND, startMINUTE, startTOTAL;
int stopSECOND, stopMINUTE, stopTOTAL;
int dis_m, dis_s;
float i;
boolean startCount = false;

public void timer()
{ 
    startSECOND = second();
    startMINUTE = minute();
    startTOTAL = startMINUTE*60 + startSECOND;
    startCount = true;
}

public void calculate()
{
if(startCount)
  {
    stopMINUTE = minute();
    stopSECOND = second();
    stopTOTAL = stopMINUTE*60 + stopSECOND;

    int diff = stopTOTAL-startTOTAL;
    dis_m = diff/60;
    dis_s = diff - dis_m*60;
  }
}
public void display()
{
  textSize(60);
  fill(255);
  text(nf(dis_m,2)+":"+nf(dis_s - 3,2), width * .5f, 100);
}
public void displayStart()
{
  if((dis_s == 3 && dis_m == 0))
  {
    canStart = true;
    textSize(100);
    fill(255);
    i += 0.04f; 
    i %= 2*PI; 
    text("GO!", width * .5f + random((cos(i) * 200)), height * .5f  + random((sin(i) * 200)) );
  }
  if(dis_s < 3 && dis_m == 0)
  {
    textSize(150);
    fill(255);
    text(dis_s + 1, width * .5f, height * .5f);
  }
}
  public void settings() {  size(1000,1000); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Oscillapong" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
