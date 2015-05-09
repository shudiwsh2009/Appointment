var width=$(window).width();
var height=$(window).height();
var student_table_data=[
                    	//{appId:'2012012345', teacher:'赵一',	startTime:'15-03-02 15:00',endTime:'15-03-02 16:00', status:'AVAILABLE'},
                    	//{appId:'2012012345', teacher:'钱二',	startTime:'15-03-02 15:00',endTime:'15-03-02 16:00', status:'AVAILABLE'},
                    	//{appId:'2012012345', teacher:'孙三',	startTime:'15-03-02 15:00',endTime:'15-03-02 16:00', status:'AVAILABLE'},
                    	//{appId:'2012012345', teacher:'李四',	startTime:'15-03-02 15:00',endTime:'15-03-02 16:00', status:'AVAILABLE'},
                    	//{appId:'2012012345', teacher:'周五',	startTime:'15-03-02 15:00',endTime:'15-03-02 16:00', status:'AVAILABLE'},
                    	//{appId:'2012012345', teacher:'吴六',	startTime:'15-03-02 15:00',endTime:'15-03-02 16:00', status:'AVAILABLE'},
                    	//{appId:'2012012345', teacher:'赵七',	startTime:'15-03-02 15:00',endTime:'15-03-02 16:00', status:'AVAILABLE'},
                    	//{appId:'2012012345', teacher:'钱八',	startTime:'15-03-02 15:00',endTime:'15-03-02 16:00', status:'APPOINTED'},
                    	//{appId:'2012012345', teacher:'孙九',	startTime:'15-03-02 15:00',endTime:'15-03-02 16:00', status:'APPOINTED'},
                    	//{appId:'2012012345', teacher:'李十',	startTime:'15-03-02 15:00',endTime:'15-03-02 16:00', status:'APPOINTED'},
                    	//{appId:'2012012345', teacher:'周十一',	startTime:'15-03-02 15:00',endTime:'15-03-02 16:00', status:'APPOINTED'},
                    	//{appId:'2012012345', teacher:'吴十二',	startTime:'15-03-02 15:00',endTime:'15-03-02 16:00', status:'APPOINTED'},
                    	//{appId:'2012012345', teacher:'赵赵一',	startTime:'15-03-02 15:00',endTime:'15-03-02 16:00', status:'APPOINTED'},
                    	//{appId:'2012012345', teacher:'钱钱二',	startTime:'15-03-02 15:00',endTime:'15-03-03 16:00', status:'APPOINTED'},
                    	//{appId:'2012012345', teacher:'孙孙三',	startTime:'15-03-02 15:00',endTime:'15-03-03 16:00', status:'APPOINTED'},
                    	//{appId:'2012012345', teacher:'李李四',	startTime:'15-03-02 15:00',endTime:'15-03-03 16:00', status:'FEEDBACK'},
                    	//{appId:'2012012345', teacher:'周周五',	startTime:'15-03-02 15:00',endTime:'15-03-03 16:00', status:'FEEDBACK'},
                    	//{appId:'2012012345', teacher:'吴吴六',	startTime:'15-03-02 15:00',endTime:'15-03-03 16:00', status:'FEEDBACK'},
                    	//{appId:'2012012345', teacher:'赵赵七',	startTime:'15-03-02 15:00',endTime:'15-03-03 16:00', status:'FEEDBACK'},
                    	//{appId:'2012012345', teacher:'钱钱八',	startTime:'15-03-02 15:00',endTime:'15-03-03 16:00', status:'FEEDBACK'},
                    	//{appId:'2012012345', teacher:'孙孙九',	startTime:'15-03-02 15:00',endTime:'15-03-03 16:00', status:'FEEDBACK'},
                    	//{appId:'2012012345', teacher:'李李十',	startTime:'15-03-02 15:00',endTime:'15-03-03 16:00', status:'FEEDBACK'},
                    	//{appId:'2012012345', teacher:'周周十一',	startTime:'15-03-02 15:00',endTime:'15-03-03 16:00', status:'FEEDBACK'},
                    	//{appId:'20120123', teacher:'吴吴十二',	startTime:'15-03-02 15:00',endTime:'15-03-03 16:00', status:'FEEDBACK'}
];

var feedback;

function addInfo(){
	$('#page_maintable')[0].innerHTML='\
			<div class="table_col" id="col0">\
				<div class="table_head table_cell" id="head_0"><button onclick="$(\'.checkbox\').click();">全选</button></div>\
			</div>\
			<div class="table_col" id="col1">\
				<div class="table_head table_cell">时间</div>\
			</div>\
			<div class="table_col" id="col2">\
				<div class="table_head table_cell">咨询师</div>\
			</div>\
			<div class="table_col" id="col3">\
				<div class="table_head table_cell">状态</div>\
			</div>\
			<div class="table_col" id="col4">\
				<div class="table_head table_cell">学生</div>\
			</div>\
			<div class="clearfix"></div>\
	';
	for (var i in student_table_data){
		$('#col0').append('<div class="table_cell" id="cell0_'+i+'"><input class="checkbox" type="checkbox" id="checkbox_'+i+'"></div>');
		$('#col1').append('<div class="table_cell" id="cell1_'+i+'" onclick="admin_edit('+i+')">'+student_table_data[i].startTime+'至'+student_table_data[i].endTime+'</div>');
		$('#col2').append('<div class="table_cell" id="cell2_'+i+'">'+student_table_data[i].teacher+'</div>');
		if (student_table_data[i].status=='AVAILABLE'){
			$('#col3').append('<div class="table_cell" id="cell3_'+i+'">未预约×</div>');
			$('#col4').append('<div class="table_cell" id="cell4_'+i+'"><button type="button" id="cell4b_'+i+'" disabled="disabled">查看</button></div>');
		}else if (student_table_data[i].status=='APPOINTED'){
			$('#col3').append('<div class="table_cell" id="cell3_'+i+'">已预约√</div>');
			$('#col4').append('<div class="table_cell" id="cell4_'+i+'"><button type="button" id="cell4b_'+i+'" onclick="admin_chakan('+i+')">查看</button></div>');
		}else{
			$('#col3').append('<div class="table_cell" id="cell3_'+i+'"><button type="button" id="cell3b_'+i+'" onclick="fankui_admin('+i+')">反馈</button></div>');
			$('#col4').append('<div class="table_cell" id="cell4_'+i+'"><button type="button" id="cell4b_'+i+'" onclick="admin_chakan('+i+')">查看</button></div>');

		}
	}
	$('#col0').append('<div class="table_cell" id="cell0_'+'add'+'"><input type="checkbox"></div>');
	$('#col1').append('<div class="table_cell" id="cell1_'+'add'+'" onclick="admin_add();">点击新增，请输入符合"201505111300"格式的时间</div>');
	$('#col2').append('<div class="table_cell" id="cell2_'+'add'+'"></div>');
	$('#col3').append('<div class="table_cell" id="cell3_'+'add'+'"></div>');
	$('#col4').append('<div class="table_cell" id="cell4_'+'add'+'"></div>');
}

function optimize(t){
	$('#col0').width(width*0.04);
	$('#col1').width(width*0.34);
	$('#col2').width(width*0.14);
	$('#col3').width(width*0.08);
	$('#col4').width(width*0.08);
	$('#col0').css('margin-left',width*0.02+'px')
	for (var i in student_table_data){
		$('#cell1_'+i).height($('#cell4_'+i).height());
		$('#cell2_'+i).height($('#cell4_'+i).height());
		$('#cell3_'+i).height($('#cell4_'+i).height());
		$('#cell0_'+i).height($('#cell4_'+i).height());
	}
	$('#cell1_'+'add').height($('#cell0_'+'add').height());
	$('#cell2_'+'add').height($('#cell0_'+'add').height());
	$('#cell3_'+'add').height($('#cell0_'+'add').height());
	$('#cell4_'+'add').height($('#cell0_'+'add').height());

	$('.table_head').height($('#head_0').height());
	$(t).css('left',(width-$(t).width())/2-11+'px');
	$(t).css('top',(height-$(t).height())/2-11+'px');
}


function admin_chakan(num){
	$.ajax({
		type:'POST',
		async:false,
		url:'appointment/viewAppointment',
		data:{appId:student_table_data[num].appId},
		dataType:'json',
		success:function(data){
			if (data.state=='SUCCESS'){
				$('body').append('\
					<div class="admin_chakan" style="text-align:left">\
						姓名：'+data.name+'<br>\
						性别：'+data.gender+'<br>\
						院系：'+data.school+'<br>\
						学号：'+data.studentId+'<br>\
						手机：'+data.mobile+'<br>\
						生源地：'+data.hometown+'<br>\
						邮箱：'+data.email+'<br>\
						是否使用过本系统：'+data.experience+'<br>\
						咨询问题：'+data.problem+'<br>\
						<button type="button" onclick="$(\'.admin_chakan\').remove();">返回</button>\
					</div>\
				');
				optimize('.admin_chakan');
			}
		}
	});
}

function fankui_admin(num){
	$.ajax({
		type:'POST',
		async:false,
		url:'appointment/adminCheck',
		data:{appId:student_table_data[num].appId},
		dataType:'json',
		success:function(data){
			if (data.state=='SUCCESS'){
				feedback=data;
				fankui_tch(num);
			}
		}
	});
}

function fankui_tch(num){
	$('body').append('\
		<div class="fankui_tch" id="fankui_tch_'+num+'" style="text-align:left;">\
			咨询师反馈表<br>\
			您的姓名：<input id="fb_teacherName"></input><br>\
			工作证号：<input id="fb_teacherId"></input><br>\
			来访者姓名：<input id="fb_studentName"></input><br>\
			来访者问题描述：<br>\
			<textarea id="fb_problem"></textarea><br>\
			咨询师提供的问题解决方法：<br>\
			<textarea id="fb_solution"></textarea><br>\
			对中心的工作建议：<br>\
			<textarea id="fb_advice"></textarea><br>\
			<button type="button" onclick="if (getFeedbackData('+num+')) adminPostFeedback(feedbackdata,'+num+');">提交反馈</button>\
			<button type="button" onclick="$(\'.fankui_tch\').remove();">取消</button>\
		</div>\
	');
	if (feedback.teacherName!=""){
		$('#fb_teacherName').val(feedback.teacherName);
		$('#fb_studentName').val(feedback.studentName);
		$('#fb_teacherId').val(feedback.teacherId);
		$('#fb_problem').val(feedback.problem);
		$('#fb_solution').val(feedback.solution);
		$('#fb_advice').val(feedback.advice);
	}
	optimize('.fankui_tch');
}


function getFeedbackData(num){
	var t;
	t=$('#fb_teacherName').val();
	if (t!='') feedbackdata.teacherName=t;
		else return false;
	t=$('#fb_studentName').val();
	if (t!='') feedbackdata.studentName=t;
		else return false;
	t=$('#fb_problem').val();
	if (t!='') feedbackdata.problem=t;
		else return false;
	t=$('#fb_teacherId').val();
	if (t!='') feedbackdata.teacherId=t;
		else return false;
	t=$('#fb_solution').val();
	if (t!='') feedbackdata.solution=t;
		else return false;
	t=$('#fb_advice').val();
	if (t!='') feedbackdata.advice=t;
		else return false;

	feedbackdata.appId=student_table_data[num].appId;
	return true;
}

function adminPostFeedback(postdata, num){
	$.ajax({
		type:'POST',
		async:false,
		url:'appointment/adminFeedBack',
		data:postdata,
		dataType:'json',
		success:function(data){
			if (data.state=='SUCCESS'){
				fankui_tch_confirm(num);
			}
		}
	});
}

function fankui_tch_confirm(num){
	$('.fankui_tch').remove();
//	$('#cell3b_'+num).attr('disabled','true');
//	$('#cell3b_'+num).text('已反馈');
	$('body').append('\
		<div class="fankui_tch_success">\
			您已成功提交反馈！<br>\
			<button type="button" onclick="$(\'.fankui_tch_success\').remove();">确定</button>\
		</div>\
	');
	optimize('.fankui_tch_success');
}

function admin_edit(num){
	$('#cell1_'+num)[0].onclick='';
	$('#cell1_'+num)[0].innerHTML='<input id="time_st'+num+'" value="'+parseTime2(student_table_data[num].startTime)+'">至<input id="time_ed'+num+'" value="'+parseTime2(student_table_data[num].endTime)+'"">';
	$('#cell2_'+num)[0].innerHTML='<input id="name'+num+'" value="'+student_table_data[num].teacher+'">';
	$('#cell3_'+num)[0].innerHTML='<button type="button" onclick="edit_commit('+num+');">确认</button>';
}

function parseTime2(t){
	var tt=t.split(' ');
	var s=tt[0].split('-');
	var r=tt[1].split(':');
	return s[0]+s[1]+s[2]+r[0]+r[1];
}

function edit_commit(num){
	var postdata={appId:student_table_data[num].appId, startTime:parseTime($("#time_st"+num).val()), endTime:parseTime($("#time_ed"+num).val()), teacher:$("#name"+num).val()};
console.log(postdata);
	$.ajax({
		type:'POST',
		async:false,
		url:'appointment/editAppointment',
		data:postdata,
		dataType:'json',
		success:function(data){
			if (data.state=='SUCCESS'){
				getData();
			}
		}
	});
}

function admin_add(){
	$('#cell1_add')[0].onclick='';
	$('#cell1_add')[0].innerHTML='<input id="time_st"></input>至<input id="time_ed"></input>';
	$('#cell2_add')[0].innerHTML='<input id="name"></input>';
	$('#cell3_add')[0].innerHTML='<button type="button" onclick="add_commit();">确认</button>';
}

function parseTime(t){
	var s=t.split(' ');
	return s[0][0]+s[0][1]+s[0][2]+s[0][3]+'-'+s[0][4]+s[0][5]+'-'+s[0][6]+s[0][7]+' '+s[0][8]+s[0][9]+':'+s[0][10]+s[0][11];
}

function add_commit(){
	var postdata={startTime:parseTime($("#time_st").val()), endTime:parseTime($("#time_ed").val()), teacher:$("#name").val()};
	console.log(postdata);
	$.ajax({
		type:'POST',
		async:false,
		url:'appointment/addAppointment',
		data:postdata,
		dataType:'json',
		success:function(data){
			if (data.state=='SUCCESS'){
				getData();
			}
		}
	});
}

function admin_delete(){
	var postdata={appIds:[]};
	for (var i in student_table_data)
	{
		if ($('#checkbox_'+i)[0].checked)
		{
			postdata.appIds.push(student_table_data[i].appId);
		}
	}
	console.log(postdata);
	$.ajax({
		type:'POST',
		async:false,
		url:'appointment/removeAppointment',
		data:postdata,
		dataType:'json',
		success:function(data){
			if (data.state=='SUCCESS'){
				getData();
			}
		}
	});
}

function admin_cancel(){
	var postdata={appIds:[]};
	for (var i in student_table_data)
	{
		if ($('#checkbox_'+i)[0].checked)
		{
			postdata.appIds.push(student_table_data[i].appId);
		}
	}

	$.ajax({
		type:'POST',
		async:false,
		url:'appointment/cancelAppointment',
		data:postdata,
		dataType:'json',
		success:function(data){
			if (data.state=='SUCCESS'){
				getData();
			}
		}
	});
}

function admin_export(){
	var postdata={appIds:[]};
	for (var i in student_table_data)
	{
		if ($('#checkbox_'+i)[0].checked)
		{
			postdata.appIds.push(student_table_data[i].appId);
		}
	}

	$.ajax({
		type:'POST',
		async:false,
		url:'appointment/exportAppointment',
		data:postdata,
		dataType:'json',
		success:function(data){
			if (data.state=='SUCCESS'){
				window.open(data.url);
			}
			else{
				alert("导出失败，请联系管理员");
			}
		}
	});
}

function getData(){
	$.ajax({
		type:'GET',
		async:false,
		url:'appointment/viewAppointments',
		dataType:'json',
		success:function(data){
			if (data.state=='SUCCESS'){
				student_table_data=data.array;
				addInfo();
				optimize();
			}
		}
	});
}
