# ThreeTriosGame

A turn-based strategy game where two players must occupy the board with the most cards given. When a player places a card, that space cannot be a hole and it cannot already be occupied. If the card's number is greater than the number in the facing direction of another card that the other player occupies, the person ends up occupying that card on the space, and that effect stacks. The game ends when all possible spaces on the board are occupied. The winner is the player who occupies more spaces on the board. 

Reverse mode is basically "less than" instead of greater.
Fallen Ace is that the A is the lowest, less than 1, usually it's the greatest.
Reverse Fallen Ace is Reverse and Fallen Ace Combined.
Same is a modification that can be added to all these game modes where the card can flip the other card if the number facing the other number are the same. 

Overview:
This codebase is the implementation for the Three Trios gameplay with a textual customer.provider.view. To understand the code, the user must know about builder customer.provider.model patterns, how to connect the textual customer.provider.view to the customer.provider.model, and know how to test the customer.provider.model and customer.provider.view based on how the game works.

Quick start:
To use the code, such as testing, you can start by creating a ThreeTriosModel, a GameView, and starting the game with the given configuration grid and card files which are located in src\\customer.ConstructorFiles. 
An example is like this:
ThreeTriosModel customer.provider.model = new ThreeTriosGameModel();
    GameView customer.provider.view = new ThreeTriosView(customer.provider.model);
    customer.provider.model.startGame(
            "src\\customer.ConstructorFiles\\GridFile_13",
            "src\\customer.ConstructorFiles\\CardFile_5",
            false);

Then you can build on the code using the public methods of customer.provider.model, such as playToGrid() and battle() to play the game. After using a playToGrid(), you must use battle(). The functions have been divided into two to make debugging and "playing the game" purposes easier.


Key Components:
The ThreeTriosGameModel drives the ThreeTriosView since a customer.provider.view needs the game customer.provider.model,
 and all the files in customer.gamefeatures drive the ThreeTriosGameModel because those classes are needed to start and setup the game.
- GameGrid is for the grid/board of the game.
- GameCard is for each card in the player's hand which can be put on a grid.
- Direction is for the 4 directions of the card (east, west, south, and north) used for battling.
- Player is for each player in the game, red and blue.Three
  The JThreeTriosPanel is driven by the customer.provider.controller and the
  customer.provider.model as the customer.provider.controller starts the gui, and the customer.provider.model's commands
  change up how the gui looks after moves.


Source Organization:
- Configuration Grid and Card files are found in src\\customer.ConstructorFiles
- File Operation files on operating the configuration grid and card files are found in src\\customer.fileoperation
- The code for the game customer.provider.model ThreeTriosModel is found in src\\customer.provider.model
- The code for the textual customer.provider.view/output is found in src\\customer.provider.view
- The code for all the game features such as the card, grid, player, etc. are found in src\\customer.gamefeatures
- The code for that runs all the tests for the individual classes are found in their respective test packages.
- The GUI for HW6 is in JThreeTriosPanel, which is connected to the Controller, which implements View Features.
    - When a card is selected, it is hilighted in a black box. (Original/not-selected is a light gray box around the card)
    - Press on a card to selected, press on it again, or press on a different card to deselect the card if it is the
    player's turn.
    - Press on a spot on the grid/board to get the square's coordinates.
- Screenshots for HW6 are in the Screenshots folder which include the pictures:
    - A selected card from blue player's hand when it is blue's turn.
    - A selected card from red player's hand when it is red's turn.
    - The beginning of the game when nothing has been done yet.
    - In the middle of the game after some battles and playing to grid by both provider.players have been done.
    (A non-trivial intermediate point of the game)


Update #1 (12/24):
- Get a shallow copy of the game grid, where the user cannot modify the actual game grid
- Get the number of cards a player can flip given a card index from the player's hand,
  and the x and y (the cell location) assuming it is the player's turn
- Get a player's current score in the game.
- Updated some of the functions to throw an IllegalStateException where if the game either hasn't started yet
  or the game is already over.
- Updated a lot of the game feature classes to have a constructor to take in another of its own (i.e. public Player(Player another))
  so that while copying things, objects to not end up getting referenced to each other and so that the game
  does not actually get played during the
  getNumCardsCanFlip() as it uses a copy of the game customer.provider.model and plays a "different card"
  (the same card but not for the actual game) to a copied grid and then a "simulation".

Update #2 (12/24):
- Fixed the bugs of recursive battling and battling when cards got played to the grid
  (color of cards did not change for some reason).
- Fixed UI implementation of deselecting the card if the player tried to play a card to a hole.
- Fixed the UI implementation where if the game is over and someone won, it displays a message that the game is already
  over and a player won.
- Added that there could be a tie game, and also fixed the message to display a message of a tie game if there is a tie
  game at the end.
- Added another method in our implementation of getting all the game's cards when the game is made because the provider
  had a method in the model that got all the original cards of the game.

Update #3 (12/24):
- Pressing H reveals the hints for a player for how many cards they can flip, only if a card is selected. Press H again to hide the hints. When it moves to the next player's turn, the new player needs to press H to reveal their hints.
- In the command line, typing "r" for the reverse battle mode, typing "fa" for the fallen ace battle mode, "rfa" for the reverse and fallen ace battle mode, and "n" for the normal battle mode.

Update #4 (5/20/25):
- Added a modification type "Same" where the player can modify the game mode to include where if the number of the player's card is the same as the
  attacking direction of the opponent's card number, the opponent's card gets flipped (the player takes it).
- Fixed a small bug where players could not get hints when the board was a different length and width.
