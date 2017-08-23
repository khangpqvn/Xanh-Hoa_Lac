var authConfig = require('../auth.config');
module.exports = {

    logout: function (req, res) {
        req.session.destroy(function (err) {
            // req.flash('info', 'Flash Message Added');
            res.redirect('/'); //Inside a callback… bulletproof!
        });
    },
    loginSuccess: function (req, res) {
        // console.log(req);
        // console.log("Domain: " + config.DOMAIN);
        // console.log(req.user._json.domain);
        //
        // if (req.user._json.domain && config.DOMAIN.toLowerCase().includes(req.user._json.domain.toLowerCase())) {

        // console.log(req.user.role);
        if (req.user && req.user.role) {
            if (authConfig.userRoles.indexOf(req.user.role) == 0 || req.user.isBlock) {
                require('./google.controller').logout(req, res);
            } else {
                if (req.isAuthenticated())
                    res.redirect("/homepage");
            }
        } else {
            require('./google.controller').logout(req, res);
        }
    },
    loginFail: function (req, res) {
        res.redirect("/");
    }
}