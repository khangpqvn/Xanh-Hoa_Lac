/**
 * Created by phamquangkhang on 4/21/17.
 */
'use strict';
var request = require("request");
var url = "http://api.openweathermap.org/data/2.5/forecast?id=1567621&APPID=5f08b7a91e96dd7a81ce96424bbd3e01"
var logger = require('../logger');
var ret;
module.exports = {
    view: function (req, res) {
        res.json(ret);
    },
    getDataFromAPIToFile: function () {
        request({
            url: url,
            json: true
        }, function (error, response, body) {
            if (!error && response.statusCode === 200) {
                ret = body;
            }
        })
        setInterval(function () {
                request({
                    url: url,
                    json: true
                }, function (error, response, body) {
                    if (!error && response.statusCode === 200) {
                        ret = body;
                    }
                })
        }
        ,300000);

    }
}
