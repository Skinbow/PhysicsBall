int BallRadius;
float BallX, BallY;
float PBallX, PBallY;
float XPull, YPull;
float accelX, accelY;
float BounceLoss = 1.1, RollLoss = 1.006, AirFrict = 1.0006;
boolean jump = false;
boolean frame = true;

void setup() {
  size(800, 600);
  
  BallRadius = 5;
  BallX = 400;
  BallY = height - BallRadius;
  PBallX = BallX;
  PBallY = BallY;
  XPull = 0;
  YPull = 0;
  accelX = 0.4;
  accelY = 0.5;
  
  background(255);
  noStroke();
  fill(0);
  ellipse(BallX, BallY, 2*BallRadius, 2*BallRadius);
}

void draw() {
  if (!(BallX == PBallX && BallY == PBallY)) {
    background(255);
    fill(0);
    noStroke();
    ellipse(BallX, BallY, 2*BallRadius, 2*BallRadius);
    stroke(0);
    line(BallX, BallY, BallX + 10*XPull, BallY + 10*YPull);
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
  if (abs(XPull) < 10e-3) XPull = 0;
  
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

void keyPressed() {
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
