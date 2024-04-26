<!--  
Name: Ethan Fuller  
Course: CNT 4714 – Spring 2024 – Project Four 
Assignment title:  A Three-Tier Distributed Web-Based Application 
Date:  April 23, 2024 
-->

<!DOCTYPE html>
<html lang="en">

<%
   String message = (String) session.getAttribute("message");
   if (message ==null) message = " ";
%>
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

            form {
                text-align: center;
            }

            #query_area {
                resize: none;
            }

            .button-container {
                text-align: center;
                margin-top: 10px;
                /* Adjust spacing between textarea and buttons */
                width: 100%;
                /* Adjust the width as needed */
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

            .accountant-level {
                color: red;
                /* Set color to red */
            }

            #list {
                margin-left: 250px;
                margin-top: -5px;
                /* Adjust the margin-top as needed */
            }

            .light-gray-border {
                border: 2px solid lightgray;
                /* Sets a 2px solid white border */
                padding: 20px;
                /* Optional padding to demonstrate the effect */
                /* Additional styles */
                width: 1500px;
                /* Optional width for the box */
                height: 300px;
                /* Optional height for the box */
                margin: 0 auto;
                /* Centers the box horizontally */
                background-color: lightgray;
                /* Add background color */
            }

            /* Style for blue text */
            .blue-text {
                color: blue;
            }

            /* Style for black text inside parenthesis */
            .black-in-parentheses {
                color: black;
            }

            /* Style for bigger text in list items */
            #list li {
                font-size: larger;
            }

            button[type="submit"] {
                background-color: darkgrey;
                color: green;
                padding: 10px 20px;
                font-size: 18px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
            }

            #clear {
                background-color: darkgray;
                color: red;
                padding: 10px 20px;
                font-size: 18px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
            }
            #list {
            margin-left: 250px;
            margin-top: -5px;
            padding-left: 0; /* Remove default padding */
        }
        </style>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
        <script type = "text/javascript">
        function eraseData() {
        $("#data").empty(); // Remove all content inside the table
    }

    </script>
    </head>

    <body>
    <div class="container">
        <h1>Welcome to the Spring 2024 Project 4 Enterprise System</h1>
        <H3>A Servlet/JSP-based Multi-tiered Enterprise Application Using A Tomcat <br>Container</H3>
        <hr class="separator">
        <p>You are connected to the Project 4 Enterprise System database as a <span class="accountant-level">accountant-level</span> user. <br> Please select the operation you would like to perform from the list below.</p>
        <div class="light-gray-border">
            <form action="/Project-4/accountant" method = "post">
                <ul id="list" style="margin-left: 0;">
                <li style="margin-left: 0;"><input type="radio" name="operation" id="max_status" value="max_status"> <label for="max_status" class="blue-text">Get The Maximum Status Value Of All Suppliers</label> <span class="black-in-parentheses">(Returns a maximum value)</span></li>
                <br>
                <li style="margin-left:0;"><input type="radio" name="operation" id="total_weight" value="total_weight"> <label for="total_weight" class="blue-text">Get The Total Weight Of All Parts</label> <span class="black-in-parentheses">(Returns a sum)</span></li>
                <br>
                <li style="margin-left: 0;"><input type="radio" name="operation" id="num_shipments" value="total_shipments"> <label for="num_shipments" class="blue-text">Get The Total Number of Shipments</label> <span class="black-in-parentheses">(Returns the current number of shipments in total)</span></li>
                <br>
                <li style="margin-left: 0;"><input type="radio" name="operation" id="job_mostworkers" value="job_with_most_workers"> <label for="job_mostworkers" class="blue-text">Get The Name And Number Of Workers Of The Job With The Most Workers</label> <span class="black-in-parentheses">(Returns two values)</span></li>
                <br>
                <li style="margin-left: 0;"><input type="radio" name="operation" id="status_nameOfSuppliers" value="list_suppliers"> <label for="status_nameOfSuppliers" class="blue-text">List The Name And Status Of Every Supplier</label> <span class="black-in-parentheses">(Returns a list of supplier names with status)</span></li>
                <br>
            </ul>
                <br>
                <button type="submit">Execute Selected Command</button>
                <button id="clear" type="button" onclick="eraseData();">Clear Results</button>
            </form>
        </div>
        <br>
        <br>
        <br>
        <br>
        <p>All execution results will appear below this line.</p>
        <hr class="separator">
        <center>
            <p><b>Execution Results:</b><br>
                <table id="data">
                    <%=message%>
                </table>
            </p>
        </center>
    </div>
</body>

</html>