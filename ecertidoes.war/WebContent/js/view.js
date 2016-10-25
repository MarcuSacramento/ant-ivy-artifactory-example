function novoCadastro() {
	document.forms[0].action = '/certidoesDNN_AN/cadastroCertidoes/novoCadastro.do';
	document.forms[0].submit();
}

function principal() {
	document.forms[0].action = '/certidoesDNN_AN/login/sair.do';
	document.forms[0].submit();
}
	    	
function limparLogin() {
	document.forms[0].login.value = '';
	document.forms[0].senha.value = '';
}

function limparCadastro() {
	document.forms[0].action = '/certidoesDNN_AN/cadastroCertidoes/limpar.do';
	document.forms[0].submit();
}

function pesquisarCertidoes() {
	document.forms[0].action = '/certidoesDNN_AN/pesquisaCertidoes/pesquisarTela.do';
	document.forms[0].submit();
}

function limparPesquisarCertidoes() {
	document.forms[0].action = '/certidoesDNN_AN/pesquisaCertidoes/limpar.do';
	document.forms[0].submit();
}


function verificarLimite(campo, limite) {
	var s = new String(campo.value);
	var tamanho = s.length;
	if (tamanho > limite-1) {
		campo.value = s.substring(0,limite-1);
	}
}
	    	
//-----------------------------------------------------
//Funcao: validaCampos
//Sinopse: Verifica a obrigatoriedade dos campos definidos no array "campos" declarado no formulario
//Parametro:
//   nenhum
//Retorno: nenhum
//Autor: Simone Azevedo
//-----------------------------------------------------
function validaCampos() {

	var validaEsse = true;
	var validaTodos = true;
	var campoValido;
	var campoInvalido;
	var divInvalido;
	var txt = "";

	var proibido = new Array('N?O INFORMADA','N?O INFORMADO', 'N?O CADASTRADO', 'INEXISTENTE','OMITIDO','A  DECLARAR', 'N?O DECLARADO', 'N?O CONSTA', 'N?O PREENCHIDO');

	for( var i = 0; i < campos.length; i++ ) {
	   validaEsse = true;
           try { 
		campoValido = document.getElementById( campos[i] );	
		divInvalido = document.getElementById( campos[i] + 'Invalido'  );
		txt = trim(campoValido.value);
		if ( txt.length < 1) 
			validaEsse = false;
		else {
			for( var j = 0; j < proibido.length; j++ ) {
				if ( txt.toUpperCase() == proibido[j]) {
					validaEsse = false;
					break;
				}
			}
		}
		if (validaEsse) {
			for( var j = 0; j < camposMin.length; j++ ) {
				if ( (campos[i] == camposMin[j]) && (txt.length < valoresMin[j])) {
					validaEsse = false;
					break;
				}
			}
		}
  	   }catch(e) {
  	   	alert(e.message + '\n' + campos[i]);
		validaEsse = true;
	   }

	   if (!validaEsse) {
			divInvalido.style.display = '';
			campoValido.style.borderColor = "#ff0000";
			validaTodos = validaEsse;
	   }
	   else {
			divInvalido.style.display = 'none';
			campoValido.style.borderColor = "#2e358e";
	   }


				
	}
	

	if (!validaTodos){
		window.scrollTo(0,0);

	}

	return validaTodos;

}



function getCheckedValue(radioObj) {

	if(!radioObj)
		return "";
	var radioLength = radioObj.length;
	if(radioLength == undefined)
		if(radioObj.checked)
			return radioObj.value;
		else
			return "";
	for(var i = 0; i < radioLength; i++) {
		if(radioObj[i].checked) {
			return radioObj[i].value;
		}
	}
	return "";
}

// set the radio button with the given value as being checked
// do nothing if there are no radio buttons
// if the given value does not exist, all the radio buttons
// are reset to unchecked
function setCheckedValue(radioObj, newValue) {
	if(!radioObj)
		return;
	var radioLength = radioObj.length;
	if(radioLength == undefined) {
		radioObj.checked = (radioObj.value == newValue.toString());
		return;
	}
	for(var i = 0; i < radioLength; i++) {
		radioObj[i].checked = false;
		if(radioObj[i].value == newValue.toString()) {
			radioObj[i].checked = true;
		}
	}
}

function validaRadios () {
	var validaTodos = true;
	var validaEsse = true;

	for( var i = 0; i < radios.length; i++ ) {
		validaEsse = true;
		var radioNome = radios[i];
		var radioValor = '';
		var radioObj = document.forms[0].elements[radioNome];
		var divInvalido = document.getElementById( radios[i] + 'Invalido');
		radioValor = getCheckedValue(radioObj);
		if (radioValor == "" ){
			divInvalido.style.display = '';
			validaEsse = false;
			validaTodos = false;
		}
		else{
			divInvalido.style.display = 'none';		
			
		}
	}

	if (!validaTodos)
		window.scrollTo(0,0);

	return validaTodos;
}

function validaCombos () {
    var validaTodos = true;
    var validaEsse = true;

    try {
        for( var i = 0; i < combos.length; i++ ) {
            validaEsse = true;
            var comboNome = combos[i];
            var comboValor = '';
            var comboObj = document.getElementById(comboNome);
            var valor = comboObj.options[comboObj.selectedIndex].value;
            var divInvalido = document.getElementById( comboNome + 'Invalido');

            if (valor == "" ){
                divInvalido.style.display = '';
                validaEsse = false;
                validaTodos = false;
            } else{
                divInvalido.style.display = 'none';     
            }
        }   

        if (!validaTodos)
            window.scrollTo(0,0);
    } catch(e){
    	alert(e.message);
        validaTodos = true;
    }

    return validaTodos;
}

function validaDuplicadas (txt) {
            var encontrouLetraDuplicada = false;

            if (txt != '')
            {
                for (var i = 0; i < txt.length; i++)
                {
                    if (i < txt.length - 1)
                    {
                        var letraAtual = txt.substr(i, 1);

                        if (txt.indexOf(letraAtual+letraAtual+letraAtual, 0) > -1)
                        {
                            encontrouLetraDuplicada = true;
                            break;
                        }
                    }
                }
            }


	    return !encontrouLetraDuplicada;
}



//-----------------------------------------------------
//Funcao: limpar
//Sinopse: Limpa todos os campos do formul?rio e retoma os layouts 
//As divs que ser?o escondidas ser?o aquelas especificadas nos arrays "campos" e "outrasDiv"
//Parametro:
//   form : Formulario que ser? limpo
//Retorno: nenhum
//Autor: Simone Azevedo
//-----------------------------------------------------
function limpar(form) {
	form.reset(); 

	try {
		for( var i = 0; i < campos.length; i++ ) {
			campoValido = document.getElementById( campos[i] );		
			divInvalido = document.getElementById( campos[i] + 'Invalido' );
			divInvalido.style.display = 'none';
			campoValido.style.borderColor = "#2e358e";
			
		}

		for( var i = 0; i < outrasDiv.length; i++ ) {
			divInvalido = document.getElementById( outrasDiv[i] );
			divInvalido.style.display = 'none';
		}

		for( var i = 0; i < radios.length; i++ ) {
			divInvalido = document.getElementById( radios[i] + 'Invalido' );
			divInvalido.style.display = 'none';
		}
	}
	catch(e){
  	   	alert(e.message + '\n' + campos[i]);
	}
}


//-----------------------------------------------------
//Funcao: ApenasNumeros
//Sinopse: Valida se o caracter preenchido ? num?rico
//Parametro:
//   teclaPress : Evento
//Retorno: Booleano
//Autor: Simone Azevedo
//-----------------------------------------------------

function ApenasNumeros(event) {

	var tecla =  event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;

	ctrl = event.ctrlKey;

	if ( ( (tecla >= 32) && (tecla <= 47) ) || ( ( tecla >= 58) && (tecla <= 126) ))
		return false;
	
	else
		return true;
	

}
//-----------------------------------------------------
//Funcao: ApenasAlfa
//Sinopse: Valida se o caracter preenchido ? alfa
//Parametro:
//   campo : Campo que sofrer? valida??o
//   event : Evento
//Retorno: Booleano
//Autor: Simone Azevedo
//-----------------------------------------------------

function ApenasAlfa(campo,event) {

	var tecla =  event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;

	if ( (tecla >= 33) && (tecla <= 64) )
		return false;
	
	

	//Triplicatas
	var s = campo.value;
	tam = s.length;

	if ((tam >= 2) && (tecla != 73)) {
		
		var code1 = s.charCodeAt(tam-2);
		var code2 = s.charCodeAt(tam-1);

		if ((tecla == code1) && (tecla == code2)) {
			return false;	
		}
	}
	return true;
}

//-----------------------------------------------------
//Funcao: Miusculas
//Sinopse: coloca todo o campo em letras maiusculas
//Parametro:
//   campo : Campo que sofrer? valida??o
//Retorno: nenhum
//Autor: Simone Azevedo
//-----------------------------------------------------

function Maiusculas(campo) {

	campo.value = campo.value.toUpperCase();
}

//-----------------------------------------------------
//Funcao: Mascara
//Sinopse: Coloca o campo na m?scara de acordo com o tipo passado
//Parametro:
//   tipo: tipo de mascara que deve ser feita
//   campo: campo que sofrer? m?scara
//   teclapress: evento
//Retorno: Booleano
//Autor: Simone Azevedo
//-----------------------------------------------------

function Mascara(tipo, campo, teclaPress) {

	var tecla =  teclaPress.keyCode ? teclaPress.keyCode : teclaPress.which ? teclaPress.which : teclaPress.charCode;

	var s = new String(campo.value);
	// Remove todos os caracteres ? seguir: ( ) / - . e espa?o, para tratar a string denovo.
	s = s.replace(/(\.|\(|\)|\/|\-| )+/g,'');
	ss = s.replace(/(a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z)+/gi,'');

	if (s.length != ss.length)
		campo.value = ss;

	s = ss;
	tam = s.length + 1;

 
	ctrl = teclaPress.ctrlKey;
	
	if ( (ApenasNumeros(teclaPress)) || ctrl){

		switch (tipo)
		{
		case 'CPF' :
			if (tam > 3 && tam < 7)
				campo.value = s.substr(0,3) + '.' + s.substr(3, tam);
			if (tam >= 7 && tam < 10)
				campo.value = s.substr(0,3) + '.' + s.substr(3,3) + '.' + s.substr(6,tam-6);
			if (tam >= 10 && tam < 13)
				campo.value = s.substr(0,3) + '.' + s.substr(3,3) + '.' + s.substr(6,3) + '-' + s.substr(9,tam-9);

		break;
 
		case 'CNPJ' :
 
			if (tam > 2 && tam < 6)
				campo.value = s.substr(0,2) + '.' + s.substr(2, tam);
			if (tam >= 6 && tam < 9)
				campo.value = s.substr(0,2) + '.' + s.substr(2,3) + '.' + s.substr(5,tam-5);
			if (tam >= 9 && tam < 13)
				campo.value = s.substr(0,2) + '.' + s.substr(2,3) + '.' + s.substr(5,3) + '/' + s.substr(8,tam-8);
			if (tam >= 13 && tam < 16)
				campo.value = s.substr(0,2) + '.' + s.substr(2,3) + '.' + s.substr(5,3) + '/' + s.substr(8,4)+ '-' + s.substr(12,tam-12);
		break;
 
		case 'TEL' :
			if (tam > 2 && tam < 4)
				campo.value = '(' + s.substr(0,2) + ') ' + s.substr(2,tam);
			if (tam >= 7 && tam < 12)
				campo.value = '(' + s.substr(0,2) + ') ' + s.substr(2,4) + '-' + s.substr(6,tam-6);
		break;
 
 		case 'TEL_SEM_DDD' :
			if (tam == 5)
				campo.value = s.substr(0,4) + '-' + s.substr(5, tam);
		break;
		
		case 'CEP' :
			if (tam > 2 && tam < 6)
				campo.value = s.substr(0,2) + '.' + s.substr(2, tam);
			if (tam >= 6 && tam < 10)
				campo.value = s.substr(0,2) + '.' + s.substr(2,3) + '-' + s.substr(5,tam-5);
			
			
		break;

		case 'DATA' :
			if (tam > 2 && tam < 4)
				campo.value = s.substr(0,2) + '/' + s.substr(2, tam);
			if (tam > 4 && tam < 12)
				campo.value = s.substr(0,2) + '/' + s.substr(2,2) + '/' + s.substr(4,tam-4);
			if (tam == 5 && tecla==8)				
				campo.value = s.substr(0,2) + '/' + s.substr(2,2)				
			if (tam <= 4 && tecla==8)				
				campo.value = s.substr(0,tam);		
		break;
		
		case 'CERTIDAO' :
			if(tecla != 9) { //TAB
				if (tam == 5)
					campo.value = s.substr(0,1) + '/' + s.substr(1,tam);
				if (tam >= 6 && tam <= 7)
					campo.value = s.substr(0,tam-4) + '/'+ s.substr(tam-4,tam);
				if (tam >= 8 && tam <=10)
					campo.value = s.substr(0,tam-7) + '.' + s.substr(tam-7,3) + '/'+ s.substr(tam-4,tam);
				if (tam >= 11 && tam <=13)				
					campo.value = s.substr(0,tam-10) + '.' + s.substr(tam-10,3) + '.' + s.substr(tam-7,3) + '/'+ s.substr(tam-4,tam);
				if (tam >= 14 && tam <= 16) 
					campo.value = s.substr(0, tam-13) + '.' + s.substr(tam-13,3) + '.' + s.substr(tam-10,3) + '.' + s.substr(tam-7,3) + '/'+ s.substr(tam-4,tam);			
			}	
			break;
			
		case 'HORA' :
			if (tam > 2 && tam < 4)
				campo.value = s.substr(0,2) + ':' + s.substr(2, tam);
			if (tam == 3 && tecla==8)				
				campo.value = s.substr(0,2);			
			if (tam <= 2 && tecla==8)				
				campo.value = s.substr(0,tam);					
			break;
	
		case 'MOEDA' :
			return MascaraMoeda(campo,'.',',',teclaPress);
		break;
		}

		return true;
	}
	else 
	{
		return false;

	}
}

//-----------------------------------------------------
//Funcao: MascaraMoeda
//Sinopse: Mascara de preenchimento de moeda
//Parametro:
//   objTextBox : Objeto (TextBox)
//   SeparadorMilesimo : Caracter separador de mil?simos
//   SeparadorDecimal : Caracter separador de decimais
//   e : Evento
//Retorno: Booleano
//-----------------------------------------------------
function MascaraMoeda(objTextBox, SeparadorMilesimo, SeparadorDecimal, e){
    var sep = 0;
    var key = '';
    var i = j = 0;
    var len = len2 = 0;
    var strCheck = '0123456789';
    var aux = aux2 = '';
    var whichCode = (window.Event) ? e.which : e.keyCode;
    if (whichCode == 13) return true;
    key = String.fromCharCode(whichCode); // Valor para o c?digo da Chave
    if (strCheck.indexOf(key) == -1) return false; // Chave inv?lida
    len = objTextBox.value.length;
    if (len > objTextBox.maxLength) return false; //Invalida se preencher mais do que o maxLength
    for(i = 0; i < len; i++)
        if ((objTextBox.value.charAt(i) != '0') && (objTextBox.value.charAt(i) != SeparadorDecimal)) break;
    aux = '';
    for(; i < len; i++)
        if (strCheck.indexOf(objTextBox.value.charAt(i))!=-1) aux += objTextBox.value.charAt(i);
    aux += key;
    len = aux.length;
    if (len == 0) objTextBox.value = '';
    if (len == 1) objTextBox.value = '0'+ SeparadorDecimal + '0' + aux;
    if (len == 2) objTextBox.value = '0'+ SeparadorDecimal + aux;
    if (len > 2) {
        aux2 = '';
        for (j = 0, i = len - 3; i >= 0; i--) {
            if (j == 3) {
                aux2 += SeparadorMilesimo;
                j = 0;
            }
            aux2 += aux.charAt(i);
            j++;
        }
        objTextBox.value = '';
        len2 = aux2.length;
        for (i = len2 - 1; i >= 0; i--)
        objTextBox.value += aux2.charAt(i);
        objTextBox.value += SeparadorDecimal + aux.substr(len - 2, len);
    }
    return false;

}

//-----------------------------------------------------
//Funcao: TRIM
//Sinopse: retira espa?os da string
//Parametro:
//   stringToTrim : String que sofrer? retirada de espa?os
//Retorno: string
//Autor: Simone Azevedo
//-----------------------------------------------------
function trim(stringToTrim) {
	return stringToTrim.replace(/^\s+|\s+$/g,"");
}
function ltrim(stringToTrim) {
	return stringToTrim.replace(/^\s+/,"");
}
function rtrim(stringToTrim) {
	return stringToTrim.replace(/\s+$/,"");
}

//validador CPF
function verificarCPF(c){
    var i;
    s = c.replace(".","").replace(".","").replace("-","");
    var c = s.substr(0,9);
    var dv = s.substr(9,2);
    var d1 = 0;
    var v = false;
 
    for (i = 0; i < 9; i++){
        d1 += c.charAt(i)*(10-i);
    }
    if (d1 == 0){
        alert("CPF Inválido")
        v = true;
        c.focus();
        return false;
    }
    d1 = 11 - (d1 % 11);
    if (d1 > 9) d1 = 0;
    if (dv.charAt(0) != d1){
        alert("CPF Inválido")
        v = true;
        c.focus();
        return false;
    }
 
    d1 *= 2;
    for (i = 0; i < 9; i++){
        d1 += c.charAt(i)*(11-i);
    }
    d1 = 11 - (d1 % 11);
    if (d1 > 9) d1 = 0;
    if (dv.charAt(1) != d1){
        alert("CPF Inválido")
        v = true;
        c.focus();
        return false;
    }
//    if (!v) {
//        alert(c + "nCPF Válido")
//    }
}




//retorna TRUE se segunda for maior que primeira
function dataMaior(dt1,dt2) {
    // dt1 e dt2: Devem ser tipo String, para evitar confusao de tipos
    if(typeof dt1 != "string" || typeof dt2 != "string"){
        return false;
    }

    // Instanciamos as datas, para poder usar getTime();
    data1 = new Date(dt1);
    data2 = new Date(dt2);
    if(!data1 || !data2){
        return false;
    }

    // milliSegundos1: ir? conter a quantidade de segundos corridos desde 1/1/1970 0h ate dt1
    milliSegundos1 = data1.getTime();
    // milliSegundos2: ir? conter a quantidade de segundos corridos desde 1/1/1970 0h ate dt2
    milliSegundos2 = data2.getTime();

    // Comparando millisegundos para retornar a conclus?o de quem ? maior que quem...
    if(milliSegundos1 == milliSegundos2){
        return false;
    } else if(milliSegundos1 > milliSegundos2){ 
        return false;
    } else if(milliSegundos1 < milliSegundos2){
        return true;
    } else return false;
}

function mascararProtocoloMj(campo, teclaPress) {
	var tecla =  teclaPress.keyCode ? teclaPress.keyCode : teclaPress.which ? teclaPress.which : teclaPress.charCode;

	var s = new String(campo.value);
	// Remove todos os caracteres ? seguir: ( ) / - . e espa?o, para tratar a string denovo.
	s = s.replace(/(\.|\(|\)|\/|\| )+/g,'');
	ss = s.replace(/(a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z)+/gi,'');

	if (s.length != ss.length)
		campo.value = ss;

	s = ss;
	tam = s.length + 1;
	if(tecla != 9 && tecla != 8) { //TAB
		if (tam >= 6 && tam <= 13)
			campo.value = s.substr(0,5) + '.' + s.substr(5,tam);
		if (tam == 12)
			campo.value = s.substr(0,5) + '.' + s.substr(5,7) + '/' + s.substr(11,tam);					
		if (tam >= 13)
			campo.value = s.substr(0,5) + '.' + s.substr(5,6) + '/' + s.substr(11,tam);					
	}

    return true;
}

function verificarSobrenome(campo) {
    var nomeInformado = trim(campo.value + "");
    if (nomeInformado == '') {
        return true;
    }

    var palavras = nomeInformado.split(" ");
    if (palavras.length == 1) {
        alert("Informe ao menos um sobrenome.");
        campo.focus();
        return false;
    }    
    
    nomeCompleto = campo.value;
    if(nomeCompleto.indexOf(" "+"ou"+" ") != -1 || nomeCompleto.indexOf(" "+"OU"+" ") != -1 || 
       nomeCompleto.indexOf(" "+"Ou"+" ") != -1 || nomeCompleto.indexOf(" "+"oU"+" ") != -1){       
    	alert("Não é permitida a palavra \"OU\".");
        campo.focus();
        return false;
    }    
    
    valor = campo.value;
	for(i=0;i<valor.length;i++){				
	silaba = valor.charAt(i);
	if(silaba =='/'){
		alert('Caracter Inválido ( / )!') 
		campo.focus();
        return false;
	 }
	if(silaba =='\\'){
		alert('Caracter Inválido ( \\ )!') 
		campo.focus();
        return false;
	 }	
	 if(silaba ==','){
		alert('Caracter Inválido ( , )!') 
		campo.focus();
        return false;
	 }
	 if(silaba =='.'){
		alert('Caracter Inválido ( . )!') 
		campo.focus();
        return false;
	 }
	 if(silaba =='+'){
		alert('Caracter Inválido ( + )!') 
		campo.focus();
        return false;
	 }
	 if(silaba =='|'){
		alert('Caracter Inválido ( | )!') 
		campo.focus();
        return false;
	 }
	 if(silaba == ':'){
		alert('Caracter Inválido ( : )!') 
		campo.focus();
        return false;
	 }
	 if(silaba == ';'){
		alert('Caracter Inválido ( ; )!') 
		campo.focus();
        return false;
	 }
	  if(silaba == '?'){
		alert('Caracter Inválido ( ? )!') 
		campo.focus();
        return false;
	 }
	 if(silaba == '#'){
		alert('Caracter Inválido ( # )!') 
		campo.focus();
        return false;
	 }
	 if(silaba == '@'){
		alert('Caracter Inválido ( @ )!') 
		campo.focus();
        return false;
	 }
	 if(silaba == '*'){
		alert('Caracter Inválido ( * )!') 
		campo.focus();
        return false;
	 }
	 if(silaba == '&'){
		alert('Caracter Inválido ( & )!') 
		campo.focus();
        return false;
	 }
	 if(silaba == '!'){
		alert('Caracter Inválido ( ! )!') 
		campo.focus();
        return false;
	 }
	 if(silaba == '$'){
		alert('Caracter Inválido ( $ )!') 
		campo.focus();
        return false;
	 }
	 if(silaba == '¨'){
		alert('Caracter Inválido ( ¨ )!') 
		campo.focus();
        return false;
	 }
	 if(silaba == '('){
		alert('Caracter Inválido ( ( )!') 
		campo.focus();
        return false;
	 }
	 if(silaba == ')'){
		alert('Caracter Inválido ( ) )!') 
		campo.focus();
        return false;
	 }
	 if(silaba == '-'){
		alert('Caracter Inválido ( - )!') 
		campo.focus();
        return false;
	 }
	 if(silaba == '_'){
		alert('Caracter Inválido ( _ )!') 
		campo.focus();
        return false;
	 }
	 if(silaba == '"'){
		alert('Caracter Inválido ( " )!') 
		campo.focus();
        return false;
	 }
	 if(silaba == '>'){
		alert('Caracter Inválido ( > )!') 
		campo.focus();
        return false;
	 }
	 if(silaba == '<'){
		alert('Caracter Inválido ( < )!') 
		campo.focus();
        return false;
	 }
	 if(silaba == '{'){
		alert('Caracter Inválido ( { )!') 
		campo.focus();
        return false;
	 }
	 if(silaba == '}'){
		alert('Caracter Inválido ( } )!') 
		campo.focus();
        return false;
	 }
	 if(silaba == '['){
		alert('Caracter Inválido ( [ )!') 
		campo.focus();
        return false;
	 }
	 if(silaba == ']'){
		alert('Caracter Inválido ( ] )!') 
		campo.focus();
        return false;
	 }
	 if(silaba == '£'){
		alert('Caracter Inválido ( £ )!') 
		campo.focus();
        return false;
	 }
	 if(silaba == '='){
		alert('Caracter Inválido ( = )!') 
		campo.focus();
        return false;
	 }
	 if(silaba == '§'){
		alert('Caracter Inválido ( § )!') 
		campo.focus();
        return false;
	 }
	 if(silaba == '°'){
		alert('Caracter Inválido ( ° )!') 
		campo.focus();
        return false;
	 }
	 if(silaba == '%'){
		alert('Caracter Inválido ( % )!') 
		campo.focus();
        return false;
	 }
	 if(silaba == '¢'){
		alert('Caracter Inválido ( ¢ )!') 
		campo.focus();
        return false;
	 }
   }
	
   return true;
}

function exibirAjuda(img, e) {
    var div = document.getElementById('balaoAjudaEsconde');

    if (div == null || div == undefined) {
        div = document.createElement('div'); 
    }

    var style = 'left:' + (e.clientX + 10)+ 'px;'
    div.innerHTML = img.getAttribute('aviso');
    div.setAttribute('id', 'balaoAjuda');    

    try {
        img.insertAdjacentElement("AfterEnd", div);
    } catch (s) {
        img.parentNode.insertBefore(div, img.nextSibling);
	    div.setAttribute('style', style);
	    //document.body.appendChild(div);
    }
}

function esconderAjuda(img) {
    var div = document.getElementById('balaoAjuda');
    if (div != undefined) {
        div.innerHTML = '';
        div.setAttribute('id', 'balaoAjudaEsconde');
    }
}