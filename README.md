# ing-sw-2018-Zucca-Salvini-Tonelli

## Group members

* Zucca Diego, 843617, 10503482
* Salvini Elio,
* Tonelli Alessio,

## Coverage Test and UML

See the Coverage Test folder and UML image in repository.

## Introduction

The project has the target to develop "Sagrada" game. In particular, as we are students, the aim of the project is to 
develop skills on Software Engeneering by using most part of the Design Pattern. 

Model - View - Controller is the main pattern used in order to face a project of such a complexity.

Model has the whole logic of the Game. It is part of the Server. All the informations are passed through a Singleton Instance 
called ViewData which, implementing Serializable, is responsible for dialog between Client and Logic. We focused on one creed:
our client has should been Thin Client. In fact, there's no trace in View side of some imported classes belonging to Model.

Before starting reading each paragraph is important to give a look to UML diagram which explains a lot about
operational decision.

## Model

Design Pattern : State , Strategy , Singleton , Observer , Factory

There are two main classes: Match and Player. They have the responsability to contain all data of the game.

Match is the one which initializes the real match, namely Objects (RoundTrack, DiceBag, DraftPool, ToolCard, PublicCard, Player)
which are part of the match itself. In order to generate random number for the production of Cards and Maps, we implemented 
RandomGenerator which imports java.util.Random.

Player, instead, is the one that contains WindowPattern, which contains 20 Cells, and PrivateCard.

All the other Model classes contains methods and attributes that are used and modified by Match during the game.

Moving to the design:

### State

State is used to maintain the user's states.

At the begin of the match every user is "disabled", then only if a user is "enabled" he is authorized to play and makes changes into the Model.
It is also used to handle the connection and disconnection of a user from the match with a particular state "disconnected".

### Strategy

Strategy has been adopted due to the choice of the card. As the tool and public cards has the same structure respectively, we create 
two interface ToolStartegy and PublicStrategy. Public Cards are simplier than the Tool Cards, so we are going to describe the last one.

Each tool needs a particular input in order to be executed. So we are supported by a class called ObjectiveTool which is enriched 
by an other class PlayerToolParameter. Now, this is complicated but we will explain in the controller why all this has been done.

So when a tool card is used by a user the objective tool will be fill in the gap every time the user will pressed a button or an image.
By using this trick we ensure the parameter which is passed to the method ToolCard.doOP(parameter) is enough in order
to execute the entire card.

### Singleton

This pattern is used in three cases: Match, ViewData and Server.

While Server is a single istance because we have a single match and it wasn't necessary to have more than one, Match is a single istance in order to avoid that users have different tool or public cards.

Instead, ViewData is a single istance to maintain, as a memory, the informations about the match and make more easier to use it in the Observer pattern.

### Observer

DraftPool, RoundTrack, ToolCards and WindowPattern have an istance of Observer. They are objects that in game are steadily modified by someone, so these classe at the end of every changes of Model notify their own Observer about these.
At the end of the invoked method every user call updateView() to obtain changes made by the other users.

Some ToolCards don't end with a single operation. To notify the intermediate modification, Match has an Observer that calls all the Observer of the other classes. It is used only with ToolCards.

### Factory

Factory has been adopted due to the choice of the map. When a user chooses Wp between four randomic windows, he creates 
instance of WindowPFactory which has method createWindowPattern(parameter).
Every Wp is identified by a number, so in this case using this number as parameter the Match can create the correct Wp and assign it to the correct player.

### Rendering

Window Patterns' item requires a more specific description. In fact, there is a detailed work done on it.
One of the Advanced Feature required by Politecnico di Milano is to realize a system able to upload dynamic Wps and makes 
authomatic rendering.

Solution: Factory class creates a SAXParser able to read XML file containing infos regarded to WP. At the moment of creation
parser gets the exactly file due to the choice made by user and builds the map authomatically by creating cells with color 
and number and giving title and difficulty to the Wp. Once a Window has been complitely created, it is passed to View which 
is responsible of rendering the map. Class PaneView, which extends Pane, creates twenty panes with specific background.
Each pane is a cell.

Moreover, the Factory has information of how many XML files are into the server contained in an ArrayList of String.
So, when a programmer wants to upload a new one it is important he or she put a successor number to the last one already existing, 
just to be sure the file will be found in the list.

## View

JavaFX and no fxml file used.

Things begin with StartView. Simple Stage which allows the user to choose the connection (RMI or Socket).
After having chosen, an instance of particular Client is created.
Next step is an other stage: the menu. GUI class contains login and signup service. Logic, constraints and database is 
implemented in the server.
Once the client gets the access he will wait in the lobby until the timeout will be over or the number of players 
will be equal to four.
After that, client should choose window he wants. When all players choose the window, game can start.

PlayGame is the main class. It has a timeline which displays countdown of time in which the player can do a move. Time is the same
of the one in ControllerTimer when the server started after the time has been set.
But this is not the only thing it does.Timeline is also responsible of the update of my view during the match. Each 5 seconds it 
calls update() in order to update, by getting new infos from ViewData.

GridPaneRound, GridPaneDraftPool, GridPaneWindow and GridPaneWEnemy, whose names are quite explicit, have their own method update useful to get an update when a modification in model is performed.

ToolView is class which allows the use of tool cards. It encapsulates infos like button's coordinates, toolclicked. It will 
be passed throws client.useTool(toolView) to controller and it will be elaborated (see Controller paragraph).

Just to understand how it works we suggest you to read last paragraph and then try playing yourself.


## Controller

Controller has all the methods responsible for dialog between each component of the structure (timer, client, server, match...).

In Model and View paragraphs we promised you to describe what happened when a user pressed a tool card. Now the answer comes:

1) collect infos in ToolView.

2) create PlayerToolParameter transforming toolView infos in object belonging to the model 
(example:  map has 20 button into a gridpane. Each button has two int. These will be transformed to 
Coordinate which corrispond to a specific Cell in model).

3) send number of card used and PlayerToolParameter to the Match.

4) according to card selected create an ObjectiveTool which enrich infos coming from PlayerToolParameter with Object of the match such as DraftPool, Roundtrack, WindowPattern or DiceBag.

5) call the exactly tool card with doOP(ObjectiveTool) and tranform the model (*)

6) update()

(*) some cards needs to be stopped and ask for other input: we got this. In ToolView we add an attribute phase which facilitate
us to perform only the part of the tool card's code we need.

## Connectivity


## How to play

First of all you have to launch the server, initializing it with an input of search time and move time of the game.

After that, launch the game. You have to choice between two connectivity, RMI or Socket, choose what you prefer.
In the main menu you have to log in, but only if you are just registered. If you not, sign up and then make log in to play.

When a match is found, you have to choose between 4 different WP and then you have to wait until all other users have made their choices. Now the game is started, the first player will be the one who started first the research of the match.

Screen is divided in three columns : 

1) There are three tool cards on the left, which drop out when game started.

2) There are three public cards on the right, which drop out when game started required to count final score.

3) The middle column is a little bit complicated. In the upper part there is the Roundtrack which contains all the extra dice which remain from rounds which finish. Going down on the screen we see the enemies' maps and dice on them. Further more, there are three horizontal objects: player's map, buttons through which player can interact with images, and your personal private card (only you can see that). DraftPool is on the bottom part of the screen.

So when player gets his turn (he is enable), he is able to do some moves, icluding take a die, using a tool card, skip turn
or even exit from the game.

-Take die: you can place a die only once in a turn (except for Tool 8). To make this, press "Take Die" button, then click on the dice that you want in DraftPool and finally click on the cell of your WP to place it.

-Using a tool card: Tool card that says "After drafting" can be used once in a turn and only before placing a dice, while the others can be used when you want and every time you want (if you have enough tokens or there are no particular restrictions).
To use it, click "Use Tool" button, then select the tool you want to use. After select it, your token will be consumed, so pay attention to your choice.
Every tool has different operations to do, but in general if it says "After drafting" the first thing to do is select a dice from DraftPool, then follow the istructions of the tool (for Tool 12 you have to select first the color of the dice from RoundTrack and after that you can move dice from WP).

-Skip turn: if you click it, your turn is finished.

-Cancel: this button undo your selection of dice or can interrupt a tool card (in this case tokens will not be returned, so pay attention!)

-Exit Game: with this button you can exit from the game before it is over.

If you don't click on "take die" button, player is not enable to take a die from draftpool grid.
Same reasoning would apply to using tools. He must click on "using tool" in order to choose the card he wants.
It is also important that when a player has chosen a tool card he follows all the instruction the tool card asks in order to be used correctly.

There is nothing else to say ... JUST ENJOY YOURSELF !
