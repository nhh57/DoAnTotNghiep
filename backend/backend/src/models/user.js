const db = require('../config/db'); // Đảm bảo đường dẫn chính xác tới file db.js

module.exports = class User {
    constructor(UserID, FullName, Email, Password, Role, PhoneNumber, Status) {
        this.UserID = UserID;
        this.FullName = FullName;
        this.Email = Email;
        this.Password = Password;
        this.Role = Role;
        this.PhoneNumber = PhoneNumber;
        this.Status = Status;
    }


    // Lưu user mới vào database
    static save(user) {
        return new Promise((resolve, reject) => {
            const query = `
                INSERT INTO users (fullname, email, password, phone_number, role, status)
                VALUES (?, ?, ?, ?, ?, ?)
            `;

            // Thực hiện truy vấn
            db.execute(query, [
                user.FullName,
                user.Email,
                user.Password,
                user.PhoneNumber,
                user.Role || 'user',  // Nếu không có giá trị Role thì mặc định là 'user'
                user.Status || 'active' // Nếu không có giá trị Status thì mặc định là 'active'
            ], (err, result) => {
                if (err) {
                    console.error('Error inserting user:', err.message); // Log lỗi
                    return reject(err); // Ném lỗi ra
                }

                // Kiểm tra kết quả của query, nếu không có hàng nào bị ảnh hưởng
                if (result.affectedRows === 0) {
                    return reject(new Error('User insert failed'));
                }

                // Trả về kết quả
                resolve(result.insertId); // Gọi resolve với id của người dùng mới
            });
        });
    }


    static async findUserByEmail(email) {
        const query = `SELECT *
                       FROM users
                       WHERE email = ?`;

        return new Promise((resolve, reject) => {
            db.execute(query, [email], (err, rows) => {
                if (err) {
                    reject(new Error('Error finding user: ' + err.message));
                    return;
                }
                resolve(rows.length > 0 ? rows[0] : null); // Trả về thông tin người dùng nếu tìm thấy, nếu không thì null
            });
        });
    }

};