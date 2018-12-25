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

public class PhysicsBall extends PApplet {

int BallRadius;
float BallX, BallY;
float PBallX, PBallY;
float XPull, YPull;
float accelX, accelY;
float BounceLoss = 1.1f, RollLoss = 1.003f, AirFrict = 1.0006f;
boolean jump = false;
boolean frame = true;

public void setup() {
  

  BallRadius = 5;
  BallX = 400;
  BallY = height - BallRadius;
  PBallX = BallX;
  PBallY = BallY;
  XPull = 0;
  YPull = 0;
  accelX = 0.4f;
  accelY = 0.5f;

  background(255);
  noStroke();
  fill(0);
  ellipse(BallX, BallY, 2*BallRadius, 2*BallRadius);
}

public void draw() {
  if (!(BallX == PBallX && BallY == PBallY)) {
    background(255);
    fill(0);
    noStroke();
    ellipse(BallX, BallY, 2*BallRadius, 2*BallRadius);
    stroke(0);
    //line(BallX, BallY, BallX + 10*XPull, BallY + 10*YPull);
  }
  PBallX = BallX;
  PBallY = BallY;
  BallX += XPull;
  BallY += YPull;

  if (BallX < BallRadius) {
    BallX = BallRadius;
    XPull /= -BounceLoss;
  }
  if (BallX > width - BallRadius) {
    BallX = width - BallRadius;
    XPull /= -BounceLoss;
  }

  XPull /= AirFrict;
  YPull /= AirFrict;
  if (BallY == height - BallRadius) XPull /= RollLoss;
  if (abs(XPull) < 10e-3f) XPull = 0;

  if (jump) {
    if (BallY > height - BallRadius) {
      BallY = height - BallRadius;
      if (YPull < 1) {
        YPull = 0;
        BallY = height - BallRadius;
        jump = false;
        return;
      }
      YPull /= -BounceLoss;
    }
    YPull += accelY;
  }
}

public void keyPressed() {
  if (key == 'a' || key == 'A') {
    XPull -= accelX;
  }
  if (key == 'd' || key == 'D') {
    XPull += accelX;
  }
  if (key == 'w' || key == 'W') {
    if (!jump) {
      jump = true;
      YPull = -20;
    }
  }
}
  public void settings() {  size(800, 600); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "PhysicsBall" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
