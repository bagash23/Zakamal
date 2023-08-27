const app = require('./index');

app.listen(9000, () => {
    console.log(`Server telah berjalan pada http://localhost:9000`);
}).on('error', (err) => {
    console.log(`Server error:`, err);
});