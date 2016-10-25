<%@include file="/WebContent/paginas/comum/Global.jspf"%>

<jsp:directive.page import="br.gov.mj.dnn.pojo.*" />
<jsp:directive.page import="java.util.*" />

<tiles:insert definition=".mainLayout">
	
    <tiles:put name="subtitulo" type="string">
        COMPROVANTE DE ENCAMINHAMENTO DE SOLICITAÇÃO DE CERTIDÃO NEGATIVA DE NATURALIZAÇÃO
    </tiles:put>
	
	<tiles:put name="javascript" type="string">
        <script>
            window.onload = function() {
                var span = document.getElementById('menuEmitirCNN');
                span.innerHTML = "<b>" + span.innerHTML + "</b>"; 
            };
        </script>
	</tiles:put>

	<tiles:put name="areaTrabalho" type="string">
			<!-- PÁGINAS DA APLICAÇÃO -->	
				<br>
				<div class="texto">
					Sua solicitação formalizada em <b>${form.dataSolicitacao}</b>, 
					às <b>${form.horaSolicitacao}</b>, foi encaminhada ao Departamento de Estrangeiros 
					da Secretaria Nacional de Justiça e possui o seguinte número de Protocolo: <b>nº ${form.numeroProtocolo}</b>.
					<br><br><br>
					Os dados constantes em nossos registros para pesquisa são os seguintes:
					<br><br>
					<b>DADOS DO REQUERENTE</b>
					<br><br>
					&nbsp;&nbsp;&nbsp;&nbsp;<b>Nacionalidade:</b> ${form.labelNacionalidade}<br>
					&nbsp;&nbsp;&nbsp;&nbsp;<b>Nome do Interessado:</b> ${form.nomeRequerente}<br>
					&nbsp;&nbsp;&nbsp;&nbsp;<b>Sexo:</b> ${form.sexoRequerente}<br>					
					&nbsp;&nbsp;&nbsp;&nbsp;<b>Parentesco com o requerido:</b> ${form.grauParentesco}<br>					
					<c:if test="${form.cpf != NULL}">
						&nbsp;&nbsp;&nbsp;&nbsp;<b>CPF:</b> ${form.cpf}<br>
					</c:if>
					<c:if test="${form.passaporte != NULL}">					
						&nbsp;&nbsp;&nbsp;&nbsp;<b>Nº do Passaporte:</b> ${form.passaporte}<br>
					</c:if>
					<c:if test="${form.rneRgRequerente != NULL}">
						&nbsp;&nbsp;&nbsp;&nbsp;<b>RG ou RNE:</b> ${form.rneRgRequerente}<br>
					</c:if>
					&nbsp;&nbsp;&nbsp;&nbsp;<b>País do Endereço:</b> ${form.labelPais}<br>
					<c:if test="${form.enderecoPais != 'BR'}">
						&nbsp;&nbsp;&nbsp;&nbsp;<b>Endereço Completo:</b> ${form.endereco}<br>
					</c:if>
					<c:if test="${form.enderecoPais == 'BR'}">
						&nbsp;&nbsp;&nbsp;&nbsp;<b>Endereço Completo:</b> ${form.endereco}<br>
						&nbsp;&nbsp;&nbsp;&nbsp;<b>Número:</b> ${form.enderecoNumero}<br>
						&nbsp;&nbsp;&nbsp;&nbsp;<b>Complemento:</b> ${form.enderecoComplemento}<br>
						&nbsp;&nbsp;&nbsp;&nbsp;<b>UF do Endereço:</b> ${form.labelUf}<br>
						&nbsp;&nbsp;&nbsp;&nbsp;<b>Cidade:</b> ${form.labelCidade}<br>
						&nbsp;&nbsp;&nbsp;&nbsp;<b>Bairro:</b> ${form.enderecoBairro}<br>
						&nbsp;&nbsp;&nbsp;&nbsp;<b>CEP:</b> ${form.cep}<br>
					</c:if>
					
					
					&nbsp;&nbsp;&nbsp;&nbsp;<b>E-mail:</b> ${form.email}<br>
					&nbsp;&nbsp;&nbsp;&nbsp;<b>Telefone:</b>(${form.ddi})(${form.ddd})${form.telefone}<br>
					&nbsp;&nbsp;&nbsp;&nbsp;<b>Motivo da Solicitação:</b> ${form.motivo}<br>		
					&nbsp;&nbsp;&nbsp;&nbsp;<b>Número de Procotolo no MJ:</b> ${form.protocoloMj}<br>
					<br><br>
					<b>DADOS DO REQUERIDO</b>
					<br><br>
					&nbsp;&nbsp;&nbsp;&nbsp;<b>Nome:</b> ${form.todosOsNomes}<br>
					&nbsp;&nbsp;&nbsp;&nbsp;<b>Sexo:</b> ${form.sexoRequerido}<br>					
					&nbsp;&nbsp;&nbsp;&nbsp;<b>Nome do Pai:</b> ${form.nomePaiRequerido}<br>
					&nbsp;&nbsp;&nbsp;&nbsp;<b>Nome da Mãe:</b> ${form.nomeMaeRequerido}<br>
					&nbsp;&nbsp;&nbsp;&nbsp;<b>Data ou Ano de Nascimento:</b> ${form.dataNascimentoRequerido}<br>
					&nbsp;&nbsp;&nbsp;&nbsp;<b>País de Nascimento:</b> ${form.naturalidadeRequerido}<br>
					&nbsp;&nbsp;&nbsp;&nbsp;<b>Estado Civil:</b> ${form.estadoCivilRequerido}<br>
					&nbsp;&nbsp;&nbsp;&nbsp;<b>Data ou Ano de chegada no Brasil:</b> ${form.dataChegadaBrasil}<br>
					&nbsp;&nbsp;&nbsp;&nbsp;<b>RNE ou RG:</b> ${form.rneRg}<br>
					&nbsp;&nbsp;&nbsp;&nbsp;<b>Nº carteira Modelo 19:</b> ${form.carteiraModelo}<br>	
					&nbsp;&nbsp;&nbsp;&nbsp;<b>Se falecido, data ou ano do Óbito:</b> ${form.dataObito}<br>						
					&nbsp;&nbsp;&nbsp;&nbsp;<b>Observação:</b> ${form.observacao}<br>	
					
                    <br><br>

                    <p>Para seu controle será encaminhado e-mail para o endereço eletrônico informado. 
                    Sugere-se imprimir esta página para acompanhamento ou anotar o nº do seu protocolo, 
                    caso haja incorreções no endereço eletrônico ou este não tenha sido informado.</p>

                    <p>Em breve atenderemos sua solicitação, com o encaminhamento da respectiva 
                    resposta para o endereço ou e-mail cadastrado. De qualquer forma, você poderá 
                    acompanhar o andamento de sua solicitação no menu "Consultar Solicitação" desta 
                    página eletrônica.</p>

                    <p>Para mais informações entre em contato com a Central de Atendimento da 
                    Secretaria Nacional de Justiça por meio do telefone: (55) (61) 2025-3232 ou do 
                    e-mail <a href="mailto:estrangeiros@mj.gov.br">estrangeiros@mj.gov.br.</a></p> 

					<div class="botoes">
						<a href="javascript:window.print();">
							<img src="${contexto}/WebContent/img/imprimir.jpg" border="0"/>
						</a>
						<a href="${contexto}/abrirPesquisa/abrirEmissao.do">
							<img src="${contexto}/WebContent/img/voltar.jpg" border="0"/>
						</a>
					</div>
				</div>
	</tiles:put>
</tiles:insert> 