<%@include file="/WebContent/paginas/comum/Global.jspf"%>

<jsp:directive.page import="br.gov.mj.dnn.pojo.*" />
<jsp:directive.page import="java.util.*" />

<tiles:insert definition=".mainLayout">

    <tiles:put name="subtitulo" type="string">ACOMPANHENTO DE SOLICITAÇÃO DE CNN</tiles:put>

    <tiles:put name="javascript" type="string">
        <script>
	        window.onload = function() {
	            var span = document.getElementById('menuConsultarSolicitacao');
	            span.innerHTML = "<b>" + span.innerHTML + "</b>"; 
	        };
        </script>
    </tiles:put>

	<tiles:put name="areaTrabalho" type="string">
        <div class="texto">
            <p align="center"><b>Solicitação de CNN Indeferida</b></p>
			<br><br>
			<b>Motivo:</b> ${form.motivoCertidaoIndeferida}<br><br>
			<b>Nome do Interessado:</b> ${form.nomeInteressado}<br>
			<b>Nome do Pai do requerido:</b> ${form.nomePai}<br>
			<b>Nome da Mãe do requerido:</b> ${form.nomeMae}<br>
			<b>Data de Nascimento do requerido:</b> ${form.dataNascimento}<br><br>
			<b>Número da Certidão:</b> ${form.numeroCertidao}
			<br><br><br>
			<div class="botoes">
                <a href="javascript:window.print();">
                    <img src="${contexto}/WebContent/img/imprimir.jpg" border="0"/>
				</a>
				<a href="${contexto}/abrirPesquisa/abrirAutenticacao.do">
				    <img src="${contexto}/WebContent/img/voltar.jpg" border="0"/>
				</a>
			</div>
		</div>
	</tiles:put>
</tiles:insert> 