# AI-Snort-Player
An Artificial Intelligence player written for a simple game called Snort (included in the project)
## General overview
### Game rules
Snort is a two-player game in which players take turns dropping **stones** in two different colours on a **board** divided into **fields**. Once a field is taken by a stone of one colour the fields on the right, left, above and beneath are **restricted** for the other player. The **objective** is to lead to a situation in which the other player runs out of **moves**.
### Player
If there are more than **125** moves available for the player it moves similarly to a horse in chess trying to **take as much space** as possible. If there are more than **50** moves available for the player it uses the **alpha-beta algorythm** with the depth of **2** to determine the best move. If there are less than **50** the depth increases to **3**.
## Build and run
* **mvn package** from command line in the main project directory
* run the **games.jar**
* choose the **board size**, the **time per move** and the **players**
* to choose the player you need to select the **path to the jar** containing the player
* leaving it empty will cause the **user** to become the **player**
## Authors
* **Mikołaj Frankowski** - [RolandMcDoland](https://github.com/RolandMcDoland)
* **Krzysztof Pasiewicz** - [Nebizar](https://github.com/Nebizar)

