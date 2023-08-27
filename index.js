const express = require('express');
const cors = require('cors');
const bodyParser = require('body-parser');
const app = express();


app.use(cors());
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

app.get('/', (req, res) => {
  res.send('Server telah berjalan!');
});

// Dummy API
app.use('/', require('./dummy/mon_zakat'));

// Admin API
app.use('/', require('./routes/admin/mon_zakat'));
app.use('/', require('./routes/admin/provinsi'));

// User API
app.use('/', require('./routes/pezakat/auth'));



module.exports = app;