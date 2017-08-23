/**
 * Created by phamquangkhang on 4/15/17.
 */
'use strict';
var express = require('express');
var controller = require('./google_id_token.controller.js');
var passport = require('passport');
var router = express.Router();
//post to api/auth/idtoken with json like: "id_token" : "abcxyzt"
/***************************** api/auth/idtoken  *****************************/


router.post('/', passport.authenticate('google-id-token'), controller.doAuthen);

module.exports = router;