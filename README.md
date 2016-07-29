# NiceNameGen
Allows to generate random names :) 

It's quite simple to use. But it allows you to set up generation in your own way.

You can set up 'groups' which are just like random bricks and 'masks' that specify how your wall (nickname) should be built from that random bricks. Yeah, this explanation sucks but I can't come up with something better. So just try it out.

### How to use it
Actually it's quite easy. Firstly, please paste this into 'Groups' tab:
```
A: ao o
B: p k
```
These are two groups: A and B. 

Now paste this into 'Masks' tab:
```
BABA
BAB
```
Now switch to 'Result' tab and try to generate some names. You may notice some patterns in generated names.

### How it works

Actually it's quite easy.

The first tab is about groups. Every group is located on separate line. For example, let's say we have something like this:
```
1: a o e i
2: r t p br nv m c
```
So here 1 and 2 are the names of groups. Group 1 contains symbols 'a', 'o', 'e' and 'i'. Group 2 contains 'r' 't' 'p' 'br' 'nv' 'm' 'c'.

The second tab is about masks. Every mask is located on separate line. Mask is just a set of symbols, nothing more. 

Third tab is obvious. And the 'Save' button saves the masks and groups from first two tabs.

Now the most important part: The nickname generation.
So Algorhythm takes random mask. For every symbol of this mask it checks if this symbol is the name of some group. If it is then this symbol is replaced with random element of the group. In other case it just leaves it untouched. It's that easy.
