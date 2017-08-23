/**
 * Created by phamquangkhang on 6/5/17.
 */

'use strict';

var express = require('express');
var controller = require('./log.controller');
var auth = require('../auth/service');
var roles = require('../auth/auth.config');
var userService = require('../user/user.service');
var router = express.Router();
var passport = require('passport');


// router.post("/add", controller.addNode);
router.get("/findAll", auth.hasRole(roles.userRoles[1]), userService.checkAcceptAble(), controller.findAll);
// router.get("/viewlog", auth.hasRole(roles.userRoles[1]), userService.checkAcceptAble(), controller.viewLog);
// router.get("/log", auth.hasRole(roles.userRoles[1]), userService.checkAcceptAble(), controller.showLog);
router.post("/viewLogByTarget", auth.hasRole(roles.userRoles[1]), userService.checkAcceptAble(), controller.viewTargetLog);
router.post("/mobileViewLog",passport.authenticate('google-id-token'), auth.hasRole(roles.userRoles[0]), userService.checkAcceptAble(), controller.mobileViewLog);
// router.post("/mobileViewLogTest", controller.mobileViewLog);

router.post("/sync", passport.authenticate('google-id-token'), auth.hasRole(roles.userRoles[0]), userService.checkAcceptAble(), controller.mobileSync);//release
// router.post("/syncTest", controller.mobileSync);//for test


module.exports = router;