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
app.use('/v1', require('./routes/admin/mon_zakat'));
app.use('/v1', require('./routes/admin/provinsi'));
app.use('/v1', require('./routes/admin/pezakat'));
app.use('/v1', require('./routes/admin/post_feed'));

// Pezakat API
app.use('/v1', require('./routes/user/auth'));
app.use('/v1', require('./routes/user/pezakat'));
app.use('/v1', require('./routes/user/post_feed'));
app.use('/v1', require('./routes/user/komentar'));



module.exports = app;