const express = require('express');
const router = express.Router();
const db = require('../../config');


// Get all data pezakat
router.get('/admin/pezakat', (req, res) => {
    const query = `SELECT * FROM pezakat`;

    db.query(query, (queryErr, rows) => {
        if (queryErr) {
            console.error('Error executing query:', queryErr);
            res.status(500).json({
                message: 'Terjadi kesalahan: ' + queryErr.message
            });
            return;
        }
        res.status(200).json(rows);
    });
});

// Get pezakat by id_pezakat
router.get('/admin/pezakat/:id_pezakat', (req, res) => {
    const inputIdPezakat = req.params.id_pezakat;

    const query = `SELECT * FROM user WHERE id_pezakat = ?`;

    db.query(query, [inputIdPezakat], (queryErr, rows) => {
        if (queryErr) {
            console.error('Error executing query:', queryErr);
            res.status(500).json({
                message: 'Terjadi kesalahan: ' + queryErr.message
            });
            return;
        }
        if (rows.length > 0) {
            res.status(200).json(rows[0]);
        } else {
            res.status(404).json({
                message: 'Pezakat tidak ditemukan'
            });
        }
    });
});

// Post pezakat
router.post('/admin/pezakat/add', (req, res) => {
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

// Update pezakat by id_pezakat
router.put('/admin/pezakat/update/:id_pezakat', (req, res) => {
    const inputIdPezakat = req.params.id_pezakat;

    const { nama_lengkap, telepon, email, id_provinsi } = req.body;

    if (!nama_lengkap || !telepon || !email || !id_provinsi) {
        return res.status(400).json({
            message: 'Mohon lengkapi semua data'
        });
    }

    if (telepon.toString().length !== 12) {
        return res.status(400).json({
            message: 'Nomor telepon harus 12 digit'
        });
    }

    if (!email.includes('@')) {
        return res.status(400).json({
            message: 'Email tidak valid'
        });
    }

    const connection = db;

    const checkQuery = `SELECT * FROM pezakat WHERE (telepon = ? OR email = ?) AND id_pezakat <> ?`;
    connection.query(checkQuery, [telepon, email, inputIdPezakat], (checkErr, checkResults) => {
        if (checkErr) {
            console.error(checkErr);
            return res.status(500).json({
                message: 'Terjadi kesalahan: ' + checkErr.message
            });
        }

        if (checkResults.length > 0) {
            return res.status(400).json({
                message: 'Nomor telepon atau email sudah digunakan oleh pengguna lain'
            });
        }

        const pezakatQuery = `UPDATE pezakat SET nama_lengkap = ?, telepon = ?, email = ?, id_provinsi = ? WHERE id_pezakat = ?`;
        const userQuery = `UPDATE user SET nama_lengkap = ?, telepon = ?, email = ?, id_provinsi = ? WHERE id_pezakat = ?`;

        connection.query(pezakatQuery, [nama_lengkap, telepon, email, id_provinsi, inputIdPezakat], (pezakatErr, pezakatResults) => {
            if (pezakatErr) {
                console.error(pezakatErr);
                return res.status(500).json({
                    message: 'Terjadi kesalahan: ' + pezakatErr.message
                });
            }

            connection.query(userQuery, [nama_lengkap, telepon, email, id_provinsi, inputIdPezakat], (userErr, userResults) => {
                if (userErr) {
                    console.error(userErr);
                    return res.status(500).json({
                        message: 'Terjadi kesalahan: ' + userErr.message
                    });
                }

                res.status(200).json({
                    message: 'Data akun anda berhasil diubah'
                });
            });
        });
    });
});


// Delete pezakat by id_pezakat
router.delete('/admin/pezakat/delete/:id_pezakat', (req, res) => {
    const inputIdPezakat = req.params.id_pezakat;

    const query = `DELETE FROM pezakat WHERE id_pezakat = ?`;

    db.query(query, [inputIdPezakat], (queryErr, result) => {
        if (queryErr) {
            console.error('Error executing query:', queryErr);
            res.status(500).json({
                message: 'Terjadi kesalahan: ' + queryErr.message
            });
            return;
        }
        if (result.affectedRows > 0) {
            res.status(200).json({
                message: 'Pezakat berhasil dihapus'
            });
        } else {
            res.status(404).json({
                message: 'Pezakat tidak ditemukan'
            });
        }
    });
});



module.exports = router;