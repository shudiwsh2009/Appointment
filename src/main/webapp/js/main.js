var width=$(window).width();
var height=$(window).height();
var student_table_data=[
];

function addInfo(data){
	student_table_data=data;
	for (var i in data){
		$('#col1').append('<div class="table_cell" id="cell1_'+i+'">'+student_table_data[i].startTime+'至<br>'+student_table_data[i].endTime+'</div>');
		$('#col2').append('<div class="table_cell" id="cell2_'+i+'">'+student_table_data[i].teacher+'</div>');
		if (student_table_data[i].status=='APPOINTED'){
			$('#col3').append('<div class="table_cell" id="cell3_'+i+'"><button type="button" id="cell3b_'+i+'" disabled="true">已预约</button></div>');
		}else if (student_table_data[i].status=='FEEDBACK'){
			$('#col3').append('<div class="table_cell" id="cell3_'+i+'"><button type="button" id="cell3b_'+i+'" onclick="fankui_stu_pre('+i+')">反馈</button></div>');
		}else{
			$('#col3').append('<div class="table_cell" id="cell3_'+i+'"><button type="button" id="cell3b_'+i+'" onclick="yuyue_stu_pre('+i+')">预约</button></div>');
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
		if (maxheight<$('#cell2_'+i).height()) maxheight=$('#cell3_'+i).height();
		if (maxheight<$('#cell3_'+i).height()) maxheight=$('#cell3_'+i).height();

		$('#cell1_'+i).height(maxheight);
		$('#cell2_'+i).height(maxheight);
		$('#cell3_'+i).height(maxheight);
		if (i%2==1){
			$('#cell1_'+i).css('background-color','white');
			$('#cell2_'+i).css('background-color','white');
			$('#cell3_'+i).css('background-color','white');
		}else{
			$('#cell1_'+i).css('background-color','#f3f3ff');
			$('#cell2_'+i).css('background-color','#f3f3ff');
			$('#cell3_'+i).css('background-color','#f3f3ff');
		}
	}
	$(t).css('left',(width-$(t).width())/2-11+'px');
	$(t).css('top',(height-$(t).height())/2-11+'px');
}

function fankui_stu_pre(num){
	$('body').append('\
		<div class="fankui_stu_pre">\
			请输入预约学号\
			<br>\
			<input class="feedback_studentid"></input>\
			<br>\
			<button type="button" onclick="check_studentid('+num+');">确定</button>\
			<button type="button" onclick="$(\'.fankui_stu_pre\').remove();">取消</button>\
		</div>\
	');
	optimize('.fankui_stu_pre');
}

function check_studentid(num){
	var len=$('.feedback_studentid').length;
	var postdata={
		appId:student_table_data[num].appId,
		studentId:$('.feedback_studentid')[len-1].value
	};
	console.log(postdata);
	//studentid_now=$('.feedback_studentid')[len-1].value;

	//return true;

	$.ajax({
		type:'POST',
		async:false,
		url:'appointment/studentCheck',
		dataType:'json',
		data:postdata,
		success:function(data){
			if (data.state=='SUCCESS'){
				$('.fankui_stu_pre').remove();
				 feedback=data;
				 fankui_stu(num);
				return true;
			}
		}
	});/*
	$.ajax({
		type:'POST',
		async:false,
		url:'appointment/studentCheck',
		data:postdata,
		dataType:'json',
		success:function(data){
			if (data.state=='SUCCESS'){
				alert('success');
				return true;
			}
			else
				return false;
		}
	});*/
}

function fankui_stu(num){
	$('body').append('\
		<div class="fankui_stu" id="fankui_stu_'+num+'" style="text-align:left;font-size:8px;position:absolute;height:606px;top:110px;left:5px;">\
		<div style="text-align:center;font-size:23px">咨询效果反馈问卷</div><br>\
			·姓名：<input id="fb_name"></input><br>\
			·咨询问题：<br><textarea id="fb_problem" style="width:250px;margin-left:20px"></textarea><br>\
			·我和咨询师对咨询目标的看法是一致的<select id="fb_q1"><option value="">请选择</option><option value="非常同意">非常同意</option><option value="一般">一般</option><option value="不同意">不同意</option></select><br>\
			·我对自己有新的认识<select id="fb_q2"><option value="">请选择</option><option value="非常同意">非常同意</option><option value="一般">一般</option><option value="不同意">不同意</option></select><br>\
			·我对如何解决面临的问题有了新的思路<select id="fb_q3"><option value="">请选择</option><option value="非常同意">非常同意</option><option value="一般">一般</option><option value="不同意">不同意</option></select><br>\
			·了解到有关这一问题的政策信息与知识<select id="fb_q4"><option value="">请选择</option><option value="非常同意">非常同意</option><option value="一般">一般</option><option value="不同意">不同意</option></select><br>\
			·我很清楚接下来需要干什么<select id="fb_q5"><option value="">请选择</option><option value="非常同意">非常同意</option><option value="一般">一般</option><option value="不同意">不同意</option></select><br>\
			·我掌握了认识自己的方法<select id="fb_q6"><option value="">请选择</option><option value="非常同意">非常同意</option><option value="一般">一般</option><option value="不同意">不同意</option></select><br>\
			·我掌握了如何获取更多信息的方法<select id="fb_q7"><option value="">请选择</option><option value="非常同意">非常同意</option><option value="一般">一般</option><option value="不同意">不同意</option></select><br>\
			·我掌握了如何提升自身能力的方法<select id="fb_q8"><option value="">请选择</option><option value="非常同意">非常同意</option><option value="一般">一般</option><option value="不同意">不同意</option></select><br>\
			·我意识到要对自己的学习与发展负责<select id="fb_q9"><option value="">请选择</option><option value="非常同意">非常同意</option><option value="一般">一般</option><option value="不同意">不同意</option></select><br>\
			·我将尝试将咨询中的收获应用于生活中<select id="fb_q10"><option value="">请选择</option><option value="非常同意">非常同意</option><option value="一般">一般</option><option value="不同意">不同意</option></select><br>\
			·通过本次咨询，让我对解决问题更有信心了<select id="fb_q11"><option value="">请选择</option><option value="非常同意">非常同意</option><option value="一般">一般</option><option value="不同意">不同意</option></select><br>\
			·我喜欢我的咨询师，下次还会来预约咨询<select id="fb_q12"><option value="">请选择</option><option value="非常同意">非常同意</option><option value="一般">一般</option><option value="不同意">不同意</option></select><br>\
			·请为本次咨询打分（0~100）：<input id="fb_score" style="width:50px;"></input><br>\
			·感受和建议：<br><textarea id="fb_feedback" style="width:250px;margin-left:20px"></textarea><br>\
			<div style="text-align:center;">\
			<button type="button" onclick="if (getFeedbackData('+num+')) studentPostFeedback(feedbackdata,'+num+');else alert(\'请完整填写\');">提交反馈</button>\
			<button type="button" onclick="$(\'.fankui_stu\').remove();">取消</button></div>\
		</div>\
	');
	if (feedback.choices!=""){
		$('#fb_name').val(feedback.name);
		$('#fb_problem').val(feedback.problem);
		$('#fb_score').val(feedback.score);
		$('#fb_feedback').val(feedback.feedback);
		for (i=1;i<=12;i++){
			var t=feedback.choices[i-1];
			$('#fb_q'+i).val(t=='A'?'非常同意':(t=='B'?'一般':'不同意'));
		}
	}
//	optimize('.fankui_stu');
}

function fankui_stu_confirm(num){
	$('.fankui_stu').remove();
	//$('#cell3b_'+num).attr('disabled','true');
	//$('#cell3b_'+num).text('已反馈');
	$('body').append('\
		<div class="fankui_stu_success">\
			感谢使用中心咨询预约系统！<br>\
			<button type="button" onclick="$(\'.fankui_stu_success\').remove();">确定</button>\
		</div>\
	');
	optimize('.fankui_stu_success');
}

function yuyue_stu_pre(num){
	$('body').append('\
		<div class="yuyue_stu_pre">\
			确定预约后请准确填写个人信息，方便学习与发展中心老师与你取得联系\
			<br>\
			<button type="button" onclick="$(\'.yuyue_stu_pre\').remove();yuyue_stu('+num+');">立即预约</button>\
			<button type="button" onclick="$(\'.yuyue_stu_pre\').remove();">暂不预约</button>\
		</div>\
	');
	optimize('.yuyue_stu_pre');
}

function yuyue_stu(num){
	$('body').append('\
		<div class="yuyue_stu" id="yuyue_stu_'+num+'" style="text-align:left;height:370px">\
		<div style="text-align:center;font-size:23px">咨询申请表</div><br>\
			·姓名：<input id="ap_name"></input><br>\
			·性别：<select id="ap_gender">\
 			 <option value ="男">男</option>\
 			 <option value ="女">女</option>\
			</select><br>\
			·学号：<input id="ap_id"></input><br>\
			·院系：<input  id="ap_school"></input><br>\
			·生源地：<input id="ap_hometown"></input><br>\
			·手机：<input id="ap_mobile"></input><br>\
			·Email：<input id="ap_email"></input><br>\
			·以前曾做过学习发展咨询、职业咨询或心理咨询吗？<select id="ap_experience">\
 			 <option value ="是">是</option>\
 			 <option value ="否">否</option>\
			</select><br>\
			·请概括您最想要咨询的问题:<br>\
			<textarea id="ap_problem"  style="width:250px;margin-left:20px"></textarea>\
			<br>\
			<div style="text-align:center;">\
			<button type="button" onclick="if (getAppointmentData('+num+')) studentPostAppointment(appointmentdata, '+num+');else alert(\'请完整填写\');">确定预约</button>\
			<button type="button" onclick="$(\'.yuyue_stu\').remove();">取消</button></div>\
		</div>\
	');
	optimize('.yuyue_stu');
}

function yuyue_stu_confirm(num){
	$('.yuyue_stu').remove();
	$('#cell3b_'+num).attr('disabled','true');
	$('#cell3b_'+num).text('已预约');
	$('body').append('\
		<div class="yuyue_stu_success">\
			您已预约<br>'+student_table_data[num].startTime+'<br>'+student_table_data[num].endTime+'。请等待中心老师与你联系!\
			<br>\
			感谢使用中心咨询预约系统！<br>\
			<button type="button" onclick="$(\'.yuyue_stu_success\').remove();">确定</button>\
		</div>\
	');
	optimize('.yuyue_stu_success');
}

function combine_time(s1, s2)//合并两个时间，比如1月1日12:00-2日13:00
{
	var t1=new Date(s1);
	var t2=new Date(s2);
    var u='/';
    var y1=t1.getFullYear();
    var y2=t2.getFullYear();
    var m1=1+t1.getMonth();
    var m2=1+t2.getMonth();
    var d1=t1.getDate();
    var d2=t2.getDate();
    var h1=t1.getHours()+''; h1=h1.length==1?'0'+h1:h1;
    var h2=t2.getHours()+''; h2=h2.length==1?'0'+h2:h2;
    var mi1=t1.getMinutes()+''; mi1=mi1.length==1?'0'+mi1:mi1;
    var mi2=t2.getMinutes()+''; mi2=mi2.length==1?'0'+mi2:mi2;
    var day=['Sun.','Mon.','Tue.','Wed.','Thur.','Fri.','Sat.'];
    var day1=day[t1.getDay()];
    var day2=day[t2.getDay()];
    if (y1!=y2)
        return y1+u+m1+u+d1+' '+h1+':'+mi1+'－'+y2+u+m2+u+d2+' '+h2+':'+mi2;
    if (m1!=m2)
        return m1+'月'+d1+'日 '+h1+':'+mi1+'－'+m2+'月'+d2+'日 '+h2+':'+mi2;
    if (d1!=d2)
        return m1+'月'+d1+'日 '+h1+':'+mi1+'－'+d2+'日 '+h2+':'+mi2;
    return m1+'月'+d1+'日 '+h1+':'+mi1+'－'+h2+':'+mi2;
}

function getData(){
	$.ajax({
		type:'GET',
		async:false,
		url:'appointment/viewAppointments',
		dataType:'json',
		success:function(data){
			if (data.state=='SUCCESS'){
				addInfo(data.array);
				optimize();
			}
		}
	});


}

function getAppointmentData(num){
	var t;
	t=$('#ap_name').val();
	if (t!='') appointmentdata.name=t;
		else return false;
	t=$('#ap_gender').val();
	if (t!='') appointmentdata.gender=t;
		else return false;
	t=$('#ap_id').val();
	if (t!='') appointmentdata.studentId=t;
		else return false;
	t=$('#ap_school').val();
	if (t!='') appointmentdata.school=t;
		else return false;
	t=$('#ap_hometown').val();
	if (t!='') appointmentdata.hometown=t;
		else return false;
	t=$('#ap_mobile').val();
	if (t!='') appointmentdata.mobile=t;
		else return false;
	t=$('#ap_email').val();
	if (t!='') appointmentdata.email=t;
		else return false;
	t=$('#ap_problem').val();
	if (t!='') appointmentdata.problem=t;
		else return false;
	t=$('#ap_experience').val();
	if (t!='') appointmentdata.experience=t;
		else return false;
	appointmentdata.appId=''+student_table_data[num].appId;
	return true;
}

function studentPostAppointment(postdata, num){
	$.ajax({
		type:'POST',
		async:false,
		url:'appointment/makeAppointment',
		data:postdata,
		dataType:'json',
		success:function(data){
			if (data.state=='SUCCESS'){
				yuyue_stu_confirm(num);
			}
		}
	});
}

function getFeedbackData(num){
	var t;
	t=$('#fb_name').val();
	if (t!='') feedbackdata.name=t;
		else return false;
	t=$('#fb_problem').val();
	if (t!='') feedbackdata.problem=t;
		else return false;
	t=$('#fb_score').val();
	if (t!='') feedbackdata.score=t;
		else return false;
	t=$('#fb_feedback').val();
	if (t!='') feedbackdata.feedback=t;
		else return false;
	t='';
	for (var i=1;i<=12;i++){
		var s=$('#fb_q'+i).val();
		if (s=='') return false;
		t+=(s=='非常同意'?'A':(s=='一般'?'B':'C'));
	}
	feedbackdata.choices=t;
	feedbackdata.appId=student_table_data[num].appId;
	feedbackdata.studentId=''+studentid_now;
	return true;
}

function studentPostFeedback(postdata, num){
	$.ajax({
		type:'POST',
		async:false,
		url:'appointment/studentFeedback',
		data:postdata,
		dataType:'json',
		success:function(data){
			if (data.state=='SUCCESS'){
				fankui_stu_confirm(num);
			}
		}
	});
}
