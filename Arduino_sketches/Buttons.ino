/*
 * 1 - Bianco
 * 2 - Verde
 * 3 - Rosso
 * 4 - Giallo (filo Giallo)
 * 5 - Blu
 * 6 - Giallo (filo Marrone/Arancio)
 */

int pin1 = 53, pin2 = 51, pin3 = 49, pin4 = 47, pin5 = 45, pin6 = 43;
int r1, r2, r3, r4, r5, r6;

String checkButton() {
  String buttonPressed = "";
  while (buttonPressed == "") {
    //Serial.println("aspetto click");
    r1 = digitalRead(pin1);
    r2 = digitalRead(pin2);
    r3 = digitalRead(pin3);
    r4 = digitalRead(pin4);
    r5 = digitalRead(pin5);
    r6 = digitalRead(pin6);
    if(r1 == HIGH) {
      buttonPressed = "button1";
      break;
    } else if(r2 == HIGH) {
      buttonPressed = "button2";
      break;
    } else if(r3 == HIGH) {
      buttonPressed = "button3";
      break;
    } else if(r4 == HIGH) {
      buttonPressed = "button4";
      break;
    } else if(r5 == HIGH) {
      buttonPressed = "button5";
      break;
    } else if(r6 == HIGH) {
      buttonPressed = "button6";
      break;
    }
    delay(100);
  }
  Serial.println(buttonPressed);
  return buttonPressed;
}
