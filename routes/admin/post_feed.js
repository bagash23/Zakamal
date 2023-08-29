const express = require('express');
const router = express.Router();
const db = require('../../config');


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
            return res.status(500).json({
                message: 'Terjadi kesalahan saat mengambil post_feed'
            });
        }

        res.status(200).json(allPostFeedRows);
    });
});

// Update status post_feed by id_post_feed
router.put('/admin/post_feed/:id_post_feed/status', (req, res) => {
    const { id_post_feed } = req.params;
    const { status } = req.body;

    const updateStatusPostFeedQuery = `
        UPDATE post_feed
        SET status = ?
        WHERE id_post_feed = ?
    `;

    db.query(updateStatusPostFeedQuery, [status, id_post_feed], (updateStatusPostFeedError, updateStatusPostFeedResult) => {
        if (updateStatusPostFeedError) {
            console.error(updateStatusPostFeedError);
            return res.status(500).json({
                message: 'Terjadi kesalahan saat mengubah status post_feed'
            });
        }

        res.status(200).json({
            message: 'Status post_feed berhasil diubah'
        });
    });
});

// Delete post_feed by id_post_feed
router.delete('/admin/post_feed/delete/:id_post_feed', (req, res) => {
    const { id_post_feed } = req.params;

    const deletePostFeedQuery = `
        DELETE FROM post_feed
        WHERE id_post_feed = ?
    `;

    db.query(deletePostFeedQuery, [id_post_feed], (deletePostFeedError, deletePostFeedResult) => {
        if (deletePostFeedError) {
            console.error(deletePostFeedError);
            return res.status(500).json({
                message: 'Terjadi kesalahan saat menghapus post_feed'
            });
        }

        res.status(200).json({
            message: 'Post feed berhasil dihapus'
        });
    });
});



module.exports = router;