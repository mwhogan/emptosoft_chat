# Emptosoft Chat

This is an instant messaging utility that makes use of Java RMI. It can operate as a server and as a client, and has two user interface modes: it can be used through a GUI (graphical user interface) or it can be used in a more rudimentary form from a terminal.

## Getting started

To run this utility and use the GUI, use:
```sh
make run
```

To run this utility in your terminal, use:
```sh
make run_cmdline
```

In both cases you will be prompted to choose a name for yourself. Once a name has been entered, a chat server will start in the background, followed by a client (connected to that server) in the foreground.

In the terminal mode, you will only be able to send (by typing and pressing Enter) and receive messages.

In the GUI mode, you will additionally be able to see a list of other participants and their statuses, set your own status and become a client of another server.

### Common problems

If a java.security.AccessControlException occurs, mentioning java.net.SocketPermission, you may need to edit the chat/Chat.policy file to grant the permissions mentioned to the IP address and port mentioned.

### Diagnostic mode

A diagnostic mode, with additional logging, may be activated by entering "Diagnostic Mode" as your name. Activation of diagnostic mode will be confirmed, and you will then be prompted to choose a name again.

## Building

To build a redistributable Jar file, use:
```sh
make Chat.jar
```
This will produce a file called "Chat.jar".

## Change log

v1.1c (06/09/09):
- Interface version: 1.1
- One-off to demonstrate how to make Emptosoft Chat command-line based again
- "-c" option on command line makes Emptosoft Chat use the command line rather than try to create a GUI

v1.1 (30/08/09):
- Fixed bugs with participants list
- There is now an interface version, which clients use to check if they are compatible with a server
- Interface version: 1.1
- Various general bug fixes
- Diagnostic Mode added

v1.0 (20/07/09):
- First version
- Ability to connect to a server or be a server
- Ability to chat to people connected to same server
- All users have statuses (online, busy, away, offline)
- Participant list, with statuses
- Relevant IPs displayed
- Ability to choose name (IP used if no name specified)
- Basic UI
