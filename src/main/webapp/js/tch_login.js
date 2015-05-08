var width=$(window).width();
var height=$(window).height();

function optimize(t){
	$(t).css('left',(width-$(t).width())/2-11+'px');
	$(t).css('top',(height-$(t).height())/2-11+'px');
}

function commit(){
	$.ajax({
		type:'POST',
		async:false,
		url:'user/login',
		data:{username:$('#user').val(), password:$('#pwd').val()},
		dataType:'json',
		success:function(data){
			if (data.state=='SUCCESS'){
				window.location.href=data.url;
			}else{

			}
		}
	});
}
