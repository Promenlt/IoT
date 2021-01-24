//---------------------------------------------------------------------------------------------
var fs = require('fs');
var url = require('url');
var http = require('http');
var querystring = require('querystring');
const database = require('./connect_db');
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
        
        // if(client.subscribe("nhom14_data")) {
        //     console.log("subcribed!");
        // }
        
    
        // if(client.subscribe("nhom14_data")){
        //     console.log("subcribed");
        // }
    client.on('message', (topic, message) => {
        console.log("Message"); 
        humd = message.toString();
        pathname = '/update?humd='+humd; 
        var newData = {
            humd: humd,
            time: new Date()
        };
        var sql1 = 'UPDATE pump SET Pump_Humd=? WHERE Pump_ID=1'
        database.query(sql1,[Number(humd)],function(err, results) {
                    if (err) throw err;
                    console.log("Updated success");
        });
        db.push(newData);
        console.log(newData);
        console.log(message.toString());
    })
    //-----------------------------------------------------------------------------------------
    if (pathname == '/update') {
        console.log("update"); 
        var newData = {
            humd: queryData.humd,
            time: new Date()
        };
        db.push(newData);
        console.log(newData);
        response.end();
    //-----------------------------------------------------------------------------------------
    }else if(pathname == '/control'){
        console.log("control: "+queryData.id);
        Number(queryData.id);
        var request_mqtt = JSON.stringify(Number(queryData.id));
        // client.publish("nhom14_controll",request);

            if(client.publish("nhom14_controll",request_mqtt)){
                console.log("request succesful!");
             // client.publish("nhom14_controll",request);    
            }
            var sql2 = 'UPDATE pump SET Pump_Humd=?,Pump_Status=? WHERE Pump_ID=1'
            if(Number(queryData.id)==1){
                database.query(sql2,[Number(humd),1],function(err, results) {
                            if (err) throw err;
                            console.log("Update Sucess");
                });
            }else{
                database.query(sql2,[0,0],function(err, results) {
                            if (err) throw err;
                            console.log("Update Sucess");
                });
            }
            
        response.end();
        
    }else if(pathname == '/api'){
        if (request.method == 'POST') {
            console.log('POST')
            request.on('data', function(data) {
                console.log('Partial body: ' + data)
                var obj = JSON.parse(data);
                var requestCode = obj.code;
        //controll pump
                if(requestCode.toString() == 'control') {
                    request.on('end', function() {
                    console.log('control')
                    var requestId= obj.id.toString();
                    Number(requestId);
                    var request_mqtt = JSON.stringify(requestId);
                    if(client.publish("nhom14_controll",request_mqtt)){
                       console.log("request succesful!");
                    }
                    var sql2 = 'UPDATE pump SET Pump_Humd=?,Pump_Status=? WHERE Pump_ID=1'
                    if(requestId==1){
                    database.query(sql2,[Number(humd),1],function(err, results) {
                            if (err) throw err;
                            console.log("Update Sucess");
                    });
                    }else{
                    database.query(sql2,[0,0],function(err, results) {
                            if (err) throw err;
                            console.log("Update Sucess");
                    });
                    }
                    response.writeHead(200, {'Content-Type': 'text/html'})
                    response.end('control')
                    })            
                } 
        //check account valid
                else if(requestCode.toString() =='signin'){
                    request.on('end', function() {
                    var sql2 = 'SELECT * FROM user WHERE User_Name=? AND User_Password=?'
                    database.query(sql2,[obj.username.toString(),obj.password.toString()],function(err, results) {
                                if (err){
                                    response.writeHead(200, {
                                        'Content-Type': 'application/json'
                                    });
                                    response.end(JSON.stringify('Not valid account'))
                                    throw err;
                                }else{
                                    response.writeHead(200, {
                                        'Content-Type': 'application/json'
                                    });
                                    response.end(JSON.stringify('accept'))
                                };
                                console.log("Update Sucess");
                        });
                    }) 
                }
                else if(requestCode.toString()=='info'){
                    request.on('end',function(){
                        response.writeHead(200, {
                            'Content-Type': 'application/json'
                        });
                        response.end(JSON.stringify(db));
                    })
                }
            })
        
          } 
    }else if (pathname == '/get') {
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
    client.on('connect', () => {   
        console.log("Creating client."); 
        if(client.subscribe("nhom14_data")){
            console.log("subcribed");
        }
    })
var server = http.createServer(requestHandler);
server.listen(8080); 
process.on('warning', e => console.warn(e.stack));
console.log('Server listening on port 8080');