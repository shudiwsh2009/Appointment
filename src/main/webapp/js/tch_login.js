var width=$(window).width();
var height=$(window).height();

function optimize(t){
	$(t).css('left',(width-$(t).width())/2-11+'px');
	$(t).css('top',(height-$(t).height())/2-11+'px');
}

