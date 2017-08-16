#include "SIM900.h"
#include "SoftwareSerial.h"
#include "sms.h"
SMSGSM sms;
char state[50], msg[50], resp[50], phong[100]="TTL Phong";//khoi tao cac xau ki tu
char smstext[160];//noi dung tin nhan den
char number[20];//so dien thoai tin nhan den theo dinh dang quoc te
int numdata;//bien nay dung cho doc du lieu
int flag=0;   
boolean started = false; //trạng thái modul sim
void setup() 
{
  Serial.begin(9600);
  Serial.println("GSM Shield testing.");
  if (gsm.begin(2400)){
    Serial.println("\nstatus=READY");
    started = true;
  }
  else Serial.println("\nstatus=IDLE");
}
 
void loop() {
 if(started){
//    char pos; //địa chỉ bộ nhớ sim (sim luu tối đa 40 sms nên max pos = 40)
//    pos = sms.IsSMSPresent(SMS_UNREAD); // kiểm tra tin nhắn chưa đọc trong bộ nhớ
//    //hàm này sẽ trả về giá trị trong khoảng từ 0-40
//    if((int)pos){//nêu có tin nhắn chưa đọc
//      if(sms.GetSMS(pos, number, smstext, 160)){
//        Serial.print("So dien thoai: ");
//        Serial.println(number);
//        Serial.print("Noi dung tin nhan: ");
//        Serial.println(smstext);
//        if(strcmp(smstext,"KIEMTRA")==0){//so sánh 2 chuỗi,neu trong SMS gui toi co cuoi ky tu KIEMTRA moi thuc hien kiem tra
          gsm.SimpleWriteln("AT+CUSD=1,\"*101#\"");   
//          delay(5000);//phai doi 5 giay moi gui va doc het du lieu
          char resp[100];//khoi tao xau cho noi dung tra ve
          gsm.read(resp, 100); //lay no
          delay(100);
          strcpy(phong,resp);//chep toan bo xau noi dung ra xau ta muon
          Serial.println(phong);//in ra thanh cong
          Serial.println("DM Hao ml");
          delay(100);
//          sms.SendSMS(number, phong); //nhan tin tra ve noi dung
//          delay(100);
//        } else {
//          Serial.println("Co tin nhan den nhung sai cu phap");
//        }
//      }
//      sms.DeleteSMS(byte(pos));//xóa sms vừa nhận
//    }
// delay(1000);
  }
}
