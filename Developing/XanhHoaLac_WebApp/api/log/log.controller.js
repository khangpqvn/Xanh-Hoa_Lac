/**
 * Created by phamquangkhang on 6/5/17.
 */
'use strict';

var Log = require('./log.model');
var User = require('../user/user.model');
var Node = require('../node/node.model');
var logger = require('../logger');
var util = require('../util');
var logConfig = require('./log.config');
var messages = require("./log.message.json").messages;
var ret = false;
module.exports = {
    mobileSync: function (req, res) {
        //for mobile
        var logs = req.body.logs;
        var errLogs = [];
        req.session.destroy();
        if (logs && Array.isArray(logs)) {
            logs.forEach(function (log, index, p3) {
                addLog(log.username, log.targetNode, log.action, new Date(+log.time), log.description, function (err) {
                    if (err) {
                        logger.error(err.message, log.username, log.targetNode, log.action, new Date(+log.time), log.description);
                        errLogs.push(index);
                    }
                    if (index === logs.length - 1) {
                        res.json({status: true, message: errLogs});
                    }
                });
                // console.log(index);
            });


        } else {
            res.json({status: false, message: "Sai định dạng log gửi lên"});
        }
    },
    mobileViewLog: function (req, res) {
        require("./log.controller").viewTargetLog(req, res);
        req.session.destroy();
    },
    addLogAuto: function (req, target, actionType, description) {
        //for web
        addLog(req.user.emails[0].value, target, actionType, Date.now(), description, function (err) {
            if (err) {
                logger.error(err.message, req.user.emails[0].value, target, actionType, Date.now(), description);
            }
        });
    },

    findAll: function (req, res) {

        if (req.body) {
            Log.find({})
                .populate("targetNode")
                // .populate("targetUser")
                .populate("username")
                .exec(function (err, data) {
                    if (data) {
                        res.json(data);
                    }
                });
        }
    },
    viewTargetLog: function (req, res) {
        var pageOptions = {
            limit: req.body.limit || 5
        }
        if (req.body.target) {
            Log.find({$or: [{targetNode: req.body.target}, {username: req.body.target}]}, {}, {sort: {'time': -1}})
                .populate("targetNode")
                .populate("username")
                .exec(function (err, data) {
                    if (!err) {
                        var count = data.length;
                        // console.log(count);
                        if (pageOptions.limit == -1) {
                            res.json({status: true, data: data, viewmore: false});
                        } else if (count <= pageOptions.limit && count > 0) {
                            res.json({status: true, data: data, viewmore: false});
                        } else if (count > pageOptions.limit) {
                            res.json({status: true, data: data.splice(0, pageOptions.limit), viewmore: true});
                        } else {
                            res.json({status: false, message: messages.nothing_to_show});
                        }
                    } else {
                        res.json({status: false, message: messages.search_err});
                    }

                })
        } else {
            res.json({status: false, message: messages.target_required});
        }
    }
}


function addLog(username, target, actionType, time, description, callback) {
    if (actionType)
        actionType = actionType.toLowerCase().trim();
    if (!(username && target && logConfig.nodeActionType.indexOf(actionType) >= 0)) {
        callback(new Error("Dữ liệu không hợp lệ"));
        return;
    }
    username = username.toLowerCase().trim();
    target = target.toLowerCase().trim();

    var newLog = null;
    var userID;
    var targetNodeID = "";

    User.findOne({username: username}).exec(function (err, data) {
        if (data) {
            userID = data._id;
            Node.findOne({phoneNumber: target}).exec(function (err, data) {
                if (data) {
                    targetNodeID = data._id;
                    cb();
                } else {
                    callback(new Error("Không tồn tại Node mục tiêu trong hệ thống"));
                }
            });
        } else {
            callback(new Error("Không tồn tại người dùng trong hệ thống"));
        }
    });


    function cb() {
        newLog = new Log({
            username: userID,
            targetNode: targetNodeID,
            time: time,
            action: actionType,
            description: description
        });

        newLog.validate(function (err) {
            if (!err) {
                Log.create(newLog, function (err, data) {
                    if (!err) {
                        callback(null);
                    } else {
                        callback(err)
                    }
                });
            } else {
                callback(err);
            }
        });
    }
}