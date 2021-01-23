const mysql = require('mysql')
console.log('Get connection ...');
const database = mysql.createConnection({
    host: process.env.DB_HOST || 'localhost',
    user: process.env.DB_USER || 'root',
    password: process.env.DB_PASS || '',
    database: process.env.DB_NAME || 'nhom_14',
    port: process.env.DB_PORT || '3306'
})
module.exports = database;
database.connect(function(err) {
    if (err) throw err;
    console.log("Connected!");

});