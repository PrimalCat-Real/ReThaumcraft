### VecHelper Class

**Description:**
The `VecHelper` class is a utility class designed to simplify and unify the use of vectors in Minecraft Forge modding. It provides various methods for manipulating and working with vectors in the Minecraft world.

#### Constants

1. **CENTER_OF_ORIGIN**
   - Description: A constant representing the center of origin in Minecraft.
   - Type: `Vec3`
   - Value: `(0.5, 0.5, 0.5)`

#### Methods

1. **offsetDir**
   - Description: Returns a `Vec3i` object representing a direction based on the provided `Direction` enum.
   - Parameters:
     - `dir` (Type: `Direction`) - The direction enum.
   - Returns: `Vec3i`

2. **radialOffset**
   - Description: Calculates a position on the perimeter of a circle around a given position.
   - Parameters:
     - `pos` (Type: `Vec3`) - The center of the circle.
     - `distance` (Type: `float`) - The radius of the circle.
     - `current` (Type: `float`) - The current point within the circle.
     - `total` (Type: `float`) - The total number of points in the circle.
   - Returns: `Vec3`

3. **rotatingRadialOffsets**
   - Description: Calculates an array of positions on the perimeter of a circle that rotate around the center based on game time.
   - Parameters:
     - `pos` (Type: `Vec3`) - The center of the circle.
     - `distanceX` (Type: `float`) - The radius of the circle along the X-axis.
     - `distanceZ` (Type: `float`) - The radius of the circle along the Z-axis.
     - `total` (Type: `float`) - The total number of points in the circle.
     - `gameTime` (Type: `long`) - The current game time.
     - `time` (Type: `float`) - The total time for one position to complete a full rotation cycle.
   - Returns: `ArrayList<Vec3>`

4. **rotatingRadialOffset**
   - Description: Calculates a single position on the perimeter of a circle that rotates around the center based on game time.
   - Parameters:
     - `pos` (Type: `Vec3`) - The center of the circle.
     - `distanceX` (Type: `float`) - The radius of the circle along the X-axis.
     - `distanceZ` (Type: `float`) - The radius of the circle along the Z-axis.
     - `current` (Type: `float`) - The current point within the circle.
     - `total` (Type: `float`) - The total number of points in the circle.
     - `gameTime` (Type: `long`) - The current game time.
     - `time` (Type: `float`) - The total time for one position to complete a full rotation cycle.
   - Returns: `Vec3`

5. **blockOutlinePositions**
   - Description: Calculates an array of positions around a block's perimeter for rendering outlines.
   - Parameters:
     - `level` (Type: `Level`) - The Minecraft world level.
     - `pos` (Type: `BlockPos`) - The position of the block.
   - Returns: `ArrayList<Vec3>`

6. **getCenterOf**
   - Description: Calculates the center of a given `Vec3i` position.
   - Parameters:
     - `pos` (Type: `Vec3i`) - The position to calculate the center of.
   - Returns: `Vec3`

7. **axisAlignedPlaneOf**
   - Description: Calculates a vector representing an axis-aligned plane based on a given vector.
   - Parameters:
     - `vec` (Type: `Vec3`) - The input vector.
   - Returns: `Vec3`

8. **rotate**
   - Description: Rotates a vector around a specified axis by a given angle.
   - Parameters:
     - `vec` (Type: `Vec3`) - The input vector.
     - `deg` (Type: `double`) - The angle in degrees to rotate by.
     - `axis` (Type: `Direction.Axis`) - The axis of rotation (X, Y, or Z).
   - Returns: `Vec3`

9. **projectToPlayerView**
   - Description: Projects a 3D world coordinate onto the player's screen.
   - Parameters:
     - `target` (Type: `Vec3`) - The 3D world coordinate to project.
     - `partialTicks` (Type: `float`) - The partial tick value.
   - Returns: `Vec3`

**Usage Examples:**

Include practical examples of how to use each method in your modding project.

**Note:** Replace the descriptions and examples with more detailed information specific to your modding project.

This documentation provides an overview of the `VecHelper` class and its methods. Please add specific details, examples, and explanations relevant to your Minecraft Forge modding project for each method to complete the documentation.