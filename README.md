This repository represent a game. The game board is display on a graph with vertices and edges, it is possible to move across the board by moving from vertex to vertex that has a edges between them. The GUI window is featured the game board, the level of the game, time to end and the grade of the player. The game has robots and fruits (banana and apple). The purpose of the game is to gain as much point as you can, it is possible to gain a points by eating the the fruits with the robots. Each vertex has a weight that represent the time that take to the robots to cross the edge.
The game has 24 levels (0-23) that represnt on type of 5 maps, every level has a diffrent number of fruits and robots.
There is two type of game -Automatic and by mouse.
* By Mouse - you select the start position of the robots, The progress of the robots is by choosing the vertex that you want that one of the robot will arrive, the robot that will select is the closest one to the vertex. 
* Automatic - the start position of the robots is selected by the ideal place( by the fruit with the highest value). The progress of the robots is by algorithm that is purpose is to gain a good score.

There are a few packages:
* Obj package has class for fruit, robot (pacman), and the game server.
* DataStructure package has the class of the structure of the graph include class for the vertices(node_data), class for the edges(edge_data), class for the graph that use the other class .
* Algorithms package has class that include algorithms for graph such a shortest path, TSP a algorithms of the shortest path etc..
* Utils package - include StdDraw class(without improvements), Point3D that represent the position of each vertex robot and fruit, and Range class. 
* GameCleint packege responsible for the game - has a class for the GUI window(MyGameGUI), class for the algorithms of the game(algoForGui), class for create objects for the game(createObjFromJson), and KML_Logger class.
MyGameGUI class responsible for the drawing. it uses the algoForGui class the create the best movement for the robot.
CreateObjFromJson class create object from json String that the game returns.
KML_Logger class create kml file for every game so we can put it in google earth and see an amination of ower game.


To start a game -you need to run the class simpleGameClient then select level and type of game.
To save kml file you neer to run KML_Logger class and than the files will save on the priject file.
 
Table of maximum grade for each level by the auto drive :


