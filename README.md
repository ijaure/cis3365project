# cis3365project
When you clone the project, you have to add this run config: --module-path javafx-sdk-11.0.2\lib --add-modules=javafx.controls,javafx.fxml
You also need a path to the javafx-sdk-11.0.2 folder in Project Structure -> Libraries

Instructions for Local Database: 
You'll need SQL Server Express installed on your laptop, since this uses SQL Express.
The SQLEXPRESS service should also be running on your computer.

1. Add a path in Project Structure -> Libraries to the jdbcjar folder

2. Copy the sqljdbc_auth.dll from the jdbcjar folder in the project and paste it to your JDK bin in C:\Program Files\Java\jdk-11.0.2\bin
   This is for Windows authentication to work so you don't have to use a username & password. 
