/**
 * Created by phamquangkhang on 4/21/17.
 */
var configRoles = require('./auth.config');
var compose = require('composable-middleware');

module.exports = {
    hasRole: function (roleRequired) {
        if (!roleRequired) {
            throw new Error('Required role needs to be set');
        }
        return compose()
            .use(require("./service").ensureAuthenticated)
            .use(function (req, res, next) {
                if (configRoles.userRoles.indexOf(req.user.role) >=
                    configRoles.userRoles.indexOf(roleRequired)) {
                    next();
                } else {
                    // req.logout();
                    // res.status(403).send('Bạn không đủ quyền để thực hiện hành vi này');
                    // next(new Error('Bạn không đủ quyền để thực hiện hành vi này'));
                    res.json({status: false, message: 'Bạn không đủ quyền để thực hiện hành vi này'});

                }
            });
    },
    // Simple route middleware to ensure user is authenticated.
    ensureAuthenticated: function (req, res, next) {
        // console.log(req.isAuthenticated());
        if (req.isAuthenticated()) {
            return next();
        } else
        // res.json({status: false, message: "You have not logged in !!!"});
            res.redirect("/");
    },


}

