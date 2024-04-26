<!--  
Name: Ethan Fuller  
Course: CNT 4714 – Spring 2024 – Project Four 
Assignment title:  A Three-Tier Distributed Web-Based Application 
Date:  April 23, 2024 
-->
<!DOCTYPE html>


<%
   String sqlStatement = (String) session.getAttribute("sqlStatement");
   if (sqlStatement == null) sqlStatement = "select * from suppliers";

   String message = (String) session.getAttribute("message");
   if (message ==null) message = " ";
%>

<html lang="en">

<head>
    <title>CNT 4714 Spring 2024 Project 4</title>
    <meta charset="utf-8" />
    <style type="text/css">
        body {
            background-color: black;
            font-family: Calibri, Arial, sans-serif;
            font-size: x-large;
            color: white;
            /* Set text color to white */
            margin: 0;
            /* Remove default margin */
            padding: 0;
            /* Remove default padding */
        }

        .container {
            height: 100vh;
            /* Set container height to viewport height */
            overflow-y: auto;
            /* Enable vertical scrolling */
        }

        h1 {
            text-align: center;
            color: yellow;
            /* Center align heading */
        }

        h3 {
            text-align: center;
            color: green;
        }

        .separator {
            border: 1px solid white;
            /* Set border style */
            margin: 20px 0;
            /* Adjust margin as needed */
        }

        p {
            text-align: center;
        }

        .root-level {
            color: red;
            /* Set color to red */
        }

        form {
            text-align: center;
        }

        #cmd {
            resize: none;
        }

        /* Style for button container */
        .button-container {
            text-align: center;
            margin-top: 10px;
            /* Adjust spacing between textarea and buttons */
        }

        /* Style for buttons */
        button {
            margin: 0 5px;
            /* Add space between buttons */
        }

        #execute {
            background-color: darkgray;
            color: green;
            width: 150px;
            /* Adjust the width as needed */
            height: 25px;
            /* Adjust the height as needed */
        }

        #reset {
            background-color: darkgray;
            color: red;
            width: 150px;
            /* Adjust the width as needed */
            height: 25px;
            /* Adjust the height as needed */
        }

        #clear {
            background-color: darkgray;
            color: yellow;
            width: 150px;
            /* Adjust the width as needed */
            height: 25px;
            /* Adjust the height as needed */
        }
    </style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script type = "text/javascript">
    function eraseText(){
        $("#cmd").html("");
    }
    </script>
    <script type = "text/javascript">
        function eraseData() {
            $("#data").remove();
        }

    </script>
</head>

<body>
    <div class="container">
        <h1>CNT 4714 Spring 2024 Project 4</h1>
        <H3>A Servlet/JSP-based Multi-tiered Enterprise Application Using A Tomcat <br>Container</H3>
        <hr class="separator">
        <p>You are connected to the Project 4 Enterprise System database as a <span
                class="root-level">root-level</span>
            user. <br> Please enter any SQL
            query or update command in the box below.</p>

        <form action="/Project-4/root" method="post">
            <textarea name="sqlStatement" id="cmd" cols="70" rows="15"><%=sqlStatement%></textarea>
            <!-- Button container -->
            <div class="button-container">
                <input id="execute" value="Execute Command" type="submit"/>
                <input id="reset"  value="Reset Form" type="reset" onclick ="javascript:eraseText();"/>
                <input id="clear" value ="Clear Results" type ="button" onclick ="javascript:eraseData();"/>
            </div>
        </form>
        <p>All execution results will appear below this line.</p>
        <!-- Add your content here -->
        <hr class="separator">
      
        <center>
        <p>
        <b> Execution Results: </b><br>
        <table id= "data">
        <%=message%>
        </table>
        </p>
        </center>


    </div>

</body>

</html>