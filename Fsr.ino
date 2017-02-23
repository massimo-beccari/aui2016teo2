#define INTERVAL 2000
#define MIN_INTERVAL 1000
#define THRESHOLD1 600
#define THRESHOLD2 900
#define WAITING_TIME 2000
#define RANGE_FRONT 50
#define RANGE_FSR6 500
#define RANGE_FSR7 150
#define DELTA 200

#define NO_INTERACTION 0
#define ABBRACCIO 1
#define PUGNO 2
#define CAREZZA 3

int evaluateInteraction(int i) {
  if((interactionStat[i].maximum() - interactionStat[i].average() < DELTA) && (interactionStat[i].average() > THRESHOLD1))
    return ABBRACCIO;
  else
    return PUGNO;
}

void evaluateInteractions() {
  int i;
  for(i = 0; i<5; i++) {
    if(flag2[i] == 1) {
      //blocco per i tre sensori davanti
      if(i < 3) {
        if(!(interactionStat[i].maximum() - interactionStat[i].average() < DELTA) || !(interactionStat[i].average() > THRESHOLD1))
          if(interactionStat[i].maximum() > THRESHOLD2)
            interaction[i] = PUGNO;
          else
            interaction[i] = CAREZZA;
      } else {
        //blocco per i due sensori dietro
        if((interactionStat[i].maximum() - interactionStat[i].average() < DELTA) && (interactionStat[i].average() > THRESHOLD1))
          interaction[i] = ABBRACCIO;
        else
          if(interactionStat[i].maximum() > THRESHOLD2)
            interaction[i] = PUGNO;
          else
            interaction[i] = CAREZZA;
      }
    }
  }
}

String printInteractions() {
  int i;
  for(i = 0; i<5; i++) {
    if(interaction[i] != NO_INTERACTION) {
      //Serial.print("FSR ");
      //Serial.print(i);
      switch(interaction[i]) {
        case ABBRACCIO:
        Serial.print(": abbraccio  ");
        return "hug";
        case PUGNO:
        Serial.print(": pugno  ");
        return "punch";
        case CAREZZA:
        Serial.print(": carezza  ");
        return "caress";
        default:
        Serial.println("default di FSR " + i);
        break;
      }
    }
  }
  return "";
}

/*
void setup() {
  Serial.begin(9600);
  Serial.println("FSR: start");
}*/

void manageFSR(int i, int RANGE) {
  fsrReading[i] = analogRead(fsrAnalogPin[i]);
  actualTime[i] = millis();
  if(flag1[i] == 0) {
    if((actualTime[i] - endTime[i] > WAITING_TIME) && (fsrReading[i] > persistentStat[i].average() + RANGE)) {
      flag1[i] = 1;
      interactionStat[i].add(fsrReading[i]);
      startTime[i] = millis();
    } else {
      persistentStat[i].add(fsrReading[i]);
    }
  } else {
    if((actualTime[i] - startTime[i] < MIN_INTERVAL) || (fsrReading[i] > persistentStat[i].average() + RANGE)) {
      interactionStat[i].add(fsrReading[i]);
    } else {
      endTime[i] = millis();
      flag1[i] = 0;
      flag2[i] = 1;
      persistentStat[i].add(fsrReading[i]);
    }
  }
  //Serial.println(fsrReading[i]);
}

String checkTouch() {
  // loop
  int i, s;
  String interactionRes = "";
  while (interactionRes == "") {
    for(i = 0; i<5; i++) {
      if(i<3)
        manageFSR(i, RANGE_FRONT);
      else if(i == 3)
        manageFSR(i, RANGE_FSR6);
      else
        manageFSR(i, RANGE_FSR7);
    }
    s = 0;
    for(i = 0; i < 5 && s == 0; i++)
      if(flag2[i] == 1)
        s = 1;
    if(s == 1) {
      evaluateInteractions();
      interactionRes = printInteractions();
      //reinizializza flag2, i flag interazioni e le statistiche di interazione
      for(i = 0; i<5; i++) {
        flag2[i] = 0;
        interaction[i] = NO_INTERACTION;
        interactionStat[i].clear();
      }
    }
  }
  Serial.println("checkTouch " + interactionRes);
  return interactionRes;
}

