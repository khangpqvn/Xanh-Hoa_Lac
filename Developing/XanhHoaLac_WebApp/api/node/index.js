/**
 * Created by phamquangkhang on 4/21/17.
 */
'use strict';

var express = require('express');
var controller = require('./node.controller');
var auth = require('../auth/service');
var roles = require('../auth/auth.config');
var userService = require('../user/user.service');
var passport = require('passport');

var router = express.Router();
router.post("/add", auth.hasRole(roles.userRoles[1]), userService.checkAcceptAble(), controller.addNode);
// router.post("/add", controller.addNode);
router.post("/edit", auth.hasRole(roles.userRoles[1]), userService.checkAcceptAble(), controller.modifyNode);
// router.post("/delete", auth.hasRole(roles.userRoles[1]), userService.checkAcceptAble(), controller.deleteNode);
router.get("/findAll", auth.hasRole(roles.userRoles[1]), userService.checkAcceptAble(), controller.findAll);


router.post("/sync", passport.authenticate('google-id-token'), auth.hasRole(roles.userRoles[0]), userService.checkAcceptAble(), controller.syncNode);//release
router.post("/syncTest", controller.syncNode);//for test


module.exports = router;