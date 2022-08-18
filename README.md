# HotPlate

A simple restaurant waitlist management software

![Untitled_Artwork 17](https://user-images.githubusercontent.com/109395254/184273293-91bcf997-d268-4e04-9fa6-7501ce92a893.jpg)


# Hot Plate (Waitlist Manager)
<p align="center">
  HotPlate is a powerful software tool that is made for managing restaurants <br>
  This software version is made for managing waitlists. <br> <br>
  HotPlate was created by Andrew Collin and it has plans to expand beyond a waitlist management software
</p>

<h1>Getting Started</h1>

<ul>
<li><a href="#Requirements">Requirements</a></li>
  <li><a href="#How_to_use">How To Use</a></li>
  <ul>
    <li><a href="#Main_Screen">Main Screen (Customer's Portal) </a></li>
    <li><a href="#Reservation_Page">Reservation Page</a></li>
    <li><a href="#Sign_In_Page"> Sign In Page </a></li>
    <li><a href="#Admin_Page"> Admin Page </a></li>
    <li><a href="#Settings"> Settings </a></li>
    <li><a href="#Crash_Report"> Crash Report </a></li>
  </ul>
  <li><a href="#Troubleshoot"> Troubleshoot </a></li>
  <ul>
    <li><a href="#Can_Not_Find_File"> Can not find file </a></li>
  </ul>
</ul>

# Requirements
<ul>
  <li>Java SDK 15 and above</li>
  <li>Java FX</li>
  <li>Twilio (Optional, see below)</li>
</ul>

# How_to_use
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

<img width="596" alt="Screen_Shot_2022-08-11_at_8 18 53_PM 2" src="https://user-images.githubusercontent.com/109395254/184279071-b42b330d-92d0-470a-bb80-e8b3cc57b723.png">

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

![image](https://user-images.githubusercontent.com/109395254/184475648-2520f96b-828d-4a52-84c3-11f55b399fb0.png)

<hr>

# Crash_Report
  
  See <a href = "#Troubleshoot"> Troubleshoot </a> for more details about your error.
  The crash report windows opens up when there is a severe error that has taken place. It shows you the entire log file since you first open your program. It also has   a red label at the bottom telling you what the exact error is. You can send the report so I can look at it or you can exit the program and restart it. At the moment   I will not receive any reports because this program is still in beta, but you can voice your errors on this Github's repository.

![crash report](https://user-images.githubusercontent.com/109395254/185509625-93405e90-5ffd-4491-9387-b52c791e566c.PNG)

<hr>

# Troubleshoot

If you encountered a crash report, make sure to copy the red label at the bottom of the log display. The red text is the main culprit to the error. 
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


