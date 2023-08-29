const express = require('express');
const router = express.Router();
const db = require('../../config');
const multer = require('multer');

const storage = multer.memoryStorage();
const upload = multer({ storage: storage });

// Get all post_feed
router.get('/user/post_feed/all', (req, res) => {
    const getAllPostFeedQuery = `
        SELECT post_feed.id_post_feed, post_feed.id_user, post_feed.id_provinsi, post_feed.judul_post, post_feed.biaya, post_feed.alamat, post_feed.keterangan, post_feed.status, post_feed.tanggal_post, user.nama_lengkap, provinsi.nama_provinsi, user.telepon
        FROM post_feed
        INNER JOIN user ON post_feed.id_user = user.id_user, user.telepon
        INNER JOIN provinsi ON post_feed.id_provinsi = provinsi.id_provinsi
        ORDER BY post_feed.tanggal_post DESC
    `;
    db.query(getAllPostFeedQuery, (getAllPostFeedError, allPostFeedRows) => {
        if (getAllPostFeedError) {
            console.error(getAllPostFeedError);
            return res.status(500).send('Terjadi kesalahan saat mengambil post_feed');
        }

        res.status(200).json(allPostFeedRows);
    });
});

// Get post_feed by id_post_feed
router.get('/user/post_feed/:id_post_feed', (req, res) => {
    const { id_post_feed } = req.params;

    const getPostFeedQuery = `
        SELECT post_feed.id_post_feed, post_feed.id_user, post_feed.id_provinsi, post_feed.judul_post, post_feed.biaya, post_feed.alamat, post_feed.keterangan, post_feed.status, post_feed.tanggal_post, user.nama_lengkap, user.telepon, provinsi.nama_provinsi, role.nama_role
        FROM post_feed
        INNER JOIN user ON post_feed.id_user = user.id_user
        INNER JOIN provinsi ON post_feed.id_provinsi = provinsi.id_provinsi
        INNER JOIN role ON user.id_role = role.id_role
        WHERE post_feed.id_post_feed = ?
    `;
    db.query(getPostFeedQuery, [id_post_feed], (getPostFeedError, postFeedRows) => {
        if (getPostFeedError) {
            console.error(getPostFeedError);
            return res.status(500).send('Terjadi kesalahan saat mengambil post_feed');
        }

        if (postFeedRows.length === 0) {
            return res.status(404).send('Post feed tidak ditemukan');
        }

        res.status(200).json(postFeedRows[0]);
    });
});

// Get dokumen post_feed by id_post_feed
router.get('/user/post_feed/:id_post_feed/dokumen', (req, res) => {
    const { id_post_feed } = req.params;

    const getDokumenQuery = `
        SELECT dokumen
        FROM post_feed
        WHERE id_post_feed = ?
    `;
    db.query(getDokumenQuery, [id_post_feed], (getDokumenError, dokumenRows) => {
        if (getDokumenError) {
            console.error(getDokumenError);
            return res.status(500).send('Terjadi kesalahan saat mengambil dokumen');
        }

        if (dokumenRows.length === 0) {
            return res.status(404).send('Dokumen tidak ditemukan');
        }

        const dokumen = dokumenRows[0].dokumen;

        res.writeHead(200, {
            'Content-Type': 'image/png', // 'application/pdf',
            // 'Content-Disposition': 'attachment; filename=dokumen.pdf',
            'Content-Length': dokumen.length
        });
        res.end(dokumen);
    });
});

// Get dokumen pengajuan by id_post_feed
router.get('/user/:id_user/post_feed/:id_post_feed/dokumen', (req, res) => {
    const { id_user, id_post_feed } = req.params;

    const getDokumenQuery = `
        SELECT dokumen
        FROM post_feed
        WHERE id_user = ? AND id_post_feed = ?
    `;

    db.query(getDokumenQuery, [id_user, id_post_feed], (getDokumenError, dokumenRows) => {
        if (getDokumenError) {
            console.error(getDokumenError);
            return res.status(500).send('Terjadi kesalahan saat mengambil dokumen');
        }

        if (dokumenRows.length === 0) {
            return res.status(404).send('Dokumen tidak ditemukan');
        }

        const dokumen = dokumenRows[0].dokumen;

        res.writeHead(200, { //application/pdf dan image/jpeg buat nampilin file pdf dan image
            'Content-Type': 'image/jpeg',
            'Content-Length': dokumen.length

            
        });
        res.end(dokumen);
    });
});

// Post post_feed
router.post('/user/:id_user/post_feed/add', upload.single('dokumen'), (req, res) => {
    const { id_provinsi, judul_post, biaya, alamat, keterangan } = req.body;
    const id_user = req.params.id_user;
    const status = 'Pending';
    const tanggal_post = new Date();

    const dokumen = req.file.buffer;

    const getUserInfoQuery = `
        SELECT nama_lengkap, id_role
        FROM user
        WHERE id_user = ?
    `;

    db.query(getUserInfoQuery, [id_user], (getUserError, userRows) => {
        if (getUserError) {
            console.error(getUserError);
            return res.status(500).send('Terjadi kesalahan saat mengambil informasi user');
        }

        if (userRows.length === 0) {
            return res.status(404).send('User tidak ditemukan');
        }

        const nama = userRows[0].nama_lengkap;
        const id_role = userRows[0].id_role;

        const insertQuery = `
            INSERT INTO post_feed 
            (id_provinsi, id_user, nama, judul_post, biaya, alamat, keterangan, dokumen, status, tanggal_post, id_role) 
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        `;

        const insertValues = [
            id_provinsi, id_user, nama, judul_post, biaya, alamat, keterangan, dokumen, status, tanggal_post, id_role
        ];

        db.query(insertQuery, insertValues, (insertError, result) => {
            if (insertError) {
                console.error(insertError);
                return res.status(500).send('Terjadi kesalahan saat membuat posting');
            }

            const postId = result.insertId;
            res.status(201).json({ message: 'Post berhasil dibuat', postId });
        });
    });
});



module.exports = router;