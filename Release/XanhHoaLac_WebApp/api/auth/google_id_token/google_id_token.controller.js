/**
 * Created by phamquangkhang on 4/15/17.
 */
var passport = require('passport');
var config = require('../../../config');
// var domain = config.DOMAIN.split(",");

module.exports = {
    doAuthen: function (req, res) {
        // do something with req.user
        // console.log("Do authen req.user : " + req.user);
        // console.log("Do authen req.user.email : " + req.user.email);
        // console.log("Do authen req.user.name : " + req.user.name);
        // console.log("Do authen req.user.picture : " + req.user.picture);
        // console.log("Do authen req.user.given_name : " + req.user.given_name);
        // console.log("Do authen req.user.family_name : " + req.user.family_name);
        // console.log("Do authen req.user.locale : " + req.user.locale);
        //Todo: Check email ton tai trong db ko. Neu ton tai thi tra ve vi tri cac node
        // res.send(req.user? 200 : 401);
        // res.json({status: true});
        if (req.user.role) {
            if (req.user.isBlock) {
                req.session.destroy(function (err) {
                    res.json({status: false, message: "Tài khoản đã bị khóa"});
                });
            } else {
                res.json({status: true,role:req.user.role,message: "Đăng nhập dưới quyền: "+req.user.role+" "+req.user.name});

            }
        } else {
            res.json({status: false, message: "Không tìm thấy tài khoản trong hệ thống!"});
        }
    }

}