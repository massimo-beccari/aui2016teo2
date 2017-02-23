#include <SoftwareSerial.h>
#include <SerialCommand.h>
#include <Adafruit_GFX.h>
#include <Adafruit_NeoMatrix.h>

#include <MC33887.h>
#include <Triskar.h>
#include "Eyelashes.h"
#include "Eyes.h"
#include "Mouths.h"
#include "Timer.h"

#include "Statistic.h"

#define MATRIX_PIN 4
//#define BTDEBUG
#define BTSerial  Serial3

//FACE
Adafruit_NeoMatrix matrix = Adafruit_NeoMatrix(16, 16, MATRIX_PIN, NEO_MATRIX_TOP + NEO_MATRIX_LEFT + NEO_MATRIX_ZIGZAG + NEO_MATRIX_ROWS, NEO_GRB + NEO_KHZ800);

const uint16_t green = matrix.Color(0, 200, 0);
const uint16_t blue = matrix.Color(0, 0, 200);
const uint16_t yellow = matrix.Color(150, 150, 0);
const uint16_t red = matrix.Color(190, 50, 50);
const uint16_t purple = matrix.Color(186, 85, 211);

Timer timer;

//FSR
Statistic persistentStat[5], interactionStat[5];
int fsrAnalogPin[5] = {9, 10, 11, 14, 15};
int fsrReading[5];
int flag1[5] = {0, 0, 0, 0, 0};
int flag2[5] = {0, 0, 0, 0, 0};
int interaction[5];
long startTime[5] = {0, 0, 0, 0, 0}, endTime[5], actualTime[5];


void setup() {
  Serial.begin(115200);
  BTCommInit();
  Serial.println("Setup done.");
  
  matrix.begin();
  matrix.setBrightness(10);

  //Initialize the mood
  setHappyMood();
  
  timer.every(3000, animateEyes);
  timer.every(4000, animateEyelashes);

  int fsrId;
  for(fsrId = 0; fsrId<5; fsrId++) {
    persistentStat[fsrId].clear();
    interactionStat[fsrId].clear();
  }
  
}

void loop() {
  timer.update();
}

