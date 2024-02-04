# Readme: Intelligent Agent for m-n-k Game with Alpha-Beta Pruning
## Overview
The main challenge of this project was to develop an intelligent agent capable of playing a variant of the m-n-k game, specifically the 4,4,4 configuration, where the goal is to align four pieces on a 4x4 board. The focus is on creating an effective adversarial search algorithm that can predict and optimally react to the opponent's moves.

## Solution Strategy
To overcome this challenge, I implemented the Minimax algorithm with Alpha-Beta pruning, a classic strategy game algorithm that seeks the best possible move by anticipating the opponent's actions. Alpha-Beta pruning is applied to optimize the search, significantly reducing the number of nodes evaluated in the decision tree. The development followed an iterative model, starting with the basic implementation of Minimax and later integrating Alpha-Beta pruning. Unit tests were conducted to ensure the algorithm's correctness and efficiency.

## Strategy Design Pattern
I adopted the Strategy Design Pattern to manage different types of players within the game, providing flexibility in choosing and switching game strategies without the need to alter client code. This results in three types of players:

* Random Player: Makes moves randomly, without following a determined strategy.
* Human Player: Allows me, the user, to play, making decisions based on observing the state of the game.
* AI with Minimax and Alpha-Beta Pruning: Employs the Minimax algorithm with Alpha-Beta pruning to determine the best possible move, anticipating potential moves from the opponent.

## Heuristic Utilization
The heuristic used in this project is designed to efficiently evaluate board configurations, focusing on victory possibilities and blocking strategies. It uses the formula 
h=B−A−C, where: 

* A represents the opponent's win opportunities that were blocked, 
* B is the minimum number of moves for my victory, and 
* C for the opponent. This heuristic approach allows the agent to quickly assess the board and make informed strategic decisions.

## How to Run the Code and Operation
### Prerequisites
   Ensure Java is installed on your machine. If not, download and follow the installation instructions on [official Java website](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)

### Execution
   Open the terminal or command prompt.
   Navigate to the directory where the Console.java file is saved.
    
   Compile the file using the Java compiler with the command:
        
    javac Console.java
    
   Run the game with the command:
    
    java Console
    
### Operation
  The game starts in the console, with user interaction through terminal inputs. I am guided through the options available, including choosing the type of player (Human, Random, or AI with Minimax and Alpha-Beta Pruning) and making moves when it's my turn.
