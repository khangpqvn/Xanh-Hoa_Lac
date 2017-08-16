#include "SIM900.h" 
#include "sms.h"
#include <SoftwareSerial.h> 
#include <Wire.h> 

SMSGSM sms;
const byte DS1307 = 0x68;       /* Địa chỉ của DS1307 */
const byte NumberOfFields = 7;  /* Số byte dữ liệu sẽ đọc từ DS1307 */
/* DEFINE */
/* Module SIM */
boolean started = false;    /* trạng thái module SIM */
char number[20];            /* số điện thoại format theo định dạng quốc tế */
char smstext[160];          /* nội dung tin nhắn đến */
char smsReview[160];        /* nội dung tin nhắn review Schedule */
boolean flagAssetChecking = false;  /* đánh dấu việc kiểm tra TK */
int primaryAsset = 0;
/* NODE */
byte node = 12;      /* Device PIN */
int nodeSession = 0; /* phiên làm việc của node */
int nodeStatus = 0;  /* trạng thái node */
int nodeDuration = 0;/* thời gian hoạt động còn lại (s) */
int nodeOTSchedule[6] = {0,0,0,0,0,0};
/* 0-y 1-M 2-d 3-H 4-m 5-Duration */
int nodeMTSchedule[4][11];
/* 0-flag 1-Su 2-Mo 3-Tu 4-We 5-Th 6-Fr 7-Sa 8-H 9-m 10-Duration */
int nodeMTNewSchedule[11];
/* DS-1307 */
int currentSecond = 0;
int second, minute, hour, day, wday, month, year; /* khai báo các biến thời gian */
/* END-DEFINE */
/* TEST FUNCTION */
void testResult(){
  Serial.print("Node status: ");
  Serial.println(checkStatus());
  Serial.print("Node session: ");
  Serial.println(checkSession());
  Serial.print("Node duration: ");
  Serial.println(nodeDuration);
  Serial.print("Node OT schedule: ");
  for(int i=0; i<6; i++){
    Serial.print(nodeOTSchedule[i]);
    Serial.print(" ");
  }
  Serial.print("\nNode MT schedule:\n");
  for (int k=0; k<4; k++){
    for(int i=0; i<11; i++){
      Serial.print(nodeMTSchedule[k][i]);
      Serial.print(" ");
    }
    Serial.print("\n");
  }
  Serial.print("Primary Asset: ");
  Serial.println(primaryAsset);
}
/* END-TEST FUNCTION */
/* DEVELOPING FUNCTION */
/* convert SĐT thành số Integer để lưu session */
int numberToInt(){
  int num = 0;
  for (int i=8; i< 12; i++)
    num = num*10 + ((int)number[i] - 48);
  return num;
}
/* trả về giá trị session hiện tại */
int checkSession(){
  return nodeSession;
}
/* thay đổi giá trị session */
void setSession(int changeTo){
  nodeSession = changeTo;
}
/* trả về status hiện tại của node */
int checkStatus(){
  return nodeStatus;
}
/* thay đổi status của node */
void setStatus(int changeTo){
  nodeStatus = changeTo;
  if (changeTo == 0){
    digitalWrite(node,HIGH); //tắt
    setSession(0);
    setDuration(0);
    //sms.SendSMS(number, "Thiet bi da tat.");
    flagAssetChecking = true;
  } else {
      digitalWrite(node,LOW);//bật
      //sms.SendSMS(number, "Bat thiet bi thanh cong.");
      flagAssetChecking = true;
  }
}
/* thay đổi giá trị DURATION trong phiên */
void setDuration(int dur){
  nodeDuration = dur;
}
/* đếm thời gian hoạt động trong phiên, kết thúc phiên khi đạt giá trị DURATION */
void nodeDurationCountdown(){
  nodeDuration = nodeDuration - 5;
  if (nodeDuration == 0)
    setStatus(0);
  if (nodeDuration < 0)
    setDuration(0);
}
/* trả về giá trị MODE của SMS */
int getMode(){
  return (int)smstext[15] - 48;
}
/* trả về giá trị STATUS của SMS */
int getStatus(){
  return (int)smstext[14] - 48;
}
/* trả về giá trị DURATION của SMS */
int getDuration(){
  int duration = (int)smstext[0] - 48;
  for(int i=1; i<=2; i++){
    duration = duration*10 + ((int)smstext[i] - 48);
  }
  return duration*60;
}
/* trả về giá trị WDAY của SMS */
int getWDay(){
  return (int)smstext[7] - 48;
}
/* trả về giá trị YEAR của SMS */
int getYear(){
  int year = (int)smstext[8] - 48;
  year = year*10 + ((int)smstext[9] - 48);
  return year+2000;
}
/* trả về giá trị MONTH của SMS */
int getMonth(){
  int month = (int)smstext[10] - 48;
  month = month*10 + ((int)smstext[11] - 48);
  return month;
}
/* trả về giá trị DAY của SMS */
int getDay(){
  int day = (int)smstext[12] - 48;
  day = day*10 + ((int)smstext[13] - 48);
  return day;
}
/* trả về giá trị HOUR của SMS */
int getHour(){
  int hour = (int)smstext[5] - 48;
  hour = hour*10 + ((int)smstext[6] - 48);
  return hour;
}
/* trả về giá trị MINUTE của SMS */
int getMinute(){
  int minute = (int)smstext[3] - 48;
  minute = minute*10 + ((int)smstext[4] - 48);
  return minute;
}
/* check thời gian trong quá khứ hay không */
boolean isPassedDatetime(int yr, int mo, int da, int hr, int mi){
  int date = mo*100 + da;
  int dateInRT = month*100 + day;
  int ti = hr*100 + mi;
  int tiInRT = hour*100 + minute;
  if (yr<year) return true;
  if (yr==year){
    if (date<dateInRT) return true;
    if (date==dateInRT)
      if (ti<=tiInRT) return true;
  }
  return false;
}
/* thay đổi lịch đặt một lần */
void setOnetimeSchedule(){
  int yr = getYear();
  int mo = getMonth();
  int da = getDay();
  int hr = getHour();
  int mi = getMinute();
  if (!isPassedDatetime(yr,mo,da,hr,mi)){
    nodeOTSchedule[0] = yr;
    nodeOTSchedule[1] = mo;
    nodeOTSchedule[2] = da;
    nodeOTSchedule[3] = hr;
    nodeOTSchedule[4] = mi;
    nodeOTSchedule[5] = getDuration();
    sms.SendSMS(number, "Dat lich thanh cong.");
  } else sms.SendSMS(number, "Khong the hen gio vao thoi gian trong qua khu.");
  
}
/* decode lịch đặt theo tuần */
void setWeeklySchedule(){
  for(int i=1; i<=7; i++)
    nodeMTNewSchedule[i] = (int)smstext[i+6] - 48;
  nodeMTNewSchedule[8] = getHour();
  nodeMTNewSchedule[9] = getMinute();
  nodeMTNewSchedule[10] = getDuration();
  nodeMTNewSchedule[0] = 1;
}
/* decode lịch đặt hàng ngày */
void setDailySchedule(){
  for(int i=1; i<=7; i++)
    nodeMTNewSchedule[i] = 1;
  nodeMTNewSchedule[8] = getHour();
  nodeMTNewSchedule[9] = getMinute();
  nodeMTNewSchedule[10] = getDuration();
  nodeMTNewSchedule[0] = 1;
}
/* add lịch mới vào bộ nhớ */
void addMTNewSchedule(){
  for (int k=0; k<4; k++){
    if (nodeMTSchedule[k][0]==0){
      for(int i=0; i<11; i++)
        nodeMTSchedule[k][i] = nodeMTNewSchedule[i];
      return;
    }
  }
  sms.SendSMS(number, "Bo nho dat lich da day.");
}
/* reset đặt lịch 1 lần */
void resetOTSchedule(){
  for(int i=0; i<6; i++)
    nodeOTSchedule[i] = 0;
}
/* reset đặt lịch nhiều lần */
void resetMTSchedule(int index){
  for(int i=0; i<11; i++)
    nodeMTSchedule[index][i] = 0;
}
/* reset toàn bộ lịch đã đặt */
void resetAllSchedule(){
  resetOTSchedule();
  for (int k=0; k<4; k++)
    resetMTSchedule(k);
}
/* gửi tin nhắn trả về list schedule */
void reviewSchedule(){
  char temp[4];
  smsReview[0] = '\0';
  if (nodeOTSchedule[0]>0){
    int yr = nodeOTSchedule[0] - 2000;
    sprintf(temp, "%d", yr);
    strcat(smsReview,temp);
  } else strcat(smsReview,"00");
  for (int i=1; i<5; i++)
  {
    if(nodeOTSchedule[i]<10)
      strcat(smsReview,"0");
    sprintf(temp, "%d", nodeOTSchedule[i]);
    strcat(smsReview,temp);
  }
  int ot = nodeOTSchedule[5]/60;
  if(ot<10) strcat(smsReview,"00");
  else if (ot<100) strcat(smsReview,"0");
  sprintf(temp, "%d", ot);
  strcat(smsReview,temp);
  for (int k=0; k<4; k++)
  {
    strcat(smsReview,"|");
    for (int i=1; i<8; i++)
    {
      sprintf(temp, "%d", nodeMTSchedule[k][i]);
      strcat(smsReview,temp);
    }
    for (int i=8; i<10; i++)
    {
      if(nodeMTSchedule[k][i]<10)
        strcat(smsReview,"0");
      sprintf(temp, "%d", nodeMTSchedule[k][i]);
      strcat(smsReview,temp);
    }
    int mt = nodeMTSchedule[k][10]/60;
    if(mt<10) strcat(smsReview,"00");
    else if (mt<100) strcat(smsReview,"0");
    sprintf(temp, "%d", mt);
    strcat(smsReview,temp);
  }
  Serial.println(smsReview);
  sms.SendSMS(number, smsReview);
}
/* load schedule vào session */
void loadNodeSchedule(){
  if(checkSession()!=0) return;
  if (nodeOTSchedule[0]==year && nodeOTSchedule[1]==month && nodeOTSchedule[2]==day
    && nodeOTSchedule[3]==hour && nodeOTSchedule[4]==minute){
    setSession(1);
    setStatus(1);
    setDuration(nodeOTSchedule[5]);
    return;
  }
  for (int k=0;k<4;k++){
    if (nodeMTSchedule[k][wday]==1 && nodeMTSchedule[k][8]==hour && nodeMTSchedule[k][9]==minute){
      setSession(1);
      setStatus(1);
      setDuration(nodeMTSchedule[k][10]);
      return;
    }
  }
}
/* trả về thời gian kết thúc (startTime+duration), return 0 nếu vượt qua 24:00 */
int endTime(int hour, int minute, int duration){
  int eT;
  minute = minute + duration/60;
  while (minute>=60){
    hour++;
    minute = minute - 60;
  }
  eT = hour*100 + minute;
  if(eT > 2400)
    return 0;
  else return eT;
}
/* kiểm tra xung đột giữa lịch mới với các lịch đã đặt */
boolean isConflict(){
  boolean iC = false;
  int startTimeNew = getHour()*100 + getMinute();
  int endTimeNew = endTime(getHour(),getMinute(),getDuration());
  // nếu endTime của lịch mới vượt qua 24h return TRUE
  if (endTimeNew == 0)
    return true;
  for (int k=0; k<4; k++){
    if (nodeMTSchedule[k][0] == 1){
      for(int i=1; i<8; i++){
        if(nodeMTSchedule[k][i] == 1 && nodeMTNewSchedule[i] == 1){
          int currentST = nodeMTSchedule[k][8]*100 + nodeMTSchedule[k][9];
          int currentET = endTime(nodeMTSchedule[k][8],nodeMTSchedule[k][9],nodeMTSchedule[k][10]);
          if ((startTimeNew>=currentST && startTimeNew<=currentET)||(endTimeNew>=currentST && endTimeNew<=currentET))
            return true;
        }
      }
    }
  }
  return iC;
}
/* kiểm tra tài khoản chính, gửi tin nhắn khi TKC < 10000 */
void primaryAssetChecking(){
  if (flagAssetChecking == true){
    gsm.SimpleWriteln("AT+CUSD = 1,\"*101#\"");
    delay(5000);
    char resp[100];
    gsm.read(resp, 100);  // lấy nội dung trả về
    Serial.print("\nResponse TKC: ");
    Serial.println(resp);
    int i = 50;
    primaryAsset = 0;
    while (resp[i] != ' '){
      primaryAsset = primaryAsset*10 + ((int)resp[i] - 48);
      i++;
    }
    if (primaryAsset < 10000)
      //sms.SendSMS(number, "Tai khoan chinh sap het. Vui long nap them tien de duy tri hoat dong.");
    flagAssetChecking = false;
  }
}
/* END-DEVELOPING FUNCTION */
/* DS1307 FUNCTION */
/* Chuyển từ format BCD (Binary-Coded Decimal) sang Decimal */
int bcd2dec(byte num){
  return ((num/16 * 10) + (num % 16));
}
/* Chuyển từ Decimal sang BCD */
int dec2bcd(byte num){
  return ((num/10 * 16) + (num % 10));
}
/* Đọc dữ liệu của DS1307 */
void readDS1307(){
  Wire.beginTransmission(DS1307);
  Wire.write((byte)0x00);
  Wire.endTransmission();
  Wire.requestFrom(DS1307, NumberOfFields);
  second = bcd2dec(Wire.read() & 0x7f);
  minute = bcd2dec(Wire.read() );
  hour   = bcd2dec(Wire.read() & 0x3f); // chế độ 24h.
  wday   = bcd2dec(Wire.read() );
  day    = bcd2dec(Wire.read() );
  month  = bcd2dec(Wire.read() );
  year   = bcd2dec(Wire.read() );
  year += 2000;    
}
/* cài đặt thời gian cho DS1307 */
void setTime(byte hr, byte min, byte sec, byte wd, byte d, byte mth, byte yr){
  Wire.beginTransmission(DS1307);
  Wire.write(byte(0x00)); // đặt lại pointer
  Wire.write(dec2bcd(sec));
  Wire.write(dec2bcd(min));
  Wire.write(dec2bcd(hr));
  Wire.write(dec2bcd(wd)); // day of week: Sunday = 1, Saturday = 7
  Wire.write(dec2bcd(d)); 
  Wire.write(dec2bcd(mth));
  Wire.write(dec2bcd(yr));
  Wire.endTransmission();
}
/* Dùng xong xóa */
void digitalClockDisplay(){
  Serial.print(hour);
  printDigits(minute);
  printDigits(second);
  Serial.print(" ");
  Serial.print(day);
  Serial.print(" ");
  Serial.print(month);
  Serial.print(" ");
  Serial.print(year); 
  Serial.println(); 
}
void printDigits(int digits){
  // các thành phần thời gian được ngăn chách bằng dấu :
  Serial.print(":");

  if(digits < 10)
    Serial.print('0');
  Serial.print(digits);
}

/* END-DS1307 FUNCTION */
void setupDS1307(){
  Wire.begin();
  //setTime(11, 53, 00, 1, 6, 8, 17); // 07:52:00 CN 11-06-2017
  Serial.begin(9600);
  Serial.println("\nDS1307 is now running.");
}
void setupSIM(){
  Serial.begin(9600);
  Serial.println("\nSetting up SIM");
  if (gsm.begin(2400)){     
    Serial.println("\nstatus=READY");     
    started=true;
  } else {
    Serial.println("\nstatus=IDLE");
  }
  if(started){     
    Serial.println("\nNode is ready.");
    pinMode(13,OUTPUT);
    digitalWrite(13,HIGH); /* đèn sáng khi sẵn sàng nhận sms */
    //sms.SendSMS("+841626733099", "Online");
  }
}
void setupNode(){
  pinMode(node,OUTPUT);   //Chân NODE là OUTPUT
  digitalWrite(node,HIGH); //Mặc định NODE sẽ ở trạng thái OFF
  for(int k=0; k<4; k++)
    for(int i=0; i<11; i++)
    nodeMTSchedule[k][i] = 0;
}
void setup() {
  setupDS1307();
  setupSIM();
  setupNode();
}
void loop() {
  if(started){
    readDS1307(); /* Đọc dữ liệu của DS1307 */
    if (second%5 == 0 && currentSecond != second){
      digitalClockDisplay();
      testResult();
      loadNodeSchedule();
      nodeDurationCountdown();
      primaryAssetChecking();
      currentSecond = second;
      int pos;      /* địa chỉ bộ nhớ SIM (SIM lưu tối đa 40 sms nên max pos = 40) */
      pos = sms.IsSMSPresent(SMS_UNREAD); /* kiểm tra tin nhắn chưa đọc trong bộ nhớ */
                                          /* hàm này sẽ trả về giá trị trong khoảng từ 0-40 */
      if(pos){//nếu có tin nhắn chưa đọc
        if(sms.GetSMS(pos, number, smstext, 160)){
          /* Test */
          Serial.print("\nSMS from ");
          Serial.print(number);
          Serial.print(" : ");
          Serial.println(smstext);
          /* End-Test */
          switch (getMode()){
            case 1: /* điều khiển bằng tay */
              if (getStatus() == 1){
                if (checkStatus() == 0){
                  setStatus(1);
                  setSession(numberToInt());
                  setDuration(getDuration());
                } else {
                  if (checkSession()==1){
                    setSession(numberToInt());
                    setDuration(getDuration());
                    sms.SendSMS(number, "Ghi de session thanh cong.");
                    flagAssetChecking = true;
                  } else {
                    sms.SendSMS(number, "Thiet bi dang hoat dong.");
                    flagAssetChecking = true;
                  }
                }
              } else {
                  if (checkSession() == numberToInt() || checkSession()==1)
                    setStatus(0);
                  else {
                    sms.SendSMS(number, "Thiet bi dang hoat dong o phien khac.");
                    flagAssetChecking = true;
                  }
              }
              break;
            case 2: /* đặt lịch */
              switch (getStatus()){
                case 0:
                  switch (smstext[13]){
                    case '0':
                      reviewSchedule();
                      break;
                    case '1':
                      resetOTSchedule();
                      break;
                    case '2':
                      int index;
                      index = (int)smstext[13]-48;
                      resetMTSchedule(index);
                      break;
                    default:
                      resetAllSchedule();
                  }
                  break;
                case 1:
                  setOnetimeSchedule();
                  break;
                case 2:
                  setDailySchedule();
                  if (!isConflict()){
                    addMTNewSchedule();
                    sms.SendSMS(number, "Dat lich thanh cong.");
                    flagAssetChecking = true;
                  } else {
                    sms.SendSMS(number, "Xung dot thoi gian he thong.");
                    flagAssetChecking = true;
                  }
                  break;
                case 3:
                  setWeeklySchedule();
                  if (!isConflict()){
                    addMTNewSchedule();
                    sms.SendSMS(number, "Dat lich thanh cong.");
                    flagAssetChecking = true;
                  } else {
                    sms.SendSMS(number, "Xung dot thoi gian he thong.");
                    flagAssetChecking = true;
                  }
                  break;
              }
              break;
            case 3: /* chế độ ghi đè của admin và manager */
              if (getStatus() == 1){
                if (checkStatus() == 0)
                  setStatus(1);
                else {
                  sms.SendSMS(number, "Ghi de bang quyen quan ly thanh cong.");
                  flagAssetChecking = true;
                }
                setSession(numberToInt());
                setDuration(getDuration());
              } else setStatus(0);
              break;
            default: /* AdSMS, spamSMS, ... */
              Serial.println("Undefine Syntax SMS.");
          }
        }
        sms.DeleteSMS(byte(pos));//xóa sms vừa nhận, tránh tràn bộ nhớ
      }
    }
  } else Serial.println("Offline");
}
