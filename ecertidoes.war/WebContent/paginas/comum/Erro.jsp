<%@include file="/WebContent/paginas/comum/Global.jspf"%>
<jsp:directive.page import="br.gov.mj.dnn.pojo.*" />
<jsp:directive.page import="java.util.*" />

<%@page import="org.apache.struts.Globals"%>
<%@page import="java.io.PrintWriter"%>

<tiles:insert definition=".mainLayout">

    <tiles:put name="javascript" type="string"></tiles:put>
    <tiles:put name="subtitulo" type="string"></tiles:put>
    <tiles:put name="titulo" type="string"><br /><center></center><br /></tiles:put>
    <tiles:put name="areaTrabalho" type="string">
		<html:form action="/pesquisaCertidoes/emissaoEmitir" method="post">    
		    <tiles:put name="cabecalho" type="string">
		    	<!--  COLOCAR O MENU AQUI-->
		    	<div class="titulo">Secretaria Nacional de Justi&ccedil;a - E-Certid&atilde;o</div>
		    	<br>
		    	<img src="${contexto}/WebContent/img/banner_ecertidao.jpg" border="0"/>
				<div class="menu"><a href="/eCertidao/abrirPesquisa/abrirApresentacao.do" target="_self">Apresentação</a></div>
				<div class="menu">|</div>
				<div class="menu"><a href="/eCertidao/abrirPesquisa/abrirEmissao.do" target="_self">Emissão</a></div>
				<div class="menu">|</div>
				<div class="menu"><a href="/eCertidao/abrirPesquisa/abrirAutenticacao.do" target="_self">Autenticação</a></div>
				<div class="menu">|</div>
				<div class="menu"><a href="#">Base Legal</a></div>
				<br /><br /><br />
		    </tiles:put>
		    
			<!-- PÁGINAS DA APLICAÇÃO -->		
			<div class="linha">
				<div class="label" style="width:205px;">Nome Interessado<span class="style1">*</span></div>
				<html:text name="form" property="nomeInteressado" styleId="nomeInteressado" style="width:314px;" styleClass="campo" maxlength="100"/>
			</div>
			<div class="linha">
				<div class="label" style="width:205px;">Parentesco</div>
				<html:select name="form" property="grauParentesco" styleId="grauParentesco" style="width:314px"  styleClass="campo">
				<option value=""><fmt:message key="opcao.selecione" /></option>
			              <html:optionsCollection name="form" property="listaGrauParentesco" label="denominacao" value="codigoGrauParentesco" />
		        </html:select>
			</div>			
			<div class="linha">
				<div class="label" style="width:205px;">Nome do Requerido<span class="style1">*</span></div>
				<html:text name="form" property="nome" styleId="nome" style="width:314px;" styleClass="campo" maxlength="100"/>
				<a href="javascript:addElement();">
					<img src="${contexto}/WebContent/img/mais.jpg" border="0"/>
				</a>
			</div>
			<div class="linha" id="org_div1">
			</div>
			<div class="linha">
				<div class="label" style="width:205px;">Nome do Pai do requerido</div>
				<html:text name="form" property="nomePai" styleId="nomePai" style="width:314px;" styleClass="campo" maxlength="100"/>
			</div>
			
			<div class="linha">
				<div class="label" style="width:205px;">Nome da M&atilde;e do requerido<span class="style1">*</span></div>
				<html:text name="form" property="nomeMae" styleId="nomeMae" style="width:314px;" styleClass="campo" maxlength="100"/>
			</div>
			
			<div class="linha">
			  <div class="label" style="width:205px;">Data de Nascimento do requerido<span class="style1">*</span></div>
			  <html:text name="form" property="dataNascimento" styleId="dataNascimento" style="width:75px;" styleClass="campo" maxlength="10" onkeypress="return Mascara('DATA',this,event);" onblur="return Mascara('DATA',this,event);"/><font class="texto"> (99/99/9999)</font>
			</div>
			
			<div class="linha">
				<div class="label" style="width:205px;">País de Nascimento do Requerido<span class="style1">*</span></div>
				<html:select name="form" property="naturalidade" styleId="naturalidade" style="width:314px"  styleClass="campo">
				<option value=""><fmt:message key="opcao.selecione" /></option>
			         <html:optionsCollection name="form" property="listaPais" label="descricao" value="codigo" />
		        </html:select>
			</div>
			
			<div class="linha">
				<div class="label" style="width:205px;">Motivo da Solicitação<span class="style1">*</span></div>
				<html:select name="form" property="motivoSolicitacao" styleId="motivoSolicitacao" onchange="javascript:selecionarMotivo(this.value);" style="width:314px"  styleClass="campo">
				<option value=""><fmt:message key="opcao.selecione" /></option>
			              <html:optionsCollection name="form" property="listaMotivo" label="descricao" value="codigo" />
		        </html:select>
			</div>
			<div class="linha" id="divPais">
				<div class="label" style="width:205px;">&nbsp;</div>
 				<html:select name="form" property="motivoPais" styleId="motivoPais" style="width:314px"  styleClass="campo">
			        <option value=""><fmt:message key="opcao.selecione" /></option>
			           <html:optionsCollection name="form" property="listaPais" label="descricao" value="codigo" />
			    </html:select>
		    </div>
	        
	        <div class="linha" id="divOutros">
				<div class="label" style="width:205px;">&nbsp;</div>	        
		        <html:text name="form" property="motivoOutros" styleId="motivoOutros" style="width:314px;" styleClass="campo" maxlength="500"/>
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
				<img src="${contexto}/WebContent/img/emitir.jpg" border="0" />
			</div>
			
			<div class="linha">
				<font color="RED"><b>Caso ocorra algum problema que inviabilize a emissão da Certidão Negativa ou o encaminhamento de sua solicitação, entre em contato com a Secretaria Nacional de Justiça através do e-mail snj@mj.gov.br ou ligue para nossa Central de Atendimento: (61) 3429-3232.<b></font>
		    </div>
		</html:form>	    
    </tiles:put>
</tiles:insert>