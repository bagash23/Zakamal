const express = require('express');
const router = express.Router();
const db = require('../../config');
const multer = require('multer');

const storage = multer.memoryStorage();
const upload = multer({ storage: storage });


// Get all post_feed
router.get('/admin/post_feed', (req, res) => {
    const getAllPostFeedQuery = `
        SELECT post_feed.id_post_feed, post_feed.id_user, post_feed.id_provinsi, post_feed.nama, post_feed.judul_post, post_feed.biaya, post_feed.alamat, post_feed.keterangan, post_feed.status, post_feed.tanggal_post, user.nama_lengkap, provinsi.nama_provinsi, role.nama_role
        FROM post_feed
        INNER JOIN user ON post_feed.id_user = user.id_user
        INNER JOIN provinsi ON post_feed.id_provinsi = provinsi.id_provinsi
        INNER JOIN role ON user.id_role = role.id_role
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



module.exports = router;