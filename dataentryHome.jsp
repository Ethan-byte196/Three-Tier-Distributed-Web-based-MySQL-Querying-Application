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
            height: calc(100vh - 50px);
            /* Adjust height as needed */
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
            margin-top: 50px;
        }

        #query_area {
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

        .data-entry-level {
            color: red;
            /* Set color to red */
        }

        .white-border {
            border: 2px solid white;
            /* Sets a 2px solid white border */
            padding: 20px;
            /* Optional padding to demonstrate the effect */
            /* Additional styles */
            width: 1500px;
            /* Optional width for the box */
            height: 200px;
            /* Optional height for the box */
            margin: 0 auto;
            /* Centers the box horizontally */
        }

        h2 {
            margin-left: 70px;
            /* Adjust this value to move the element further to the right */
        }

        table,
        th,
        td {
            border: 1px solid black;
        }

        #sup_sum {
            resize: none;
        }

        .centered-textarea {
            width: 90%;
            margin: auto;
            display: block;
            text-align: center;
            resize: none;
            width: 200px;
            height: 25px;
        }

        button[type="submit"] {
            background-color: darkgray;
            color: green;
            width: 250px;
            /* Adjust the width as needed */
            height: 50px;
            /* Adjust the height as needed */
        }

        button[type="button"] {
            background-color: darkgray;
            color: red;
            width: 250px;
            /* Adjust the width as needed */
            height: 50px;
            /* Adjust the height as needed */
        }
    </style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

    <script type = "text/javascript">
    function eraseSupplier(){
    $("#cmd1").val(""); // Clearing the value of the textarea with ID "cmd1"
    $("#cmd2").val(""); // Clearing the value of the textarea with ID "cmd2"
    $("#cmd3").val(""); // Clearing the value of the textarea with ID "cmd3"
    $("#cmd4").val(""); // Clearing the value of the textarea with ID "cmd4"
    $("#data").empty(); // Removing any existing data from the table with ID "data"
    }
    </script>

     <script type = "text/javascript">
    function erasePart(){
    $("#a1").val(""); // Clearing the value of the textarea with ID "a1"
    $("#a2").val(""); // Clearing the value of the textarea with ID "a2"
    $("#a3").val(""); // Clearing the value of the textarea with ID "a3"
    $("#a4").val(""); // Clearing the value of the textarea with ID "a4"
    $("#a5").val(""); // Clearing the value of the textarea with ID "a5"
    $("#data").empty(); // Removing any existing data from the table with ID "data"
}
    </script>
     <script type = "text/javascript">
    function eraseJob(){
    $("#b1").val(""); // Clearing the value of the textarea with ID "b1"
    $("#b2").val(""); // Clearing the value of the textarea with ID "b2"
    $("#b3").val(""); // Clearing the value of the textarea with ID "b3"
    $("#b4").val(""); // Clearing the value of the textarea with ID "b4"
    $("#data").empty(); // Removing any existing data from the table with ID "data"
}
    </script>
     <script type = "text/javascript">
    function eraseShipment(){
    $("#c1").val(""); // Clearing the value of the textarea with ID "c1"
    $("#c2").val(""); // Clearing the value of the textarea with ID "c2"
    $("#c3").val(""); // Clearing the value of the textarea with ID "c3"
    $("#c4").val(""); // Clearing the value of the textarea with ID "c4"
    $("#data").empty(); // Removing any existing data from the table with ID "data"
}
    </script>
</head>

<body>
    <div class="container">
        <h1>Welcome to the Spring 2024 Project 4 Enterprise System</h1>
        <H3> Data Entry Application</H3>
        <hr class="separator">
        <p>You are connected to the Project 4 Enterprise System database as a <span
                class="data-entry-level">data-entry-level</span>
            user.<br> Enter the data values in a form below to add a new record to the corresponding database table.</p>

        <h2>Suppliers Record Insert</h2>
        <div class="white-border">
            <form action="/Project-4/suppliersInsert" id="suppliers"  method="post">
                <table style="width: 100%">
                    <tr>
                        <th>snum</th>
                        <th>sname</th>
                        <th>status</th>
                        <th>city</th>
                    </tr>
                    <tr>
                        <td><textarea type="text" id ="cmd1" name="snum_input" class="centered-textarea"></textarea></td>
                        <td><textarea type="text" id ="cmd2" name="sname_input" class="centered-textarea"></textarea></td>
                        <td><textarea type="text" id ="cmd3" name="status_input" class="centered-textarea"></textarea></td>
                        <td><textarea type="text" id ="cmd4" name="scity_input" class="centered-textarea"></textarea></td>
                    </tr>
                </table>
                <br>
                <button type="submit" id="s_execute">Enter Supplier Record Into Database</button> <button type="button"
                    id="s_reset" onclick="javascript:eraseSupplier();">Clear Data and
                    Results</button>
            </form>
        </div>
        <br>

        <h2>Parts Record Insert</h2>
        <div class="white-border">
            <form action="/Project-4/partsInsert" id="parts" method="post">
                <table style="width: 100%">
                    <tr>
                        <th>pnum</th>
                        <th>pname</th>
                        <th>color</th>
                        <th>weight</th>
                        <th>city</th>
                    </tr>
                    <tr>
                        <td><textarea type="text" id ="a1" name="pnum_input" class="centered-textarea"></textarea></td>
                        <td><textarea type="text" id ="a2" name="pname_input" class="centered-textarea"></textarea></td>
                        <td><textarea type="text" id ="a3" name="color_input" class="centered-textarea"></textarea></td>
                        <td><textarea type="text" id ="a4" name="pweight_input" class="centered-textarea"></textarea></td>
                        <td><textarea type="text" id ="a5" name="pcity_input" class="centered-textarea"></textarea></td>
                    </tr>
                </table>
                <br>
                <button type="submit" id="p_execute">Enter Part Record Into Database</button> <button type="button"
                    id="p_reset" onclick="javascript:erasePart();">Clear Data and
                    Results</button>
            </form>
        </div>
        <br>

        <h2>Jobs Record Insert</h2>
        <div class="white-border">
            <form action="/Project-4/jobsInsert" id="jobs"  method="post">
                <table style="width: 100%">
                    <tr>
                        <th>jnum</th>
                        <th>jname</th>
                        <th>numworkers</th>
                        <th>city</th>
                    </tr>
                    <tr>
                        <td><textarea type="text" id ="b1" name="jnum_input" class="centered-textarea"></textarea></td>
                        <td><textarea type="text" id ="b2" name="jname_input" class="centered-textarea"></textarea></td>
                        <td><textarea type="text" id ="b3" name="numworkers_input" class="centered-textarea"></textarea></td>
                        <td><textarea type="text" id ="b4" name="jcity_input" class="centered-textarea"></textarea></td>
                    </tr>
                </table>
                <br>
                <button type="submit" id="j_execute">Enter Job Record Into Database</button> <button type="button"
                    id="j_reset" onclick = "javascript:eraseJob();">Clear Data and
                    Results</button>
            </form>
        </div>
        <br>
        <h2>Shipments Record Insert</h2>
        <div class="white-border">
            <form action="/Project-4/shipmentsInsert" id="shipments"  method="post">
                <table style="width: 100%">
                    <tr>
                        <th>snum</th>
                        <th>pnum</th>
                        <th>jnum</th>
                        <th>quantity</th>
                    </tr>
                    <tr>
                        <td><textarea type="text" id ="c1" name="ship_snum_input" class="centered-textarea"></textarea></td>
                        <td><textarea type="text" id ="c2"name="ship_pnum_input" class="centered-textarea"></textarea></td>
                        <td><textarea type="text" id ="c3" name="ship_jnum_input" class="centered-textarea"></textarea></td>
                        <td><textarea type="text" id ="c4" name="quantity_input" class="centered-textarea"></textarea></td>
                    </tr>
                </table>
                <br>
                <button type="submit" id="ship_execute">Enter Shipment Record Into Database</button> <button
                    type="button" id="ship_reset" onclick = "javascript:eraseShipment();">Clear Data and
                    Results</button>
            </form>
        </div>

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