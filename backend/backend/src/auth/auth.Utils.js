'use strict'

const JWT = require('jsonwebtoken')
const asyncHandler = require('../helpers/asyncHandler')
const {next} = require("lodash/seq");
const {BadRequestError, AuthFailureError, NotFoundError} = require("../core/error.response");
const {findByUserId} = require('../service/keyToken.service')


const HEADER = {
    // API_KEY: 'x-api-key',
    CLIENT_ID: 'x-client-id',
    AUTHORIZATION: 'authorization',
    REFRESH_TOKEN: 'x-rtoken-id',
}


const authenticationV2 = asyncHandler(async (req, res, next) => {
    console.log('auth.Utils - authenticationV2')
    const userId = req.headers[HEADER.CLIENT_ID]
    console.log("userId:: %s", userId)
    if (!userId) throw new AuthFailureError('Invalid Request')

    //2
    const keyStore = await findByUserId(userId)

    if (!keyStore) throw new NotFoundError('Not Found KeyStore')

    // 3
    if (req.headers[HEADER.REFRESH_TOKEN]) {
        try {
            const refreshToken = req.headers[HEADER.REFRESH_TOKEN]
            console.log("refreshToken:: %s", refreshToken)
            const decodeUser = JWT.verify(refreshToken, keyStore.private_key)
            console.log("decodeUser3::%s", decodeUser)
            if (userId !== decodeUser.userId) throw new AuthFailureError('Invalid UserId')
            req.keyStore = keyStore
            req.user = decodeUser
            req.refreshToken = refreshToken
            return next()
        } catch (error) {
            throw error
        }
    }

    const accessToken = req.headers[HEADER.AUTHORIZATION]
    console.log("accessToken4::%s", accessToken)
    if (!accessToken) throw new AuthFailureError('Invalid Request')

    try {
        const decodeUser = JWT.verify(accessToken, keyStore.public_key)
        if (userId !== decodeUser.userId) throw new AuthFailureError('Invalid Request')
        req.keyStore = keyStore
        req.user = decodeUser
        return next()
    } catch (error) {
        throw error
    }
})


const createTokenPair = async (payload, publicKey, privateKey) => {
    try {
        // accessToken
        const accessToken = await JWT.sign(payload, publicKey, {
            // algorithm:'RS256',
            expiresIn: '2 days'
        })

        const refreshToken = await JWT.sign(payload, privateKey, {
            // algorithm:'RS256',
            expiresIn: '7 days'
        })
        //
        JWT.verify(accessToken, publicKey, (err, decode) => {
            if (err) {
                console.log(`error verify::`, err)
            } else {
                console.log(`decode verify::`, decode)
            }
        })
        return {accessToken, refreshToken}
    } catch (error) {
        throw error
    }
}

const verifyJWT = async (token, keySecret) => {
    return await JWT.verify(token, keySecret)
}
module.exports = {
    createTokenPair,
    // authentication,
    verifyJWT,
    authenticationV2,

}