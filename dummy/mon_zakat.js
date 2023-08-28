const express = require('express');
const router = express.Router();
const db = require('../config');


// Insert dummy data zakat
router.post('/dummy/zakat', (req, res) => {
    const connection = db;

    const bulanOptions = [
        'Januari', 'Februari', 'Maret', 'April', 'Mei', 'Juni',
        'Juli', 'Agustus', 'September', 'Oktober', 'November', 'Desember'
    ];

    const tahunOptions = ['2020', '2021', '2022'];

    const insertValues = [];
    const insertedMonths = new Set();

    for (let i = 1; i <= 1000; i++) {
        const idProvinsi = Math.floor(Math.random() * 38) + 1;
        const bulan = bulanOptions[Math.floor(Math.random() * bulanOptions.length)];
        const tahun = tahunOptions[Math.floor(Math.random() * tahunOptions.length)];

        if (insertedMonths.has(`${idProvinsi}-${bulan}-${tahun}`)) {
            continue;
        }

        const terkumpul = Math.floor(Math.random() * 4500000) + 5000000;
        const pengeluaran = Math.floor(Math.random() * 900000) + 1000000;

        if (terkumpul <= pengeluaran) {
            pengeluaran = terkumpul - 100000;
        }

        const total_keseluruhan = terkumpul - pengeluaran;

        insertValues.push(`(${idProvinsi}, '${bulan}', '${tahun}', ${terkumpul}, ${pengeluaran}, ${total_keseluruhan})`);
        insertedMonths.add(`${idProvinsi}-${bulan}-${tahun}`);
    }

    const query = `INSERT INTO monitoring_zakat (
            id_provinsi, bulan, tahun, total_terkumpul, total_pengeluaran, total_keseluruhan
        ) VALUES ${insertValues.join(', ')}`;

    connection.query(query, (err, results) => {
        if (err) {
            console.error(err);
            res.status(500).json({
                message: 'Terjadi kesalahan: ' + err.message
            });
        }
        const totalDataInserted = insertValues.length;
        res.status(200).json({
            message: `Berhasil memasukkan ${totalDataInserted} data monitoring zakat`
        });
    });
});

// Delete all data zakat
router.delete('/dummy/zakat', (req, res) => {
    const connection = db;

    const query = `DELETE FROM monitoring_zakat`;

    connection.query(query, (err, result) => {
        if (err) {
            console.error('Error executing query:', err);
            res.status(500).json({
                message: 'Terjadi kesalahan: ' + err.message
            });
            return;
        }
        const totalDataDeleted = result.affectedRows;
        res.status(200).json({
            message: `Berhasil menghapus ${totalDataDeleted} data monitoring zakat`
        });
    });
});



module.exports = router;