const express = require('express');
const router = express.Router();
const db = require('../../config');
const bcrypt = require('bcrypt');


// Post register
router.post('/pezakat/register', (req, res) => {
    const { nama_lengkap, telepon, email, password, id_provinsi } = req.body;

    if (!nama_lengkap || !telepon || !email || !password || !id_provinsi) {
        res.status(400).json({
            message: 'Semua kolom wajib diisi'
        });
        return;
    }

    if (telepon.toString().length !== 12) {
        res.status(400).json({
            message: 'Nomor telepon minimal 12 digit'
        });
        return;
    }

    if (!email.includes('@')) {
        res.status(400).json({
            message: 'Email tidak valid'
        });
        return;
    }

    if (password.length < 8) {
        res.status(400).json({
            message: 'Password minimal 8 karakter'
        });
        return;
    }

    const connection = db;

    const checkExistingQuery = `SELECT * FROM pezakat WHERE email = ? OR telepon = ?`;
    connection.query(checkExistingQuery, [email, telepon], (checkExistingErr, existingResult) => {
        if (checkExistingErr) {
            console.error('Error checking existing data:', checkExistingErr);
            res.status(500).json({
                message: 'Terjadi kesalahan: ' + checkExistingErr.message
            });
            return;
        }

        if (existingResult.length > 0) {
            res.status(400).json({
                message: 'Email atau telepon sudah digunakan'
            });
            return;
        }

        const pezakatQuery = `INSERT INTO pezakat (nama_lengkap, telepon, email, password, id_provinsi) VALUES (?, ?, ?, ?, ?)`;
        const userQuery = `INSERT INTO user (nama_lengkap, telepon, email, password, id_provinsi, id_role, id_pezakat) VALUES (?, ?, ?, ?, ?, 2, ?)`;

        bcrypt.hash(password, 10, (err, hash) => {
            if (err) {
                res.status(500).json({
                    message: 'Terjadi kesalahan: ' + err.message
                });
                return;
            }

            connection.query(pezakatQuery, [nama_lengkap, telepon, email, hash, id_provinsi], (pezakatQueryErr, pezakatResult) => {
                if (pezakatQueryErr) {
                    console.error('Error executing pezakat query:', pezakatQueryErr);
                    res.status(500).json({
                        message: 'Terjadi kesalahan: ' + pezakatQueryErr.message
                    });
                    return;
                }
                
                const id_pezakat = pezakatResult.insertId;

                connection.query(userQuery, [nama_lengkap, telepon, email, hash, id_provinsi, id_pezakat], (userQueryErr, userResult) => {
                    if (userQueryErr) {
                        console.error('Error executing user query:', userQueryErr);
                        res.status(500).json({
                            message: 'Terjadi kesalahan: ' + userQueryErr.message
                        });
                        return;
                    }
                    
                    res.status(201).json({
                        message: 'Akun berhasil didaftarkan',
                    });
                });
            });
        });
    });
});

// Post login
router.post('/user/login', (req, res) => {
    const { email, password } = req.body;

    if (!email || !password) {
        res.status(400).json({
            message: 'Semua kolom wajib diisi'
        });
        return;
    }

    if (!email.includes('@')) {
        res.status(400).json({
            message: 'Email tidak valid'
        });
        return;
    }

    const connection = db;

    const query = `SELECT * FROM user WHERE email = ?`;

    connection.query(query, [email], (queryErr, rows) => {
        if (queryErr) {
            console.error('Error executing query:', queryErr);
            res.status(500).json({
                message: 'Terjadi kesalahan: ' + queryErr.message
            });
            return;
        }

        if (rows.length === 0) {
            res.status(401).json({
                message: 'Email tidak terdaftar!'
            });
            return;
        }

        const user = rows[0];

        bcrypt.compare(password, user.password, (err, result) => {
            if (err) {
                res.status(500).json({
                    message: 'Terjadi kesalahan saat membandingkan password'
                });
                return;
            }

            if (result) {
                res.status(200).json(user);
            } else {
                if (password === user.password) {
                    const newHash = bcrypt.hashSync(password, 10);
                    const updateQuery = `UPDATE user SET password = ? WHERE id_user = ?`;
                    db.query(updateQuery, [newHash, user.id_user], (updateQueryErr, updateResult) => {
                        if (updateQueryErr) {
                            console.error('Error updating password:', updateQueryErr);
                            res.status(500).json({
                                message: 'Terjadi kesalahan: ' + updateQueryErr.message
                            });
                            return;
                        }

                        res.status(200).json(user);
                    });
                } else {
                    res.status(401).json({
                        message: 'Password salah!'
                    });
                }
            }
        });
    });
});



module.exports = router;