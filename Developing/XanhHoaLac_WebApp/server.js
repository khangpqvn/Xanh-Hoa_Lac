/**
 * Created by phamquangkhang on 4/12/17.
 */
'use strict';

/****************************** Variables ******************************/

var express = require('express');
var http = require('http');
var bodyParser = require('body-parser');
var cookieParser = require('cookie-parser');
var session = require('express-session');
var RedisStore = require('connect-redis')(session);
var mongoose = require('mongoose');
var passport = require('passport');
const MongoStore = require('connect-mongo')(session);

/****************************** Mongo DB  ******************************/
var db_username = require("./config.json").MONGO_DATABASE_USERNAME;
var db_password = require("./config.json").MONGO_DATABASE_PASSWORD;
var db_url = require("./config.json").MONGO_DATABASE_URL;
var connection_string = "mongodb://" + db_username + ":" + db_password + db_url;
mongoose.connect(connection_string);
//mongoose.connect('mongodb://localhost/techkids');

var db = mongoose.connection;
db.on('error', console.error.bind(console, 'DB connection error: '));
db.once('open', function () {
    console.log('DB connection success! ');
});

/****************************** app Express config  ******************************/
var app = express();
app.use(cookieParser(require("./config.json").APP_SECRET_KEY, {maxAge: 120}));
app.use(session({
    secret: require("./config.json").APP_SECRET_KEY,
    name: 'KhangPQ',
    // store: new RedisStore({
    //     host: require("./config.json").HOSTNAME,
    //     port: require("./config.json").SERVER_PORT
    // }),
    store: new MongoStore({ mongooseConnection: mongoose.connection }),
    proxy: true,
    resave: true,
    saveUninitialized: true,
}));

//app.use('/admin', auth.hasRole('admin'), express.static(__dirname + "/client/admin"));
app.use('/', express.static(__dirname + "/client"));
app.use(cookieParser());
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
    extended: true
}));
app.use(passport.initialize());
app.use(passport.session());
require('./routes')(app);


/******************************   ******************************/
/******************************   ******************************/

var server = http.createServer(app).listen(
    (process.env.PORT || require("./config.json").SERVER_PORT),
    function () {
        var host = server.address().address;
        var port = server.address().port;
        console.log("Example app listening at http://%s:%s", host, port);
    }
);