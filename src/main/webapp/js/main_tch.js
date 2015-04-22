var width=$(window).width();
var height=$(window).height();
var tchdent_table_data=[
	{n:'赵一',	t:'4月18日19:00', u:'n'},
	{n:'钱二',	t:'4月18日19:00', u:'y'},
	{n:'孙三',	t:'4月18日19:00', u:'n'},
	{n:'李四',	t:'4月18日19:00', u:'o'},
	{n:'周五',	t:'4月18日19:00', u:'n'},
	{n:'吴六',	t:'4月18日19:00', u:'n'},
	{n:'赵七',	t:'4月18日19:00', u:'y'},
	{n:'钱八',	t:'4月18日19:00', u:'n'},
	{n:'孙九',	t:'4月18日19:00', u:'o'},
	{n:'李十',	t:'4月18日19:00', u:'y'},
	{n:'周十一',	t:'4月18日19:00', u:'n'},
	{n:'吴十二',	t:'4月18日19:00', u:'n'},
	{n:'赵赵一',	t:'4月18日19:00', u:'n'},
	{n:'钱钱二',	t:'4月18日19:00', u:'y'},
	{n:'孙孙三',	t:'4月18日19:00', u:'n'},
	{n:'李李四',	t:'4月18日19:00', u:'o'},
	{n:'周周五',	t:'4月18日19:00', u:'n'},
	{n:'吴吴六',	t:'4月18日19:00', u:'n'},
	{n:'赵赵七',	t:'4月18日19:00', u:'y'},
	{n:'钱钱八',	t:'4月18日19:00', u:'n'},
	{n:'孙孙九',	t:'4月18日19:00', u:'o'},
	{n:'李李十',	t:'4月18日19:00', u:'y'},
	{n:'周周十一',	t:'4月18日19:00', u:'n'},
	{n:'吴吴十二',	t:'4月18日19:00', u:'n'}
];

function addInfo_tch(){
	for (var i in tchdent_table_data){
		$('#col1').append('<div class="table_cell" id="cell1_'+i+'">'+tchdent_table_data[i].t+'</div>');
		$('#col2').append('<div class="table_cell" id="cell2_'+i+'">'+tchdent_table_data[i].n+'</div>');
		if (tchdent_table_data[i].u=='n'){
			$('#col3').append('<div class="table_cell" id="cell3_'+i+'"><button type="button" id="cell3b_'+i+'" disabled="true">已预约</button></div>');
		}else if (tchdent_table_data[i].u=='o'){
			$('#col3').append('<div class="table_cell" id="cell3_'+i+'"><button type="button" id="cell3b_'+i+'" onclick="fankui_tch_pre('+i+')">反馈</button></div>');
		}else{
			$('#col3').append('<div class="table_cell" id="cell3_'+i+'"><button type="button" id="cell3b_'+i+'" disabled="true">未预约</button></div>');
		}
	}
}


function optimize(t){
	$('#col1').width(width*0.44);
	$('#col2').width(width*0.24);
	$('#col3').width(width*0.24);
	$('#col1').css('margin-left',width*0.02+'px')
	for (var i in tchdent_table_data){
		$('#cell1_'+i).height($('#cell3_'+i).height());
		$('#cell2_'+i).height($('#cell3_'+i).height());
	}
	$(t).css('left',(width-$(t).width())/2-11+'px');
	$(t).css('top',(height-$(t).height())/2-11+'px');
}

function fankui_tch_pre(num){
	$('body').append('\
		<div class="fankui_tch_pre">\
			请输入预约学号\
			<br>\
			<input></input>\
			<br>\
			<button type="button" onclick="$(\'.fankui_tch_pre\').fadeOut(100);fankui_tch('+num+');">确定</button>\
			<button type="button" onclick="$(\'.fankui_tch_pre\').fadeOut(100);">取消</button>\
		</div>\
	');
	optimize('.fankui_tch_pre');
}

function fankui_tch(num){
	$('body').append('\
		<div class="fankui_tch" id="fankui_tch_'+num+'">\
			咨询师反馈表\
			<br><br><br>\
			<br><br><br>\
			<br><br><br>\
			<br><br><br>\
			<br><br><br>\
			<br><br><br>\
			<br><br><br>\
			<br><br><br>\
			<button type="button" onclick="fankui_tch_confirm('+num+');">提交反馈</button>\
			<button type="button" onclick="$(\'.fankui_tch\').fadeOut(100);">取消</button>\
		</div>\
	');
	optimize('.fankui_tch');
}

function fankui_tch_confirm(num){
	$('.fankui_tch').fadeOut(100);
	$('#cell3b_'+num).attr('disabled','true');
	$('#cell3b_'+num).text('已反馈');
	$('body').append('\
		<div class="fankui_tch_success">\
			您已成功提交反馈！<br>\
			<button type="button" onclick="$(\'.fankui_tch_success\').fadeOut(100);">确定</button>\
		</div>\
	');
	optimize('.fankui_tch_success');
}

