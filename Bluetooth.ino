#define BTSerial  Serial3
//#define BTDEBUG

#ifdef BTSerial
SerialCommand CommandHandler;
#else
SoftwareSerial BTSerial(14, 15); // (RX,TX)
SerialCommand CommandHandler(BTSerial);
#endif

void waitRfidObject();
void unrecognized();

//Initialization
void BTCommInit() {
  BTSerial.begin(9600);

  // Movement
  //CommandHandler.addCommand("move", moveHandler);
  
  // Face moods
  CommandHandler.addCommand("happy", setHappyMood);
  CommandHandler.addCommand("sad", setSadMood);
  CommandHandler.addCommand("angry", setAngryMood);
  CommandHandler.addCommand("scared", setScaredMood);

  //Neutral mood - if necessary
  CommandHandler.addCommand("neutral", setNeutralMood);

  // RFID
  CommandHandler.addCommand("waitRfidObject", waitRfidObject);

  // FSR
  CommandHandler.addCommand("waitFsr", waitFsr);

  // Button
  CommandHandler.addCommand("waitButton", waitButton);
  CommandHandler.addDefaultHandler(unrecognized);
#ifdef BTDEBUG
  Serial.println("Init!");
#endif
}

void imalive() {
  Serial.println("Send command");
#ifdef BTDEBUG
  //BTSerial.println("I'm alive!");
#endif
}

//Update call
void listenCommand() {
  int i;
  char buf[32];
  CommandHandler.readSerial();
}

/*
// Callbacks
void moveHandler() {
  char* args;
  float params[3];
  int i, j;

#ifdef BTDEBUG
  BTSerial.print("move ");
#endif

  for (i = 0, j = 0; i < 3; i++) {
    args = CommandHandler.next();
    if (args != NULL) {
      params[i] = atof(args);
      j++;

#ifdef BTDEBUG
      BTSerial.print(args);
      BTSerial.print(" ");
#endif

    }

#ifdef BTDEBUG
    else {
      BTSerial.print(" NULL");
    }
#endif
  }

  if (j == 3) {

    Go(params[0], params[1], params[2]);
#ifdef BTDEBUG
    BTSerial.println("Moving...");
    delay(500);
    Stop();
    BTSerial.println("Stopped!");
#endif
  }

}*/

void waitRfidObject() {
#ifdef BTDEBUG
  Serial.println("waitRfidObject");
#endif
  Serial.println("waitRfid");
  String rfidResult = readTag();
  //Serial.println(rfidResult);
  Serial3.println(rfidResult);
  return;
}

void waitFsr() {
#ifdef BTDEBUG
  Serial.println("waitFsrObject");
#endif
  Serial.println("waitFSR");
  String fsrResult = checkTouch();
  Serial3.println(fsrResult);
  return;
}

void waitButton() {
#ifdef BTDEBUG
  Serial.println("waitButtonObject");
#endif
  Serial.println("waitButton");
  String buttonResult = checkButton();
  //Serial.println(result);
  Serial3.println(buttonResult);
  return;
}


void unrecognized() {
#ifdef BTDEBUG
  Serial.println("Command not recognized");
#endif
  return;
}

void serialEvent3() {
  while (BTSerial.available() > 0)
    listenCommand();
}
