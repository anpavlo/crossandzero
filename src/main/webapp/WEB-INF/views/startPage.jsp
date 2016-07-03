<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link type="text/css" href="${pageContext.request.contextPath}/resources/lib/jquery-ui.css" rel="stylesheet" />


<script type="text/JavaScript" src="${pageContext.request.contextPath}/resources/lib/jquery-1.9.1.min.js"></script>
<script type="text/JavaScript" src="${pageContext.request.contextPath}/resources/lib/jquery-ui.js"></script>
<script type="text/JavaScript" src="${pageContext.request.contextPath}/resources/js/gameSocket.js"></script>
<script type="text/JavaScript" src="${pageContext.request.contextPath}/resources/js/uiActions.js"></script>
<script type="text/JavaScript" src="${pageContext.request.contextPath}/resources/js/commandsHandler.js"></script>
<script type="text/JavaScript" src="${pageContext.request.contextPath}/resources/js/game.js"></script>


<style type="text/css">
body {
    font-family: Arial, sans-serif;
    font-size: 11pt;
    background-color: #eeeeea;
    padding: 10px;
}

#console-container {
    float: left;
    background-color: #fff;
    width: 270px;
    margin-right: 25px;
}

#console {
    font-size: 10pt;
    height: 310px;
    overflow-y: scroll;
    padding-left: 5px;
    padding-right: 5px;
}

#members {
    font-size: 10pt;
    height: 310px;
}

#members select{
    width: 270px;
    height: 310px;
    padding-left: 5px;
    padding-right: 5px;

}

#console p {
    padding: 0;
    margin: 0;
}

#drawContainer {
    float: left;
    display: block;
    margin-right: 25px;
}

#labelContainer {
    margin-bottom: 15px;
}

#drawContainer, #console-container {
    box-shadow: 0px 0px 8px 3px #bbb;
    border: 1px solid #CCCCCC;
}

table, th, td {
  border: 1px solid black;
  background-color: #fff;
  border-collapse: collapse;
  table-layout: fixed;
  width:300px;
  height:300px
}

.cells{
  width:100px; 
  height:100px
}

h1 {
   padding-left: 36px;
}

</style>

 
<title>Insert title here</title>
</head>
<body>
<div class="noscript"><h2 style="color: #ff0000">Seems your browser doesn't support Javascript! Websockets rely on Javascript being enabled. Please enable
    Javascript and reload this page!</h2></div>
    
     <div id="labelContainer"><p>
        <input type="text" readonly id="info" />
    </p></div> 
    
  <div id="drawContainer">
    <table>
      <tr>
        <td class='cells'><div id='a1'></div></td>
        <td class='cells'><div id='a2'></div></td>
        <td class='cells'><div id='a3'></div></td>
      </tr>
      <tr>
        <td class='cells'><div id='b1'></div></td>
        <td class='cells'><div id='b2'></div></td>
        <td class='cells'><div id='b3'></div></td>
      </tr>
      <tr>
        <td class='cells'><div id='c1'></div></td>
        <td class='cells'><div id='c2'></div></td>
        <td class='cells'><div id='c3'></div></td>
      </tr>
    </table>
  </div>
  <div id="console-container">
    <div id="console">
      <ol id="selectable">
      </ol>
    </div>
  </div>

  <div id="console-container">
    <div id="members">
      <select id = 'sel' name="FavWebSite" size="5">
      </select>
    </div>
  </div>
  
  <button type="button" id = 'challangeBut'>Challenge!</button>
  <div style="clear: left;"></div>    
  <div id="dialog_window_1" class="dialog_window" title="CHALLENGE">
   </div>
    
    
    
    
    
<!-- <div>
    
    <div id="console-container">
        <div id="console"></div>
    </div>
   
</div>
<br>
<div>


<button type="button">Click Me!</button>

</div> -->
</body>
</html>