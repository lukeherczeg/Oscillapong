void dashline(float x0, float y0, float x1, float y1, float[ ] spacing)
{
  float distance = dist(x0, y0, x1, y1);
  float [ ] xSpacing = new float[spacing.length];
  float [ ] ySpacing = new float[spacing.length];
  float drawn = 0.0;  // amount of distance drawn
 
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

void dashline(float x0, float y0, float x1, float y1, float dash, float gap)
{
  float [ ] spacing = { dash, gap };
  dashline(x0, y0, x1, y1, spacing);
}
