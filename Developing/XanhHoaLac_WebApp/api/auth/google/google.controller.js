module.exports = {

    logout: function (req, res) {
        req.logout();
        res.redirect('/');
    },
    loginSuccess: function (req,res) {
        res.sendfile('./client/success.html');
    },
    loginFail:function (req,res) {
        res.sendfile('./client/fail.html')
    }


}