//float ballSpeed = 8;          //Fullscreen
//float scrollSpeed = 10;
float ballSpeed = 6;        //1000/1000
float scrollSpeed = 10;
float ballSize = 15;
float padWide = width * .15;
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

void setup()
{
  //fullScreen();
  size(1000,1000);
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

void draw()
{
  scene1();
  scene2();
  scene3();
}

// CODE FOR INTERACTION

void keysPressed()
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

void keyPressed()
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

void keyReleased()
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


void ResetBall()
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
void scores()
{
  fill(0);
  textSize(60);
  text(p1, width * .25, 100);
  fill(0,255,255);
  text(p2, width * .75, 100);
}


void gameOver()
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

void gameOverText(String text, color c, color b, color a)
{
  fill(c, b, a);
  textSize(60);
  text("GAME!", width/2, height/4);
  text(text, width/2, height/3);
  text("Hit space to play again!", width/2, height/1.2); 
  
  
  
  button3 = new buttons(width/2 - 300,height/1.15,600,height * .05f,255,255,255);
  button3.colorChange();
  button3.show();
  textFont(buttonsfont);
  button3.buttonText("Options",0,0,0,buttonTextSize);
  
  button4 = new buttons(width/2 - 300,height/1.07,600,height * .05f,255,255,255);
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
