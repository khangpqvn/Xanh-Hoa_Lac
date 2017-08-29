/**
 * Created by phamquangkhang on 4/28/17.
 */
'use strict';

var authConfig = require("./auth/auth.config");
var fs = require("fs");
var messages = require("./message");
module.exports = {
    CompareRole1IsSmallerThanRole2: function (role1, role2) {
        return authConfig.userRoles.indexOf(role1) < authConfig.userRoles.indexOf(role2) ? true : false;
    },
    replaceAll: function (input, searchVal, replaceVal) {
        while (input.indexOf(searchVal) >= 0) {
            (input = input.replace(searchVal, replaceVal));
        }
        return input;
    },

    standardizePhoneNumber: function (phoneNumber) {
        phoneNumber = require("./util").replaceAll(phoneNumber, " ", "");
        if (phoneNumber.length > 2) {
            if (!/\D/.test(phoneNumber)) {
                if(!(phoneNumber.substring(0, 2) === "84" || phoneNumber.substring(0, 1) === "0")){
                    throw new Error(messages.phoneNumber.start_with);
                }
                if (phoneNumber.substring(0, 2) === "84" && (phoneNumber.length >= 11)) {
                    return phoneNumber;
                } else if (phoneNumber.substring(0, 1) === "0" && (phoneNumber.length >= 10)) {
                    return "84" + phoneNumber.substring(1);
                } else {
                    throw new Error(messages.phoneNumber.min_length);
                }
            } else {
                throw new Error(messages.phoneNumber.invalid);
            }
        } else {
            throw new Error(messages.phoneNumber.invalid);
        }

    },
    getVersion: function (versionFile) {
        return fs.readFileSync(versionFile, "utf8")
    },
    changeVersion: function (versionFile) {
        fs.writeFile(versionFile, +new Date(), function (err) {
            if (err)
                require("./logger").error(String(err));
        })
    }


}