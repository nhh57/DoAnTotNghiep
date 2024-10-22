'use strict'


const KeytokenModel = require("../models/keytoken.model"); // Đảm bảo đường dẫn chính xác tới file keytokenModel.js

class KeyTokenService {
    static async createKeyToken({ userId, publicKey, privateKey, refreshToken }) {
        try {
            const result = await KeytokenModel.createOrUpdateKeyToken({ userId, publicKey, privateKey, refreshToken });
            return result; // Trả về publicKey
        } catch (error) {
            console.error('Error in KeyTokenService:', error);
            throw error; // Ném lỗi ra để xử lý ở tầng trên
        }
    }
}

module.exports = KeyTokenService;
