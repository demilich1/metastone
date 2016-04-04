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
- Clone the repo from GitHub
- Download and install Gradle (most recent version 2.7 is recommended, but older version should also work)
- You need to add the /bin folder of Gradle to your PATH environment variable if you are on Windows
- Now navigate to the folder with your local git repo, open Command Prompt/Terminal and type 'gradle eclipse'
- This will download all required dependencies and create appropriate .project files
- Now you can open up Eclipse and choose 'Import -> Existing projects into workspace'
- After the import is done, be sure to check the Build Path settings in Eclipse. you may have to change the path to your JDK installation there (JDK 1.8u60 is required)
- That's it, you should now be able to launch the project from Eclipse ('gradle run' on the command line should also work, but it is much slower than the launch from within Eclipse)
(This guides assumes you are using Eclipse, other IDEs should work too. I think IntelliJ can even directly import Gradle projects) 

### How do I build my own cards? ###
This feature is in very early stages and there is no official support yet. There is no documentation at all. If you really want to start right now:
- Navigate to your MetaStone install folder
- Look for a folder named 'cards'
- Create a new folder named 'custom'
- Any .json file in there will be parsed and treated like built-in cards
- To learn the format it is advised to copy an existing card, change the filename and the 'id' attribute (important!) and make small changes
- To validate that the cards you added are well formed and can be parsed, run the following command: `./gradlew test -Dtest.single=ValidateCards` 
- You have to restart MetaStone for new cards to be detected
- **The card format is subject to change; cards you create now may not work in future versions**

### Running tests
* The easiest way to run tests is from the command line.  `./gradlew test`
* You can also run tests from your favorite IDE. 
   * For example, in IntelliJ right click on `src/test` folder and select `Run All Tests`
* You can also run individual tests:
   * From the command line `./gradlew test -Dtest.single=ValidateCards`  This will run the ValidateCards test to ensure that all cards are parseable.
   * From your IDE, right click on the individual test file and select `Run Test`
* If you encounter test failures open the test report file `build/reports/tests/index.html` for details on the failures
* Look [**here**](/src/test/java/net/demilich/metastone/tests) for list of existing tests
