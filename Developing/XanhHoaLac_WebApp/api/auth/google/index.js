/**
 * Created by phamquangkhang on 4/12/17.
 */
'use strict';

var express = require('express');
var controller = require('./google.controller.js');
var passport = require('passport');

var router = express.Router();

require('./google.passport');

router.get("/logout", controller.logout);
router.get('/login', passport.authenticate('google', {
    scope: [
        'https://www.googleapis.com/auth/plus.me',
        'https://www.googleapis.com/auth/plus.login',
        'https://www.googleapis.com/auth/plus.profile.emails.read']
}));
router.get('/success', ensureAuthenticated, controller.loginSuccess);
router.get('/fail', ensureAuthenticated, controller.loginFail);
router.get('/callback', passport.authenticate('google', {
    successRedirect: '/api/auth/google/success',
    failureRedirect: '/api/auth/google/fail'
}));

// Simple route middleware to ensure user is authenticated.
function ensureAuthenticated(req, res, next) {
    console.log(req.isAuthenticated());
    if (req.isAuthenticated()) {
        return next();
    } else
        res.redirect('/api/auth/google/login');
}
module.exports = router;