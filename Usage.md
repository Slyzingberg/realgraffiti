# Introduction #

To get best results, follow the guidelines below.

# Details #

**Selecting and Snapping the Wall**

The "wall" should be a flat area with some easy to detect features. I noticed it works best on paintings etc. It will not work on a bare wall or on something with a repeating pattern.

It is best to snap the frame when you stand directly in front of the wall.

**Graffiti Viewing**

When there is no match, you will see the camera feed in black and white. This is sort of "no match" indication. It will switch to color when there is a match. The first match takes 2-3 seconds.

Because we use very simple descriptors (something call BRIEF), it will not tolerate much scale, rotation or perspective changes, so you can't move a lot from where you took the reference frame.

After there is a match, optical flow tracking is used to update the location every frame. If you move in or out quickly or if you rotate the phone, the graffiti will not match the background.

An updated match and warp occurs once every 3-4 seconds.

After several bad matches, the video will turn BW again.

Good Luck!