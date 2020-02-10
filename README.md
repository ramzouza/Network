
# HTTP Client Application

## Authors
* Ramez Zaid - 27753349 
* Berfin Saricam - 40017210 

## Tested Commands

### HELP
* help
* help post 
* help get

### GET
* GET -v http://httpbin.org/get
* GET -v http://httpbin.org/absolute-redirect/3 -o output.txt
* GET -v http://httpbin.org/absolute-redirect/3
* GET http://httpbin.org/get?course=networking&assignment=1
* GET -h "User-Agent: Hello" http://httpbin.org/status/418
* GET -h "User-Agent: Hello" http://httpbin.org/status/418 -o teapot.txt

### POST

* POST -h Content-Type:application/json -d '{"Assignment": 1}' http://httpbin.org/post
* POST -h Content-Type:application/json -d '{"Assignment": {"Page": 2, "Paragraph": 2}}'http://httpbin.org/post
* POST -f testingFile.txt http://httpbin.org/post
