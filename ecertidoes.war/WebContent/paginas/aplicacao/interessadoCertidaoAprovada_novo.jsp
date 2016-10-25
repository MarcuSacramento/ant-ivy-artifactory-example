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

    <tiles:put name="subtitulo" type="string">AUTENTICAÇÃO DE CERTIDÃO ${form.tipoCertidao} DE NATURALIZAÇÃO</tiles:put>

	<tiles:put name="areaTrabalho" type="string">
		    <br />
			<!-- PÁGINAS DA APLICAÇÃO -->		
				<div class="texto">
				    <p>
				        O Departamento de Estrangeiros, da Secretaria Nacional de Justiça, confere 
				        autenticidade à Certidão ${form.tipoCertidao} de Naturalização <b> nº 
				        ${form.numeroCertidao}</b>, emitida, via Internet, 
				        em <b>${form.dataEmissaoCertidao}</b>, às <b>${form.horaEmissaoCertidao}</b>, 
				        na forma da Portaria nº 18 de 01 de junho de 2009, a partir dos critérios 
				        abaixo informados:
				    </p>
					<br>
					<!-- b>DADOS DO REQUERENTE</b -->
					<!-- br -->
					<!-- b>Nome Interessado:</b> ${form.nomeInteressado}<br -->
					<!-- b>Sexo Interessado:</b> ${form.sexoRequerente}<br -->
					<!-- b>Grau Parentesco:</b> ${form.grauParentesco}<br -->
					<br>
                    <b>DADOS DO REQUERIDO</b>
                    <br>
					<b>Nome:</b> ${form.todosOsNomes}<br>
					<!-- b>Sexo:</b> ${form.sexoRequerido}<br -->
					<!-- b>Nome do Pai:</b> ${form.nomePai}<br -->
					<b>Nome da Mãe:</b> ${form.nomeMae}<br>
					<b>Data de Nascimento:</b> ${form.dataNascimento}<br>
					<!-- b>País de Nascimento:</b> ${form.naturalidade}<br -->
					<br><br><br><br>
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