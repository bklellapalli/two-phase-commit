# two-phase-commit
Two-Phase Commit over replicated file servers

# Durable Remote File Service
Your file service should support three methods: 
1. writeFile: given the file name and contents, the corresponding file should be written to the server.
2. deleteFile: if a file  exists on the server, it should be deleted. Otherwise, an exception should be thrown.
3. readFile: if a file exists on the server, return its contents. Otherwise, an exception should be thrown.

# Two-Phase Commit
coordinator the “coordinator” process should expose an RPC interface to clients that contains three methods: writeFile, readFile, and deleteFile. When the coordinator process receives a state-changing operation (write- File or deleteFile), it uses two-phase commit to commit that state-changing operation to all participants. When the coordinator receives a readFile operation, it selects a participant at random to issue the request against.
The coordinator should be concurrent. It should allow multiple clients to connect to it and should be able to process multiple operations concurrently.

participant each participant implements a durable remote file service you built in Part 1. It exposes an RPC interface to the coordinator to participate in two-phase commit. It is up to you what specific methods the participants should support.

The participants should be concurrent: it should be able to process multiple commands concurrently. Thus, it needs to implement some form of concurrency control. For this project, the concurrency control scheme is very simple: if two concurrent operations manipulate different files, they can safely proceed concurrently. If two concurrent operations manipulate the same file (e.g., two writeFile operations to the same file show up at the same time), the first to arrive should be able to proceed while the second’s two-phase commit should abort.

To implement two-phase commit, both the coordintor and the participants will need to do some form of logging to keep durable state associated with the two-phase commit. You will need to figure out how to implement logging, what to log, and how to replay your log on recovery. Your implementation needs to be fault-tolerant. If either the coordinator or one or more participants fail, your two-phase commit implementation needs to do the right thing. Similarly, after a failed component recovers, your two-phase commit implementation needs to recover and proceed as appropriate.

# Clients
ou should implement a client program that connects to the coordinator and issues a stream of writeFile, readFile, and deleteFile requests. You should be able to launch multiple clients in order to drive the coordinator concurrently – i.e., each separate client should be issuing its own stream of writeFile, readFile, and deleteFile operations.

Use your client programs to test your implementation of two-phase commit. Be sure to find a way to test as many corner cases as you can, such as the coordinator failing during the period of uncertainty.

