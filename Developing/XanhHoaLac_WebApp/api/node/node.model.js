/**
 * Created by phamquangkhang on 4/21/17.
 */
'use strict';

var express = require('express');
// var controller = require('./node.controller');

var mongoose = require('mongoose');
mongoose.Promise = require('bluebird');
var Schema = mongoose.Schema;
// var findOrCreate = require('mongoose-find-one-or-create');
var util = require('../util');
var messages = require("./node.message.json").messages;
var LATITUDE_PATTERN = /^(\+|-)?(?:90(?:(?:\.0{1,6})?)|(?:[0-9]|[1-8][0-9])(?:(?:\.[0-9]{1,6})?))$/;
var LONGITUDE_PATTERN = /^(\+|-)?(?:180(?:(?:\.0{1,6})?)|(?:[0-9]|[1-9][0-9]|1[0-7][0-9])(?:(?:\.[0-9]{1,6})?))$/;
var node = new Schema({
    phoneNumber: {
        type: String,
        minlength: [11, messages.min_length_phoneNumber],
        maxlength: [15, messages.max_length_phoneNumber],
        require: [true, messages.not_empty_phoneNumber],
        // unique: true,


    },
    lat: {
        type: String,
        require: [true, messages.empty_lat],
        // match: [LATITUDE_PATTERN, '{VALUE} is not a valid LATITUDE']
    },
    lng: {
        type: String,
        require: [true, messages.empty_lng],
        // match: [LONGITUDE_PATTERN, '{VALUE} is not a valid LONGITUDE']
    },
    description: {type: String},

    isDelete: {type: Boolean, default: false}
    // version: {type: Number, default: 1.0}
});

node.pre('save', function (next) {
    next();
    // try {
    //     this.phoneNumber = util.standardizePhoneNumber(this.phoneNumber);
    //     next();
    // } catch (err) {
    //     next(err);
    // }
});
// user.plugin(findOrCreate);


module.exports = mongoose.model('Node', node);
// module.exports = mongoose.model('User', user);