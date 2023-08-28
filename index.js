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
app.use('/', require('./dummy/pezakat'));

// Admin API
app.use('/', require('./routes/admin/mon_zakat'));
app.use('/', require('./routes/admin/provinsi'));
app.use('/', require('./routes/admin/pezakat'));

// Pezakat API
app.use('/', require('./routes/user/auth'));
app.use('/', require('./routes/user/pezakat'));



module.exports = app;