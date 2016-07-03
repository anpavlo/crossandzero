/**
 * 
 */
var yourMoves = [];
var opponentMoves = [];



function yourMove(message){
	
	boardSetEnable(message);
}

function opponentMove(message){
	id = message.value;
	opponentMoves.push(id);
	$('#'+id+'').html("<h1>"+opponentSign+"</h1>");
	message.value = yourSign;
	yourMove(message);
}

function moveDone(moveCoord,message){
	yourMoves.push(moveCoord);
	if(checkIfWinOrDraw(message,moveCoord)) return;
	message.value = moveCoord;
	message.cmd = "MOVE";
	boardSetDisable();
	Game.sendMessage(message);

}

function waitForOpponentMove(){
	boardSetDisable();
}

function checkIfWinOrDraw(message,moveCoord){
	if((yourMoves.length+opponentMoves.length)<9){
		
		
		if(checkWin()){
			youWin();
			message.value = moveCoord;
			message.cmd = "END_GAME";
			message.gameRes = 'OPPONENT_WIN';
			boardSetDisable();
			Game.sendMessage(message);
			return true;
		}
		
		return false;
	}else{
		drawGame();
		message.value = moveCoord;
		message.cmd = "END_GAME";
		message.gameRes = 'DRAW';
		boardSetDisable();
		Game.sendMessage(message);
		return true;
	}
	
	return false;
	
}

function checkWin(){
	
	
	for(var i=0; i< yourMoves.length; i++){
		var x = convertCoord(yourMoves[i])[1];
		var y = convertCoord(yourMoves[i])[0];
		var count = 0;
		var count1 = 0;
		for(var j=i; j< yourMoves.length; j++){
			var curX = convertCoord(yourMoves[j])[1];
			var curY = convertCoord(yourMoves[j])[0];
			
			if (x==curX) count++;
			if (count==3)return true;
			
			if (y==curY) count1++;
			if (count1==3)return true;
		}
	
	}
	count2 = 0;
	count3 = 0;
	var strMoves = '';
	yourMoves.forEach(function(move){
		strMoves = strMoves + move;
	});
	
	var diagon1 = ['a1','b2','c3'];
	var diagon2 = ['a3','b2','c1'];
	
	diagon1.forEach(function(diagonCoord){
		if(strMoves.includes(diagonCoord)) count2++;
	});
	if (count2==3) return true;
	diagon2.forEach(function(diagonCoord){
		if(strMoves.includes(diagonCoord)) count3++;
	});
	if (count3==3) return true;
	
	return false;
}

function convertCoord(strCoord){
	var arrCoord = [];
	var str = strCoord.charAt(1);
	var int1 = strCoord.charCodeAt(0)-97;
	var int2 = parseInt(str)-1;
	arrCoord.push(int1);
	arrCoord.push(int2);
	return arrCoord;
}














