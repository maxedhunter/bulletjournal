[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/x6ckGcN8)
# 3500 PA05 Project Repo

[PA Write Up](https://markefontenot.notion.site/PA-05-8263d28a81a7473d8372c6579abd6481)

Final Design:
![Screenshot 2023-06-22 at 5.34.51 AM.png](Screenshot%202023-06-22%20at%205.34.51%20AM.png)

### Pitch: 
Introducing BanzaiBill, your very own bullet journal! The application supports creating tasks and events,
edits, deletions, and helps you keep track of completion progress. You can also save your week as a .bujo file
and access it later. There is a menu bar but you can also use shortcuts listed below. 
Enjoy your journaling :D

### Shortcuts:
- Ctrl + S : Save to file
- Ctrl + O : Open an existing file
- Ctrl + N : Open a new file
- Ctrl + T : Create a new task
- Ctrl + E : Create a new event
- Ctrl + R : Remove a task
- Ctrl + C : Set a task to complete
- Ctrl + D : Remove event

### Principles:
#### S 
    - One example of the single-responsibility principle is
    in the ControllerImpl.sumUp() method. The method has a specific job,
    to set up JsonNode representation of a week. It does so by formatting data,
    and then calling a helper method, createWeek, which does the serialization.
    In this case, we separate two responsibilities into two methods, maintaining
    the single-responsibility principle.
#### O 
    - Having the interfaces for Controller and View, makes our code open for extension.
    At the same time, our fields are set to private, thereby making our code
    closed for modification.
#### L 
    - We originally had a bullet class, but changed it to a interface as to not break
    Liskov Substitution Principle, since subclasses should (ideally) be interchangeable.
#### I 
    - We extensively used interfaces, like for controller and view, as well as for bullets.
    Having smaller interfaces allowed up more flexibility, and helps us only implement
    the methods we want.
#### D 
    - We used dependency injections by giving the views a controller. This helps to decouple
    our code as we can create new controllers in the future. 

### Extending to add new features:

    So far, when we decided to add new features, we did it in a very modular fashion. Since
    our program has a lot of popups, we create individual views, and controllers for each popup.
    In the future, we would probably first design the data necessary, like for example, notes and
    quotes. We could then create a view that displays a popup prompting the user to type in
    new notes/quotes. Finally, the controller could handle and properly display the information!

    * We would also need to update WeekJson to include a list of notes and quotes.



