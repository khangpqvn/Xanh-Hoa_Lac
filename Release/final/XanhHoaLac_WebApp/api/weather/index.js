/**
 * Created by phamquangkhang on 4/21/17.
 */
'use strict';

var express = require('express');
var controller = require('./weather.controller');
var router = express.Router();


/***************************** api/user  *****************************/
router.get("/view", controller.view);
module.exports = router;