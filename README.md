# LevelEditor

[![Build Status](https://travis-ci.org/alex-ac/LevelEditor.svg?branch=master)](https://travis-ci.org/alex-ac/LevelEditor)

Isometric level editor for defold game engine.

## Concept

### Definitions

 * Project - Bundle of all data.
 * Project file - Json file with full project packed inside.
 * Block - Simple construction unit of the level.
 * Static block - Concrete specialisation of the Block. Uses voxel model to
    automatically subdivide tiles to the levels and generate collisions.
 * Interactive block - Another concrete specialisation of the Block. Uses several
    Layers that can be drawn separately. Have script file.
 * Theme - Just a string name. It can be used to select which of block variants
    should be used for drawing. Each block must have 'default' theme that will be
    used when block have no image for current theme.
 * Block instance - Block, placed into the world. It contains position and
    reference to the original block.
 * Level - the world with number of blocks. It selects the theme.
 * Voxel - cube which defines collisions. Each voxel have a 'u' (unit) which
    is defined as f(n) = 2 ^ (- n), where N is a non-negative integer number.
    The length of the voxel's edge is equal to 1 unit. The voxel has origin
    (the point with smallest x, y, z coordinates of the voxel). x = u * a;
    y = u * b; z = u * c, where a, b & c are integer numbers. The voxel's origin
    z-coordinate can be negative only when u = 1 and z = -1.

### Game axes

Assume that x points to right-forward, y points to left-forward and z points to
top.

```

     z |
  `-.  |  .-´
  y  `-.-´  x

```
 
### Editor modes

 1. Static block. This mode is used to edit static blocks. It allows to
    select an image for each theme and edit voxel model.
 2. Interactive block. This mode is used to edit interactive blocks. It allows
    to select a bundle of images for each theme, and assign z-coordinate of each
    image (from -1.0 to 1.0). It also allows to create several collision objects
    for block.
 3. Level. This mode is used to edit level. It allows to place block instances
    into the level and switch level's theme.

