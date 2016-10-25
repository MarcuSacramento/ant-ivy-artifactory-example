<%@include file="/WebContent/paginas/comum/Global.jspf"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 

<jsp:directive.page import="br.gov.mj.dnn.pojo.*" />
<jsp:directive.page import="java.util.*" />

<tiles:insert definition=".mainLayout">

    <tiles:put name="javascript" type="string">
        <script>
            window.onload = function() {
                var span = document.getElementById('menuEmitirCNN');
                span.innerHTML = "<b>" + span.innerHTML + "</b>"; 
            };
        </script>
	<script language="javascript">	
		var contadorNomesFoneticos = 1;
		ar_campo1 = new Array(0);
		var mainDivName = 'org_div1';
		
		function addElement() {
			y = ar_campo1.push("");
			if (contadorNomesFoneticos <=10) {
				contadorNomesFoneticos++;
				var ni = document.getElementById(mainDivName);
				var numi = document.getElementById('theValue');
				var numero = (document.getElementById("theValue").value -1)+ 2;
				numi.value = numero;
				
			    //indicar o nome do campo como array[novo elemento]
			    var divIdName = numero;
			    newDiv = document.createElement("div");
			    newDiv.setAttribute("id",divIdName);
			    var texto = '<div class="label" style="width:205px;">&nbsp</div><html:text name="form" value="" property="nome" styleId="nome" style="width:314px;text-transform:uppercase;" onkeyup="javascript:trimNome(this);this.value=this.value.toUpperCase();" styleClass="campo" maxlength="100" onblur="return verificarSobrenome(this);"/>&nbsp;<a href="javascript:retiraElemento(' + numero + ');"><img src="${contexto}/WebContent/img/menos.jpg" border="0"/></a>';
			    newDiv.innerHTML = texto;
			
				ni.appendChild(newDiv);
		    } else {
			    alert("Permitido no máximo 10(dez) outros nomes.");
		    }
	    }
	    
	    function retiraElemento(divNum) {
	    	contadorNomesFoneticos--;
	    	var d = document.getElementById(mainDivName);
			var olddiv = document.getElementById(divNum);
			d.removeChild(olddiv);

	    }
	    
	    function selecionarMotivo(motivoEscolhido) {
			if (motivoEscolhido == "1") {
				document.getElementById('divPais').style.display = 'block';
				document.getElementById('divOutros').style.display = 'none';
				return;
			} else if (motivoEscolhido == "2") {
				document.getElementById('divPais').style.display = 'none';
				document.getElementById('divOutros').style.display = 'block';
				return;
			} else {
				document.getElementById('divPais').style.display = 'none';
				document.getElementById('divOutros').style.display = 'none';
				return;
			}
		
		}


	    function selecionarDocumentoNacionalidade(nacionalidade){
	    	selecionarDocumentoNacionalidadeRefresh(nacionalidade, true);
	    }
	
		
	    function selecionarDocumentoNacionalidadeRefresh(nacionalidade, cpf) {
			if(cpf == true){
				document.getElementById('cpf').value = "";
				document.getElementById('registroEstrangeiro').value = "";
				document.getElementById('numeroPassaporte').value = "";
			}
		    
			if (nacionalidade != "" && nacionalidade != "BR") {
				document.getElementById('divCPF').style.display = 'none';
				document.getElementById('divRegistroEstrangeiro').style.display = 'block';
				document.getElementById('divNumeroPassaporte').style.display = 'block';
				return;
			} else if (nacionalidade != "" && nacionalidade == "BR") {
				document.getElementById('divCPF').style.display = 'block';
				document.getElementById('divRegistroEstrangeiro').style.display = 'none';
				document.getElementById('divNumeroPassaporte').style.display = 'none';
				return;
			} else {
				document.getElementById('divCPF').style.display = 'none';
				document.getElementById('divRegistroEstrangeiro').style.display = 'none';
				document.getElementById('divNumeroPassaporte').style.display = 'none';
				return;
			}
		
		}
    
    	function emitirEmissao() {
			document.forms[0].action = '${contexto}/pesquisaCertidoes/emissaoEmitir.do';
			document.forms[0].submit();
		}
		
		function gerarCaptcha() {
			document.forms[0].action = '${contexto}/abrirPesquisa/gerarCaptchaEmitir.do';
			document.forms[0].submit();
		}
		
		function trimNome(nome) {
			nome.value = nome.value.replace(/\s{2,}/, ' ');
		}
		
	</script>

    </tiles:put>

    <tiles:put name="subtitulo" type="string">EMITIR CERTIDÃO NEGATIVA DE NATURALIZAÇÃO</tiles:put>

	<tiles:put name="areaTrabalho" type="string">
		<html:form action="/pesquisaCertidoes/emissaoEmitir" method="post">    
			<div class="linha">
				<div class="label" style="width:250px;"><b>DADOS DO REQUERENTE</b></div>
				&nbsp;
			</div>
			<div class="linha">
				<div class="label" style="width:205px;">Nacionalidade<span class="style1">*</span></div>
				<html:select name="form" property="nacionalidade" styleId="nacionalidade" onchange="javascript:selecionarDocumentoNacionalidade(this.value);" style="width:314px"  styleClass="campo">
    				<option value=""><fmt:message key="opcao.selecione" /></option>
                    <html:optionsCollection name="form" property="listaNacionalidade" label="descricao" value="codigo" />
		        </html:select>
			</div>			
			<div class="linha"id="divCPF">
					<div class="label" style="width:205px;">CPF do Interessado<span class="style1">*</span></div>
					<html:text name="form" property="cpf" styleId="cpf" style="width:110px;" styleClass="campo" maxlength="14" onkeypress="return Mascara('CPF',this,event);" onblur="return verificarCPF(this.value)"/>
			</div>
			<div class="linha" id="divNumeroPassaporte">
					<div class="label" style="width:205px;">Nº do Passaporte<span class="style1">*</span></div>
					<html:text name="form" property="numeroPassaporte" styleId="numeroPassaporte" style="width:110px;" styleClass="campo" maxlength="30"/>
			</div>			
			<div class="linha" id="divRegistroEstrangeiro">
					<div class="label" style="width:205px;">RG ou RNE<span class="style1">*</span></div>
					<html:text name="form" property="registroEstrangeiro" styleId="registroEstrangeiro" style="width:110px;" styleClass="campo" maxlength="10"/>
			</div>			
			<div class="linha">
				<div class="label" style="width:205px;">Nome Interessado<span class="style1">*</span></div>
				<html:text name="form" property="nomeInteressado" styleId="nomeInteressado" style="width:314px;text-transform:uppercase;" styleClass="campo" maxlength="100" onkeyup="javascript:trimNome(this);this.value=this.value.toUpperCase();" onblur="return verificarSobrenome(this);"/>
			</div>
			<div class="linha">
				<div class="label" style="width:205px;">Sexo do Interessado<span class="style1">*</span></div>
				<html:select name="form" property="sexoRequerente" styleId="sexoRequerente" style="width:100px"  styleClass="campo">
				    <option value=""><fmt:message key="opcao.selecione" /></option>
                    <html:optionsCollection name="form" property="listaSexo" label="descricao" value="codigo" />
		        </html:select>
			</div>
			<div class="linha">
				<div class="label" style="width:205px;">Grau Parentesco<span class="style1">*</span></div>
				<html:select name="form" property="grauParentesco" styleId="grauParentesco" style="width:200px"  styleClass="campo">
				    <option value=""><fmt:message key="opcao.selecione" /></option>
                    <html:optionsCollection name="form" property="listaGrauParentesco" label="descricao" value="codigo" />
		        </html:select>
		        <img src="${contexto}/WebContent/img/interrogacao.JPG" onmouseover="exibirAjuda(this, event)" onmouseout="esconderAjuda(this)" border="0" 
		          aviso="<fmt:message key='ajuda.grau.parentesco' />"/>
			</div>

            <div class="linha">
                <div class="label" style="width:205px;">Motivo da Solicitação<span class="style1">*</span></div>
                <html:select name="form" property="motivoSolicitacao" styleId="motivoSolicitacao" onchange="javascript:selecionarMotivo(this.value);" style="width:314px"  styleClass="campo">
                <option value=""><fmt:message key="opcao.selecione" /></option>
                    <html:optionsCollection name="form" property="listaMotivo" label="descricao" value="codigo" />
                </html:select>
            </div>

            <div class="linha" id="divPais">
                <div class="label" style="width:205px;">Indicar país</div>
                <html:select name="form" property="motivoPais" styleId="motivoPais" style="width:314px"  styleClass="campo">
                    <option value=""><fmt:message key="opcao.selecione" /></option>
                    <html:optionsCollection name="form" property="listaPais" label="descricao" value="codigo" />
                </html:select>
            </div>

            <div class="linha" id="divOutros">
                <div class="label" style="width:205px;">Indicar motivo</div>          
                <html:text name="form" property="motivoOutros" styleId="motivoOutros" style="width:314px;" styleClass="campo" maxlength="500"/>
            </div>
			<br>
			<div class="linha">
				<div class="label" style="width:250px;"><b>DADOS DO REQUERIDO</b></div>
				&nbsp;
				<div></div>
				<br> <font class="msg_ajuda_nome"><b>Obs: Caso existam variações, clique no (+) para que o sistema inclua mais um campo de pesquisa de nomes.</b>	
			</div>	
		    <div class="linha">
                <input type="hidden" value="${form.tamanhoArrayNome}" id="theValue" />
				<div class="label" style="width:205px;">Nome<span class="style1">*</span></div>

				<c:if test="${empty form.nome}">
					<html:text name="form" property="nome" value="${bean}" styleId="nome" style="width:314px;text-transform:uppercase;" onkeyup="javascript:trimNome(this);this.value=this.value.toUpperCase();" styleClass="campo" maxlength="100" onblur="return verificarSobrenome(this);"/>
					<a href="javascript:addElement();"><img src="${contexto}/WebContent/img/mais.jpg" border="0"/></a>   <img src="${contexto}/WebContent/img/interrogacao.JPG" onmouseover="exibirAjuda(this, event)" onmouseout="esconderAjuda(this)" border="0" aviso="<fmt:message key='ajuda.outros.nome' />"/>
					</c:if>
				<c:if test="${!empty form.nome}">
	                <div id="org_div1">
	                    <c:forEach items="${form.nome}" var="bean" varStatus="i">
	                        <div id="${i.index}">
	                            <c:if test="${i.index != 0}">
	                                <div class="label" style="width:205px;">&nbsp;</div>
	                            </c:if>
	                            <html:text name="form" property="nome" value="${bean}" styleId="nome" style="width:314px;text-transform:uppercase;" onkeyup="javascript:trimNome(this);this.value=this.value.toUpperCase();" styleClass="campo" maxlength="100" onblur="return verificarSobrenome(this);"/>
	                            <c:if test="${i.index == 0}">
	                                <a href="javascript:addElement();"><img src="${contexto}/WebContent/img/mais.jpg" border="0"/></a>
	                                <img src="${contexto}/WebContent/img/interrogacao.JPG" onmouseover="exibirAjuda(this, event)" onmouseout="esconderAjuda(this)" border="0" aviso="<fmt:message key='ajuda.outros.nome' />"/>
	                              </c:if>
	                            <c:if test="${i.index != 0}">
	                                <a href="javascript:retiraElemento(${i.index});">
	                                    <img src="${contexto}/WebContent/img/menos.jpg" border="0"/>
	                                </a>
	                            </c:if>
	                        </div>
	                    </c:forEach>
	                </div>
                </c:if>
			</div>
			
			<c:if test="${empty form.nome}">
				<div id="org_div1">
				</div>
			</c:if>
			
			<div class="linha">
				<div class="label" style="width:205px;">Sexo<span class="style1">*</span></div>
				<html:select name="form" property="sexoRequerido" styleId="sexoRequerido" style="width:100px"  styleClass="campo">
				<option value=""><fmt:message key="opcao.selecione" /></option>
                    <html:optionsCollection name="form" property="listaSexo" label="descricao" value="codigo" />
		        </html:select>
			</div>	

			<div class="linha">
				<div class="label" style="width:205px;">Nome do Pai</div>
				<html:text name="form" property="nomePai" styleId="nomePai" style="width:314px;" styleClass="campo" maxlength="100"/>
			</div>

			<div class="linha">
				<div class="label" style="width:205px;">Nome da M&atilde;e<span class="style1">*</span></div>
				<html:text name="form" property="nomeMae" styleId="nomeMae" style="width:314px;" styleClass="campo" maxlength="100"/>
			</div>
			
			<div class="linha">
			  <div class="label" style="width:205px;">Data de Nascimento</div>
			  <html:text name="form" property="dataNascimento" styleId="dataNascimento" style="width:75px;" styleClass="campo" maxlength="10" onkeypress="return ApenasNumeros(event);" onblur="return Mascara('DATA',this,event);"/><font class="texto"> (DD/MM/AAAA)</font>

			  <font class="texto">ou Ano 
              <html:text name="form" property="anoNascimento" styleId="anoNascimento" style="width:40px;" styleClass="campo" maxlength="4" onkeypress="return ApenasNumeros(event);"/> (AAAA)</font>
              <img src="${contexto}/WebContent/img/interrogacao.JPG" onmouseover="exibirAjuda(this, event)" onmouseout="esconderAjuda(this)" border="0" 
                    aviso="<fmt:message key='ajuda.data.ano.nascimento' />"/>
			</div>
			
			<div class="linha">
				<div class="label" style="width:205px;">País de Nascimento<span class="style1">*</span></div>
				<html:select name="form" property="naturalidade" styleId="naturalidade" style="width:314px"  styleClass="campo">
    				<option value=""><fmt:message key="opcao.selecione" /></option>
                    <html:optionsCollection name="form" property="listaPais" label="descricao" value="codigo" />
		        </html:select>
                <img src="${contexto}/WebContent/img/interrogacao.JPG" onmouseover="exibirAjuda(this, event)" onmouseout="esconderAjuda(this)" border="0" 
                    aviso="<fmt:message key='ajuda.pais.nascimento' />"/>
			</div>

			<div class="linha">
			    <div class="label" style="width:205px;">&nbsp</div>
		    	<img class="box_captcha" src="${contexto}/captcha"/>
		    	<a href="javascript:gerarCaptcha();" target="_self"><img src="${contexto}/WebContent/img/gerar_novamente.jpg" border="0" /></a>
		    </div>

			<div class="linha">
				<div class="label" style="width:205px;" for="captcha">Repita os Caracteres<span class="style1">*</span></div>
			    <html:text name="form" property="codigoSeguranca" styleId="codigoSeguranca" style="width:43px;" styleClass="campo" maxlength="4"/>
		    </div>

			<div class="texto">

                <br><br>
				<b><img src="/ecertidao/WebContent/img/icone_exclamacao.gif" border="0" /> 
				ATENÇÃO: É necessário ter instalado o <a href="http://get.adobe.com/br/reader/" target="_blank"><img src="${contexto}/WebContent/img/adobe_pdf.png" width="50" height="50" border="0" /></a>em seu computador para a emissão da Certidão Negativa de Naturalização.</b>
				<br><br>
			</div>

			<div class="botoes">
				<a href="javascript:emitirEmissao();" target="_self"><img src="${contexto}/WebContent/img/emitir.jpg" border="0" /></a>
				<a href="${contexto}/abrirPesquisa/abrirEmissao.do"><img src="${contexto}/WebContent/img/limpar.jpg" border="0" /></a>				
			</div>
			
			<script>
				//setTimeout("document.getElementById('cpf').focus()", 10);
			</script>
			</html:form>
			<script language="JavaScript">
				if (document.forms[0].motivoSolicitacao.value == "1") {
					document.getElementById('divPais').style.display = 'block';
					document.getElementById('divOutros').style.display = 'none';
				} else if (document.forms[0].motivoSolicitacao.value == "2") {
					document.getElementById('divPais').style.display = 'none';
					document.getElementById('divOutros').style.display = 'block';
				} else {
					document.getElementById('divPais').style.display = 'none';
					document.getElementById('divOutros').style.display = 'none';
				}

				selecionarDocumentoNacionalidadeRefresh(document.getElementById("nacionalidade").value, false);
			</script>	    
	</tiles:put>
</tiles:insert> 

