//---------------------------------------------------------------------------------------------
var fs = require('fs');
var url = require('url');
var http = require('http');
var querystring = require('querystring');
var db = []; //database
var humd;
var id;
const mqtt = require("mqtt");
const client = mqtt.connect("tcp://broker.hivemq.com:1883");
//---------------------------------------------------------------------------------------------



// function gửi yêu cầu(response) từ phía server hoặc nhận yêu cầu (request) của client gửi lên
function requestHandler(request, response) {

    // Giả sử địa chỉ nhận được http://192.168.1.4:8080/update?temp=30&humd=40
    var uriData = url.parse(request.url);
    var pathname = uriData.pathname;          // /update?
    var query = uriData.query;                // temp=30.5&hum=80
    var queryData = querystring.parse(query); // queryData.temp = 30.5, queryData.humd = 40
    
    client.on('connect', () => {    
        client.subscribe("nhom14_data");
    })
    client.on('message', (topic, message) => {
        humd = message.toString();
        pathname = '/update?humd='+humd; 
        var newData = {
            humd: humd,
            time: new Date()
        };
        db.push(newData);
        console.log(newData);
        console.log(message.toString())
        client.end();
    })
    //-----------------------------------------------------------------------------------------
    if (pathname == '/update') {
        var newData = {
            humd: queryData.humd,
            time: new Date()
        };
        db.push(newData);
        console.log(newData);
        response.end();
    //-----------------------------------------------------------------------------------------
    }else if(pathname == '/control'){
        console.log(queryData.id);
        var request = JSON.stringify(queryData.id);
        client.on('connect',()=>{
            console.log("RUNRUNRUNRUNRUNRUNRUNRUNRURNURNUNRUNRUN");
            client.publish("nhom14_controll",request);
        });
    } 
    else if (pathname == '/get') {
        response.writeHead(200, {
            'Content-Type': 'application/json'
        });
        response.end(JSON.stringify(db));
        db = [];
    //-----------------------------------------------------------------------------------------
    } else { 
        fs.readFile('./index.html', function(error, content) {
            response.writeHead(200, {
                'Content-Type': 'text/html'
            });
            response.end(content);
        });
    }
    //-----------------------------------------------------------------------------------------
}
var server = http.createServer(requestHandler);
server.listen(8080); 
process.on('warning', e => console.warn(e.stack));
console.log('Server listening on port 8080');