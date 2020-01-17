This repository represent a game. The game board is display on a graph with vertices and edges, it is possible to move across the board by moving from vertex to vertex that has a edges between them. The GUI window is featured the game board, the level of the game, time to end and the grade of the player. The game has robots and fruits (banana and apple). The purpose of the game is to gain as much point as you can, it is possible to gain a points by eating the the fruits with the robots. Each vertex has a weight that represent the time that take to the robots to cross the edge.
The game has 24 levels (0-23) that represnt on type of 5 maps, every level has a diffrent number of fruits and robots.
There is two type of game -Automatic and by mouse.
By Mouse - you select the start position of the robots, The progress of the robots is by choosing the vertex that you want that one of the robot will arrive, the robot that will select is the closest one to the vertex. 
Automatic - the start position of the robots is selected by the ideal place( by the fruit with the highest value). The progress of the robots is by algorithm that is purpose is to gain a good score.

the obj packege has the class of the fruit, the robot (pacman), and the game server.
There is to

the dataStructure packege has the class of the structure of the graph.

the algorithms packege has class of algorithms of the shortest path and so.

the gameCleint packege responsible for the game.
the MyGameGUI class responsible for the drawing. it uses the algoForGui class the create the best movement for the robot.
the createObjFromJson class create object for the json that the game returns.
the KML_Logger class create kml file for every game so we can put it in google earth and see an amination of ower game.

