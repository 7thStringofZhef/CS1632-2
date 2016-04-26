# CS1632-2
Deliverable Repo

How to build/compile Deliverable 6:

Deliverable 6 was written and compiled in Visual Studio 2015 Community
This is free to anyone with an edu address and can be downloaded off Pitt's download service.
Install this version of visual studio and open the outer solution file inside it.

There will be two projects: CS1632D6 and CS1632D6Tests

First, in each project's properties
	-Under VC++ Directories
		-Edit Include Directories to include SDL/include (Inside the folder)
		-Edit Library Directories to include SDL/lib/x86 (Inside the folder)
	-Under Linked->Input
		-Ensure that SDL2.lib and SDLmain.lib are included under "Additional Dependencies"
		
To run the test suite
	-In the CS1632D6 Project's properties, under General, set configuration type to static library
	-Open test explorer and hit "run all"
	
To run the program
	-Set main project's configuration type to ".exe"
	-Hit run