const mysql = require('mysql');
let connectionPool = mysql.createPool({
    connectionLimit: 5,
    host: "localhost",
    port: "3306",
    user: "root",
    password: "",
    database: "cst3130cw1",
    debug: false
});

const cors = require('cors')

const bodyParser = require('body-parser');
const express = require('express');

const app = express();
app.use(bodyParser.json());
app.use(express.static('public'));
app.use(cors())




app.get('/degrees', handleGetRequest);
app.get('/university', handleuniveristyRequest);
app.get('/dbcom',handlecompRequest);

function handlecompRequest(request,response){
    let sql = "SELECT * FROM db_comparison";

    connectionPool.query(sql, (err, result) => {
        if (err) {
            console.error("error executing query : " + JSON.stringify(err));
        }
        else {
            response.send(JSON.stringify(result));
        }
    });
}


function handleGetRequest(request, response) {
    let sql = "SELECT * FROM degrees";

    connectionPool.query(sql, (err, result) => {
        if (err) {
            console.error("error executing query : " + JSON.stringify(err));
        }
        else {
            response.send(JSON.stringify(result));
        }
    });
}

//function to handle request to get the inbox content
function handleuniveristyRequest(request, response) {
    let sql = "SELECT * FROM university";

    connectionPool.query(sql, (err, result) => {
        if (err) {
            console.error("error executing query : " + JSON.stringify(err));
        }
        else {
            response.send(JSON.stringify(result));
        }
    });
}

app.listen(8080);