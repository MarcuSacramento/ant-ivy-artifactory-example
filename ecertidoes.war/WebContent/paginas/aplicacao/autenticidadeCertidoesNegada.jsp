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
  	</tiles:put>

    <tiles:put name="subtitulo" type="string">CERTID�O N�O AUTENTICADA</tiles:put>

	<tiles:put name="areaTrabalho" type="string">
			<!-- P�GINAS DA APLICA��O -->		
			<div class="texto">
			<br>
			N�o foi poss�vel confirmar a autenticidade da Certid�o com os seguintes dados:
			<br><br><br>
			&nbsp;&nbsp;&nbsp;&nbsp;<b>N� Certid�o:</b> ${form.numeroCertidao}
			<br><br>
			&nbsp;&nbsp;&nbsp;&nbsp;<b>Data:</b> ${form.dataEmissaoCertidao}
			<br><br>
			&nbsp;&nbsp;&nbsp;&nbsp;<b>Hora:</b> ${form.horaEmissaoCertidao}
			<br><br><br>
			Confira-os e tente novamente.
			</div>
			<br><br>
			<div class="botoes">
				<a href="javascript:history.back();">
                    <img src="${contexto}/WebContent/img/voltar.jpg" border="0"/>
				</a>
			</div>
	</tiles:put>
</tiles:insert> 