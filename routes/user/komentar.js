const express = require('express');
const router = express.Router();
const db = require('../../config');


// Post data komentar by id_post_feed
router.post('/user/:id_user/:id_post_feed/komentar/add', (req, res) => {
    const { id_user, id_post_feed } = req.params;
    const { komentar } = req.body;

    if (komentar === '') {
        res.status(400).json({
            message: 'Semua bagian harus diisi'
        });
        return;
    }

    const getUserInfoQuery = `
        SELECT nama_lengkap
        FROM user
        WHERE id_user = ?
    `;

    db.query(getUserInfoQuery, [id_user], (getUserError, userRows) => {
        if (getUserError) {
            console.error(getUserError);
            res.status(500).json({
                message: 'Terjadi kesalahan: ' + getUserError.message
            });
            return;
        }

        if (userRows.length === 0) {
            res.status(404).json({
                message: 'User tidak ditemukan'
            });
            return;
        }

        const nama = userRows[0].nama_lengkap;

        const insertQuery = `
            INSERT INTO komentar (id_post_feed, id_user, nama, komentar)
            VALUES (?, ?, ?, ?)
        `;

        db.query(insertQuery, [id_post_feed, id_user, nama, komentar], (insertError, result) => {
            if (insertError) {
                console.error('Error executing query:', insertError);
                res.status(500).json({
                    message: 'Terjadi kesalahan: ' + insertError.message
                });
                return;
            }
            res.status(201).json({
                message: 'Komentar berhasil ditambahkan'
            });
        });
    });
});



module.exports = router;