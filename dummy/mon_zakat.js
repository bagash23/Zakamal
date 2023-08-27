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

        const terkumpul = Math.floor(Math.random() * 5000) + 5000000;
        const pengeluaran = Math.floor(Math.random() * 1000) + 1000000;
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
            res.status(500).json('Terjadi kesalahan: ' + err.message);
        } else {
            const totalDataInserted = insertValues.length;
            const successMessage = {message: `Berhasil memasukkan ${totalDataInserted} data.`};
            res.status(200).json(successMessage);
        }
    });
});



module.exports = router;