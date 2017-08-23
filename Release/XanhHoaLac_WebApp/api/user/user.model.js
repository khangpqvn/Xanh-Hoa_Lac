/**
 * Created by phamquangkhang on 4/21/17.
 */
'use strict';

var mongoose = require('mongoose');
mongoose.Promise = require('bluebird');
var Schema = mongoose.Schema;

// var findOrCreate = require('mongoose-find-one-or-create');
var config = require('../auth/auth.config');
var messages = require("./user.message.json").messages;
var emailRegex = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/i;


var user = new Schema({
    username: {
        type: String,
        lowercase: true,
        require: true,
        match: [emailRegex, messages.email_invalid],
        trim:true
    },
    name: {
        type: String,
        require: true,
        uppercase: true,
        trim:true
    },
    role: {
        type: String,
        default: config.userRoles[0],
        enum: {
            values: config.userRoles,
            message: messages.role_invalid
        },
        lowercase: true,
        require: true,
        trim:true

    },
    isBlock: {//true is the same as:  user is existed but have been block by admin and can' login to the system
        type: Boolean,
        default: false,
        require: true
    },
    // nodes:[{type: Number, ref: "Node"}]
});

// user.plugin(findOrCreate);

user.pre('save', function (next) {
    // Handle new user update role
    if (config.domain.indexOf(this.username.toLowerCase().trim().split("@")[1]) >= 0) {//validate domain is accepted}
        this.name = this.username.split("@")[0];
        return next();
    } else return next(new Error(messages.domain_err));

});


module.exports = mongoose.model('User', user);