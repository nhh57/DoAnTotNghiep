'use strict'
const User = require("../models/user");
const {BadRequestError, AuthFailureError, ForbiddenError} = require("../core/error.response");
const bcrypt = require('bcrypt')
const crypto = require('crypto')
const KeyTokenService = require("../service/keyToken.service")
const {createTokenPair} = require("../auth/auth.Utils");

class AccessService {


    static login = async ({email, password, refreshToken = null}) => {
        console.log("login:: email %s :: password %s :: refreshToken %s", email, password, refreshToken)
        //1.
        const foundShop = await User.findUserByEmail(email)
        console.log('User found:', foundShop); // In ra thông tin người dùng nếu tìm thấy

        if (!foundShop) throw new BadRequestError('Shop not registered!')
        //2.
        const match = bcrypt.compare(password, foundShop.password)
        if (!match) throw new AuthFailureError('Authentication error')
        //3.
        // create privateKey, publicKey
        const privateKey = crypto.randomBytes(64).toString('hex')
        const publicKey = crypto.randomBytes(64).toString('hex')
        //4. generate tokens
        const {id: userId} = foundShop

        const tokens = await createTokenPair({userId, email}, publicKey, privateKey)

        await KeyTokenService.createKeyToken({
            userId,
            publicKey,
            privateKey,
            refreshToken: tokens.refreshToken,

        })
        return {
            // shop: {
            //     // "user_id": newShop,
            //     // "name": name,
            //     // "email": email
            // },
            tokens
        }
    }





    static signUp = async ({name, email, password}) => {
        console.log("signUp:: name %s :: email %s :: password %s", name, email, password)
        const holderShop = await User.findUserByEmail(email);
        console.log('holderShop::', holderShop)
        if (holderShop) {
            throw new BadRequestError('Error Shop already registered!')
        }
        const passwordHash = await bcrypt.hash(password, 10)

        // Lưu người dùng mới
        const newShop = await User.save({
            FullName: name,
            Email: email,
            Password: passwordHash,
            PhoneNumber: "0346135365",
            Role: "user", // Có thể để mặc định là 'user'
            Status: "active" // Có thể để mặc định là 'active'
        });
        if (newShop) {
            const privateKey = crypto.randomBytes(64).toString('hex')
            const publicKey = crypto.randomBytes(64).toString('hex')

            console.log({privateKey, publicKey}) //save collection KeyStore

            const keyStore = await KeyTokenService.createKeyToken({
                userId: newShop,
                publicKey,
                privateKey
            })
            if (!keyStore) {
                throw new BadRequestError('KeyStore Error')
                // return {
                //     code :'xxxx',
                //     message :'keyStore error'
                // }
            }
            // create token pair
            const tokens = await createTokenPair({userId: newShop, email}, publicKey, privateKey)
            console.log(`Created Tokens Success::`, tokens)
            return {
                code: 201,
                metadata: {
                    shop: {
                        "user_id": newShop,
                        "name": name,
                        "email": email
                    },
                    tokens
                }
            }
        }

        return {
            code: 200,
            metadata: null
        }

        // } catch (error) {
        //     console.error(error)
        //     return {
        //         code: 'XXX',
        //         message: error.message,
        //         status: 'error'
        //     }
        // }
    }

}





module.exports = AccessService