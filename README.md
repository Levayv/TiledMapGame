### Private Self-Educational project. 
Any material copyright or trademark was ignored.
# 2D Tiled Game with top-down view
###### LibGdx project (LWJGL OpenGL orthographic spritebatch drawing)

Short description.
https://docs.google.com/document/d/1DXjIJ1i1VLg8V8BtIQRvqlCTvYGndlUhkUMHK1tuswA/edit#

-----

[logo]: https://www.pscpower.com/wp-content/uploads/2018/03/Under-Construction.jpg "Under Constuctionn sign"
![alt text][logo]


## Major Things done
- Tiled .tsx .tmx 
  - Loaded from XML
  - can have multiple tile / object layers
- Basic player movement WASD
- Collision detection (AABB) when player moves
- Basic camera movement Arrows
- Static and Dynamic objects based on Actors
  - Static uses builder with pool
  - Dynamic uses pathfinder and fsm
- Dynamic objects detect nearby friend or foe
- Pathfinder stores path for later traversal
- Fsm used for different logic , animation , response to events
- Basic GUI 
  - create / destroy static object with builder+pool
  - open close doors (basic events)
## minor / integrated features
- Actor hierarchy
 - Static objects (Something abstract) 
   - deprecated, used for tiles  
   - future integration to tiled maps needed   
 - Dynamic objects (NPC abstract)
  - Door extends NPC
  - Mob extends NPC
 - Both can get data from tileset and/or json
- WorldManager 
 - composite builder
- PositionManager
  - calculating different coordinates
   - stage position to/from viewport position
   - tile place in grid >>> pointer to start drawing actors
   - pointer location >>> tile position in grid 
## Major Tasks to do 
- path start end for pathfinder must be stored in map XML or level json
- integrate game clock with job scheduling  
- create new json for level/map additional config
## Luxury tasks to do 
- get string literals from singlton helper (future localisation support)
  - create DB for them , maybe JDBC  
