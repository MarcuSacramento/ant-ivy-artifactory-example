$('#dataNascimento').blur(function(){
	if ($(this).val()!=""){
		$('#anoNascimento').val('').attr('readonly', 'readonly').css('background-color','#CCC');
	}
	if ($(this).val()==""){
		$('#anoNascimento').removeAttr('readonly').css('background-color','#FFF');
	}
});