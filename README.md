# HotPlate

A simple restaurant waitlist management software created by AndrewCollin32

![Untitled_Artwork 17](https://user-images.githubusercontent.com/109395254/184273293-91bcf997-d268-4e04-9fa6-7501ce92a893.jpg)


# Hot Plate (Waitlist Manager)
<p align="center">
  HotPlate is a powerful software tool that is made for managing restaurants <br>
  This software version is made for managing waitlists. <br> <br>
  HotPlate was created by Andrew Collin and it has plans to expand beyond a waitlist management software
</p>

<h1>Getting Started</h1>--

<ul>
<li><a href="#Requirements">Requirements</a></li>
  <li><a href="#How_to_use">How To Use</a></li>
  <ul>
    <li><a href="#Main_Screen">Main Screen (Customer's Portal) </a></li>
    <li><a href="#Reservation_Page">Reservation Page</a></li>
    <li><a href="#Sign_In_Page"> Sign In Page </a></li>
    <li><a href="#Admin_Page"> Admin Page </a></li>
    <li><a href="#Settings"> Settings </a></li>
    <li><a href="#Register"> Register </a></li>
    <li><a href="#SQL_Setup"> SQL Setup </a></li>
    <ul>
    <li><a href = "#Enable_SQL"> Enable SQL </a></li>
    <li><a href = "#Disable_SQL"> Disable SQL </a></li>
    </ul>
    <li><a href="#Crash_Report"> Crash Report </a></li>
  </ul>
  <li><a href="#Troubleshoot"> Troubleshoot </a></li>
  <ul>
    <li><a href="#Can_Not_Find_File"> Can not find file </a></li>
    <li><a href="#Loading_User_Settings"> Loading User's Settings </a></li>
    <li><a href="#Initializing_Load"> Initializing Load </a></li>
  </ul>
</ul>

# Requirements
<ul>
  <li>Java SDK 15 and above</li>
  <li>Java FX</li>
  <li>Twilio (Optional, see below)</li>
  <li>SQL Database (Optional)</li>
</ul>

# How_to_use

![Untitled_Artwork 10](https://user-images.githubusercontent.com/109395254/186785857-1f6e66ac-9e95-4f6a-8071-5dfb9931d9da.png)


# Main_Screen 
(Customer Portal)
  When you first open your program, you will be greeted with this screen. This is the customer's screen, this is used for customers to reserve their seats. <br>
  The goal of this screen is to have customer's eyes drawn to the screen when they walk in so they will independently reserve their seats without any assistance.
  <ul>
  <li>0: HotPlate Drop-down menu Button</li>
  <li>1: Sign In Button - Takes you to a login page where you will be granted admin access. </li>
  <li>2: About Me Button - Takes you to a page that has my credits as well as my social media.</li>
  <li>3: Call Waiter Button [Disabled] - Send a text to your waiter's phone number. </li>
  <li>4: Waitlist Label - Shows the current waitlist size. </li>
  <li>5: Reserve Your Seat Button - Takes you to an input page where you can fill in your information to reserve your seat</li>
</ul>

![Untitled_Artwork 18](https://user-images.githubusercontent.com/109395254/184273779-28d0263f-5ae3-4352-b1c0-85cfac26fa87.jpg)
<hr>

# Reservation_Page

  This page is for customers to input their information to reserve their seat.
  <ul>
  <li>0: Drop-down menu for party size (Min = 1, Max = 12)</li>
  <li>1: Name text field - Where customers enter their prefered name. Will be used in the text message </li>
  <li>2: Phone number text field - Customers have to enter their phone number in order to receive the SMS message. Allowed format: <br>
    <b>(123)-456-7890, 123-456-7890, 1234567890</b></li>
  <li>3: Submit Button - Saves all the entries including the time it was submitted </li>
  <li>4: Cancel Button - Deletes all the entries and takes you back to the customer's portal </li>
</ul>

![Reservation Page](https://user-images.githubusercontent.com/109395254/184277390-15ad5f0d-28b5-453d-822a-618367a94812.png)

<hr>

# Sign_In_Page

  The sign in page is used by employees to grant access to the admin page. 
  <ul>
  <li>0: Pin number textfield - Used for employees to enter their pin number. (The default pin number is 1111, but you can change it in the settings)</li>
  <li>1: Close Button - Takes you back to the customer portal </li>
  <li>2: Submit Button - Validates the pin number and takes you to the admin page</li>
</ul>

![signin](https://user-images.githubusercontent.com/109395254/186794232-ab59f5e5-7b8a-40c1-b79c-f88765e32f2b.PNG)

<hr>

# Admin_Page

  The admin page has control over all the entries submitted. In this page, you can add, edit and delete entries. You can also warn and call the selected customer. You will also have access to settings, saving the data and loading the data.
  <ul>
  <li>Hotplate Drop-down menu
    <ul>
      <li> Customer Portal - Takes you back to the main page </li>
      <li> Options Drop-down menu</li>
        <ul>
          <li>Custom Message Button - Takes you to a page where you can customize your SMS messages.</li>
          <li>Settings - Takes you to the settings page where you can change your name, change the restaurant's name, change the auto load feature, change the pin and the time format.</li>
        </ul>
      <li>About Me Button - Takes you to a page that has my credits as well as my social media</li>
      <li>Exit - Closes the entire program (without saving)</li>
    </ul>
  <li>File Drop-down menu</li>
  <ul>
    <li>Save - Saves all of your customer's data. (Your settings will be automatically saved when you exit the settings page and customize message page)</li>
    <li>Load - Loads all of your customer's data</li>
  </ul>
  <li>Edit Drop-down menu</li>
  <ul>
    <li>Add - Takes you to a page where you can add an additional entry</li>
    <li>Edit - Takes you to a page where you can edit your selected entry on the table</li>
    <li>Cut - Deletes a selected entry</li>
    <li>Warn - Sends a SMS message to your selected entry warning your customer that their seating is almost ready</li>
    <li>Call - Sends a SMS message to your selected entry warning your customer that their seating is ready</li>
    <li>Clear Table - Deletes every entry on your table</li>
  </ul>
  <li>Help - Takes you to my GitHub page</li>
  <br>
  <br>
  <li>TableView - A table that displays all of the entries. It includes the name, party size, the time it was submitted and their phone number</li>
</ul>

![Image 8-11-22 at 8 33 PM](https://user-images.githubusercontent.com/109395254/184280691-124aff72-8b2b-4786-a823-abac8b668153.JPG)

<hr>

# Settings 

  The settings can only be accessed through the admin page. In this page, you can edit your name, your restaurant's name, auto load feature, pin and the time format. Changes made in this page will be applied to admin and customer portals. 
  <ul>
  <li>Name - Textfiled for you to enter your prefered name.</li>
  <li>Restaurant - Textfield to enter your restaurant's name. It can be used as an string injection for your messages. </li>
  <li>Load - Check box where if selected, your last saved customer's data will automatically load on to the table.</li>
  <li>Light Settings - Disabled and will be released in future developments</li>
  <li>Pin - Textfield for you to enter a new pin. The pin is used to grant access to the Admin's page.</li>
  <li>Time Format - You can choose between the 12hr and the 24hr time format. Once saved, the new time format will be applied to new and existing customer's data including the clock on the upper right corner of the Admin page.</li>
</ul>

![Settings](https://user-images.githubusercontent.com/109395254/186794239-89c284dd-d442-4d82-a5c5-a38be37614c3.PNG)

<hr>

# Register

The register page can only be opened if you have SQL enabled. If SQL is disabled, then it will give you an alert box that has your sign in information. In this page, you can create a new user by adding their username, password, user's name and restaurant name. Once submitted, it will create a new row in your usersettings table. 

  <ul>
  <li>Username - User is prompted to enter their prefered username for login. This username is unique and is registered as a primary key in SQL.</li>
  <li>Password - Users need to enter a password that is at least 4 characters long. </li>
  <li>Name - This is the name you'll be greeted with when you are in the Admin portal. It will also be used when you send call and warn messages.</li>
  <li>Restaurant Name - This field will only be used when sending out messages and in the customer's portal</li>
  <li>Cancel - Button that takes you back to the sign in page.</li>
  <li>Submit - Checks that every textfield is filled and password meets the requirements. Afterwards it will insert the user to the user settings table in your database.</li>
</ul>

![register](https://user-images.githubusercontent.com/109395254/186794266-16533ad5-e75e-4355-b72a-5b5f7b006192.PNG)

<hr>

# SQL_Setup

This is how you can enable SQL for this program. With SQL, you'll get a lot of benefits like: Registering more users, better organize data and ease of access to data. Inorder to begin the SQL setup you'll need three important things:
  <ul>
  <li>SQL Username</li>
  <li>SQL Password</li></li>
  <li>SQL Database URL</li>
</ul>

Go to SQLSetup.java in your src/main/java/com/Hotplate/Hotplate/ directory. 
Fill out the missing values (See below)
To Enable SQL, change the useSQL to true.
You should get something like this: <br>
<code> public static boolean useSQL = true; </code> <br>
<code> public static String databaseURL = "jdbc:mysql://localhost:{PortNumber}/{databaseName}"; </code> <br>
<code> public static String sqlUsername = "{Your Username}"; </code> <br>
<code> public static String sqlPassword = "{Your password}"; </code> <br>
</code>

![setup](https://user-images.githubusercontent.com/109395254/186794278-d65e42d3-c471-42f0-8eaf-9819051b194c.PNG)

<hr>

# Enable_SQL

After you've put your credentials, you'll need to create the tables to make this possible. Luckily, you don't have to create the tables. I've written the program to automatically create tables for you. It will also give you a sample list of customers and users for you to interact with. 

After filling out the credentials, just run <code> main </code> in SQLSetup.java. Everything would work perfectly if there were no exception errors.
You can even check that you've created the tables by using mySQL. (Below)

Afterwards, you are all set to go. Just go to HotplateApp and run <code> main </code>. The program should launch without any issues. 

https://user-images.githubusercontent.com/109395254/186794173-80b3e99e-4010-4375-9d40-d342ab1ad255.mp4

<hr>

# Disable_SQL

To disable SQL, simply go to SetupSQL and change <code>useSQL</code> to false.

![false](https://user-images.githubusercontent.com/109395254/186799054-e3722440-3748-462b-ac7e-bc6dc7cf33b3.PNG)

You can even delete all the data and tables by commenting out the 3 methods and uncommenting the deleteEverything method. 

![delete](https://user-images.githubusercontent.com/109395254/186799252-2903ab5d-cc40-4410-96f8-f58aabcc50a1.PNG)

<hr>

# Crash_Report
  
  See <a href = "#Troubleshoot"> Troubleshoot </a> for more details about your error.
  The crash report windows opens up when there is a severe error that has taken place. It shows you the entire log file since you first open your program. It also has   a red label at the bottom telling you what the exact error is. You can send the report so I can look at it or you can exit the program and restart it. At the moment   I will not receive any reports because this program is still in beta, but you can voice your errors on this Github's repository.

![crash report](https://user-images.githubusercontent.com/109395254/185509625-93405e90-5ffd-4491-9387-b52c791e566c.PNG)

<hr>

# Troubleshoot

If you encountered a crash report, make sure to copy the red label at the bottom of the log display. The red text is the main culprit to the error. 
<br>

![image](https://user-images.githubusercontent.com/109395254/185512235-c474ced9-12ce-42c8-85b8-bce9330d3df8.png)

# Can_Not_Find_File
This error only occurs when the user is first loading up the program. Before the software starts, it searches through the required directories to find the required files to run this program. When the program can't find a file, it is either:
<ul>
  <li>1. The file was deleted</li>
  <li>2. The file was moved</li>
  <li>3. The file's name or format was changed</li>
</ul>
<code>Can not find file: FileName.class </code>  This error means the program couldn't find the required java classes. Go to src/main/java/com/Hotplate/Hotplate and see if the class file is in there. If not, just re-download it from this repository. 
<br>
<code>Can not find file: FileName.fxml </code>  This error means the program couldn't find the required fxml document. Go to src/main/resources and see if the fxml file is in there. If not, just re-download it from this repository. 

<hr>

# Loading_User_Settings

The error can appear in any of these labels. 

![errorSaveSettings](https://user-images.githubusercontent.com/109395254/185515923-21660543-f739-4f07-8a96-40976989ded0.PNG)
<br>
![erroSaveSettings](https://user-images.githubusercontent.com/109395254/185515926-8d1a0b71-c625-41f2-b881-6c3391b44745.PNG)


When the program is starting it will load the previous user's settings. The file it uses is SaveSettings.ser and it could be found in the main files.
The problem occurs if the SaveSettings.ser file is corrupted. This can be caused by running outdated program or running a program that failed to update. In order to resolve this problem, you'll have to delete SaveSettings.ser. Keep in mind that by deleting this file, you'll lose all of your user settings. Customer's data will not be affected. 

Another solution is to go to <code>HotPlate.java</code> and change <code>public boolean bypassSaveSettingsDeBug = false;</code> to true <code>public boolean bypassSaveSettingsDeBug = true;</code>. After, you exit the program, just change it back to false. It will resolve the problem itself. 

<hr>

# Initializing_Load

![initizliaing load](https://user-images.githubusercontent.com/109395254/185516645-d0e8a6ba-893f-4e16-8e73-8fe66f820ae8.PNG)

When the program is starting it will load the previous customer's save. The file it uses is DefaultSave.ser and it could be found in the main files.
The problem occurs if the DefaultSave.ser file is corrupted. This can be caused by running outdated program or running a program that failed to update. In order to resolve this problem, you'll have to delete DefaultSave.ser. Keep in mind that by deleting this file, you'll lose all of your customer's data. User's settings will not be affected. 

Another solution is to go to <code>HotPlate.java</code> and change <code>public boolean bypassSaveCustomersDebug = false;</code> to true <code>public boolean bypassSaveCustomersDebug = true;</code>. After, you exit the program, just change it back to false. It will resolve the problem itself. 


# Future_Plans
- Add JUnit Test
- Use StringBuilder to fuse together strings for log management
- Create a Log Management Class for universal log management
- Convert AlertBox and YesNoBox to a Singleton
