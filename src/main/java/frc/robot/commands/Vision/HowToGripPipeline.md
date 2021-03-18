# USB Vision Quick Guide

### How to reference camera Stream

IDK

### Plugging in a USB Camera

Either put it in the limelight or Rio

### How to make a GRIP Pipeline

- Take pictures of balls in positions with the camera on the robot (plug camera into laptop)
- [Download Grip](https://wpiroboticsprojects.github.io/GRIP/#/)
- Open Grip and go to the leftmost column titled 'Source'
- Add images from camera taken earlier
- Click eyeball to turn on viewing
- Use Flip to flip image if upside down
- 'HSL Threshold' -> 'Dilate' -> 'Erode' -> 'Blur' -> 'Find Contours' -> 'Filter Contours'
- Adjust values as necessary to find objects

P.S. FindBlobs is garbage and the WPI docs are near useless.

### How to use a GRIP Pipeline

- With pipeline open on Grip, Ctrl+G will generate code. Select Java and file location
- In the program, repeatedly call Pipeline.process() to run the pipeline
- Output of values can be accessed through Pipeline.filterContoursOutput()