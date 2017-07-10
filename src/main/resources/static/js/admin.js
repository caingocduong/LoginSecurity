window.onload = function(){
	$(".button1").click(function(){
		$(function (){
			setTimeout(() => {
				message();
			}, 10000);
		}
	});
	$("#cancel").click(function(){
		hide();
	});
};

function message(){
	$("#delete_message").slideDown();
}

function hide(){
	$("#delete_message").slideUp();
}