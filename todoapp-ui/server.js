"use strict"
//2019-04-04: copied from https://stackoverflow.com/a/41550669/2336212

var express = require('express');
var app = express();

//2024-12-17: copied from https://expressjs.com/en/resources/middleware/cors.html#configuration-options
const cors = require('cors');
app.use(cors());
//

app.use(express.static(__dirname + '/')); //__dir and not _dir
var port = 8000; // you can use any port
app.listen(port);
console.log('server on ' + port);