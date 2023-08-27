const express = require('express');
const router = express.Router();
const db = require('../../config');


// Get all data provinsi
router.get('/admin/provinsi', (req, res) => {
    const query = `SELECT * FROM provinsi`;

    db.query(query, (queryErr, rows) => {
        if (queryErr) {
            console.error('Error executing query:', queryErr);
            res.status(500).send('Terjadi kesalahan: ' + queryErr.message);
            return;
        }
        res.status(200).json(rows);
    });
});

// Post provinsi
router.post('/admin/provinsi/add', (req, res) => {
    const { nama_provinsi } = req.body;
    
    const connection = db;

    const query = `INSERT INTO provinsi (nama_provinsi) VALUES (?)`;

    connection.query(query, [nama_provinsi], (queryErr, result) => {
        if (queryErr) {
            console.error('Error executing query:', queryErr);
            res.status(500).send('Terjadi kesalahan: ' + queryErr.message);
            return;
        }
        res.status(201).send('Provinsi berhasil ditambahkan!');
    });
});

// Delete provinsi by id_provinsi
router.delete('/admin/provinsi/delete/:id_provinsi', (req, res) => {
    const inputIdProvinsi = req.params.id_provinsi;

    const query = `DELETE FROM provinsi WHERE id_provinsi = ?`;

    db.query(query, [inputIdProvinsi], (queryErr, result) => {
        if (queryErr) {
            console.error('Error executing query:', queryErr);
            res.status(500).send('Terjadi kesalahan: ' + queryErr.message);
            return;
        }
        res.status(200).send('Provinsi berhasil dihapus!');
    });
});



module.exports = router;