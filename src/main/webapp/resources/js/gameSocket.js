/**
 * 
 */
var Console = {};
var Members = {};
var ME;

$(function(){
	 var noscripts = document.getElementsByClassName("noscript");
	    for (var i = 0; i < noscripts.length; i++) {
	        noscripts[i].parentNode.removeChild(noscripts[i]);
	    }
	
Game = {};

Game.socket = null;

Game.connect = (function(host) {
    if ('WebSocket' in window) {
    	Game.socket = new WebSocket(host);
    } else if ('MozWebSocket' in window) {
        Game.socket = new MozWebSocket(host);
    } else {
        Console.log('Error: WebSocket is not supported by this browser.');
        return;
    }

    Game.socket.onopen = function () {
        Console.log('Info: WebSocket connection opened.');
        
    };

    Game.socket.onclose = function () {
        
        Console.log('Info: WebSocket closed.');
    };

    Game.socket.onmessage = function (message) {
        
    	console.log("recive data: "+message.data);
    	processMessage(JSON.parse(message.data));
    };
});





Game.initialize = function() {
    if (window.location.protocol == 'http:') {
        Game.connect('ws://' + window.location.host + '/websocket/game');
    } else {
        Game.connect('wss://' + window.location.host + '/websocket/game');
    }
};

Game.sendMessage = (function(message) {
	Game.socket.send(JSON.stringify(message));
});

function processMessage(message){
 
	switch (message.cmd) {
	  case 'BROADCAST':
		console.log( 'receive command: BROADCAST' );
		Console.log(message.value);
	    break;
	  case 'ME':
		console.log( 'receive command: ME!' );  
		ME = message.player;
		$('#info').val("You are "+ME.nik); 
		console.log( 'ME.nik = '+ ME.nik);
		console.log( 'ME.id = '+ ME.id);
		break;	  
	  case 'UPDATE_PLAYERS':
		console.log( 'receive command: UPDATE_PLAYERS!' );
		var players = message.playerList;
		$('option', $("#sel")).remove();
		players.forEach(function(player){
			Members.log(player);
		})
	    break;
	  case 'CHALLENGE':
			console.log( 'receive command: CHALLENGE' );
			challenge(message.player);
		    break;
	  case 'REJECT_CHALLENGE':
			console.log( 'receive command: REJECT_CHALLENGE' );
			rejectChallMessageDialog(message.player);
		    break;
	  case 'PLAYER_IS_BUSY':
		  playerIsBusy(message.player);
		  break;
	  case 'START_GAME':
		  console.log( 'receive command: START_GAME' );
		  startGame(message);
		  break;
	  case 'MOVE':
		  console.log( 'receive command: MOVE' );
		  opponentMove(message);
		  break;
	  case 'OPONENT_WIN':
		  console.log( 'receive command: OPONENT_WIN' );
		  opponentWin(message);
		  break;
	  case 'END_GAME':
		  console.log( 'receive command: DRAW_GAME' );
		  endGame(message);
		  break;

	  default:
		console.log( 'receive command: UNKNOWN COMMAND' );
	}
}


Console.log = (function(message) {
    var console = document.getElementById('console');
    var p = document.createElement('p');
    p.style.wordWrap = 'break-word';
    p.innerHTML = message;
    console.appendChild(p);
    while (console.childNodes.length > 50) {
        console.removeChild(console.firstChild);
    }
    console.scrollTop = console.scrollHeight;
});

Members.log = (function(player) {
    var members = document.getElementById('sel');
    var opt = document.createElement('option');
    opt.style.wordWrap = 'break-word';
    opt.value = player.id;
    var status = (player.isFree)? "":"busy";
    opt.innerHTML = player.nik+" "+status;
    members.appendChild(opt);
    while (members.childNodes.length > 50) {
    	members.removeChild(members.firstChild);
    }
    members.scrollTop = members.scrollHeight;
});

Game.initialize();




})
