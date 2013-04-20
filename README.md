ttrex
=====

EE5 Project to be used at the 24h loop to monitor runners on a track

It reads from a socket; sends data to webclients using 2 frames

runner frame
  ID
  Position
  
general info frame
  ...


It uses the Atmosphere Framework to establish a cross-platform & browser websocket connection with it's webclients for 
low latency full duplex communication.
