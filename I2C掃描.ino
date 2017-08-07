#include <Wire.h>
 
 
void setup()
{
  Wire.begin();
 
  Serial.begin(115200);
  //等待開啟序列埠監視視窗
  while (!Serial);            
  Serial.println("\nI2C Scanner");
}
 
 
void loop()
{
  byte error, address;
  int nDevices;
 
  Serial.println("掃描中...");
 
  nDevices = 0;
  for(address = 1; address < 127; address++ )
  {
    // The i2c_scanner uses the return value of
    // the Write.endTransmisstion to see if
    // a device did acknowledge to the address.
    
    Wire.beginTransmission(address);
    error = Wire.endTransmission();
 
    if (error == 0)
    {
      Serial.print("I2C 設備找到於位址 0x");
      if (address<16)
        Serial.print("0");
      Serial.print(address,HEX);
      Serial.println("  !");
 
      nDevices++;
    }
    else if (error==4)
    {
      Serial.print("未知錯誤於位址 0x");
      if (address<16)
        Serial.print("0");
      Serial.println(address,HEX);
    }    
  }
  if (nDevices == 0)
    Serial.println("並沒有找到任何設備d\n");
  else
    Serial.println("完成\n");
 
  delay(5000);           // wait 5 seconds for next scan
}
