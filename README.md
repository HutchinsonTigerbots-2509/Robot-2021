# 2021 Infinite Recharge

### Hutchinson Robotics Team 2509
#### Click [here](https://hutchinsontigerbots-2509.github.io/Robot-2021/build/docs/javadoc/frc/robot/package-summary.html) to see Javadoc API for this code.

## Package Functions
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
| p | Argument | `pStick` |




## Important Information for Contributers
- Master branch is to only be updated from psuedoMaster and should never, ever be directly commited to or merged from another branch.  When you push to Master you will need a review
  before you can merge branches (Assign FerisJumbo or ceciliaschmitz1 to review).
- psuedoMaster will contain documentation and never merge to psuedoMaster until you have corretly formatted code with javadoc style comments. (ex: @author, @version, @category ect.)
  - Javadoc comment style
    - Class declaration

      ```
      /**
       * [Class Name].
       *
       * [Class Description].
       *
       * @version [Major.Minor.Patch] [Month] [Day], [Year]
       * @author [author]
       */
      public testClass {...}
      ```

    - Constructor

      ```
      /** [Class Name] constructor. */
      public testClass() {...}
      ```

      ```
      /**
       * [Class Name] constructor.
       *
       * [Description if necessary].
       */
      public testClass() {...}
      ```

    - Method

      ```
      /**
       * [Method Name].
       *
       * [Description if necessary].
       *
       * @param arg1 [param description].
       * @return [return description].
       */
      public int testMethod(int arg1) {...}
      ```
      
- Before psuedoMaster is pushed to the Master branch run `gradle javadoc` in the terminal to generate documentation (Make sure first that every class has correct documentation and version control).
- Always create a branch from psuedoMaster.
- Comment logic statements if they are unclear as what they do.
- Make sure to follow coding practices to keep the code clean and clear.

## Programmers
- [Cole Gartner](https://github.com/FerisJumbo)             - FerisJumbo
- [Cecilia Schmitz](https://github.com/ceciliaschmitz1)     - ceciliaschmitz1
- [Teagan Young](https://github.com/TeaganY)                - TeaganY
- [Grace Swaja](https://github.com/BakedBeans2)             - BakedBeans2
- [Quinton MacMullan](https://github.com/quintonmacmullan4) - quintonmacmullan4
- [Noah Sturges](https://github.com/noahsturges18)          - noahsturges18
##### Moral Supporters
- [DJ Scheele](https://github.com/DScheele1)                - DScheele1
- [Cole Rahne](https://github.com/CRahne)                   - CRahne
