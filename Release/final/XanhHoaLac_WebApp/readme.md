Clone this project and install nodejs
Open cmd and CD to this folder
run "npm install"
and then run "node server.js"
write this line "127.0.0.1       quangkhang.com" to the end of host file at this address "C:\Windows\System32\drivers\etc"
go to quangkhang.com:3000 to using this app
be sure change the database of this app to your all database and create a collection name "users" and in this collection create
a document like:
{
    "_id" : ObjectId("5972139cb875a41e7eef10eb"),
    "username" : "khang21081995@gmail.com",
    "role" : "admin",
    "name" : "KHANG21081995",
    "isBlock" : false
}

with username is your gmail to login this system

