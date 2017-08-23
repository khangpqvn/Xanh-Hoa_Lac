/**
 * Created by phamquangkhang on 4/21/17.
 */
'use strict';

var Node = require('./node.model');
// var logger = require('winston');
var util = require('../util');
var phoneNumber,
    lat,
    lng,
    description,
    isDelete,
    version;
var versionFileName = "./api/node/node.version";
var logController = require("../log/log.controller");
var beforeChangeLat;
var beforeChangeLng;
var beforeChangeDescription;
var beforeChangeIsDelete;
var messages = require("./node.message.json").messages;

function renewVariable() {
    phoneNumber = "";
    lat = "";
    lng = "";
    description = "";
    isDelete = false;
}


function setVar(req, res, messageFail) {
    if (req.body.phoneNumber && req.body.phoneNumber !== "") {
        try {
            // console.log(req.body.phoneNumber);
            phoneNumber = util.standardizePhoneNumber(req.body.phoneNumber);
        } catch (err) {
            res.json({status: false, message: String(err).split(":")[1]});
            return false;
        }
        if (req.body.lat && req.body.lng) {
            lat = util.replaceAll(req.body.lat.toString(), ' ', '');
            lng = util.replaceAll(req.body.lng.toString(), ' ', '');
        }

        if (req.body.description) {
            description = util.replaceAll(req.body.description, "  ", " ");
        }
        if (req.body.isDelete !== null) {
            isDelete = req.body.isDelete;
        }
        return true;

    } else {
        res.json({status: false, message: messageFail});
        return false;

    }

}
module.exports = {
    addNode: function (req, res) {
        // console.log(req.body);
        renewVariable();
        // console.log("1");
        if (setVar(req, res, messages.addnode.fail)) {
            // console.log("2");
            Node.findOne({phoneNumber: phoneNumber, isDelete: true}).exec(function (err, data) {
                if (data) {
                    // console.log("vao day");
                    data.lat = lat;
                    data.lng = lng;
                    data.description = description;
                    data.isDelete = false;
                    data.save(function (err, newData) {
                        if (err) {
                            console.log(String(err));
                            res.json({status: false, message: String(err).split(":")[1]});
                        } else {
                            util.changeVersion(versionFileName);
                            logController.addLogAuto(req, phoneNumber, "add", "Khôi phục node đã bị xóa trước đó");
                            res.json({
                                status: true,
                                message: messages.addnode.success,
                                _id: data._id
                            });
                        }
                    })
                } else {
                    Node.findOne({phoneNumber: phoneNumber, isDelete: false}).exec(function (err, data) {
                        if (data) {
                            // console.log("3");
                            res.json({status: false, message: messages.node_existed});
                        } else {
                            Node.findOne({lat: lat, lng: lng, isDelete: false}).exec(function (err, data1) {
                                if (data1) {
                                    res.json({
                                        status: false,
                                        message: messages.node_position_existed
                                    });
                                } else {
                                    var newNode = new Node({
                                        phoneNumber: phoneNumber,
                                        lat: lat,
                                        lng: lng,
                                        description: description,
                                        isDelete: false
                                    });
                                    // console.log("newNode: " + newNode);
                                    newNode.validate(function (err) {
                                        if (err) {
                                            console.log(String(err));
                                            res.json({status: false, message: String(err).split(":")[2]});//message in validation
                                        } else {
                                            Node.create(newNode, function (err, newData) {
                                                if (!err) {
                                                    // console.log("data:" + data);
                                                    util.changeVersion(versionFileName);
                                                    logController.addLogAuto(req, newNode.phoneNumber, "add", "Thêm một node mới");
                                                    res.json({
                                                        status: true,
                                                        message: messages.addnode.success,
                                                        _id: newNode._id
                                                    });
                                                } else {
                                                    // console.log("data:" + data);
                                                    res.json({status: false, message: err.message});//message in pre save
                                                }
                                            });
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
            });
        }
    },
    modifyNode: function (req, res) {
        renewVariable();

        if (setVar(req, res, messages.modifilenode.fail)) {
            // console.log("%s %s %s %s", phoneNumber, lat, lng, description);
            Node.findOne({phoneNumber: phoneNumber, isDelete: false}).exec(function (err, data) {
                if (data) {
                    setBeforeChange(data);
                    data.lat = lat;
                    data.lng = lng;
                    data.description = description;
                    data.isDelete = isDelete;
                    // beforechangeLog();
                    // console.log("After: " + data);
                    data.save(function (err, newData) {
                        if (err) {
                            res.json({status: false, message: String(err).split(":")[1]});
                        } else {
                            util.changeVersion(versionFileName);
                            var descrip = "";
                            descrip += (beforeChangeLat === data.lat ? "" : ("Sửa vĩ độ của node từ '" + beforeChangeLat + "' sang '" + data.lat + "'\r\n"));
                            descrip += (beforeChangeLng === data.lng ? "" : ("Sửa kinh độ của node từ '" + beforeChangeLng + "' sang '" + data.lng + "'"));
                            descrip += (beforeChangeDescription === data.description ? "" : ("Sửa mô tả về node từ '" + beforeChangeDescription + "' sang '" + data.description + "'"));
                            descrip += (data.isDelete ? "Xóa node" : "" );
                            // console.log(descrip);
                            logController.addLogAuto(req, phoneNumber, data.isDelete ? "delete" : "modify", descrip);
                            res.json({status: true, message: messages.modifilenode.success});
                        }
                    })
                } else {
                    res.json({status: false, message: messages.node_not_existed});
                }
            })
        }

    },
    deleteNode: function (req, res) {
        renewVariable();
        setVar(req, res, "Delete Failed!!!");
        Node.findOne({phoneNumber: phoneNumber, isDelete: false}).exec(function (err, data) {
            if (data) {
                data.isDelete = true;
                data.save(function (err, newData) {
                    if (err) {
                        res.json({status: false, message: String(err)});
                    } else {
                        util.changeVersion(versionFileName);
                        res.json({status: true, message: "Xóa node thành công"});
                    }
                })
            } else {
                res.json({status: false, message: "Node không tồn tại "});
            }
        })
    },
    //for web
    findAll: function (req, res) {
        Node.find({isDelete: false}).exec(function (err, data) {
            res.json(data);
        })
    },
    //for android
    syncNode: function (req, res) {
        var clientVersion = req.body.version;
        // console.log(clientVersion);
        var currentVersion = util.getVersion(versionFileName);
        // console.log(currentVersion);
        req.session.destroy();
        if (clientVersion) {
            if (clientVersion != currentVersion) {
                Node.find({isDelete: false}).exec(function (err, data) {
                    res.json({status: true, currentVersion: currentVersion, nodes: data});
                })
            } else {
                res.json({status: false, message: messages.sync.not_need_sync});
            }
        } else {
            res.json({status: false, message: messages.sync.missing_version});
        }
    }
}
function setBeforeChange(data) {
    beforeChangeLat = data.lat;
    beforeChangeLng = data.lng;
    beforeChangeDescription = data.description;
    beforeChangeIsDelete = data.isDeletel;
}
//
// function beforechangeLog() {
//     console.log(beforeChangeLat);
//     console.log(beforeChangeLng);
//     console.log(beforeChangeDescription);
//     console.log(beforeChangeIsDelete);
// }