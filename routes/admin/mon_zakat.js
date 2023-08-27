const express = require('express');
const router = express.Router();
const db = require('../../config');


// Get data zakat by Provinsi
router.get('/admin/monitoring-zakat/provinsi/:nama_provinsi', (req, res) => {
    const inputProvinsi = req.params.nama_provinsi.toLowerCase();

    const query = `
        SELECT mz.*, p.nama_provinsi 
        FROM monitoring_zakat mz 
        LEFT JOIN provinsi p ON mz.id_provinsi = p.id_provinsi 
        WHERE p.nama_provinsi LIKE ?`;

    db.query(query, [`%${inputProvinsi}%`], (queryErr, rows) => {
        if (queryErr) {
            console.error('Error executing query:', queryErr);
            res.status(500).send('Terjadi kesalahan: ' + queryErr.message);
            return;
        }
        res.status(200).json(rows);
    });
});

// Get data zakat by Provinsi and Tahun
router.get('/admin/monitoring-zakat/provinsi/:nama_provinsi/tahun/:tahun', (req, res) => {
    const inputProvinsi = req.params.nama_provinsi.toLowerCase();
    const inputTahun = req.params.tahun;

    const query = `
        SELECT mz.*, p.nama_provinsi
        FROM monitoring_zakat mz
        LEFT JOIN provinsi p ON mz.id_provinsi = p.id_provinsi
        WHERE p.nama_provinsi LIKE ? AND mz.tahun = ?`;

    db.query(query, [`%${inputProvinsi}%`, inputTahun], (queryErr, rows) => {
        if (queryErr) {
            console.error('Error executing query:', queryErr);
            res.status(500).send('Terjadi kesalahan: ' + queryErr.message);
            return;
        }
        res.status(200).json(rows);
    });
});

// Post data zakat
router.post('/admin/monitoring-zakat/add', (req, res) => {
    const { id_provinsi, bulan, tahun, total_terkumpul, total_pengeluaran } = req.body;

    const total_keseluruhan = total_terkumpul - total_pengeluaran;

    const connection = db;

    const query = `INSERT INTO monitoring_zakat (id_provinsi, bulan, tahun, total_terkumpul, total_pengeluaran, total_keseluruhan) VALUES (?, ?, ?, ?, ?, ?)`;

    connection.query(query, [id_provinsi, bulan, tahun, total_terkumpul, total_pengeluaran, total_keseluruhan], (queryErr, result) => {
        if (queryErr) {
            console.error('Error executing query:', queryErr);
            res.status(500).send('Terjadi kesalahan: ' + queryErr.message);
            return;
        }

        res.status(201).json({
            message: 'Berhasil menambahkan data zakat',
        });
    });
});

// Update data zakat by id_mon_zakat
router.put('/admin/monitoring-zakat/update/:id_mon_zakat', (req, res) => {
    const { id_mon_zakat } = req.params;
    const { id_provinsi, bulan, tahun, total_terkumpul, total_pengeluaran } = req.body;

    const total_keseluruhan = total_terkumpul - total_pengeluaran;

    const connection = db;

    const query = `UPDATE monitoring_zakat SET id_provinsi = ?, bulan = ?, tahun = ?, total_terkumpul = ?, total_pengeluaran = ?, total_keseluruhan =? WHERE id_mon_zakat = ?`;

    connection.query(query, [id_provinsi, bulan, tahun, total_terkumpul, total_pengeluaran, total_keseluruhan, id_mon_zakat], (queryErr, result) => {
        if (queryErr) {
            console.error('Error executing query:', queryErr);
            res.status(500).send('Terjadi kesalahan: ' + queryErr.message);
            return;
        }

        res.status(200).json({
            message: `Berhasil mengubah data zakat pada id ${id_mon_zakat}`
        });
    });
});

// Delete all data zakat
router.delete('/admin/monitoring-zakat/delete', (req, res) => {
    const connection = db;

    const query = `DELETE FROM monitoring_zakat`;

    connection.query(query, (queryErr, result) => {
        if (queryErr) {
            console.error('Error executing query:', queryErr);
            res.status(500).send('Terjadi kesalahan: ' + queryErr.message);
            return;
        }

        res.status(200).json({
            message: 'Berhasil menghapus semua data zakat'
        });
    });
});

// Delete data zakat by id_mon_zakat
router.delete('/admin/monitoring-zakat/delete/:id_mon_zakat', (req, res) => {
    const { id_mon_zakat } = req.params;

    const connection = db;

    const query = `DELETE FROM monitoring_zakat WHERE id_mon_zakat = ?`;

    connection.query(query, [id_mon_zakat], (queryErr, result) => {
        if (queryErr) {
            console.error('Error executing query:', queryErr);
            res.status(500).send('Terjadi kesalahan: ' + queryErr.message);
            return;
        }

        res.status(200).json({
            message: `Berhasil menghapus data zakat pada id ${id_mon_zakat}`
        });
    });
});



module.exports = router;