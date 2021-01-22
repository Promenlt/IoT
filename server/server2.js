const http = require('http')

const server = http.createServer(function(request, response) {
  console.dir(request.param)

  if (request.method == 'POST') {
    console.log('POST')
    request.on('data', function(data) {
        console.log('Partial body: ' + data)
        var obj = JSON.parse(data);
        var requestCode = obj.code;
//----------------------------------------------------------------------------
        if(requestCode.toString() == 'control') {
            request.on('end', function() {
            console.log('control')
            response.writeHead(200, {'Content-Type': 'text/html'})
            response.end('control')
            })            
        } 
//-----------------------------------------------------------------------------
        else if(requestCode.toString() =='getinfo'){
            request.on('end', function() {
            // console.log('Body: ' + body)
            response.writeHead(200, {'Content-Type': 'text/html'})
            response.end('getinfo')
            }) 
        }
    })

  } 
////////////////////////////////////////////////////////////////////////////////////////////////////////////
  else {
    console.log('GET')
    var html = `
            <html>
                <body>
                    <form method="post" action="http://localhost:3000">Name: 
                        <input type="text" name="name" />
                        <input type="submit" value="Submit" />
                    </form>
                </body>
            </html>`
    response.writeHead(200, {'Content-Type': 'text/html'})
    response.end(html)
  }
})

const port = 3000
const host = '127.0.0.1'
server.listen(port, host)
console.log(`Listening at http://${host}:${port}`)

