const mysql = require('mysql');


const db = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '',
    database: 'zakamal',
    port: '3306'
});

db.connect((err) => {
    if (err) {
        console.log('Database error: ', err);
    } else {
        console.log('Database connected!');
    }
});



module.exports = db;