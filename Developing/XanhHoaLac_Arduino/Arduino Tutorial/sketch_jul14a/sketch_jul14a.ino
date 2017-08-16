#include "SIM900.h" 
#include <SoftwareSerial.h> 
#include "sms.h"
 
SMSGSM sms;
 
int numdata; 
boolean started=false; //trạng thái modul sim 
char smstext[160];// nội dung tin nhắn 
char number[20]; // số điện thoại format theo định dạng quốc tế
 
void setup(){   
    Serial.begin(9600);   
    Serial.println("Gui va nhan tin nhan");  
    if (gsm.begin(2400)){     
        Serial.println("\nstatus=READY");     
        started=true;     
    }   else 
        Serial.println("\nstatus=IDLE");
    
    if(started){     
        sms.SendSMS("+841686451311", "Online");   //đổi lại số của bạn nhé :D
    } 
}
 
void loop() {   
    if(started){     
        int pos; //địa chỉ bộ nhớ sim (sim luu tối đa 40 sms nên max pos = 40)     
        pos = sms.IsSMSPresent(SMS_UNREAD); // kiểm tra tin nhắn chưa đọc trong bộ nhớ     
        //hàm này sẽ trả về giá trị trong khoảng từ 0-40     
        if(pos){//nêu có tin nhắn chưa đọc       
            if(sms.GetSMS(pos, number, smstext, 160)){         
                Serial.print("So dien thoại: ");         
                Serial.println(number);         
                Serial.print("Noi dung tin nhan: ");         
                Serial.println(smstext);         
                sms.SendSMS(number, "Da doc tin");       
            }     
        }     
        delay(1000);   
    } else Serial.println("Offline");
}
