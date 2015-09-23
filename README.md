#MetaStone#

### What is it? ###
MetaStone is a simulator for the online collectible card game (CCG) Hearthstone&reg; by Activison Blizzard written in Java. It strives to be a useful tool for card value analyzation, deck building and performance evaluation. There is also support for custom cards, allowing users to implement their own card inventions and testing them within the simulator engine. MetaStone tries to re-implement all game mechanics and rules from the original game as accurately as possible. 

### What is it not? ###
This is no Hearthstone replacement or clone. Please do not request better graphical effects, sounds or anything which makes it feel more like Hearthstone. There won't be an mode to battle against other human players. This is a tool meant for advanced players; if you just want to play Hearthstone, please play the real game.

### Can I contribute? ###
Sure! There is still a lot to do and anybody willing to contribute is welcome

### What needs to be done? ###
- not all TGT cards are implemented
- a deck counter in Play Mode is missing (indicating how many cards are left in your deck); also UI improvements in general are welcome
- we always need more unit tests! If you don't know what to test, take a look at http://hearthstone.gamepedia.com/Advanced_rulebook and just pick an example of card interaction from that wiki page
- code refactorings to make the code simpler and/or faster
- there is a bug in the code and you know how to fix it? Great!
- better AI: at the moment the most advanced AI is 'Game State Value', however it is very subpar compared to human players. A more sophisticated AI would be a huge boon
- anything else you would like to improve

### How do I build my own cards? ###
This feature is in very early stages and there is no official support yet. There is no documentation at all. If you really want to start right now:
- navigate to your MetaStone install folder
- look for a folder named 'cards'
- create a new folder named 'custom'
- any .json file in there will be parsed and treated like built-in cards
- to learn the format it is advised to copy an existing card, change the filename and the 'id' attribute (important!) and make small changes
- you have to restart MetaStone for new
- 
- there is no notification if you make an error, the card will just not work or the program may crash
- ** the card format is subject to change; cards you create now may not work in future versions **

