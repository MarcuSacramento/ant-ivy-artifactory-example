<%@include file="/WebContent/paginas/comum/Global.jspf"%>

<jsp:directive.page import="br.gov.mj.dnn.pojo.*" />
<jsp:directive.page import="java.util.*" />

<c:set var="desabilitarSol">true</c:set>
<c:set var="desabilitarMJ">true</c:set>
<c:set var="focus"></c:set>

<c:if test="${form.opcaoConsulta == 'protocoloSolicitacao'}">
    <c:set var="desabilitarSol">false</c:set>
    <c:set var="focus">numeroCertidao</c:set>
</c:if>

<c:if test="${form.opcaoConsulta == 'protocoloMJ'}">
    <c:set var="desabilitarMJ">false</c:set>
    <c:set var="focus">protocoloMJ</c:set>
</c:if>

<tiles:insert definition=".mainLayout">

    <tiles:put name="javascript" type="string">
        <script>
            window.onload = function() {
                var span = document.getElementById('menuConsultarSolicitacao');
                span.innerHTML = "<b>" + span.innerHTML + "</b>"; 
            };
            
            <c:if test="${form.opcaoConsulta == 'protocoloSolicitacao'}">
            window.onload = function() {
                var radio = document.getElementById('radio1')
                radio.checked = true;
            };
            </c:if>

			<c:if test="${form.opcaoConsulta == 'protocoloMJ'}">
            window.onload = function() {
                var radio = document.getElementById('radio2')
                radio.checked = true;
            };
			</c:if>
        </script>
	    <script language="javascript">  
	        function gerarCaptcha() {
	            document.forms[0].action = '${contexto}/abrirPesquisa/gerarCaptchaSolicitar.do';
	            document.forms[0].submit();
	        }
	        
	        function trocarParaMJ(campo) {
                var form = campo.form;
                form.protocoloMJ.disabled = false;
                form.numeroCertidao.disabled = true;
                
                form.protocoloMJ.focus();
	        }
	        
	        function trocarParaSolicitacao(campo) {
                var form = campo.form;
                form.protocoloMJ.disabled = true;
                form.numeroCertidao.disabled = false;
                
                form.numeroCertidao.focus();
            }
	    </script>
    </tiles:put>
  	
  	<tiles:put name="subtitulo" type="string">CONSULTAR ANDAMENTO DE SOLICITAÇÃO</tiles:put>
  	
	<tiles:put name="areaTrabalho" type="string">
		<html:form action="/manterSolicitacao/consultarSolicitacao" method="post" styleId="formSolicitacao" focus="${focus}">    
			<div class="linha">
				<div class="label" style="width:170px;">
                    <html:radio property="opcaoConsulta" value="protocoloSolicitacao"
                        styleId="radio1" onclick="trocarParaSolicitacao(this); this.focus"/>
                    Protocolo da Solicitação
                </div>
				<html:text name="form" property="numeroCertidao" styleId="numeroCertidao" style="width:164px;" 
				    styleClass="campo" maxlength="20" onkeypress="return Mascara('CERTIDAO',this,event);" 
				    disabled="${desabilitarSol}"/>
                <img src="${contexto}/WebContent/img/interrogacao.JPG" onmouseover="exibirAjuda(this, event)" onmouseout="esconderAjuda(this)" border="0" aviso="<fmt:message key='ajuda.protocolo.solicitacao' />"/>
			</div>

            <div class="linha">
                <div class="label" style="width:170px;">
                    <html:radio property="opcaoConsulta" value="protocoloMJ" 
                        styleId="radio2" onclick="trocarParaMJ(this)"/>
                    N° do Protocolo MJ
                </div>
                <html:text name="form" property="protocoloMJ" styleId="protocoloMj" style="width:164px;" 
                    onkeypress="return mascararProtocoloMj(this,event);" styleClass="campo" maxlength="20" 
                    size="25" disabled="${desabilitarMJ}"/>
                <img src="${contexto}/WebContent/img/interrogacao.JPG" onmouseover="exibirAjuda(this, event)" onmouseout="esconderAjuda(this)" border="0" aviso="<fmt:message key='ajuda.solicitacao.protcolomj' />"/>
            </div>
            
			<div class="linha">
			    <div class="label" style="width:130px;">&nbsp</div>
		    	<img class="box_captcha" src="${contexto}/captcha"/>
		    	<a href="javascript:gerarCaptcha();" target="_self"><font class="texto" ><img src="${contexto}/WebContent/img/gerar_novamente.jpg" border="0" /></font></a>
		    </div>

			<div class="linha">
				<div class="label" style="width:130px;" for="captcha">Repita os Caracteres<span class="style1">*</span></div>
			    <input type="text" name="codigoSeguranca" id="codigoSeguranca" style="width:43px;" class="campo" maxlength="4"/>
		    </div>
			<div class="botoes">
				<html:image src="${contexto}/WebContent/img/consultar.jpg" border="0" />
				<a href="${contexto}/manterSolicitacao/abrirConsultarCertidao.do"><img src="${contexto}/WebContent/img/limpar.jpg" border="0" /></a>
			</div>
			<br/>
			<script>
				setTimeout("document.getElementById('numeroCertidao').focus()", 10);
			</script>
		</html:form>	    
	</tiles:put>
</tiles:insert> 