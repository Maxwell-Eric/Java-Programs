Creates a Depth Limiting Seach for a vacuum agent.

A vacuum can perform 5 actions, suck, move north, move east, move south and move west. It must consider moves in this order.

The vacuum must clean a 2x2 area.

This program creates a Depth Limiting Search of a search space tree with a depth limit of 10 and returns the first sequence of actions that will clean the entire floor.

The tree is pruned as actions are considered, which keeps the tree size at O(mb) where m is the maximum depth and b is the number of branches.

Once the first goal node(all areas clean) is found, the following information is displayed to standard output:
-The current number of nodes in the tree
-Each node starting with the root and ending with the goal node
-The state of the floor at each node
-The vacuum location at each node
-Which action was taken to get to the node from its parent
-The total number of actions
