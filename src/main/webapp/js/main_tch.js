var width=$(window).width();
var height=$(window).height();
var student_table_data=[];
var teacher = "";
var teacherMobile = "";

var feedback;

function addInfo_tch(data){
	student_table_data=data;
	$('#page_maintable')[0].innerHTML='\
	<div class="table_col" id="col0">\
		<div class="table_head table_cell" id="head_0"><button onclick="$(\'.checkbox\').click();"  style="padding:0px;">全选</button></div>\
	</div>\
	<div class="table_col" id="col1">\
		<div class="table_head table_cell">时间</div>\
	</div>\
	<div class="table_col" id="col2">\
		<div class="table_head table_cell">咨询师</div>\
	</div>\
	<div class="table_col" id="col5">\
		<div class="table_head table_cell">咨询师电话</div>\
	</div>\
	<div class="table_col" id="col3">\
		<div class="table_head table_cell">状态</div>\
	</div>\
	<div class="table_col" id="col4">\
		<div class="table_head table_cell">状态</div>\
	</div>\
	<div class="clearfix"></div>\
	';
	for (var i in data){
		$('#col0').append('<div class="table_cell" id="cell0_'+i+'"><input class="checkbox" type="checkbox" id="checkbox_'+i+'"></div>');
		$('#col1').append('<div class="table_cell" id="cell1_'+i+'" onclick="teacher_edit('+i+')">'+student_table_data[i].startTime.substr(2)+'-'+student_table_data[i].endTime.split(' ')[1]+'</div>');
		//$('#col1').append('<div class="table_cell" id="cell1_'+i+'" onclick="teacher_edit('+i+')">'+student_table_data[i].startTime+'至'+student_table_data[i].endTime+'</div>');
		$('#col2').append('<div class="table_cell" id="cell2_'+i+'">'+student_table_data[i].teacher+'</div>');
		$('#col5').append('<div class="table_cell" id="cell5_'+i+'">'+student_table_data[i].teacherMobile+'</div>');
		if (student_table_data[i].status=='APPOINTED'){
			$('#col3').append('<div class="table_cell" id="cell3_'+i+'">已预约</div>');
			$('#col4').append('<div class="table_cell" id="cell4_'+i+'"><button type="button" id="cell4b_'+i+'" onclick="teacher_lookup('+i+')">查看</button></div>');
		}else if (student_table_data[i].status=='FEEDBACK'){
			$('#col3').append('<div class="table_cell" id="cell3_'+i+'"><button type="button" id="cell3b_'+i+'" onclick="check_studentid('+i+')">反馈</button></div>');
			$('#col4').append('<div class="table_cell" id="cell4_'+i+'"><button type="button" id="cell4b_'+i+'" onclick="teacher_lookup('+i+')">查看</button></div>');
		}else{
			$('#col3').append('<div class="table_cell" id="cell3_'+i+'">未预约</div>');
			$('#col4').append('<div class="table_cell" id="cell4_'+i+'"><button type="button" id="cell4b_'+i+'" disabled="true">查看</button></div>');
		}
	}
	$('#col0').append('<div class="table_cell" id="cell0_'+'add'+'"><input type="checkbox"></div>');
	$('#col1').append('<div class="table_cell" id="cell1_'+'add'+'" onclick="teacher_add();">点击新增</div>');
	$('#col2').append('<div class="table_cell" id="cell2_'+'add'+'"></div>');
	$('#col3').append('<div class="table_cell" id="cell3_'+'add'+'"></div>');
	$('#col4').append('<div class="table_cell" id="cell4_'+'add'+'"></div>');
	$('#col5').append('<div class="table_cell" id="cell5_'+'add'+'"></div>');
}


function optimize(t){
	$('#col0').width(20);
	$('#col1').width(80);
	$('#col2').width(44);
	$('#col3').width(40);
	$('#col4').width(40);
	$('#col5').width(76);

	$('#col0').css('margin-left',(width-300)/2+'px');

	for (var i in student_table_data){
		var maxheight=$('#cell1_'+i).height();
		if (maxheight<$('#cell2_'+i).height()) maxheight=$('#cell2_'+i).height();
		if (maxheight<$('#cell3_'+i).height()) maxheight=$('#cell3_'+i).height();
		if (maxheight<$('#cell4_'+i).height()) maxheight=$('#cell4_'+i).height();
		if (maxheight<$('#cell5_'+i).height()) maxheight=$('#cell5_'+i).height();
		if (maxheight<$('#cell0_'+i).height()) maxheight=$('#cell0_'+i).height();

		$('#cell1_'+i).height(maxheight);
		$('#cell2_'+i).height(maxheight);
		$('#cell3_'+i).height(maxheight);
		$('#cell4_'+i).height(maxheight);
		$('#cell5_'+i).height(maxheight);
		$('#cell0_'+i).height(maxheight);

		if (i%2==0){
			$('#cell1_'+i).css('background-color','white');
			$('#cell2_'+i).css('background-color','white');
			$('#cell3_'+i).css('background-color','white');
			$('#cell4_'+i).css('background-color','white');
			$('#cell5_'+i).css('background-color','white');
			$('#cell0_'+i).css('background-color','white');
		}else{
			$('#cell1_'+i).css('background-color','#f5f5fa');
			$('#cell2_'+i).css('background-color','#f5f5fa');
			$('#cell3_'+i).css('background-color','#f5f5fa');
			$('#cell4_'+i).css('background-color','#f5f5fa');
			$('#cell5_'+i).css('background-color','#f5f5fa');
			$('#cell0_'+i).css('background-color','#f5f5fa');
		}
	}
	$(t).css('left',(width-$(t).width())/2-11+'px');
	$(t).css('top',(height-$(t).height())/2-11+'px');

	var s=28;
	if (t=='add') s=68;
	$('#cell0_'+'add').height(s);
	$('#cell1_'+'add').height(s);
	$('#cell2_'+'add').height(s);
	$('#cell3_'+'add').height(s);
	$('#cell4_'+'add').height(s);
	$('#cell5_'+'add').height(s);
	$('.table_head').height($('#head_0').height());
}

function teacher_lookup(num){
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

function fankui_tch_pre(num){
	$('body').append('\
		<div class="fankui_tch_pre">\
			请输入预约学号\
			<br>\
			<input class="feedback_studentid"></input>\
			<br>\
			<button type="button" onclick="check_studentid('+num+');">确定</button>\
			<button type="button" onclick="$(\'.fankui_tch_pre\').remove();">取消</button>\
		</div>\
	');
	optimize('.fankui_tch_pre');
}



function check_studentid(num){
	// var len=$('.feedback_studentid').length;
	var postdata={
		appId:student_table_data[num].appId
		// studentId:$('.feedback_studentid')[len-1].value
	};

	$.ajax({
		type:'POST',
		async:false,
		url:'appointment/teacherCheck',
		data:postdata,
		dataType:'json',
		success:function(data){
			if (data.state=='SUCCESS'){
				// $('.fankui_tch_pre').remove();
				feedback=data;
				fankui_tch(num);
				return true;
			}
			else
			{
				alert("尝试获取数据失败，请联系管理员");
				return false;
			}
		}
	});
}


function fankui_tch(num){
	$('body').append('\
	<div class="fankui_tch" id="fankui_tch_'+num+'" style="font-size:11px;text-align:left;top:100px;height:380px;width:200px;left:100px">\
			咨询师反馈表<br>\
			您的姓名：<input id="fb_teacherName" style="padding:0px;"></input><br>\
			工作证号：<input id="fb_teacherId" style="padding:0px;"></input><br>\
			来访者姓名：<input id="fb_studentName" style="padding:0px;"></input><br>\
			来访者问题描述：<br>\
			<textarea id="fb_problem" style="padding:0px;"></textarea><br>\
			咨询师提供的问题解决方法：<br>\
			<textarea id="fb_solution" style="padding:0px;"></textarea><br>\
			对中心的工作建议：<br>\
			<textarea id="fb_advice" style="padding:0px;"></textarea><br>\
			<button type="button" onclick="if (getFeedbackData('+num+')) teacherPostFeedback(feedbackdata,'+num+');">提交反馈</button>\
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

function teacherPostFeedback(postdata, num){
	$.ajax({
		type:'POST',
		async:false,
		url:'appointment/teacherFeedback',
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

function teacher_add(){
	$('#cell1_add')[0].onclick='';
	$('#cell1_add')[0].innerHTML='<input type="text" id="inputDate" style="width:60px" ></input>日<br><input style="width:20px" id="time1"></input>时<input style="width:20px" id="minute1"></input>分' + 
		'<br><input style="width:20px" id="time2"></input>时<input style="width:20px" id="minute2"></input>分';
	$('#cell2_add')[0].innerHTML='<input id="name" style="width:80px" value="' + teacher + '"></input>';
	$('#cell3_add')[0].innerHTML='<button type="button" onclick="add_commit();">确认</button>';
	$('#cell4_add')[0].innerHTML='<button type="button" onclick="window.location.reload();">取消</button>';
	$('#cell5_add')[0].innerHTML='<input id="mobile" style="width:120px" value="' + teacherMobile + '"></input>';

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
	optimize('add');
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
		startTime:startTime,
		endTime:endTime,
		teacher:$("#name").val(),
		teacherMobile:$('#mobile').val()
	};
	$.ajax({
		type:'POST',
		async:false,
		url:'appointment/teacher/addAppointment',
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

function teacher_delete(){
	var postdata={appIds:[]};
	for (var i in student_table_data)
	{
		if ($('#checkbox_'+i)[0].checked)
		{
			postdata.appIds.push(student_table_data[i].appId);
		}
	}
//	console.log(postdata);
	$.ajax({
		type:'POST',
		async:false,
		url:'appointment/teacher/removeAppointment',
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

function teacher_cancel(){
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
		url:'appointment/teacher/cancelAppointment',
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

function teacher_edit(num){
	$('#cell1_'+num).height(68);
	$('#cell1_'+num)[0].onclick='';
	// $('#cell1_'+num)[0].innerHTML='<input type="text" id="inputDate" style="width:74px;border:1px solid #ccc;padding:0px;margin:0px;" ></input>日，<input style="width:40px;border:1px solid #ccc;padding:0px;margin:0px;" id="time1"></input>时至<input style="width:40px;border:1px solid #ccc;padding:0px;margin:0px;" id="time2"></input>时';
	$('#cell1_'+num)[0].innerHTML='<input type="text" id="inputDate" style="width:60px" ></input>日<br><input style="width:20px" id="time1"></input>时<input style="width:20px" id="minute1"></input>分' + 
		'<br><input style="width:20px" id="time2"></input>时<input style="width:20px" id="minute2"></input>分';
	$('#cell2_'+num)[0].innerHTML='<input id="name'+num+'"  style="width:38px;border:1px solid #ccc;padding:0px;margin:0px;" value="'+student_table_data[num].teacher+'">';
	$('#cell3_'+num)[0].innerHTML='<button type="button" onclick="edit_commit('+num+');">确认</button>';
	$('#cell4_'+num)[0].innerHTML='<button type="button" onclick="window.location.reload();">取消</button>';
	$('#cell5_'+num)[0].innerHTML='<input id="mobile'+num+'"  style="width:70px;border:1px solid #ccc;padding:0px;margin:0px;" value="'+student_table_data[num].teacherMobile+'">';
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

function parseTime(t){
	var s=t.split(' ');
	return s[0][0]+s[0][1]+s[0][2]+s[0][3]+'-'+s[0][4]+s[0][5]+'-'+s[0][6]+s[0][7]+' '+s[0][8]+s[0][9]+':'+s[0][10]+s[0][11];
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
		teacherMobile:$('#mobile'+num).val()
	};
	$.ajax({
		type:'POST',
		async:false,
		url:'appointment/teacher/editAppointment',
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

function teacher_logout() {
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
		url:'appointment/teacher/viewAppointments',
		dataType:'json',
		success:function(data){
			if (data.state=='SUCCESS'){
				addInfo_tch(data.array);
				student_table_data=data.array;
				teacher = data.teacher;
				teacherMobile = data.teacherMobile;
				optimize();
			} else {
				alert(data.message);
			}
		}
	});
}
