/**
 * 
 */
$(function(){
	$('#challangeBut').on('click', function(){
		var playerId = $("#sel :selected").val();
		var nik = $("#sel :selected").text();
		
		var player = new Object();
		player.id = playerId;
		player.nik = nik;
		player.isFree = true;
		
		var msg = new Object();
		msg.value = null;
		msg.cmd = "CHALLENGE";
		
		msg.player = player;
		
		Game.sendMessage(msg);
		//makeChallenge(member);
	})
	
})


function boardSetEnable(message){
	var sign = message.value;
	$("table").off("click");
	
	if (sign=="cross"){
		sign="X";
	}
	if (sign=="zero"){
		sign="O";
	}
	
	$('table').on('click',{sign1: sign}, tableClick); 
			
	function tableClick(e){
		var sign1 = e.data.sign1;
		var id = e.target.childNodes[0].id;
		if($('#'+id+'').html()==''){
			$('#'+id+'').html("<h1>"+sign1+"</h1>");
			moveDone(id, message);
		}

	}
}
function clearBoard(){
	$('#drawContainer').html("<table>"+
      "<tr>"+
        "<td class='cells'><div id='a1'></div></td>"+
        "<td class='cells'><div id='a2'></div></td>"+
        "<td class='cells'><div id='a3'></div></td>"+
      "</tr>"+
      "<tr>"+
        "<td class='cells'><div id='b1'></div></td>"+
        "<td class='cells'><div id='b2'></div></td>"+
        "<td class='cells'><div id='b3'></div></td>"+
      "</tr>"+
      "<tr>"+
        "<td class='cells'><div id='c1'></div></td>"+
        "<td class='cells'><div id='c2'></div></td>"+
        "<td class='cells'><div id='c3'></div></td>"+
      "</tr>"+
    "</table>");
}

function boardSetDisable(){
	$("table").off("click");
}


















