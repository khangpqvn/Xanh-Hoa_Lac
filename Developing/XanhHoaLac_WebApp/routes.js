/**
 * Created by phamquangkhang on 4/12/17.
 */
var path = require('path');

module.exports = function (app) {
   // app.use("/api",require("./api"));
    app.get('/', function (req, res) {
        res.sendfile('index.html');
    });
   app.use("/api/auth",require("./api/auth"));

}