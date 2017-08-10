/**
 * Created by phamquangkhang on 6/5/17.
 */
'use strict';

var express = require('express');
// var controller = require('./node.controller');

var mongoose = require('mongoose');
mongoose.Promise = require('bluebird');
var Schema = mongoose.Schema;
var messages = require('./log.message.json').messages;
// var findOrCreate = require('mongoose-find-one-or-create');
var util = require('../util');
var actionType = require("./log.config").actionType;


var log = new Schema({
    username: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'User',
        require: [true, messages.user_required]
    },
    // targetUser: {
    //     type: mongoose.Schema.Types.ObjectId,
    //     ref: 'User',
    //     required: [function () {
    //         return !this.targetNode;
    //     }, "Target required"]
    // },
    targetNode: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "Node",
        required: [true, messages.target_required]
    },
    time: {type: Date, default: Date.now},
    action: {
        type: String,
        enum: actionType,
        require: [true, messages.action_required]
    },
    description: {
        type: String,
        default: ""
    },
    createdAt: {type: Date, default: Date.now, expires: 1000 * 60 * 24 * 30} // expired in 30 days
});

log.pre('save', function (next) {
    next();
    // try {
    //     this.phoneNumber = util.standardizePhoneNumber(this.phoneNumber);
    //     next();
    // } catch (err) {
    //     next(err);
    // }
});
// user.plugin(findOrCreate);


module.exports = mongoose.model('Log', log);