const express = require('express');
const router = express.Router();
const db = require('../config');
const bcrypt = require('bcrypt');


// Insert dummy data pezakat
router.post('/dummy/pezakat', (req, res) => {
    const connection = db;
    const totalData = 500;
    const id_role = 2;

    const namaOptions = [
        'Subaru', 'Emilia', 'Rem', 'Ram', 'Beatrice', 'Roswaal', 'Felt', 'Reinhard', 'Crusch', 'Felix',
        'Wilhelm', 'Priscilla', 'Anastasia', 'Julius', 'Echidna', 'Petra', 'Otto', 'Garfiel', 'Frederica', 'Meili',
        'Cocytus', 'Demiurge', 'Sebas', 'Momon', 'Narberal', 'Pandora', 'Albedo', 'Shalltear', 'Aura', 'Mare',
        'Naruto', 'Sasuke', 'Sakura', 'Kakashi', 'Iruka', 'Hinata', 'Shikamaru', 'Choji', 'Ino', 'Asuma',
        'Kurenai', 'Gai', 'Neji', 'Tenten', 'Rock', 'Might', 'Guy', 'Kiba', 'Shino', 'Hana',
        'Kabuto', 'Orochimaru', 'Jiraiya', 'Tsunade', 'Gaara', 'Temari', 'Kankuro', 'Chiyo', 'Sasori', 'Deidara'
    ];


    const getRandomArrayElement = (array) => {
        return array[Math.floor(Math.random() * array.length)];
    };

    const getRandomIdProvinsi = () => {
        return Math.floor(Math.random() * 38) + 1;
    };

    const generateRandomTelepon = () => {
        let telepon = '628';
        for (let i = 0; i < 9; i++) {
            telepon += Math.floor(Math.random() * 10);
        }
        return telepon;
    };

    const pezakatData = [];
    for (let i = 0; i < totalData; i++) {
        const nama_lengkap = getRandomArrayElement(namaOptions) + ' ' + getRandomArrayElement(namaOptions);
        const email = generateRandomTelepon() + '@mail.com';
        const telepon = generateRandomTelepon();
        const password = 'password123';
        const id_provinsi = getRandomIdProvinsi();

        const pezakat = {
            nama_lengkap: nama_lengkap,
            telepon: telepon,
            email: email,
            password: password,
            id_provinsi: id_provinsi
        };
        pezakatData.push(pezakat);
    }

    const insertData = (index) => {
        if (index >= totalData) {
            return res.status(201).json({
                message: `${totalData} akun dummy berhasil didaftarkan`,
            });
        }

        const pezakatQuery = `INSERT INTO pezakat (nama_lengkap, telepon, email, password, id_provinsi) VALUES (?, ?, ?, ?, ?)`;
        const userQuery = `INSERT INTO user (nama_lengkap, telepon, email, password, id_provinsi, id_role, id_pezakat) VALUES (?, ?, ?, ?, ?, ?, ?)`;

        const pezakat = pezakatData[index];
        bcrypt.hash(pezakat.password, 10, (err, hash) => {
            if (err) {
                console.error('Error hashing password:', err);
                return res.status(500).json({
                    message: 'Terjadi kesalahan: ' + err.message
                });
            }

            connection.query(pezakatQuery, [pezakat.nama_lengkap, pezakat.telepon, pezakat.email, hash, pezakat.id_provinsi], (pezakatQueryErr, pezakatResult) => {
                if (pezakatQueryErr) {
                    console.error('Error executing pezakat query:', pezakatQueryErr);
                    return res.status(500).json({
                        message: 'Terjadi kesalahan: ' + pezakatQueryErr.message
                    });
                }

                const id_pezakat = pezakatResult.insertId;

                connection.query(userQuery, [pezakat.nama_lengkap, pezakat.telepon, pezakat.email, hash, pezakat.id_provinsi, id_role, id_pezakat], (userQueryErr, userResult) => {
                    if (userQueryErr) {
                        console.error('Error executing user query:', userQueryErr);
                        return res.status(500).json({
                            message: 'Terjadi kesalahan: ' + userQueryErr.message
                        });
                    }

                    insertData(index + 1);
                });
            });
        });
    }

    insertData(0);
});

// Delete dummy data pezakat
router.delete('/dummy/pezakat', (req, res) => {
    const connection = db;

    const query = `DELETE FROM pezakat`;

    connection.query(query, (err, result) => {
        if (err) {
            console.error('Error executing query:', err);
            res.status(500).json({
                message: 'Terjadi kesalahan: ' + err.message
            });
            return;
        }

        const deletedRowCount = result.affectedRows || 0;

        res.status(200).json({
            message: `Berhasil menghapus ${deletedRowCount} data pezakat`
        });
    });
});



module.exports = router;