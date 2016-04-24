#MetaStone#

### What is it? ###
MetaStone is a simulator for the online collectible card game (CCG) Hearthstone&reg; by Activison Blizzard written in Java. It strives to be a useful tool for card value analyzation, deck building and performance evaluation. There is also support for custom cards, allowing users to implement their own card inventions and testing them within the simulator engine. MetaStone tries to re-implement all game mechanics and rules from the original game as accurately as possible. 

### What is it not? ###
This is no Hearthstone replacement or clone. Please do not request better graphical effects, sounds or anything which makes it feel more like Hearthstone. There won't be any mode to battle against other human players. This is a tool meant for advanced players; if you just want to play Hearthstone, please play the real game.

### How do I run it on Linux? ###
* Go to [Releases](https://github.com/demilich1/metastone/releases) and download the latest release (`metastone-X_Y_Z_jar.zip`).
* Extract the contents of the .zip file.
* Open the Terminal (`Ctrl+Alt+T` on Ubuntu) and access `../MetaStone-X.Y.Z/bin`.
* Execute this command: `./MetaStone`.
    * Executing `sudo ./MetaStone` will execute the file as Root ("Super User"), this is not necessary though.
    * You might need to make the file executable (On Ubuntu: Right Click the File -> Properties -> Permissions -> Allow executing file as program).

### Can I contribute? ###
Sure! There is still a lot to do and anybody willing to contribute is welcome

### What needs to be done? ###
- UI improvements in general are welcome
- We always need more unit tests! If you don't know what to test, take a look at http://hearthstone.gamepedia.com/Advanced_rulebook and just pick an example of card interaction from that wiki page
- Code refactorings to make the code simpler and/or faster
- There is a bug in the code and you know how to fix it? Great!
- Better AI: at the moment the most advanced AI is 'Game State Value', however it is very subpar compared to human players. A more sophisticated AI would be a huge boon
- Also consider having a look at the open issues
- Anything else you would like to improve

### How do I compile the code on my machine? ###
* NOTE **JDK 1.8 is required!**
* Clone the repo.  See [https://help.github.com/articles/cloning-a-repository/](https://help.github.com/articles/cloning-a-repository/) for help.
* Open a terminal / command prompt and nagivate to your to your git repo location
* Download dependecies and compile: 
   * Linux/Mac OSX `./gradlew compileJava`
   * Windows `gradlew.bat compileJava`
* Run the application from the command line: 
   * Linux/Mac OSX `./gradlew run`
   * Windows `gradlew.bat run`
* Get a list of all gradle tasks: 
   * Linux/Mac OSX `./gradlew tasks --all`
   * Windows `gradlew.bat tasks --all`
* If you want to build from Eclipse, you will need to create the eclipse settings files: 
   * Linux/Mac OSX `./gradlew eclipse`
   * Windows `gradlew.bat eclipse`
   * Open Eclipse and choose `Import -> Existing projects into workspace`
   * Check the Build Path settings in Eclipse and ensure JDK location is correct.
* If you want to build from IntelliJ:
   * Open a new project `File > Project From Existing Sources`.  Project will be imported from the `build.gradle` files.

### Project structure
* MetaStone is made up of a handfull of source modules.  Here's what the top level structure looks like:
```
metastone
 ├── app    // Application UI code and resources. Depends on 'game' and 'cards' modules.
 ├── game   // Game source code. Depends on 'shared' module.
 ├── shared // Shared code between 'app' and 'game' modules.
 └── cards  // Cards, decks and deckFormat data files.
```
* Each module can be built separately.  Their respective dependencies will get compiled and pulled in at build time. For example:
* To produce a `cards.jar` file which contains all the cards, decks and deckFormat data files:
   * Linux/Mac OSX `./gradlew cards:assemble`
   * Windows `gradlew.bat cards:assemble`
* To build the game module and produce a `game.jar` file:
   * Linux/Mac OSX `./gradlew game:assemble`
   * Windows `gradlew.bat game:assemble`
* To produce a standalone distributable app binary:
   * Linux/Mac OSX `./gradlew app:assemble`
   * Windows `gradlew.bat app:assemble`

### How do I build my own cards? ###
**This feature is in very early stages and there is no official support yet.** There is no documentation at all. If you really want to start right now, here's how you can start:
- Cards that are bundled with MetaStone are in `cards` module `resources` folder.  Use these as reference!
```
metastone
 └── cards
    └── src
       └── main
          └── resources
              ├── cards
              ├── decks
              └── formats
```
- Create a new `metastone/cards` folder under your user home directory:
   * Linux/Mac OSX `mkdir -p ~/metastone/cards`
   * Windows `use Windows File Explorer to create the metastone/cards dir`
- Any `.json` files you place in your `~/metastone/cards` folder will be parsed and treated like built-in cards.
- To learn the cards format it is highly recommended that you copy an existing card, change the `filename` and the `id` attribute (**<-- important!**) and make small changes.
- Make sure to validate that the cards you added are well formed and can be parsed! Run the following command: 
   - Linux/Mac OSX `./gradlew cards:test -Dtest.single=ValidateCards` 
   - Windows `gradlew.bat cards:test -Dtest.single=ValidateCards`
- Restart MetaStone for new cards to be detected.
- If you are building out official cards or fixing existing cards, make your changes in `metastone/cards/src/main/resources/cards`.  Then create a [Pull Request](https://help.github.com/articles/using-pull-requests/) into the project master branch.
- **The card format is subject to change; cards you create now MAY NOT work in future versions**

### Running tests
* The easiest way to run tests is from the command line.
   * Linux/Mac OSX `./gradlew game:test`
   * Windows `gradlew.bat game:test`
* You can also run tests from your favorite IDE. For example:
   * In IntelliJ right click on `src/test` folder in a given module and select `Run All Tests`
* You can also run individual tests using the `-Dtest.single=[TEST NAME]` command line option.
   * From the command line
      * Linux/Mac OSX `./gradlew game:test -Dtest.single=SecretTest`
      * Windows `gradlew.bat game:test -Dtest.single=SecretTest`
   * From your IDE
      * Right click on the individual test file and select `Run Test`
* If you encounter test failures open the test report file `build/reports/tests/index.html` for details on the failures
* Look [**here**](/src/test/java/net/demilich/metastone/tests) for list of existing game tests.

