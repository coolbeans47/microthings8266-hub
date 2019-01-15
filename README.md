# microthings8266-hub

The Microthings8266 project is made of of a web application (the hub) that allows easy 
communication to ESP8266 devices running on the network. The microthings8266-device project 
is the application that is loaded onto each device (via Arduino IDE). 

What makes this different from other IOT home hub applications is this does not use MQTT 
or other message queues. The hub will have a direct connection to each device and 
coordinate messages between devices. The controlling code is written in JavaScript 
with remote access to the device pins using custom Arduino code. The intention is the hub can be used to provide 
fast communication between simple devices on the network as if they were physically 
connected.  

Main goals of the project:

    1. Simple Setup of each device and automatic reconnection on device restart.
    2. Programming of each device in JavaScript which runs on the hub.
    3. A web application to setup devices and define commands, events, and 
       interactios between the different devices.
    4. Simple setup of hub application on raspberry pi including the support 
       for docker containers.