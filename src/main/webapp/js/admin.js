var width=$(window).width();
var height=$(window).height();
var student_table_data=[
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
			<div class="table_col" id="col5">\
				<div class="table_head table_cell">咨询师编号</div>\
			</div>\
			<div class="table_col" id="col6">\
				<div class="table_head table_cell">咨询师手机</div>\
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
		$('#col5').append('<div class="table_cell" id="cell5_'+i+'">'+student_table_data[i].teacherId+'</div>');
		$('#col6').append('<div class="table_cell" id="cell6_'+i+'">'+student_table_data[i].teacherMobile+'</div>');

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
	$('#col1').append('<div class="table_cell" id="cell1_'+'add'+'" onclick="admin_add();">点击新增</div>');
	$('#col2').append('<div class="table_cell" id="cell2_'+'add'+'"></div>');
	$('#col3').append('<div class="table_cell" id="cell3_'+'add'+'"></div>');
	$('#col4').append('<div class="table_cell" id="cell4_'+'add'+'"></div>');
	$('#col5').append('<div class="table_cell" id="cell5_'+'add'+'"></div>');
	$('#col6').append('<div class="table_cell" id="cell6_'+'add'+'"></div>');
}

function optimize(t){
	$('#col0').width(40);
	$('#col1').width(405);
	$('#col2').width(120);
	$('#col5').width(160);
	$('#col6').width(160);
	$('#col3').width(85);
	$('#col4').width(85);
	// $('#col0').css('margin-left',width*0.02+'px')
	for (var i in student_table_data){
		$('#cell1_'+i).height($('#cell4_'+i).height());
		$('#cell2_'+i).height($('#cell4_'+i).height());
		$('#cell3_'+i).height($('#cell4_'+i).height());
		$('#cell5_'+i).height($('#cell4_'+i).height());
		$('#cell6_'+i).height($('#cell4_'+i).height());
		$('#cell0_'+i).height($('#cell4_'+i).height());

		if (i%2==0){
			$('#cell1_'+i).css('background-color','white');
			$('#cell2_'+i).css('background-color','white');
			$('#cell3_'+i).css('background-color','white');
			$('#cell4_'+i).css('background-color','white');
			$('#cell5_'+i).css('background-color','white');
			$('#cell6_'+i).css('background-color','white');
			$('#cell0_'+i).css('background-color','white');
		}else{
			$('#cell1_'+i).css('background-color','#f5f5fa');
			$('#cell2_'+i).css('background-color','#f5f5fa');
			$('#cell3_'+i).css('background-color','#f5f5fa');
			$('#cell4_'+i).css('background-color','#f5f5fa');
			$('#cell5_'+i).css('background-color','#f5f5fa');
			$('#cell6_'+i).css('background-color','#f5f5fa');
			$('#cell0_'+i).css('background-color','#f5f5fa');
		}
	}
	$('#cell0_'+'add').height(28);
	$('#cell1_'+'add').height(28);
	$('#cell2_'+'add').height(28);
	$('#cell3_'+'add').height(28);
	$('#cell4_'+'add').height(28);
	$('#cell5_'+'add').height(28);
	$('#cell6_'+'add').height(28);

	$('.table_head').height($('#head_0').height());
	$(t).css('left',(width-$(t).width())/2-11+'px');
	$(t).css('top',(height-$(t).height())/2-11+'px');
	$('#page_maintable').css('margin-left',.5*($(window).width()-(40+405+120+85+85+320))+'px');

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
			} else {
				alert(data.message);
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
			} else {
				alert(data.message);
			}
		}
	});
}

function fankui_tch(num){
	$('body').append('\
		<div class="fankui_tch" id="fankui_tch_'+num+'" style="text-align:left;top:100px;height:300px;width:400px;left:100px">\
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
			} else {
				alert(data.message);
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
	$('#cell1_'+num)[0].innerHTML='<input type="text" id="inputDate" style="width:80px" ></input>日，<input style="width:20px" id="time1"></input>时<input style="width:20px" id="minute1"></input>分' + 
		'至<input style="width:20px" id="time2"></input>时<input style="width:20px" id="minute2"></input>分';
	$('#cell2_'+num)[0].innerHTML='<input id="name'+num+'"  style="width:80px" value="'+student_table_data[num].teacher+'">';
	$('#cell3_'+num)[0].innerHTML='<button type="button" onclick="edit_commit('+num+');">确认</button>';
	$('#cell4_'+num)[0].innerHTML='<button type="button" onclick="window.location.reload();">取消</button>';
	$('#cell5_'+num)[0].innerHTML='<input id="tec_id'+num+'"  style="width:120px" value="'+student_table_data[num].teacherId+'">';
	$('#cell6_'+num)[0].innerHTML='<input id="mobile'+num+'"  style="width:120px" value="'+student_table_data[num].teacherMobile+'">';
	$('#inputDate').DatePicker({
		format:'YY-m-dd',
		date: $('#inputDate').val(),
		current: $('#inputDate').val(),
		starts: 1,
		position: 'r',
		onBeforeShow: function(){
			$('#inputDate').DatePickerSetDate($('#inputDate').val(), true);
		},
		onChange: function(formated, dates){
			$('#inputDate').val(formated);
			$('#inputDate').val($('#inputDate').val().substr(4,10));
			$('#inputDate').DatePickerHide();
		}
	});
	optimize();
}

function parseTime2(t){
	var tt=t.split(' ');
	var s=tt[0].split('-');
	var r=tt[1].split(':');
	return s[0]+s[1]+s[2]+r[0]+r[1];
}

function edit_commit(num){
	var startTime = $('#inputDate').val()+' '+($('#time1').val().length<2?'0':'')+$('#time1').val() + ":";
	if ($('#minute1').val().length == 0) {
		startTime += "00";
	} else if ($('#minute1').val().length == 1) {
		startTime += "0" + $('#minute1').val();
	} else {
		startTime += $('#minute1').val();
	}
	var endTime = $('#inputDate').val()+' '+($('#time2').val().length<2?'0':'')+$('#time2').val() + ":";
	if ($('#minute2').val().length == 0) {
		endTime += "00";
	} else if ($('#minute2').val().length == 1) {
		endTime += "0" + $('#minute2').val();
	} else {
		endTime += $('#minute2').val();
	}
	var postdata={
		appId:student_table_data[num].appId,
		startTime:startTime,
		endTime:endTime,
		teacher:$("#name"+num).val(),
		teacherId:$('#tec_id'+num).val(),
		teacherMobile:$('#mobile'+num).val()
	};
// console.log(postdata);
	$.ajax({
		type:'POST',
		async:false,
		url:'appointment/editAppointment',
		data:postdata,
		dataType:'json',
		success:function(data){
			if (data.state=='SUCCESS'){
				getData();
			} else {
				alert(data.message);
			}
		}
	});
}

function admin_add(){
	$('#cell1_add')[0].onclick='';
	$('#cell1_add')[0].innerHTML='<input type="text" id="inputDate" style="width:80px" ></input>日，<input style="width:20px" id="time1"></input>时<input style="width:20px" id="minute1"></input>分' + 
		'至<input style="width:20px" id="time2"></input>时<input style="width:20px" id="minute2"></input>分';
	// $('#cell1_add')[0].innerHTML='<input type="text" id="inputDate" style="width:80px" ></input>日，<input style="width:40px" id="time1"></input>时至<input style="width:40px" id="time2"></input>时';
	$('#cell2_add')[0].innerHTML='<input id="name" style="width:80px"></input>';
	$('#cell3_add')[0].innerHTML='<button type="button" onclick="add_commit();">确认</button>';
	$('#cell4_add')[0].innerHTML='<button type="button" onclick="window.location.reload();">取消</button>';
	$('#cell5_add')[0].innerHTML='<input id="tec_id" style="width:120px"></input>';
	$('#cell6_add')[0].innerHTML='<input id="mobile" style="width:120px"></input>';

	$('#inputDate').DatePicker({
		format:'YY-m-dd',
		date: $('#inputDate').val(),
		current: $('#inputDate').val(),
		starts: 1,
		position: 'r',
		onBeforeShow: function(){
			$('#inputDate').DatePickerSetDate($('#inputDate').val(), true);
		},
		onChange: function(formated, dates){
			$('#inputDate').val(formated);
			$('#inputDate').val($('#inputDate').val().substr(4,10));
			$('#inputDate').DatePickerHide();
		}
	});
	optimize();
}

function parseTime(t){
	var s=t.split(' ');
	return s[0][0]+s[0][1]+s[0][2]+s[0][3]+'-'+s[0][4]+s[0][5]+'-'+s[0][6]+s[0][7]+' '+s[0][8]+s[0][9]+':'+s[0][10]+s[0][11];
}

function add_commit(){
	var startTime = $('#inputDate').val()+' '+($('#time1').val().length<2?'0':'')+$('#time1').val() + ":";
	if ($('#minute1').val().length == 0) {
		startTime += "00";
	} else if ($('#minute1').val().length == 1) {
		startTime += "0" + $('#minute1').val();
	} else {
		startTime += $('#minute1').val();
	}
	var endTime = $('#inputDate').val()+' '+($('#time2').val().length<2?'0':'')+$('#time2').val() + ":";
	if ($('#minute2').val().length == 0) {
		endTime += "00";
	} else if ($('#minute2').val().length == 1) {
		endTime += "0" + $('#minute2').val();
	} else {
		endTime += $('#minute2').val();
	}
	var postdata={
		startTime: startTime,
		endTime: endTime,
		teacher:$("#name").val(),
		teacherId:$("#tec_id").val(),
		teacherMobile:$("#mobile").val(),
	};
	// console.log(postdata);
	$.ajax({
		type:'POST',
		async:false,
		url:'appointment/addAppointment',
		data:postdata,
		dataType:'json',
		success:function(data){
			if (data.state=='SUCCESS'){
				getData();
			} else {
				alert(data.message);
			}
		}
	});
}

function admin_delete(){
	$('body').append('\
		<div class="delete_admin_pre">\
			确认删除选中的咨询记录？\
			<br>\
			<button type="button" onclick="$(\'.delete_admin_pre\').remove();admin_delete_confirm();">确认</button>\
			<button type="button" onclick="$(\'.delete_admin_pre\').remove();">取消</button>\
		</div>\
	');
	optimize('.delete_admin_pre');
}

function admin_delete_confirm(){
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
			} else {
				alert(data.message);
			}
		}
	});
}

function admin_cancel(){
	$('body').append('\
		<div class="cancel_admin_pre">\
			确认取消选中的预约记录？\
			<br>\
			<button type="button" onclick="$(\'.cancel_admin_pre\').remove();admin_cancel_confirm();">确认</button>\
			<button type="button" onclick="$(\'.cancel_admin_pre\').remove();">取消</button>\
		</div>\
	');
	optimize('.cancel_admin_pre');
}

function admin_cancel_confirm(){
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
			} else {
				alert(data.message);
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

function admin_query() {
	var postData = {
		fromTime: $('#queryDate').val(),
	};
	$.ajax({
		type:'POST',
		async:false,
		url:'appointment/admin/queryAppointments',
		data:postData,
		dataType:'json',
		success:function(data){
			if (data.state=='SUCCESS') {
				student_table_data=data.array;
				addInfo();
				optimize();
			} else {
				alert(data.message);
			}
		}
	});
}

function admin_logout() {
	$.ajax({
		type:'GET',
		async:false,
		url:'user/logout',
		data:{},
		dataType:'json',
		success:function(data){
			if (data.state=='SUCCESS'){
				window.location.href=data.url;
			}else{

			}
		}
	});
}

function getData(){
	$.ajax({
		type:'GET',
		async:false,
		url:'appointment/admin/viewAppointments',
		dataType:'json',
		success:function(data){
			if (data.state=='SUCCESS'){
				student_table_data=data.array;
				addInfo();
				optimize();
			} else {
				alert(data.message);
			}
		}
	});
}
