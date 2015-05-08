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

function addInfo_tch(data){
	student_table_data=data;
	for (var i in data){
		$('#col1').append('<div class="table_cell" id="cell1_'+i+'">'+student_table_data[i].startTime+'至'+student_table_data[i].endTime+'</div>');
		$('#col2').append('<div class="table_cell" id="cell2_'+i+'">'+student_table_data[i].teacher+'</div>');
		if (student_table_data[i].status=='APPOINTED'){
			$('#col3').append('<div class="table_cell" id="cell3_'+i+'"><button type="button" id="cell3b_'+i+'" disabled="true">已预约</button></div>');
		}else if (student_table_data[i].status=='FEEDBACK'){
			$('#col3').append('<div class="table_cell" id="cell3_'+i+'"><button type="button" id="cell3b_'+i+'" onclick="fankui_tch_pre('+i+')">反馈</button></div>');
		}else{
			$('#col3').append('<div class="table_cell" id="cell3_'+i+'">未预约</div>');
		}
	}
}

function optimize(t){
	$('#col1').width(width*0.48);
	$('#col2').width(width*0.22);
	$('#col3').width(width*0.24);
	$('#col1').css('margin-left',width*0.02+'px')

	for (var i in student_table_data){
		var maxheight=$('#cell1_'+i).height();
		if (maxheight<$('#cell2_'+i).height()) maxheight=$('#cell2_'+i).height();
		if (maxheight<$('#cell3_'+i).height()) maxheight=$('#cell3_'+i).height();

		$('#cell1_'+i).height(maxheight);
		$('#cell2_'+i).height(maxheight);
		$('#cell3_'+i).height(maxheight);
	}
	$(t).css('left',(width-$(t).width())/2-11+'px');
	$(t).css('top',(height-$(t).height())/2-11+'px');
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
	var len=$('.feedback_studentid').length;
	var postdata={
		appId:student_table_data[num].appId,
		studentId:$('.feedback_studentid')[len-1].value
	};

	$.ajax({
		type:'POST',
		async:false,
		url:'appointment/teacherCheck',
		data:postdata,
		dataType:'json',
		success:function(data){
			if (data.state=='SUCCESS'){
				$('.fankui_tch_pre').remove();
				feedback=data;
				fankui_tch(num);
				return true;
			}
			else
			{
				alert("用户名或密码错误！");
				return false;
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
	$('#cell3b_'+num).attr('disabled','true');
	$('#cell3b_'+num).text('已反馈');
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
				addInfo_tch(data.array);
				student_table_data=data.array;
				optimize();
			}
		}
	});
}
