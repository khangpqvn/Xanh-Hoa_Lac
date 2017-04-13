/**
 * Created by phamquangkhang on 4/12/17.
 */
'use strict';

var passport = require('passport');
var GoogleStrategy = require('passport-google-oauth').OAuth2Strategy;
var configGoogleServer = require('./auth-example.json').google;


module.exports = function () {

    passport.serializeUser(function (user, done) {
        // done(null, user.id);
        done(null, user);
    });

    passport.deserializeUser(function (obj, done) {
        // Users.findById(obj, done);
        done(null, obj);
    });


// Use the GoogleStrategy within Passport.
//   Strategies in Passport require a `verify` function, which accept
//   credentials (in this case, an accessToken, refreshToken, and Google
//   profile), and invoke a callback with a user object.
//   See http://passportjs.org/docs/configure#verify-callback
    passport.use(new GoogleStrategy(
        // Use the API access settings stored in ./config/auth.json. You must create
        // an OAuth 2 client ID and secret at: https://console.developers.google.com
        configGoogleServer,
        function (accessToken, refreshToken, profile, done) {
                console.log('id : '+ profile.id);
                console.log('name :'+ profile.displayName);
                console.log('email :' + profile.emails);
                return done(null,true);
        }
    ));

}
