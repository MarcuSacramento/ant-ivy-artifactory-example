<%@include file="/WebContent/paginas/comum/Global.jspf"%>

<jsp:directive.page import="br.gov.mj.dnn.pojo.*" />
<jsp:directive.page import="java.util.*" />

<tiles:insert definition=".mainLayout">

    <tiles:put name="javascript" type="string">
        <script>
            window.onload = function() {
                var span = document.getElementById('menuAutenticarCNN');
                span.innerHTML = "<b>" + span.innerHTML + "</b>"; 
            };
        </script>
	    <script language="javascript">  
	        function gerarCaptcha() {
	            document.forms[0].action = '${contexto}/abrirPesquisa/gerarCaptchaAutenticar.do';
	            document.forms[0].submit();
	        }
	    </script>
    </tiles:put>
	
  	<script language="javascript" src="${contexto}/WebContent/js/view.js" type="text/javascript"></script>
  	
  	<tiles:put name="subtitulo" type="string">AUTENTICAR CERTIDÃO DE NATURALIZAÇÃO</tiles:put>
  	
	<tiles:put name="areaTrabalho" type="string">
		<html:form action="/pesquisaCertidoes/autenticacaoConsultar" method="post">    
			<div class="linha">
				<div class="label" style="width:130px;">Protocolo da Certidão<span class="style1">*</span></div>
				<html:text name="form" property="numeroCertidao" styleId="numeroCertidao" style="width:164px;" styleClass="campo" maxlength="20" onkeypress="return Mascara('CERTIDAO',this,event);"/>
                <img src="${contexto}/WebContent/img/interrogacao.JPG" onmouseover="exibirAjuda(this, event)" onmouseout="esconderAjuda(this)" border="0" aviso="<fmt:message key='ajuda.autenticao.protocolo' />"/>
			</div>
			
			<div class="linha">
				<div class="label" style="width:130px;">Data da Emissão<span class="style1">*</span></div>
				<html:text name="form" property="dataEmissaoCertidao" styleId="dataEmissaoCertidao" style="width:100px;" styleClass="campo" maxlength="10" onkeypress="return Mascara('DATA',this,event);" onblur="return Mascara('DATA',this,event);"/><font class="texto"> (DD/MM/AAAA)</font>
				<img src="${contexto}/WebContent/img/interrogacao.JPG" onmouseover="exibirAjuda(this, event)" onmouseout="esconderAjuda(this)" border="0" aviso="<fmt:message key='ajuda.data.emissao' />"/>
			</div>
			
			<div class="linha">
				<div class="label" style="width:130px;">Hora da Emissão<span class="style1">*</span></div>
				<html:text name="form" property="horaEmissaoCertidao" styleId="horaEmissaoCertidao" style="width:50px;" styleClass="campo" maxlength="5" onkeypress="return Mascara('HORA',this,event);" onblur="return Mascara('HORA',this,event);"/>
				<img src="${contexto}/WebContent/img/interrogacao.JPG" onmouseover="exibirAjuda(this, event)" onmouseout="esconderAjuda(this)" border="0" aviso="<fmt:message key='ajuda.hora.emissao' />"/>
			</div>
			
			<div class="linha">
			    <div class="label" style="width:130px;">&nbsp</div>
		    	<img class="box_captcha" src="${contexto}/captcha"/>
		    	<a href="javascript:gerarCaptcha();" target="_self"><font class="texto" ><img src="${contexto}/WebContent/img/gerar_novamente.jpg" border="0" /></font></a>
		    </div>
		    
			<div class="linha">
				<div class="label" style="width:130px;" for="captcha">Repita os Caracteres<span class="style1">*</span></div>
			    <input type="text" name="codigoSeguranca" id="codigoSeguranca" style="width:43px;" class="campo" maxlength="4">
		    </div>
			
			<div class="botoes">
				<html:image src="${contexto}/WebContent/img/consultar.jpg" border="0" />
				<a href="${contexto}/abrirPesquisa/abrirAutenticacao.do"><img src="${contexto}/WebContent/img/limpar.jpg" border="0" /></a>
			</div>
				
			<br/>
			
			<script>
				setTimeout("document.getElementById('numeroCertidao').focus()", 10);
			</script>
		</html:form>	    
	</tiles:put>
</tiles:insert> 