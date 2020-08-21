# Tetris 210

## Tetris Survival

Special features and additions to the classic tetris game:
- A special game mode where bombs appear
- Mino hold feature
- Hard drop feature
- A special game mode with special items

*Tetris 210* is a variation of the classic tetris game, with improved features and new game modes. This game is for all fans of tetris and 
other gamers who are interested in simple games that require advanced thinking, processing, and coordination skills. 
*Tetris 210* presents two special game modes, aside from the traditional tetris marathon game mode: *Survival* mode and Arcade mode.
*Survival* mode features **BOMBS**, which can only be destroyed when hit by a mino. The player tries 
to survive for as long as possible before the matrix fills up with bombs. The *Arcade* mode, on the other hand, features special items that 
the player may encounter with interesting effects that add variation to the game.

Controls:

Move: left-arrow and right-arrow

Soft drop: down-arrow

Hard drop: space

Rotate right: up-arrow

Rotate left: Z

Pause: ENTER

Save: S

Quit: Q

User Stories

Phase 1

- As a user, I want to be able to create an account for the game 
with a username and a password, as well as a screen name
- As a user, I want to be able to view the data on my account such 
as the username, password, screen name, and my high scores
- As a user, I want to be able to login to my account
- As a user, I want to be able to quit the game

Phase 2

- As a user I want to be able to save my account
- As a user I want to be able to login to the same account I saved
- As a user, I want to be able to save my game state in all 
3 game modes
- As a user, I want to be able to load my game state and 
continue my game for all 3 game modes


Phase 3

How to play the game and access the saving and loading functions:
- Run main in Main. This should open up a pop-up
- Create a new account by entering the username, password, 
and screen name
- Login by entering the username and password
- Click the play button
- Click the new game button
- Choose whicever game mode you want to play. Another ui 
should open up
- Press the ENTER key to pause the game, then press the 
S key to save the game. The game should display the message
"SAVE SUCCESSFUL"
- Rerun main but this time after logging in and clicking the 
play button, click the load state button and then whichever
game mode you saved. The game will resume wherever you left off
- You can view the data of your current save by clicking on the 
profile button after logging in, and then clicking the 
view save state button on the bottom, and then selecting
which save state you want to view


Phase 4

Task 2
- The type hierarchy is the mino hierarchy
- The Tetromino class is the parent class
- The Imino, Jmino, Lmino, Omino, Smino, Tmino, and Zmino classes
are the subclasses, which represent the 7 tetrominos 
- Each of the subclasses have a different position which is what 
sets them apart, including the position in which they are generated
onto the matrix
- The Tetromino class also consists of a getRotation() method 
which is overrided differently in each subclass

Phase 4: Task 3
- Originally I had a RegularMatrix class, a SurvivalMatrix class,
and an ArcadeMatrix class which all extended off of the Matrix
class, but then I realized they were absolutely unnecessary
so I got rid of them
- Originally I represented the ghost as an actual Tetromino 
object but just changed its color, but that was messy since the 
ghost would not always be entirely painted, namely when the 
piece is really close to the well (the well is the group of 
blocks which consists of minos that have landed)
- My graphics had a flickering problem that was caused by when 
I was trying to erase the screen, then update it again. Also,
when I pressed two keys at the same time this problem would
occur because both of them would need to redraw the screen.
I fixed this by using a BufferedImage, which displays the previous
screen to replace the flickering