/**
 * Created by phamquangkhang on 4/13/17.
 */
'use strict';

var express = require('express');

var router = express.Router();

require("./google/google.passport")();

router.use("/google", require("./google"));
module.exports = router;