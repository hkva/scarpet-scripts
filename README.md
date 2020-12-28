# Useful Minecraft scarpet scripts

To use these scripts, install Carpet Mod and place all the `.sc` files in `.minecraft/saves/<your world>/scripts/*`

Download Carpet Mod here: https://github.com/gnembon/fabric-carpet

To load a script, run `/script load <script name>`

To call a function from a script, run `/script in <script name> invoke <function name>`

# Contents

### fishing_debug.sc
Functions:
* `debug_open_water` - Used to test whether a fishing bobber is in open water a not.

### stripmine.sc
Functions:
* `mine <y-level> <strip-num> <strip-len> <strip-gap>` - Simulate strip mining at `y-level` with `strip-num` strips of `strip-len` length every `strip-gap` blocks.
