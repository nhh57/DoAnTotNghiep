'use strict'

const express = require('express')
const accessController = require('../../controllers/authController')
const asyncHandler = require("../../helpers/asyncHandler");
const {authenticationV2} = require("../../auth/auth.Utils");
const router = express.Router()

router.post('/signup', asyncHandler(accessController.signUp))

router.post('/login', asyncHandler(accessController.login))
router.use(authenticationV2)
router.post('/logout', asyncHandler(accessController.logout))
router.post('/handler-refresh-token', asyncHandler(accessController.handlerRefreshToken))

module.exports = router;