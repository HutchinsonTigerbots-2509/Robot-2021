# Robot-2021

## Hutchinson Robotics Team 2509
#### Click [here](https://hutchinsontigerbots-2509.github.io/Robot-2021/build/docs/javadoc/frc/robot/package-summary.html) to see Javadoc API for this code.

- ### Code Highlights
  - #### Subsystems
  
    subsystems

- ### Important Information for Attributaries
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
