/**
 * Created by phamquangkhang on 5/22/17.
 */
'use strict';

var compose = require('composable-middleware');
var User = require("./user.model")
var messages = require("./user.message.json").messages;
module.exports = {
    checkAcceptAble: function () {
        return compose()
            .use(function (req, res, next) {
                // console.log(req.user);
                if (req.user.emails) {
                    User.findOne({username: req.user.emails[0].value.toLowerCase()}).exec(function (err, data) {
                        // console.log(data);
                        if (data && !data.isBlock) {
                            next();
                        } else {
                            //tai khoan bi khoa khong con co quyen thuc hien hanh dong nua.
                            req.session.destroy();
                            res.json({status: false, message: messages.block_message});
                        }
                    })
                } else if (req.user.email) {
                    User.findOne({username: req.user.email.toLowerCase()}).exec(function (err, data) {
                        // console.log(data);
                        if (data && !data.isBlock) {
                            next();
                        } else {
                            //tai khoan bi khoa khong con co quyen thuc hien hanh dong nua.
                            req.session.destroy();
                            res.json({status: false, message: messages.block_message});
                        }
                    })
                } else {
                    res.json({status: false, message: "bạn chưa đăng nhập"});
                }
            });
    }
}