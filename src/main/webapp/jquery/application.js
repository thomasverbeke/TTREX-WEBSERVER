var subSocket; //we want to acces it outside of jquery
$(function () {
    "use strict";

    var header = $('#header');
    var content = $('#content');
    var input = $('#input');
    var status = $('#status');
    
    var socket = $.atmosphere;
    //var subSocket;
    var transport = 'websocket';

    // Attributes
    //document.location.toString()
    //https://github.com/Atmosphere/atmosphere/wiki/jQuery.atmosphere.js-API
    //request.url = document.location.toString() + 'chat';
    var request = { url: 'ws://localhost:8080/ttrex/' + 'ttrex', //url to connect with
        contentType : "application/json",
        logLevel : 'debug', //allowed are info, debug and error
        transport : transport ,
        enableProtocol : true,
        fallbackTransport: 'long-polling'};


    request.onOpen = function(response) {
    	//invoked when the connection gets opened.	
    	if (response.transport == "websocket"){
    		document.getElementById("serverLink").innerHTML = "<p>Connected to server using websockets</p>";  
    	} else {
    		document.getElementById("serverLink").innerHTML = "<p>Connected using " + response.transport + "</p>";  
    	}
    	

    	
        transport = response.transport;

        if (response.transport == "local") {
            //subSocket.pushLocal("Name?");
        	console.log("**local**");
        }
    };

    
    request.onTransportFailure = function(errorMsg, request) {
    	//Invoked when the request.transport fails because it is not supported by the client or the server. 
    	//You can reconfigure a new transport (request.transport) from that function.
    	//Invoking SSE
        jQuery.atmosphere.info(errorMsg);
        if (window.EventSource) {
            request.fallbackTransport = "sse";
            transport = "see";
        }
        //header.html($('<h3>', { text: 'Atmosphere Framework. Default transport is WebSocket, fallback is ' + request.fallbackTransport }));
        //document.getElementById("atmosphereStatus").innerHTML = "Connected using " + response.transport;
        console.log("request onclose - errorMsg: "+errorMsg + "request: " + request);
        
    };

    request.onClose = function(response) {
    	//invoked when the connection gets closed
    	document.getElementById("serverLink").innerHTML = '<p style="color: red;">Disconnected from server</p>';   
    	console.log("request onclose"+response);
    };

    request.onError = function(response) {
    	//invoked when an unexpected error occurs 	
    	document.getElementById("serverLink").innerHTML = '<p style="color: red;">Disconnected from server.Problem with server.</p>';  
		console.log("request onerror"+response);
    };
    
    request.onMessage = function (response) {
    	//TODO multithread onMessage using webworkers
        //callback when a new frame has arrived
        var message = response.responseBody;
        try {
            var json = jQuery.parseJSON(message);
            console.log(json);
            //console.log("Frame : "+json.type + " " +json.value); 
           
        } catch (e) {
            console.log('This doesn\'t look like a valid JSON: ', message.data);
            return;
        }
    };
    

    subSocket = socket.subscribe(request);

});


function sendcommand(command,input){
	 	//subSocket = $.socket.subscribe(request);
		var data;	
		//subSocket.push(jQuery.stringifyJSON({ type: "sendWP", data: data, source:"client" }));
    		
}
