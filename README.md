# Minesweeper

### About

This project deals with algorithms for generating the play field and processing player moves in 
the game Minesweeper.

The game starts with an unexplored minefield that has a user-defined number of mines.

The player can:
- Mark unexplored cells as cells that potentially have a mine, and also remove those marks. 
Any empty cell can be marked, not just the cells that contain a mine. 
The mark is removed by marking the previously marked cell.
- Explore a cell if they think it does not contain a mine.

There are three possibilities after exploring a cell:

1. If the cell is empty and has no mines around, all the surrounding cells, 
including the marked ones, can be explored, and it should be done automatically. 
Also, if next to the explored cell there is another empty one with no mines around, 
all the surrounding cells should be explored as well, and so on, until no more can be explored automatically.
2. If a cell is empty and has mines around it, only that cell is explored, revealing a number of mines around it.
3. If the explored cell contains a mine, the game ends and the player loses.

There are two possible ways to win:

1. Marking all the cells that have mines correctly.
2. Opening all the safe cells so that only those with unexplored mines are left.

The symbols that represent each cell's state are:

- . as unexplored cells
- / as explored free cells without mines around it
- Numbers from 1 to 8 as explored free cells with 1 to 8 mines around them, respectively
- X as mines
- * as unexplored marked cells

Below is a possible run of the program:

    How many mines do you want on the field?
    5

    |123456789|
    -|---------|
    1|.........|
    2|.........|
    3|.........|
    4|.........|
    5|.........|
    6|.........|
    7|.........|
    8|.........|
    9|.........|
    -|---------|
    Set/unset mines marks or claim a cell as free:
    1 1 freee
    Wrong Input! Please input two numbers and a command, either 'free' or 'mine', with one space between each input.
    Set/unset mines marks or claim a cell as free:
    1 1 free

    |123456789|
    -|---------|
    1|////1....|
    2|///12.111|
    3|///1.21//|
    4|///111///|
    5|/////////|
    6|/////////|
    7|111//////|
    8|..1/111//|
    9|..1/1.1//|
    -|---------|
    Set/unset mines marks or claim a cell as free:
    5 3 mine

    |123456789|
    -|---------|
    1|////1....|
    2|///12.111|
    3|///1*21//|
    4|///111///|
    5|/////////|
    6|/////////|
    7|111//////|
    8|..1/111//|
    9|..1/1.1//|
    -|---------|
    Set/unset mines marks or claim a cell as free:
    6 2 mine

    |123456789|
    -|---------|
    1|////1....|
    2|///12*111|
    3|///1*21//|
    4|///111///|
    5|/////////|
    6|/////////|
    7|111//////|
    8|..1/111//|
    9|..1/1.1//|
    -|---------|
    Set/unset mines marks or claim a cell as free:
    6 1 free

    |123456789|
    -|---------|
    1|////11...|
    2|///12*111|
    3|///1*21//|
    4|///111///|
    5|/////////|
    6|/////////|
    7|111//////|
    8|..1/111//|
    9|..1/1.1//|
    -|---------|
    Set/unset mines marks or claim a cell as free:
    9 1 mine

    |123456789|
    -|---------|
    1|////11..*|
    2|///12*111|
    3|///1*21//|
    4|///111///|
    5|/////////|
    6|/////////|
    7|111//////|
    8|..1/111//|
    9|..1/1.1//|
    -|---------|
    Set/unset mines marks or claim a cell as free:
    6 9 mine

    |123456789|
    -|---------|
    1|////11..*|
    2|///12*111|
    3|///1*21//|
    4|///111///|
    5|/////////|
    6|/////////|
    7|111//////|
    8|..1/111//|
    9|..1/1*1//|
    -|---------|
    Set/unset mines marks or claim a cell as free:
    2 8 mine
    
    |123456789|
    -|---------|
    1|////11..*|
    2|///12*111|
    3|///1*21//|
    4|///111///|
    5|/////////|
    6|/////////|
    7|111//////|
    8|.*1/111//|
    9|..1/1*1//|
    -|---------|

    |123456789|
    -|---------|
    1|////11..*|
    2|///12*111|
    3|///1*21//|
    4|///111///|
    5|/////////|
    6|/////////|
    7|111//////|
    8|.*1/111//|
    9|..1/1*1//|
    -|---------|
    Congratulations! You found all the mines!

# General Info

To learn more about this project, please visit
[HyperSkill Website - Minesweeper (Java)](https://hyperskill.org/projects/77).

This project's difficulty has been labelled as __Medium__ where this is how
HyperSkill describes each of its four available difficulty levels:

- __Easy Projects__ - if you're just starting
- __Medium Projects__ - to build upon the basics
- __Hard Projects__ - to practice all the basic concepts and learn new ones
- __Challenging Projects__ - to perfect your knowledge with challenging tasks

This repository contains:

    minesweeper package
        - Contains the minesweeper.Main java class that contains the complete implementation of the program.

Project was built using java version 8 update 381

# How to Run

Download the minesweeper repository to your machine. Create a new project in IntelliJ IDEA, then move the downloaded 
minesweeper repository to the src folder, then run the minesweeper.Main java class.
