const int senRead = 0;
int countC = 0, countB = 0;

void setup()
{
    Serial.begin(9600);
}

void loop()
{
    //Serial.println("BLUETOOTH?");
    if (analogRead(senRead) < 800)
    {
        if (countC <= 2)
        {
            countC++;
        }
        countB = 0;
        if (countC == 2)
        {
            //Serial.print(String(millis())); Serial.print(" ");
            Serial.println("1"); // Send 1 for IR detected
        }
    }
    else if (analogRead(senRead >= 800))
    {
        if (countB <= 2)
        {
            countB++;
        }
        countC = 0;
        if (countB == 2)
        {
            //Serial.print(String(millis())); Serial.print(" ");
            Serial.println("0");
        }
    }

    delay(1);
}

