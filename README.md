TTREX
=====

EE5 (GroupT) Project to be used at the 24h loop to monitor runners on a track

It reads from a socket; sends data to webclients using 2 frames 

runner frame which contains ID & Position
  
general info frame which contains other information (ranking,..)

It uses the Atmosphere Framework to establish a cross-platform & browser websocket connection with it's webclients for 
low latency full duplex communication.

Web-interface is crossplatform SVG; it can animate collision detection.
