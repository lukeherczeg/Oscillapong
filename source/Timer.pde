int startSECOND, startMINUTE, startTOTAL;
int stopSECOND, stopMINUTE, stopTOTAL;
int dis_m, dis_s;
float i;
boolean startCount = false;

void timer()
{ 
    startSECOND = second();
    startMINUTE = minute();
    startTOTAL = startMINUTE*60 + startSECOND;
    startCount = true;
}

void calculate()
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
void display()
{
  textSize(60);
  fill(255);
  text(nf(dis_m,2)+":"+nf(dis_s - 3,2), width * .5, 100);
}
void displayStart()
{
  if((dis_s == 3 && dis_m == 0))
  {
    canStart = true;
    textSize(100);
    fill(255);
    i += 0.04f; 
    i %= 2*PI; 
    text("GO!", width * .5 + random((cos(i) * 200)), height * .5  + random((sin(i) * 200)) );
  }
  if(dis_s < 3 && dis_m == 0)
  {
    textSize(150);
    fill(255);
    text(dis_s + 1, width * .5, height * .5);
  }
}
