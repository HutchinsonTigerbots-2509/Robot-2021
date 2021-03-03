# 2021 Infinite Recharge

### Hutchinson Robotics Team 2509

## Folders
- frc.robot

  Contains the robot's central functions and holds a file with all numerical constants used throughout the code.  For example, the `Robot` class controls all routines depending on the robot state.
    
- frc.robot.commands

  Commands define the operations of the robot incorporating the capabilities defined in the subsystems.  Commands are subclasses of `Command` or `CommandGroup`.  Commands run when scheduled or in response to buttons being pressed or virtual buttons from the `SmartDashboard`.
  
- frc.robot.subsystems

  Subsystems are consolidated into one central class per subsystem, all of which extend the Subsystem abstract class.  Each subsystem used state machines for control.  Each subsystem is also a singleton, meaning that there is only one instance of each.  To modify a subsystem, one would get the instance of the subsystem and change its state.  The `Subsystem` class will work on setting the desired state.
  

## Variable Naming Conventions
| Letter | Convention | Example |
| -----: | :--------: | :------ |
| s | Subsystem | `sDrivetrain` |
| c | Command | `cAutonomous` |
| k | Constants | `kJoystickID` |
| m | Private Instance| `mHighGear` |
| b | Button Instance| `bIntakeForward`|
| p | Argument | `pStick` |


## Programmers
- [Cole Gartner](https://github.com/FerisJumbo)             - FerisJumbo
- [Cecilia Schmitz](https://github.com/ceciliaschmitz1)     - ceciliaschmitz1
- [Teagan Young](https://github.com/TeaganY)                - TeaganY
- [Grace Swaja](https://github.com/BakedBeans2)             - BakedBeans2
- [Quinton MacMullan](https://github.com/quintonmacmullan4) - quintonmacmullan4
- [Noah Sturges](https://github.com/noahsturges18)          - noahsturges18
##### Moral Supporters
- [DJ Scheele](https://github.com/DScheele1)                - DScheele1
- [hehe I Cole Rahne](https://github.com/CRahne)                   - CRahne
##### Worst Mentor
- [Nate Christensen](https://google.com)
