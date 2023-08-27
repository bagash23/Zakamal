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



module.exports = app;