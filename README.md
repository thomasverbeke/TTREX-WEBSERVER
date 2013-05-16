TTREX
=====

EE5 (GroupT) Project to be used at the 24h loop to monitor runners on a track

It reads from a socket; sends data to webclients using 2 frames 

1. runner frame which contains ID, % on Track, Latitude, Longitude, Speed, Rounds
  
2. general info frame which contains other information (ranking,..)

It uses the Atmosphere Framework to establish a cross-platform & browser "websocket" connection with it's webclients for 
low latency full duplex communication.

Web-interface is crossplatform SVG; it can animate collision detection.

MAP
===

There is also a map application which can send the track coordinates to the Raspi


API
====

There is also an API which uses REST webservices (and no websockets) It is based on the Jersey framework; this way other teams can tap into our datastream.
No calls are made to the Raspi; instead persisted data is send back.

host/ttrex/api/runner/ID

returns "application/json"
all stats of a runner with ID

host/ttrex/api/stats

return "application/json"
ranking of all runners

