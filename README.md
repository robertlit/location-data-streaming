# Overview
The goal of this project is to learn how to handle and process an incoming
stream of data. The use case is detection of motion based events in a stream of
real-time location updates. The server receives a stream of location updates
which consist of latitude, longitude, and timestamp. The data is transformed
and checked; appropriate events are streamed back to the client.

# Data and Communication Model
Protocol Buffers are used to define the data model for location updates and
events. gRPC is used for bidirectional streaming between the server and client.

# Design


## Architecture
The following interfaces define the data flow architecture in the project:

### LocationUpdateHandler
Receives location updates for further processing.

### SlidingWindow
Provides a sliding-window fashioned way for retrieving batches of data.

### Transformation
An operation that enriches a series of data points, possibly based on one another.

### EventCheck
Checks for an event in a single data point.

### CheckProcessor
Checks events in a series of data points.

### ResponseReceiver
Receives and handles event responses that were generated during data processing

### ResponseForwarder
Gateway for sending event responses back to the client.


##  Implementation
The above interfaces are implemented to work as a data processing pipeline
for a real-time data stream from gRPC:

### LocationEventService
gRPC service implementation. Forwards incoming data to a LocationUpdateHandler
and binds the client response stream to a DataForwarder.

### DataReceiver
Acts as both a LocationUpdateHandler and SlidingWindow; it takes incoming
location update data, stores it in memory, and provides a sliding window
way for retrieving it.

### ResponseHandler
Acts as both a ResponseReceiver and ResponseForwarder; it takes generated event
responses and sends them to the client response stream which is bound to it.

### DataProcessor
Repeatedly acquires data, transforms it, checks for events in it
and sends event responses.

# Tests
Unit tests are provided for calculation utilities, transformations and
event checks. Additionally, a test client is provided.

# TODO
- [ ] Check for sharp turns by measuring angle change
- [ ] More robust testing
- [ ] Authentication and support for multiple concurrent clients
