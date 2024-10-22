const express = require('express');
const { body } = require('express-validator');
const authController = require('../controllers/authController');
const User = require('../models/user');

const router = express.Router();

router.post('/signup',
    [
        body('FullName')
            .trim()
            .not().isEmpty()
            .withMessage('Full Name cannot be empty.'),
        
        body('Email')
            .isEmail()
            .withMessage('Please enter a valid email.')
            .custom(async (email) => {
                const user = await User.find({ where: { email } });
                if (user) {
                    return Promise.reject('Email address already exists!');
                }
            })
            .normalizeEmail(),
        
        body('Password')
            .trim()
            .isLength({ min: 7 })
            .withMessage('Password must be at least 7 characters long.'),
        
        body('PhoneNumber')
            .isMobilePhone()
            .withMessage('Please enter a valid phone number.')
            .optional({ checkFalsy: true }), // Optional validation if the phone number is provided
        
        body('Role')
            .trim()
            .isIn(['user', 'admin'])
            .withMessage('Role must be either user or admin.'),
        
        body('Status')
            .trim()
            .isIn(['Active', 'Inactive'])
            .withMessage('Status must be either Active or Inactive.')
            .optional({ checkFalsy: true }) // Optional, default can be set in model
    ],
    authController.signup
);

module.exports = router;
