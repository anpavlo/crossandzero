/**
 * 
 */

// invoke when we receive challenge; show modal dialog
function challenge(playerWhichChallenge){
	$('#dialog_window_1').dialog({
		   width: 'auto',
		   height: 'auto',
		   modal: true,
		   buttons: [
		      {
		         text: 'Accept',
		         click: function() {
		            //alert('Accept');
		        	 acceptChallenge(playerWhichChallenge);
		            $("#dialog_window_1").dialog("close");
		         }
		      },
		      {
			         text: 'Reject',
			         click: function() {
			            //alert('Reject');
			        	 rejectChallenge(playerWhichChallenge);
			            $("#dialog_window_1").dialog("close");
			         }
			  }
		   ],
		   open:function(){
			   $('#dialog_window_1').html(playerWhichChallenge.nik+' challenge you');
		   }
		});
}

function playerIsBusy(busyPlayer){
	$('#dialog_window_1').dialog({
		   width: 'auto',
		   height: 'auto',
		   title: "INFO",
		   modal: true,
		   buttons: [
		      {
		         text: 'Ok',
		         click: function() {
		            		        	
		            $("#dialog_window_1").dialog("close");
		         }
		      }
		   ],
		   open:function(){
			   $('#dialog_window_1').html(busyPlayer.nik+' is busy!');
		   }
		});
}

function acceptChallenge(playerWhichChallenge){
	
	var player = playerWhichChallenge;
	
	var msg = new Object();
	msg.value = null;
	msg.cmd = "ACCEPT_CHALLENGE";
	
	msg.player = player;
	
	Game.sendMessage(msg);
}

function rejectChallenge(playerWhichChallenge){
	
	var player = playerWhichChallenge;
	
	var msg = new Object();
	msg.value = null;
	msg.cmd = "REJECT_CHALLENGE";
	
	msg.player = player;
	
	Game.sendMessage(msg);
}

function rejectChallMessageDialog(playerWhichReject){

	$('#dialog_window_1').dialog({
		   width: 'auto',
		   height: 'auto',
		   modal: false,
		   title: "INFO",
		   buttons: [
		      {
		         text: 'Ok',
		         click: function() {
		            		        	
		            $("#dialog_window_1").dialog("close");
		         }
		      }
		   ],
		   open:function(){
			   $('#dialog_window_1').html(playerWhichReject.nik+' reject challenge!');
		   }
		});
}

function startGame(message){
	
	if(message.value=='cross'){
		yourSign='X';
		opponentSign = 'O';
		yourMove(message);
	}
	if(message.value=='zero'){
		yourSign='O';
		opponentSign = 'X';
		waitForOpponentMove(message);
	}
}

function endGame(message){
	switch (message.gameRes){
		case 'OPPONENT_WIN':
			opponentWin(message);
			break;
		case 'DRAW':
			drawGame(message);
	} 
}

function opponentWin(message){
	if (message){
		id = message.value;
		$('#'+id+'').html("<h1>"+opponentSign+"</h1>");
	}
	
	$('#dialog_window_1').dialog({
		   width: 'auto',
		   height: 'auto',
		   modal: false,
		   title: "INFO",
		   title: "INFO",
		   buttons: [
		      {
		         text: 'Ok',
		         click: function() {
		            $("#dialog_window_1").dialog("close");
		            clearBoard();

		         }
		      }
		   ],
		   open:function(){
			   $('#dialog_window_1').html(message.player.nik+' WIN!  you LOSS!');
		   }
		});

}

function youWin(){
	$('#dialog_window_1').dialog({
		   width: 'auto',
		   height: 'auto',
		   modal: false,
		   title: "INFO",
		   title: "INFO",
		   buttons: [
		      {
		         text: 'Ok',
		         click: function() {
		            $("#dialog_window_1").dialog("close");
		            clearBoard();
		         }
		      }
		   ],
		   open:function(){
			   $('#dialog_window_1').html('YOU WIN!');
		   }
		});
}

function drawGame(message){
	if (message){
		id = message.value;
		$('#'+id+'').html("<h1>"+opponentSign+"</h1>");
	}
	
	
	$('#dialog_window_1').dialog({
		   width: 'auto',
		   height: 'auto',
		   modal: false,
		   title: "INFO",
		   buttons: [
		      {
		         text: 'Ok',
		         click: function() {
		            $("#dialog_window_1").dialog("close");
		            clearBoard();
		         }
		      }
		   ],
		   open:function(){
			   $('#dialog_window_1').html('Game Draw');
		   }
		});
}
















