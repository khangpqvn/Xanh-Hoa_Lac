/**
 * Created by phamquangkhang on 4/13/17.
 */
'use strict';

var express = require('express');

var router = express.Router();
var User = require('../user/user.model');

require("./google/google.passport")(User);
require("./google_id_token/google_id_token.passport")(User);

/***************************** api/auth  *****************************/

router.use("/idtoken",require("./google_id_token"));
router.use("/google", require("./google"));
module.exports = router;