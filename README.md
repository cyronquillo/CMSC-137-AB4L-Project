# GhostWars
A Java implemented Game in CMSC 137
==================
## Uses:
--------------------
```
TCP		Chat System
UDP		Game
```

## How to Compile:
--------------------
```
Compile all files first using the following command:
* `javac -d . *java`
```

## Running
```
In the Server side:
* `java instantiation.GhostwarsServer <num_of_players>`

In the Client side:
* `java instantiation.GhostWarsClient <server_ip> <player_name>`

```
## Conventions
--------------------
1. Always use camelCase for functions and snake_case for variables 


2. Use 4-space tabs for indentation


3. Never forget to document every function you create to avoid confusion when read by other members


4. For syntax of if, while, functions, etc., this format is to be followed:

WHILE, FOR statements, and FUNCTIONS:

	(while|for|function name) (params|condition) {
			// statement inside here
	}

IF statements:

	if (condition) {


	} else if (condition){


	} else{


	} 
--------------------