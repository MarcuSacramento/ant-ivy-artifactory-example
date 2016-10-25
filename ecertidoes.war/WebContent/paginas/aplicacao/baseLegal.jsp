<%@include file="/WebContent/paginas/comum/Global.jspf"%>

<tiles:insert definition=".mainLayout">
    <c:set var="menuApresentacao" value="font-weight: bold"/>

    <tiles:put name="javascript" type="string">
        <script>
            window.onload = function() {
                var span = document.getElementById('menuBaseLegal');
                span.innerHTML = "<b>" + span.innerHTML + "</b>"; 
            };
        </script>
    </tiles:put>

    <tiles:put name="subtitulo" type="string">PORTARIA Nº 018, DE 1º DE JULHO DE 2009.</tiles:put>

    <tiles:put name="areaTrabalho" type="string">
        <div class="texto">
            <p align="right">
                Dispõe sobre a emissão eletrônica de Certidões Negativas <br> 
                e Positivas de Naturalização pela página eletrônica <br>
                Institucional do Ministério da Justiça.<br>
            </p>
            <br>
            <p>
                <b>O SECRETÁRIO NACIONAL DE JUSTIÇA</b>, no uso de suas atribuições legais, e  <br><br>
                <b>CONSIDERANDO</b> o disposto no art. 5º, inciso XXXIV, b, da Constituição da 
                República Federativa do Brasil;
                <br><br>
                <b>CONSIDERANDO</b> os princípios constitucionais que regem a Administração Pública;
                <br><br>
                <b>CONSIDERANDO</b> o disposto no inciso III, art. 8º, Anexo I, do Decreto nº. 6.061, 
                de 15 de março de 2007, que aprova a Estrutura Regimental e o Quadro Demonstrativo 
                dos Cargos em Comissão e das Funções Gratificadas do Ministério da Justiça, e dá 
                outras providências;
                <br><br>
                <b>CONSIDERANDO</b> o disposto no inciso V do Artigo 6º da Portaria MJ nº. 1516, 
                de 12 de setembro de 2006, que aprova o Código de Ética dos Agentes Públicos do 
                Ministério da Justiça;
                <br><br>
                <b>RESOLVE:</b>
                <br><br>
                Art. 1º   Fica instituída a emissão de Certidões Negativas ou Positivas de 
                Naturalização por meio da página eletrônica Institucional do Ministério da Justiça.
                <br><br>
                Parágrafo único.  Compete ao Departamento de Estrangeiros da Secretaria Nacional 
                de Justiça a expedição das Certidões previstas nesta Portaria.
                <br><br>
                Art. 2º  As Certidões deverão ser requeridas mediante o preenchimento de 
                formulário próprio, disponível no endereço eletrônico do Ministério da Justiça.
                <br><br>
                Art. 3º  Excetuando-se o nome dos genitores do requerido, a falta de preenchimento 
                de quaisquer dos dados constantes do formulário a que se refere o artigo anterior 
                obstará a emissão do documento.
                <br><br>
                Art. 4º  Os dados informados são de responsabilidade exclusiva do requerente, 
                ficando este responsável pela eventual inexatidão deles decorrentes.
                <br><br>
                Art. 5º  A expedição das Certidões deverá ser confirmada no endereço eletrônico do 
                Ministério da Justiça, conforme instruções constantes em campo próprio do 
                referido documento.
                <br><br>
                Art. 6º  Fica o Departamento de Estrangeiros da Secretaria Nacional de Justiça 
                autorizado a dirimir os casos omissos e as situações especiais decorrentes da 
                prestação do serviço de expedição das Certidões a que se refere esta Portaria.
                <br><br>
                Art. 7º  A Certidão Negativa ou Positiva de Naturalização serve como instrumento 
                legal para todos os fins de direito, respeitadas as exigências inerentes à 
                finalidade do documento.
                <br><br>
                Art. 8º  Esta Portaria entra em vigor na data de sua publicação. 
                <br><br>
                <center><b>ROMEU TUMA JÚNIOR</b></center>
            </p>
        </div>
        <br>
        <div class="botoes">
            <a href="javascript:history.back();">
                <img src="${contexto}/WebContent/img/voltar.jpg" border="0" />
            </a>
        </div>
    </tiles:put>
</tiles:insert>
