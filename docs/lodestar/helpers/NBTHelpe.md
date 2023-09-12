### NBTHelper Class

**Description:**
The `NBTHelper` class provides utility methods for working with NBT (Named Binary Tag) data in Minecraft Forge modding. It includes methods for saving and loading block positions and filtering tags in compound NBT data.

#### Methods

1. **saveBlockPos**
   - Description: Saves a `BlockPos` to a `CompoundTag`.
   - Parameters:
     - `compoundNBT` (Type: `CompoundTag`) - The target compound NBT to save to.
     - `pos` (Type: `BlockPos`) - The `BlockPos` to be saved.
   - Usage Example:
     ```java
     CompoundTag nbtData = new CompoundTag();
     BlockPos position = new BlockPos(10, 20, 30);
     NBTHelper.saveBlockPos(nbtData, position);
     ```

2. **saveBlockPos** (with extra text)
   - Description: Saves a `BlockPos` to a `CompoundTag` with additional text to differentiate it.
   - Parameters:
     - `compoundNBT` (Type: `CompoundTag`) - The target compound NBT to save to.
     - `pos` (Type: `BlockPos`) - The `BlockPos` to be saved.
     - `extra` (Type: `String`) - Extra text to include in the tag name.
   - Usage Example:
     ```java
     CompoundTag nbtData = new CompoundTag();
     BlockPos position = new BlockPos(10, 20, 30);
     NBTHelper.saveBlockPos(nbtData, position, "Spawn");
     ```

3. **loadBlockPos**
   - Description: Loads a `BlockPos` from a `CompoundTag`.
   - Parameters:
     - `tag` (Type: `CompoundTag`) - The source compound NBT containing the `BlockPos`.
   - Returns: `BlockPos` or `null` if not found.
   - Usage Example:
     ```java
     CompoundTag nbtData = ...; // Load the compound NBT from somewhere
     BlockPos loadedPos = NBTHelper.loadBlockPos(nbtData);
     if (loadedPos != null) {
         // Use the loaded position
     }
     ```

4. **loadBlockPos** (with extra text)
   - Description: Loads a `BlockPos` from a `CompoundTag` with additional text in the tag name.
   - Parameters:
     - `tag` (Type: `CompoundTag`) - The source compound NBT containing the `BlockPos`.
     - `extra` (Type: `String`) - Extra text used to differentiate the tag name.
   - Returns: `BlockPos` or `null` if not found.
   - Usage Example:
     ```java
     CompoundTag nbtData = ...; // Load the compound NBT from somewhere
     BlockPos loadedPos = NBTHelper.loadBlockPos(nbtData, "Spawn");
     if (loadedPos != null) {
         // Use the loaded position with extra information
     }
     ```

5. **filterTags**
   - Description: Filters out NBT tags from a `CompoundTag` based on specified filter keys. Nested compound tags are also filtered.
   - Parameters:
     - `tag` (Type: `CompoundTag`) - The source compound NBT to filter.
     - `filters` (Type: `String...`) - An array of filter keys to determine which tags to keep.
   - Returns: `CompoundTag` containing the filtered data.
   - Usage Example:
     ```java
     CompoundTag originalTag = ...; // Load the original compound NBT
     Set<String> filters = new HashSet<>(Arrays.asList("key1", "key2"));
     CompoundTag filteredTag = NBTHelper.filterTags(originalTag, filters);
     ```

**Usage Examples:**

Include practical examples of how to use each method in your modding project.

**Note:** Replace the descriptions and examples with more detailed information specific to your Minecraft Forge modding project for each method to complete the documentation.

This documentation provides an overview of the `NBTHelper` class and its methods. Please add specific details, examples, and explanations relevant to your Minecraft Forge modding project for each method to complete the documentation.
