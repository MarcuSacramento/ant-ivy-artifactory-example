<%@include file="/WebContent/paginas/comum/Global.jspf"%>

<jsp:directive.page import="br.gov.mj.dnn.pojo.*" />
<jsp:directive.page import="java.util.*" />

<tiles:insert definition=".mainLayout">
	
	<tiles:put name="javascript" type="string">
        <script>
        <%
	   	 	if (request.getAttribute("redireciona")!=null && request.getAttribute("redireciona").equals("true")) {  
		%>
					var confirmar = confirm("Não foi possível emitir uma Certidão Negativa de Naturalização, pois constam em nossos registros dados similares aos critérios informados. " +
							"Ao clicar em OK você será redirecionado para um formulário a ser encaminhado à análise do Departamento de Estrangeiros.");
					if (!confirmar) {
						document.location = '<%= request.getContextPath() %>';
					}
		<%
			}
		%>
        	
            window.onload = function() {
                var span = document.getElementById('menuEmitirCNN');
                span.innerHTML = "<b>" + span.innerHTML + "</b>"; 
            };
            
            function carregarLabel(idHidden, campo) {
                var index = campo.selectedIndex;
				var valor = campo.options[index].text;
                var hidden = document.getElementById(idHidden);
                hidden.value = valor;
            }
        </script>
        <script language="JavaScript">
        var contadorNomes = 1;
        ar_campo1 = new Array(0);
   		var mainDivName = 'org_div1';
		
		function addElement() {
			y = ar_campo1.push("");
			if (contadorNomes <=10) {
				contadorNomes++;
				var ni = document.getElementById(mainDivName);
				var numi = document.getElementById('theValue');
				var numero = (document.getElementById("theValue").value -1)+ 2;
				numi.value = numero;
				
			    //indicar o nome do campo como array[novo elemento]
			    var divIdName = numero;
			    newDiv = document.createElement("div");
			    newDiv.setAttribute("id",divIdName);
			    var texto = '<div class="label" style="width:205px;">&nbsp</div><html:text name="form" value="" property="nomeRequerido" styleId="nomeRequerido" style="width:314px;" styleClass="campo" maxlength="100"  onblur="return verificarSobrenome(this);"/>&nbsp;<a href="javascript:retiraElemento(' + numero + ');"><img src="${contexto}/WebContent/img/menos.jpg" border="0"/></a>';
			    newDiv.innerHTML = texto;
			
				ni.appendChild(newDiv);
		    } else {
			    alert("Permitido no máximo 10(dez) outros nomes.")
		    }
	    }
        
        function retiraElemento(divNum) {
        	contadorNomes--;
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

        function solicitarCNN() {
			document.forms[0].action = '${contexto}/solicitaCertidoes/solicitarCNN.do';
			document.forms[0].submit();
		}
        
        function gerarCaptcha() {
            document.forms[0].action = '${contexto}/abrirPesquisa/gerarCaptchaSolicitacao.do';
            document.forms[0].submit();
        }
    </script>
  	</tiles:put>
  	
  	<tiles:put name="subtitulo" type="string">SOLICITAÇÃO DE CERTIDÃO NEGATIVA DE NATURALIZAÇÃO</tiles:put>
  	
	<tiles:put name="areaTrabalho" type="string">
		<html:form action="/solicitaCertidoes/solicitarCNN" method="post">    
					
			<div class="linha">
				<div class="label" style="width:250px;"><b>DADOS DO REQUERENTE</b></div>
				&nbsp;
			</div>
			<div class="linha">
				<html:hidden name="form" property="labelNacionalidade" styleId="labelNacionalidade"/>
				<div class="label" style="width:205px;">Nacionalidade<span class="style1">*</span></div>
				<html:select name="form" property="nacionalidade" styleId="nacionalidade" style="width:314px"  styleClass="campo" onchange="javascript:gerarCaptcha();">
    				<option value=""><fmt:message key="opcao.selecione" /></option>
                    <html:optionsCollection name="form" property="listaNacionalidade" label="descricao" value="codigo" />
		        </html:select>
			</div>	
			<div class="linha">
				<div class="label" style="width:205px;">Nome<span class="style1">*</span></div>
				<html:text name="form" property="nomeRequerente" styleId="nomeRequerente" style="width:314px;" styleClass="campo" maxlength="100" onblur="return verificarSobrenome(this);"/>
			</div>
			<div class="linha">
				<div class="label" style="width:205px;">Sexo</div>
				<html:select name="form" property="sexoRequerente" styleId="sexoRequerente" style="width:100px"  styleClass="campo">
				<option value=""><fmt:message key="opcao.selecione" /></option>
                    <html:optionsCollection name="form" property="listaSexo" label="descricao" value="codigo" />
		        </html:select>
			</div>
			<div class="linha">
				<div class="label" style="width:205px;">Grau Parentesco</div>
				<html:select name="form" property="grauParentesco" styleId="grauParentesco" style="width:200px"  styleClass="campo">
				<option value=""><fmt:message key="opcao.selecione" /></option>
			        <html:optionsCollection name="form" property="listaGrauParentesco" label="descricao" value="codigo" />
		        </html:select>
			</div>
			
			<c:if test="${form.nacionalidade != 'BR'}">
				<div class="linha">
					<div class="label" style="width:205px;">Nº do Passaporte</div>
					<html:text name="form" property="passaporte" styleId="passaporte" style="width:314px;" styleClass="campo" maxlength="30"/>
					<img src="${contexto}/WebContent/img/interrogacao.JPG" onmouseover="exibirAjuda(this, event)" onmouseout="esconderAjuda(this)" border="0" aviso="<fmt:message  key='ajuda.passaporte.requerente' />"/>
				</div>
				<div class="linha">
	                <div class="label" style="width:205px;">RG ou RNE</div>
	                <html:text name="form" property="rneRgRequerente" styleId="rneRgRequerente" style="width:314px;" styleClass="campo" maxlength="20"/>
	                <img src="${contexto}/WebContent/img/interrogacao.JPG" onmouseover="exibirAjuda(this, event)" onmouseout="esconderAjuda(this)" border="0" aviso="<fmt:message  key='ajuda.rgrne.requerente' />"/>
	            </div>			
			</c:if>
			
			<c:if test="${form.nacionalidade == 'BR'}">
				<div class="linha">
					<div class="label" style="width:205px;">CPF</div>
					<html:text name="form" property="cpf" styleId="cpf" style="width:110px;" styleClass="campo" maxlength="14" onkeypress="return Mascara('CPF',this,event);" onblur="return Mascara('CPF',this,event);"/>
				</div>			
			</c:if>

			<div class="linha">
                <html:hidden name="form" property="labelPais" styleId="labelPais"/>
				<div class="label" style="width:205px;">País do Endereço<span class="style1">*</span></div>
				<html:select name="form" property="enderecoPais" styleId="enderecoPais" style="width:314px"  styleClass="campo"  onchange="javascript:gerarCaptcha();">
				    <option value=""><fmt:message key="opcao.selecione" /></option>
			        <html:optionsCollection name="form" property="listaPais" label="descricao" value="codigo" />
		        </html:select>
			</div>     
			
			<c:if test="${form.enderecoPais != 'BR'}">
				<div class="linha">
					<div class="label" style="width:205px;">Endereço Completo<span class="style1">*</span></div>
					<html:text name="form" property="endereco" styleId="endereco" style="width:314px;" styleClass="campo" maxlength="100"/>
					<img src="${contexto}/WebContent/img/interrogacao.JPG" onmouseover="exibirAjuda(this, event)" onmouseout="esconderAjuda(this)" border="0" aviso="<fmt:message  key='ajuda.endereco.completo' />"/>
				</div>
			</c:if>	
			
			<c:if test="${form.enderecoPais == 'BR'}">
				<div class="linha">
				&nbsp;
				&nbsp;			
				</div>
				<div class="linha">
					<div class="label" style="width:205px;">CEP<span class="style1">*</span></div>
					<html:text name="form" property="cep" styleId="cep" style="width:60px;" onkeypress="return ApenasNumeros(event);" styleClass="campo" maxlength="8"/>
					<a href="http://www.correios.com.br/servicos/cep/cep_loc_log.cfm" target="_blank">
	                    <img src="${contexto}/WebContent/img/buscar_cep.jpg" border="0"/>
	                </a>
				</div>							       
				<div class="linha">
					<div class="label" style="width:205px;">Endereço Completo<span class="style1">*</span></div>
					<html:text name="form" property="endereco" styleId="endereco" style="width:314px;" styleClass="campo" maxlength="100"/>
					<img src="${contexto}/WebContent/img/interrogacao.JPG" onmouseover="exibirAjuda(this, event)" onmouseout="esconderAjuda(this)" border="0" aviso="<fmt:message  key='ajuda.endereco.completo' />"/>
				</div>
				<div class="linha">
					<div class="label" style="width:205px;">Número<span class="style1">*</span></div>
					<html:text name="form" property="enderecoNumero" styleId="enderecoNumero" style="width:50px;" styleClass="campo" maxlength="5"/>
				</div>
				<div class="linha">
					<div class="label" style="width:205px;">Complemento</div>
					<html:text name="form" property="enderecoComplemento" styleId="enderecoComplemento" style="width:314px;" styleClass="campo" maxlength="100"/>
				</div>
				<div class="linha">
	                <html:hidden name="form" property="labelUf" styleId="labelUf"/>
					<div class="label" style="width:205px;">UF do Endereço<span class="style1">*</span></div>
					<html:select name="form" property="enderecoUF" styleId="enderecoUF" style="width:100px" styleClass="campo" onchange="javascript:gerarCaptcha();">
					    <option value=""><fmt:message key="opcao.selecione" /></option>
				         <html:optionsCollection name="form" property="listaUF" label="descricao" value="codigo"/>
			        </html:select>
				</div>
				<div class="linha">
					<div class="label" style="width:205px;">Cidade<span class="style1">*</span></div>
					<html:select name="form" property="enderecoCidade" styleId="enderecoCidade" style="width:314px"  styleClass="campo">
					    <option value=""><fmt:message key="opcao.selecione" /></option>
				        <html:optionsCollection name="form" property="listaMunicipio" label="descricao" value="codigo" />
			        </html:select>
				</div> 		
				<div class="linha">
					<div class="label" style="width:205px;">Bairro<span class="style1">*</span></div>
					<html:text name="form" property="enderecoBairro" styleId="enderecoBairro" style="width:314px;" styleClass="campo" maxlength="100"/>
				</div>					
				<div class="linha">
				&nbsp;
				&nbsp;
				</div>	
			</c:if>		
			<div class="linha">
				<div class="label" style="width:205px;">E-mail<span class="style1">*</span></div>
				<html:text name="form" property="email" styleId="email" style="width:314px;" styleClass="campo" maxlength="100"/>
				<img src="${contexto}/WebContent/img/interrogacao.JPG" onmouseover="exibirAjuda(this, event)" onmouseout="esconderAjuda(this)" border="0" aviso="<fmt:message  key='ajuda.email.contato' />"/>
			</div>
			<div class="linha">
				<div class="label" style="width:205px;">Telefone<span class="style1">*</span></div>
				<font class="texto">(</font>
				<html:text name="form" property="ddi" styleId="ddi" style="width:33px;" styleClass="campo" maxlength="3"/>
				<font class="texto">)(</font>
				<html:text name="form" property="ddd" styleId="ddd" style="width:33px;" styleClass="campo" maxlength="3"/>
				<font class="texto">)</font>
				<html:text name="form" property="telefone" styleId="telefone" style="width:67px;" styleClass="campo" onkeypress="return Mascara('TEL_SEM_DDD',this,event);" onblur="return Mascara('TEL_SEM_DDD',this,event);" maxlength="9"/><font class="texto"> (DDI)(DDD)9999-9999</font>
			</div>			
			<div class="linha">
				<div class="label" style="width:205px;">Motivo da Solicitação<span class="style1">*</span></div>
				<html:select name="form" property="motivo" styleId="motivo" style="width:160px" onchange="javascript:selecionarMotivo(this.value);" styleClass="campo">
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
				<div class="label" style="width:205px;">Indicar outro motivo</div>	        
		        <html:text name="form" property="motivoOutros" styleId="motivoOutros" style="width:314px;" styleClass="campo" maxlength="200"/>
	        </div>

            <div class="linha">
                <div class="label" style="width:205px;">Número de Procotolo no MJ</div>
                <html:text name="form" property="protocoloMj" styleId="protocoloMj" style="width:314px;" onkeypress="return mascararProtocoloMj(this,event);" styleClass="campo" maxlength="20"/>
                <img src="${contexto}/WebContent/img/interrogacao.JPG" onmouseover="exibirAjuda(this, event)" onmouseout="esconderAjuda(this)" border="0" aviso="<fmt:message  key='ajuda.solicitacao.protocolo.mj' />"/>
            </div>

			<br>

			<div class="linha">
				<div class="label" style="width:250px;"><b>DADOS DO REQUERIDO</b></div>
				&nbsp;
			</div>				
			<div class="linha">
				<div class="label" style="width:205px;">Nome<span class="style1">*</span></div>
				<input type="hidden" value="${form.tamanhoArrayNome}" id="theValue" />

				<c:if test="${empty form.nomeRequerido}">
					<html:text name="form" property="nomeRequerido" value="${bean}" styleId="nomeRequerido" style="width:314px;" styleClass="campo" maxlength="100"  onblur="return verificarSobrenome(this);"/>
					<a href="javascript:addElement();">
						<img src="${contexto}/WebContent/img/mais.jpg" border="0"/></a>
					<img src="${contexto}/WebContent/img/interrogacao.JPG" onmouseover="exibirAjuda(this, event)" onmouseout="esconderAjuda(this)" border="0" 
                        aviso="<fmt:message  key='ajuda.outros.nome' />"/>
				</c:if>
				<c:if test="${!empty form.nomeRequerido}">
					<div id="org_div1">
						<c:forEach items="${form.nomeRequerido}" var="bean" varStatus="i">
							<div id="${i.index}">
								<c:if test="${i.index != 0}">
									<div class="label" style="width:205px;">&nbsp;</div>
								</c:if>
								<html:text name="form" property="nomeRequerido" value="${bean}" styleId="nomeRequerido" style="width:314px;" styleClass="campo" maxlength="100"  onblur="return verificarSobrenome(this);"/>
								<c:if test="${i.index == 0}">
									<a href="javascript:addElement();">
										<img src="${contexto}/WebContent/img/mais.jpg" border="0"/></a>
									<img src="${contexto}/WebContent/img/interrogacao.JPG" onmouseover="exibirAjuda(this, event)" onmouseout="esconderAjuda(this)" border="0" 
                                        aviso="<fmt:message  key='ajuda.outros.nome' />"/>
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

				<c:if test="${empty form.nomeRequerido}">
					<div id="org_div1">
					</div>
				</c:if>
            </div>
			
			<div class="linha">
				<div class="label" style="width:205px;">Sexo<span class="style1">*</span></div>
				<html:select name="form" property="sexoRequerido" styleId="sexoRequerido" style="width:100px" styleClass="campo">
				<option value=""><fmt:message key="opcao.selecione" /></option>
			              <html:optionsCollection name="form" property="listaSexo" label="descricao" value="codigo" />
		        </html:select>
			</div>
			
			<div class="linha">
				<div class="label" style="width:205px;">Nome do Pai</div>
				<html:text name="form" property="nomePaiRequerido" styleId="nomePaiRequerido" style="width:314px;" styleClass="campo" maxlength="100"/>
			</div>			
			<div class="linha">
				<div class="label" style="width:205px;">Nome da Mãe</div>
				<html:text name="form" property="nomeMaeRequerido" styleId="nomeMaeRequerido" style="width:314px;" styleClass="campo" maxlength="100"/>
			</div>			
			<div class="linha">
				<div class="label" style="width:205px;">Data de Nascimento</div>
				<html:text name="form" property="dataNascimentoRequerido" styleId="dataNascimentoRequerido" style="width:75px;" styleClass="campo" maxlength="10" onkeypress="return Mascara('DATA',this,event);" onblur="return Mascara('DATA',this,event);"/><font class="texto"> (DD/MM/AAAA)</font>

				<font class="texto">ou Ano 
                <html:text name="form" property="anoNascimento" styleId="anoNascimento" style="width:40px;" styleClass="campo" maxlength="4" onkeypress="return ApenasNumeros(event);"/> (AAAA)</font>
                <img src="${contexto}/WebContent/img/interrogacao.JPG" onmouseover="exibirAjuda(this, event)" onmouseout="esconderAjuda(this)" border="0" aviso="<fmt:message  key='ajuda.data.ano.nascimento' />"/>
			</div>			
			<div class="linha">
				<div class="label" style="width:205px;">País de Nascimento<span class="style1">*</span></div>
				<html:select name="form" property="naturalidadeRequerido" styleId="naturalidadeRequerido" style="width:314px"  styleClass="campo">
				    <option value=""><fmt:message key="opcao.selecione" /></option>
                    <html:optionsCollection name="form" property="listaPais" label="descricao" value="codigo" />
		        </html:select>
                <img src="${contexto}/WebContent/img/interrogacao.JPG" onmouseover="exibirAjuda(this, event)" onmouseout="esconderAjuda(this)" border="0" aviso="<fmt:message  key='ajuda.pais.nascimento' />"/>
			</div>		
			<div class="linha">
				<div class="label" style="width:205px;">Estado Civil</div>
				<html:select name="form" property="estadoCivilRequerido" styleId="estadoCivilRequerido" style="width:114px"  styleClass="campo">
                    <option value=""><fmt:message key="opcao.selecione" /></option>
	                <html:optionsCollection name="form" property="listaEstadoCivil" label="descricao" value="codigo" />
		        </html:select>
			</div>				
			<div class="linha">
				<div class="label" style="width:205px;">Data chegada ao Brasil</div>
				<html:text name="form" property="dataChegadaBrasil" styleId="dataChegadaBrasil" style="width:75px;" styleClass="campo" maxlength="10" onkeypress="return Mascara('DATA',this,event);" onblur="return Mascara('DATA',this,event);"/><font class="texto"> (DD/MM/AAAA)  ou  Ano </font><html:text name="form" property="anoChegadaBrasil" styleId="anoChegadaBrasil" style="width:30px;" onkeypress="return ApenasNumeros(event);" styleClass="campo" maxlength="4"/><font class="texto"> (AAAA)</font>
			</div>
			<div class="linha">
				<div class="label" style="width:205px;">RNE ou RG</div>
				<html:text name="form" property="rneRg" styleId="rneRg" style="width:314px;" styleClass="campo" maxlength="20"/>
			</div>
			<div class="linha">
				<div class="label" style="width:205px;">Nº carteira Modelo 19</div>
				<html:text name="form" property="carteiraModelo" styleId="carteiraModelo" style="width:314px;" styleClass="campo" maxlength="20"/>
			</div>			
			<div class="linha">
				<div class="label" style="width:205px;">Se falecido, data do óbito</div>
				<html:text name="form" property="dataObito" styleId="dataObito" style="width:75px;" styleClass="campo" maxlength="10" onkeypress="return Mascara('DATA',this,event);" onblur="return Mascara('DATA',this,event);"/><font class="texto"> (DD/MM/AAAA)  ou  Ano </font><html:text name="form" property="anoObito" styleId="anoObito" style="width:30px;" onkeypress="return ApenasNumeros(event);" styleClass="campo" maxlength="4"/><font class="texto"> (AAAA)</font>
			</div>

            <div class="linha">
                <div class="label" style="width:205px;">Observação</div>
                <html:textarea name="form" property="observacao" styleClass="campo" cols="59" rows="6" onkeypress="return verificarLimite(this,500);" onblur="return verificarLimite(this,500);"/>
                <img src="${contexto}/WebContent/img/interrogacao.JPG" onmouseover="exibirAjuda(this, event)" onmouseout="esconderAjuda(this)" border="0" aviso="<fmt:message  key='ajuda.observacao' />"/>
            </div>

			<div class="texto">
                <br><br>
				<b><img src="${contexto}/WebContent/img/icone_exclamacao.gif" border="0" /> 
				ATENÇÃO: Confira os dados  informados acima, inclusive endereço e e-mail, antes de encaminhar sua solicitação.</b>
				<br><br>
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
					
			<div class="botoes">
				<a href="javascript:solicitarCNN();" target="_self"><img src="${contexto}/WebContent/img/encaminhar.jpg" border="0" /></a>
				<a href="${contexto}/abrirPesquisa/limparSolicitacao.do"><img src="${contexto}/WebContent/img/limpar.jpg" border="0" /></a>
			</div>
			<script>
				setTimeout("document.getElementById('nomeRequerente').focus()", 10);
			</script>
		</html:form>	
		<script language="JavaScript">
			if (document.forms[0].motivo.value == "1") {
				document.getElementById('divPais').style.display = 'block';
				document.getElementById('divOutros').style.display = 'none';
			} else if (document.forms[0].motivo.value == "2") {
				document.getElementById('divPais').style.display = 'none';
				document.getElementById('divOutros').style.display = 'block';
			} else {
				document.getElementById('divPais').style.display = 'none';
				document.getElementById('divOutros').style.display = 'none';
			}
		</script>
	</tiles:put>
</tiles:insert> 