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
The incoming stream of data is stored in-memory. The data is processed in
batches; it is acquired in a sliding-window fashion, transformed and enriched,
and then checked for the needed events. The response events are streamed back
to the client.

# Tests
Unit tests are provided for calculation utilities, transformations and
event checks. Additionally, a test client is provided.

# TODO
- [ ] Check for sharp turns by measuring angle change
- [ ] More robust testing
- [ ] Authentication and support for multiple concurrent clients
