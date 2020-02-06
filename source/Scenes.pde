float scene = 0;

void scene1()
{
  if(scene == 0)
  {
    fill(0,0,255);
    rect((0 - width * .01), (0 - height * .01), (width + (width * .01)), (height + (height * .01)));
    button2 = new buttons(width/2 - 300,height/1.29,600,height * .05f,255,255,255);
    button1 = new buttons(width/2 - 300,height/1.4,600,height * .05f,255,255,255);
  
    button2.colorChange();
    button1.colorChange();
    
    button2.show();
    button1.show();
    
    textFont(buttonsfont);
    button1.buttonText("Play",0,0,0,buttonTextSize);
    button2.buttonText("Options",0,0,0,buttonTextSize);
    fill(176,224,230);
    textFont(titlefont);
    textSize(height * .1);
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


void scene2()
{
  if(scene == 1)
  {
    textFont(buttonsfont);
    textSize(60);
    frameRate(150);
    fill(0,0,255);
    rect((0 - width * .01), (0 - height * .01), (width + (width * .01)), (height + (height * .01)));
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
        rightPad = new paddle(width - padWide,theBall.y - padTall/2.001,padTall,padWide); //MultiPlayer Paddle EASY
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

void scene3()
{
  if(scene == 2)
  {
    fill(0,0,255);
    rect((0 - width * .01), (0 - height * .01), (width + (width * .01)), (height + (height * .01)));
    fill(176,224,230);
    textFont(titlefont);
    textSize(height * .1);
    text("OPTIONS", width/2, height/3); 
    
    textFont(buttonsfont);
    button5 = new buttons(width/2 - 300,height/1.09,600,height * .05f,255,255,255);
    button5.colorChange();
    button5.show();
    button5.buttonText("Back",0,0,0,buttonTextSize);
    
    button6 = new buttons(width/2 - 157,height/2.3,300,height * .05f,255,255,255);
    button6.colorChange();
    button6.show();
    if (multiPlayer){
      button6.buttonText("Multiplayer (On)",0,0,0,buttonTextSize/1.3);
    }
    else{
      button6.buttonText("Multiplayer (Off)",0,0,0,buttonTextSize/1.3);
    }
    
    button7 = new buttons(width/3 - 150,height/2,300,height * .05f,255,255,255);
    button7.colorChange();
    button7.show();
    button7.buttonText("Vs. AI",0,0,0,buttonTextSize);
    
    button8 = new buttons(width/3 + 89,height/1.77,170,height * .05f,255,255,255);
    button8.colorChange();
    
    button9 = new buttons(width/3 - 85,height/1.77,170,height * .05f,255,255,255);
    button9.colorChange();
    
    button10 = new buttons(width/3 - 259, height/1.77,170,height * .05f,255,255,255);
    button10.colorChange();
    
    button11 = new buttons(width/2,height/2,300,height * .05f,255,255,255);
    button11.colorChange();
    button11.show();
    button11.buttonText("Ball Physics",0,0,0,buttonTextSize);
    
    button12 = new buttons(width/3 + 385,height/1.77,170,height * .05f,255,255,255);
    button12.colorChange();
    
    button13 = new buttons(width/3 + 211,height/1.77,170,height * .05f,255,255,255);
    button13.colorChange();
    
    button14 = new buttons(width/3 + 37, height/1.77,170,height * .05f,255,255,255);
    button14.colorChange();
    
    button15 = new buttons(width/3 - 150,height/1.5,300,height * .05f,255,255,255);
    button15.colorChange();
    button15.show();
    button15.buttonText("P1 Controls",0,0,0,buttonTextSize);
    
    button16 = new buttons(width/3,height/1.37,170,height * .05f,255,255,255);
    button16.colorChange();
    
    button17 = new buttons(width/3 - 175, height/1.37,170,height * .05f,255,255,255);
    button17.colorChange();
    
    button18 = new buttons(width/2,height/1.5,300,height * .05f,255,255,255);
    button18.colorChange();
    button18.show();
    button18.buttonText("P2 Controls",0,0,0,buttonTextSize);
    
    button19 = new buttons(width/3 + 315,height/1.37,170,height * .05f,255,255,255);
    button19.colorChange();
    
    button20 = new buttons(width/3 + 140, height/1.37,170,height * .05f,255,255,255);
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
      button8.buttonText("Hard",0,0,0,buttonTextSize/1.1);
      button9.show();
      button9.buttonText("Medium",0,0,0,buttonTextSize/1.2);
      button10.show();
      button10.buttonText("Easy",0,0,0,buttonTextSize/1.1);
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
      button12.buttonText("Staircase",0,0,0,buttonTextSize/1.2);
      button13.show();
      button13.buttonText("Sine Wave",0,0,0,buttonTextSize/1.3);
      button14.show();
      button14.buttonText("Classic",0,0,0,buttonTextSize/1.3);
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
      button16.buttonText("Mouse",0,0,0,buttonTextSize/1.2);
      button17.show();
      button17.buttonText("W/S Keys",0,0,0,buttonTextSize/1.2);
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
      button19.buttonText("Mouse",0,0,0,buttonTextSize/1.2);
      button20.show();
      button20.buttonText("I/K Keys",0,0,0,buttonTextSize/1.2);
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
