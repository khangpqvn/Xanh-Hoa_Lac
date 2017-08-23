/**
 * Created by phamquangkhang on 4/15/17.
 */
var GoogleTokenStrategy = require('passport-google-id-token');
var passport = require('passport');
var configGoogleServer = require('../google.server.config.json');
module.exports = function (User) {
    passport.use(new GoogleTokenStrategy({
            clientID: configGoogleServer.clientID
            // ,getGoogleCerts: optionalCustomGetGoogleCerts
        },
        function (parsedToken, googleId, done) {
            // console.log("parsedToken: " + parsedToken.payload.email);
            if (parsedToken.payload.email) {
                User.findOne({username: parsedToken.payload.email.toLowerCase().trim()}).exec(
                    function (err, data) {
                        if (data) {
                            parsedToken.payload.role = data.role;
                            parsedToken.payload.isBlock = data.isBlock;
                            return done(err, parsedToken.payload);
                        }
                    }
                )
            } else {
                return done(null, parsedToken.payload);
            }

//todo xac dinh xem role cua nguoi dang nhap la gi roi gan
//             return done(null, parsedToken.payload);
            // User.findOrCreate({ googleId: googleId }, function (err, user) {
            //     return done(err, user);
            // });
        }
    ));
}
