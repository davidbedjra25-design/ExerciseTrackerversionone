# David's Exercise Tracker

> The exercise tracker tracks the path along which a swimmer swims in a lake. It displays this information on a regular 2-dimensional grid. While swimming, the swimmer will have to avoid obstacles like rocks, logs and crocodiles. The swimmer relies on an oxygen tank during the weekdays and a snorkel during the weekend for a challenge.

### Mermaid diagram
Here is our domain model with attributes and initial behaviours added:

```mermaid
classDiagram
    class Swimmer {
        -String username
        -Set~GearType~ gears
        -int distance
        -Map map
        -ArrayList~Activity~ activities
        
        +getUserName() String
        +addGear(GearType gear) void
        +removeGear(GearType gear) void
        +addActivity(Activity activity) void
        +addMap(Map newMap) void
        +getDistance() int
        +getActivities() ArrayList~Activity~
        +getGears() Set~GearType~

    }

    note for Swimmer "Invariant properties:
    <ul>
        <li>username != null
        <li>username.size() > 0
        <li> distance > 0
        <li>activities != null
        <li>loop: all activities in List
              of activities are not null
    </ul>"

    class GearType {
        <<Enum>>
        -OXYGENTANK
        -SNORKEL
    }

    class Map {
        -int WIDTH
        -int HEIGHT
        -Obstacle obstacle
        -ArrayList~Obstacle~ obstacles
        -char[][] grid

        +getObstacles() List~Obstacle~
        +getWidth() int
        +getHeight() int
        +addObstacle(Obstacle obstacle) void
        +createGrid() void
        +getGrid() char[][]
    }

    note for Map "Invariant properties:
    <ul>
        <li>WIDTH >= 0
        <li>HEIGHT >= 0
        <li>obstacles != null
        <li>loop: all obstacles in List
              of obstacles are not null
    </ul>"


    class Obstacle {
        <<record>>
        +toString() String
    }

      note for Obstacle "Invariant properties:
    <ul>
        <li>coordinates != null
        <li>WIDTH >= 1
        <li>HEIGHT >= 1
    </ul>"
    

    class Route {
        -Coordinates STARTPOSITION
        -Coordinates ENDPOSITION
        -ArrayList~Coordinates~ path
        
        +getStartPosition() Coordinates
        +getEndPosition() Coordinates
        +getPath() ArrayList~Coordinates~ path
        +addCoordinates(Coordinates coordinates) void
    }

      note for Route "Invariant properties:
    <ul>
        <li>STARTPOSITION != null
        <li>ENDPOSITION != null
    </ul>"
    
    class Coordinates {
        -int X
        -int Y
        
        +getX() int
        +getY() int
        equals(Object object) boolean
    }

    note for Coordinates "Invariant properties:
    <ul>
        <li>X >= 0
        <li>Y >= 0
    </ul>"
    
    class Activity {
        -String activityName
        -Route route
        -int distance

        +getActivityName() String
        +getRoute() Route
        +getDistance() int
    }

      note for Activity "Invariant properties:
    <ul>
        <li>activityName != null
        <li>activityName.size > 0
        <li> distance >= 0
    </ul>"
    
    class PathFinder {
        -Route route
        -Map map
        
        +createPath(char[][] grid) void
        -isValid(int x, int y, boolean[][] visited) boolean
        -buildPath(Coordinates[][] parent, Coordinates endPosition) void
    }

    note for PathFinder "Invariant properties:
        <ul>
            <li>route != null
            <li>map != null
        </ul>"

    %% these are composite relationships
    Map --* Obstacle
    Swimmer --* Activity
    
    %% these are aggregate relationships
    Obstacle --o Coordinates
    Swimmer --o Map
    Route --* Coordinates
    Activity --* Route
    PathFinder --* Map
    PathFinder --* Route

```
