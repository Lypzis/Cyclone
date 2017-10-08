#include <AFMotor.h>
#include <SoftwareSerial.h>

SoftwareSerial BT(10, 11); //TX, RX respectively
AF_DCMotor leftMotor(3); //Declaração do motor esquerdo, parametro identifica sua posição no shield(no caso 'M3')
AF_DCMotor rightMotor(4); //Declaração do motor direito
String device;
int speed = 170;

void setup() 
{
  BT.begin(9600);
  Serial.begin(9600); 
}

void loop() 
{
  while (BT.available()) //Check if there is an available byte to read
  {
    delay(10); //Delay added to make thing stable 
    char c = BT.read(); //Conduct a serial read 
    device += c; //build the string.
  } 
  if (device.length() > 0) 
  {
    BT.println(device); 

    //move ambos motores para frente
    if (device == "moveforward"){
      leftMotor.setSpeed(speed); //Define a velocidade maxima
      rightMotor.setSpeed(speed);
      rightMotor.run(FORWARD);
      leftMotor.run(FORWARD); 
    }
    //move ambos motores para trás
    else if(device == "moveback") {
      leftMotor.setSpeed(speed);
      rightMotor.setSpeed(speed); 
      leftMotor.run(BACKWARD); 
      rightMotor.run(BACKWARD); 
    }
    //Gira o motor sentido horario
    else if(device == "rotateright") {
      leftMotor.setSpeed(speed);
      rightMotor.setSpeed(speed);
      rightMotor.run(BACKWARD);
      leftMotor.run(FORWARD); 
    }
    //Giro no sentido anti-horario
    else if(device == "rotateleft") {
      leftMotor.setSpeed(speed);
      rightMotor.setSpeed(speed);
      rightMotor.run(FORWARD);
      leftMotor.run(BACKWARD);
    }
    //Desliga os motores
    else if( device == "-"){
      leftMotor.setSpeed(0);
      rightMotor.setSpeed(0);
      leftMotor.run(RELEASE);
      rightMotor.run(RELEASE);
    }
   
   device=""; //Reset the variable
   } 
}
