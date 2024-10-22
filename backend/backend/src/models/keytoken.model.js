'use strict';
const db = require('../config/db'); // Đảm bảo đường dẫn chính xác tới file db.js

class KeytokenModel {
    constructor(userId, publicKey, privateKey, refreshTokensUsed, refreshToken) {
        this.userId = userId;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.refreshTokensUsed = refreshTokensUsed;
        this.refreshToken = refreshToken;
    }

    static createOrUpdateKeyToken({ userId, publicKey, privateKey, refreshToken }) {
        console.log("userId:: %s, publicKey:: %s, privateKey:: %s, refreshToken:: %s", userId, publicKey, privateKey, refreshToken);

        const querySelect = 'SELECT * FROM key_token WHERE user_id = ?';
        return new Promise((resolve, reject) => {
            db.execute(querySelect, [userId], (err, rows) => {
                if (err) {
                    reject(new Error('Error fetching key token: ' + err.message));
                    return;
                }

                if (rows.length > 0) {
                    console.log("Update::::::");
                    const queryUpdate = `
                        UPDATE key_token
                        SET public_key = ?,
                            private_key = ?,
                            refresh_tokens_used = ?,
                            refresh_token = ?
                        WHERE user_id = ?`;

                    db.execute(
                        queryUpdate,
                        [publicKey, privateKey, JSON.stringify([]), refreshToken || '', userId],
                        (updateErr) => {
                            if (updateErr) {
                                reject(new Error('Error updating key token: ' + updateErr.message));
                                return;
                            }
                            resolve(publicKey); // Trả về publicKey
                        }
                    );
                } else {
                    console.log("INSERT::::::");
                    const queryInsert = `
                        INSERT INTO key_token (user_id, public_key, private_key, refresh_tokens_used, refresh_token)
                        VALUES (?, ?, ?, ?, ?)`;

                    db.execute(
                        queryInsert,
                        [userId, publicKey, privateKey, JSON.stringify([]), refreshToken || ''], // Thay thế refreshToken bằng chuỗi rỗng nếu undefined
                        (insertErr) => {
                            if (insertErr) {
                                reject(new Error('Error inserting key token: ' + insertErr.message));
                                return;
                            }
                            resolve(publicKey); // Trả về publicKey
                        }
                    );
                }
            });
        });
    }
}


module.exports = KeytokenModel;