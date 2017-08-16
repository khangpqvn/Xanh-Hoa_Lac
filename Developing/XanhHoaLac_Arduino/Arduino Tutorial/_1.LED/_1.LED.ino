int led = 6;           // cổng digital mà LED được nối vào
int brightness = 0;    // mặc định độ sáng của đèn là 
int fadeAmount = 5;    // mỗi lần thay đổi độ sáng thì thay đổi với giá trị là bao nhiêu
 
 
void setup()  {
  // pinMode đèn led là OUTPUT
  // pinMode(led, OUTPUT);
}
 
//void loop()  {
//  //xuất giá trị độ sáng đèn LED
//  analogWrite(led, brightness);
// 
//  // thay đổi giá trị là đèn LED
//  brightness = brightness + fadeAmount;
// 
//  // Đoạn code này có nghĩa nếu độ sáng == 0 hoặc bằng == 255 thì sẽ đổi chiều của biến thay đổi độ sáng. Ví dụ, nếu đèn từ sáng yếu --> sáng mạnh thì fadeAmount dương. Còn nếu đèn sáng mạnh --> sáng yếu thì fadeAmmount lúc này sẽ có giá trị âm
//  if (brightness == 0 || brightness == 255) {
//    fadeAmount = -fadeAmount ;
//  }    
//  //đợi 30 mili giây để thấy sự thay đổi của đèn
//  delay(30);
//}
void setLightBrightness(int num)  {
  int fade;
  if (brightness >= num)
    fade = -1;
  else fade = 1;
  while (brightness != num){
    brightness += fade;
    analogWrite(led, brightness);
    delay(10);
  }
}

void loop() {
  setLightBrightness(255);
  delay(1000);
  setLightBrightness(20);
  delay(1000);
  setLightBrightness(150);
  delay(1000);
  setLightBrightness(100);
  delay(1000);
}

