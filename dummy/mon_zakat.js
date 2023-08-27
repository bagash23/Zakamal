const express = require('express');
const router = express.Router();
const db = require('../config');


// Get all monitoring zakat by bulan
router.get('/monitoring-zakat/:bulan', (req, res) => {
    const inputBulan = req.params.bulan.toLowerCase();

    const connection = db;

    const bulanMap = {
        'jan': 'Januari',
        'feb': 'Februari',
        'mar': 'Maret',
        'apr': 'April',
        'mei': 'Mei',
        'jun': 'Juni',
        'jul': 'Juli',
        'agu': 'Agustus',
        'sep': 'September',
        'okt': 'Oktober',
        'nov': 'November',
        'des': 'Desember'
    };

    const bulan = bulanMap[inputBulan] || inputBulan;

    connection.connect(err => {
        if (err) {
            console.error('Error connecting to database:', err);
            res.status(500).send('Terjadi kesalahan: ' + err.message);
            return;
        }

        const query = `SELECT mz.*, p.nama_provinsi FROM monitoring_zakat mz LEFT JOIN provinsi p ON mz.id_provinsi = p.id_provinsi WHERE mz.bulan LIKE ?`;

        connection.query(query, [`%${bulan}%`], (queryErr, rows) => {
            connection.end();

            if (queryErr) {
                console.error('Error executing query:', queryErr);
                res.status(500).send('Terjadi kesalahan: ' + queryErr.message);
                return;
            }
            res.status(200).json(rows);
        });
    });
});

// Insert dummy data zakat
router.post('/dummy/zakat', (req, res) => {
    const connection = db;

    const bulanOptions = [
        'Januari', 'Februari', 'Maret', 'April', 'Mei', 'Juni',
        'Juli', 'Agustus', 'September', 'Oktober', 'November', 'Desember'
    ];

    const tahunOptions = ['2020', '2021', '2022'];

    const insertValues = [];

    for (let i = 1; i <= 1000; i++) {
        const idProvinsi = Math.floor(Math.random() * 38) + 1;
        const bulan = bulanOptions[Math.floor(Math.random() * bulanOptions.length)];
        const tahun = tahunOptions[Math.floor(Math.random() * tahunOptions.length)];
        const terkumpul = Math.floor(Math.random() * 5000) + 5000000;
        const pengeluaran = Math.floor(Math.random() * 1000) + 1000000;

        insertValues.push(`(${idProvinsi}, '${bulan}', '${tahun}', ${terkumpul}, ${pengeluaran})`);
    }

    const query = `INSERT INTO monitoring_zakat (
            id_provinsi, bulan, tahun, total_terkumpul, total_pengeluaran
        ) VALUES ${insertValues.join(', ')}`;

    connection.query(query, (err, results) => {
        if (err) {
            console.error(err);
            res.status(500).send('Terjadi kesalahan: ' + err.message);
        } else {
            res.status(200).send('Data selesai dimasukkan.');
        }
    });
});

// Delete all monitoring zakat
router.delete('/monitoring-zakat', (req, res) => {
    const connection = db;

    const query = `DELETE FROM monitoring_zakat`;
    connection.query(query, (err, results) => {
        if (err) {
            console.error(err);
            res.status(500).send('Terjadi kesalahan: ' + err.message);
        } else {
            console.log(`Data yang telah dihapus: ${results.affectedRows}`);
            res.status(200).send('Data selesai dihapus.');
        }
    });
});



module.exports = router;