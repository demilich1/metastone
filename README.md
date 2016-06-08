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
* Run the application from the command line: 
   * Linux/Mac OSX `./gradlew run`
   * Windows `gradlew.bat run`
   * Note: this will download all dependecies, compile and assemble all modules and then run the app.
* Download dependecies and compile: 
   * Linux/Mac OSX `./gradlew compileJava`
   * Windows `gradlew.bat compileJava`
   * Note: this will download all dependecies and compile all modules.  Usefull when developing.
* Get a list of all gradle tasks: 
   * Linux/Mac OSX `./gradlew tasks --all`
   * Windows `gradlew.bat tasks --all`
* If you want to build from Eclipse, you will need to create the Eclipse settings files: 
   * Linux/Mac OSX `./gradlew eclipse`
   * Windows `gradlew.bat eclipse`
   * Open Eclipse and choose `File > Import > General > Existing projects into workspace`
   * Select the `Search for nested project` checkbox on the `Import Projects` screen.
   * Change `Eclipse > Preferences > Java > Compiler > Compiler Complience Level` is set to 1.8
   * Change `Eclipse > Preferences > Java > Compiler > Building > Circular dependencies` from `Error` to `Warning`.  There is a [known bug](https://issues.gradle.org/browse/GRADLE-2200) with importing multi-module gradle projects into Eclipse. The IDE of choice for working with gradle projects is [IntelliJ IDEA](https://www.jetbrains.com/idea/).
* If you want to build from IntelliJ IDEA:
   * Open a new project `File > Project From Existing Sources`.  Project will be imported from the `build.gradle` files.
* **NOTE:** When building from an IDE you will need to manually generate the `BuildConfig.java` file, prefereably before you import the project.  Otherwise your IDE will complain about unresolved references to `BuildConfig`.
   * Linux/Mac OSX `./gradlew compileBuildConfig`
   * Windows `gradlew.bat compileBuildConfig`

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
- You can build your own cards or modify existing cards without having to fork the project!
- Card files are located in the `metastone/cards` directory.  **Use these as reference!**
   * Linux/Mac OSX `~/metastone/cards`
   * Windows `C:\Users\[username]\Documents\metastone\cards`
   * Note: you must launch the app at least once for card data files to be copied.
- Any `.json` files you place in your `metastone/cards` folder will be parsed and treated like built-in cards.
- To learn the cards format it is highly recommended that you copy an existing card, change the `filename` and the `id` attribute (**<-- important!**) and make small changes.
- Restart MetaStone for new cards to be detected.
- If you are building out official cards or fixing existing cards, you will need to fork the project then make your changes in your repo's `metastone/cards/src/main/resources/cards` dir.  Then open a [Pull Request](https://help.github.com/articles/using-pull-requests/) into the project [master](https://github.com/demilich1/metastone/tree/master) branch with your changes.
- Make sure to validate that the cards you added are well formed and can be parsed! Run the following command: 
   - Linux/Mac OSX `./gradlew cards:test` 
   - Windows `gradlew.bat cards:test`
- **The card format is subject to change; cards you create now MAY NOT work in future versions**
- In the rare chance that your card files get messed up beyond repair,  you can always force the app to overwrite your local card files with the versions distributed with the app in `cards.jar`.
   * _Option 1_: Delete the `~/metastone` dir.  
      * You **WILL LOOSE** all your changes, including **ALL new files** you may have added. DANGEROUS! MAKE A BACKUP!!
      * Linux/Mac OSX `rm -rf ~/metastone`
      * Windows `rmdir /s C:\Users\[username]\Documents\metastone`
      * Card data files will be copied in their prestine state after you restart the app.
   * _Option 2_: Edit the `~/metastone/metastone.properties` file and update the `cards.copied` property.
      * change `cards.copied=true` to `cards.copied=false`
      * New files you may have added will NOT be affected.
      * All card files that are distributed with the app will be overritten after you restart the app.

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
* Look [**here**](/game/src/test/java/net/demilich/metastone/tests) for list of existing game tests.

