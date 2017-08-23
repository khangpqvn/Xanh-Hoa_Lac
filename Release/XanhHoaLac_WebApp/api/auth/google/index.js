/**
 * Created by phamquangkhang on 4/12/17.
 */
'use strict';

var express = require('express');
var controller = require('./google.controller.js');
var passport = require('passport');
var authService = require("../service");
var router = express.Router();

require('./google.passport');

/***************************** api/auth/google  *****************************/

router.get('/callback', passport.authenticate('google', {
    failureRedirect: '/api/auth/google/fail',
    successRedirect: '/api/auth/google/success'
}));

router.get('/login', passport.authenticate('google', {
    scope: [
        'https://www.googleapis.com/auth/plus.me',
        'https://www.googleapis.com/auth/plus.login',
        'https://www.googleapis.com/auth/plus.profile.emails.read'
    ]
}));
router.get('/success', authService.ensureAuthenticated, controller.loginSuccess);
router.get('/fail', controller.loginFail);

router.get("/logout", controller.logout);

module.exports = router;