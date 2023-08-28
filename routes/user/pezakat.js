const express = require('express');
const router = express.Router();
const db = require('../../config');


// Get data pezakat by id
router.get('/pezakat/:id_pezakat', (req, res) => {
    const inputIdPezakat = req.params.id_pezakat;

    const connection = db;

    const query = `SELECT * FROM user WHERE id_pezakat = ?`;

    connection.query(query, [inputIdPezakat], (err, results) => {
        if (err) {
            console.error(err);
            res.status(500).json({
                message: 'Terjadi kesalahan: ' + err.message
            });
        } else {
            if (results.length > 0) {
                res.status(200).json(results[0]);
            } else {
                res.status(404).json({
                    message: 'Data pezakat tidak ditemukan'
                });
            }
        }
    });
});

// Update data pezakat by id
router.put('/pezakat/update/:id_pezakat', (req, res) => {
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



module.exports = router;