# Done-Deal

A Dynamic Task Scheduler on a weekly basis.

## Getting Started

Simple! Just download the tag release apk file and install it on your android device.

### Prerequisites

No prerequisite needed

### How to use

This app is specifically build for local usage and keeping track. Thus, no need for login and authentication hassle (task syncing can be detailed later)

+ Upon turning on the app, the user will see a Week calendar view. Where the Columns are timestamp and rows are days of week.
+ Layout:
  - By scrolling horizontally, the user can observe all 24 hours of a day
  - Scrolling vertically will give full view of all 7 days of the week
  - Weeks navigation can be done by clicking the left and right arrow near top bar 
+ Task grid features: Each tasks is a cell in the grid that allows the user to either:
  - Hold & Drag to re-order task position
  - Hold the leftmost tag to delete a task
  - Click to see details in a specific task
  - When click on empty cell in the grid, new task can be created
  - Each task cell will comprise of:
    - Level of importance color tag (Red, Yellow, Green)
    - Name of the task
    - Picture attached to the specific task
    - Number of sub-task inside the task itself
+ Task Detail:
  - Checkbox
  - Name
  - Description
  - Image (Can be add by clicking on the image box)
  - Sub-task list (can be add by filling the text box and click the icon next to it)
  - Level of importance radio box
  - Daily switch (If task is performed on a daily basis)
  - Add to google switch ( Upon saving, will bring the user to Google calendar where the user is already authenticated to and add event there)
  

End with a few screenshots

## Built with

* [Kotlin - 1.16.10](https://kotlinlang.org) - The programming language used
* MVC - Design Pattern
* External Libraries:
  + jakewharton - butterknife: 10.1.0
  + jakewharton - butterknife-compiler: 10.0.0
  + Google - material: 1.4.0
  + Google - gson: 2.8.6
  + squareup - picasso: 2.71828
## Authors

* **Duy Nguyen Khuong Cong** - [github](https://github.com/duy-nguyen-khuong-cong)

## Acknowledgments

* Professor: Dave Drohan - For assisting me in this assignment
