var width=$(window).width();
var height=$(window).height();
var student_table_data=[
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

function addInfo(){
	for (var i in student_table_data){
		$('#col0').append('<div class="table_cell" id="cell0_'+i+'"><input class="checkbox" type="checkbox" id="checkbox_'+i+'"></div>');
		$('#col1').append('<div class="table_cell" id="cell1_'+i+'">'+student_table_data[i].t+'</div>');
		$('#col2').append('<div class="table_cell" id="cell2_'+i+'">'+student_table_data[i].n+'</div>');
		if (student_table_data[i].u=='n'){
			$('#col3').append('<div class="table_cell" id="cell3_'+i+'">已预约√</div>');
		}else if (student_table_data[i].u=='o'){
			$('#col3').append('<div class="table_cell" id="cell3_'+i+'">已预约√</div>');
		}else{
			$('#col3').append('<div class="table_cell" id="cell3_'+i+'">未预约×</div>');
		}
		$('#col4').append('<div class="table_cell" id="cell4_'+i+'"><button type="button" id="cell4b_'+i+'" onclick="admin_chakan('+i+')"'+(student_table_data[i].u=='y'?'disabled="disabled"':'')+'>查看</button></div>');
		$('#col5').append('<div class="table_cell" id="cell5_'+i+'"><button type="button" id="cell5b_'+i+'" onclick="admin_fankui('+i+')"'+(student_table_data[i].u=='y'?'disabled="disabled"':'')+'>反馈</button></div>');
	}	
	$('#col0').append('<div class="table_cell" id="cell0_'+'add'+'"><input type="checkbox"></div>');
	$('#col1').append('<div class="table_cell" id="cell1_'+'add'+'" onclick="admin_add();">点击新增</div>');
	$('#col2').append('<div class="table_cell" id="cell2_'+'add'+'"></div>');
	$('#col3').append('<div class="table_cell" id="cell3_'+'add'+'"></div>');
	$('#col4').append('<div class="table_cell" id="cell4_'+'add'+'"></div>');
	$('#col5').append('<div class="table_cell" id="cell5_'+'add'+'"></div>');
}

function optimize(t){
	$('#col0').width(width*0.04);
	$('#col1').width(width*0.24);
	$('#col2').width(width*0.14);
	$('#col3').width(width*0.08);
	$('#col4').width(width*0.08);
	$('#col5').width(width*0.08);
	$('#col0').css('margin-left',width*0.02+'px')
	for (var i in student_table_data){
		$('#cell1_'+i).height($('#cell4_'+i).height());
		$('#cell2_'+i).height($('#cell4_'+i).height());
		$('#cell3_'+i).height($('#cell4_'+i).height());
		$('#cell5_'+i).height($('#cell4_'+i).height());
		$('#cell0_'+i).height($('#cell4_'+i).height());
	}
	$('#cell1_'+'add').height($('#cell0_'+'add').height());
	$('#cell2_'+'add').height($('#cell0_'+'add').height());
	$('#cell3_'+'add').height($('#cell0_'+'add').height());
	$('#cell5_'+'add').height($('#cell0_'+'add').height());
	$('#cell4_'+'add').height($('#cell0_'+'add').height());

	$('.table_head').height($('#head_0').height());
	$(t).css('left',(width-$(t).width())/2-11+'px');
	$(t).css('top',(height-$(t).height())/2-11+'px');
}


function admin_chakan(num){
	$('body').append('\
		<div class="admin_chakan" style="text-align:left">\
			姓名：王二<br>\
			院系：软件学院<br>\
			学号：2012011111<br>\
			手机：12332112312<br>\
			邮箱：wang-er@mails.tsinghua.edu.cn<br>\
			预约事项：无<br>\
			<button type="button" onclick="$(\'.admin_chakan\').fadeOut(100);">返回</button>\
		</div>\
	');
	optimize('.admin_chakan');
}

function admin_fankui(num){
	$('body').append('\
		<div class="fankui_admin" id="fankui_admin_'+num+'">\
			咨询师反馈表\
			<br><br><br>\
			<br><br><br>\
			<br><br><br>\
			<br><br><br>\
			<br><br><br>\
			<br><br><br>\
			<br><br><br>\
			<br><br><br>\
			<button type="button" onclick="$(\'.fankui_admin\').fadeOut(100);">关闭</button>\
		</div>\
	');
	optimize('.fankui_admin');
}

function admin_add(){
	$('#cell1_add')[0].onclick='';
	$('#cell1_add')[0].innerHTML='<input value="150304 12-32"></input>';
	$('#cell2_add')[0].innerHTML='<input></input>';
	$('#cell3_add')[0].innerHTML='<button type="button" onclick="">确认</button>';
}

