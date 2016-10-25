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

    <tiles:put name="subtitulo" type="string">PORTARIA N� 018, DE 1� DE JULHO DE 2009.</tiles:put>

    <tiles:put name="areaTrabalho" type="string">
        <div class="texto">
            <p align="right">
                Disp�e sobre a emiss�o eletr�nica de Certid�es Negativas <br> 
                e Positivas de Naturaliza��o pela p�gina eletr�nica <br>
                Institucional do Minist�rio da Justi�a.<br>
            </p>
            <br>
            <p>
                <b>O SECRET�RIO NACIONAL DE JUSTI�A</b>, no uso de suas atribui��es legais, e  <br><br>
                <b>CONSIDERANDO</b> o disposto no art. 5�, inciso XXXIV, b, da Constitui��o da 
                Rep�blica Federativa do Brasil;
                <br><br>
                <b>CONSIDERANDO</b> os princ�pios constitucionais que regem a Administra��o P�blica;
                <br><br>
                <b>CONSIDERANDO</b> o disposto no inciso III, art. 8�, Anexo I, do Decreto n�. 6.061, 
                de 15 de mar�o de 2007, que aprova a Estrutura Regimental e o Quadro Demonstrativo 
                dos Cargos em Comiss�o e das Fun��es Gratificadas do Minist�rio da Justi�a, e d� 
                outras provid�ncias;
                <br><br>
                <b>CONSIDERANDO</b> o disposto no inciso V do Artigo 6� da Portaria MJ n�. 1516, 
                de 12 de setembro de 2006, que aprova o C�digo de �tica dos Agentes P�blicos do 
                Minist�rio da Justi�a;
                <br><br>
                <b>RESOLVE:</b>
                <br><br>
                Art. 1�   Fica institu�da a emiss�o de Certid�es Negativas ou Positivas de 
                Naturaliza��o por meio da p�gina eletr�nica Institucional do Minist�rio da Justi�a.
                <br><br>
                Par�grafo �nico.  Compete ao Departamento de Estrangeiros da Secretaria Nacional 
                de Justi�a a expedi��o das Certid�es previstas nesta Portaria.
                <br><br>
                Art. 2�  As Certid�es dever�o ser requeridas mediante o preenchimento de 
                formul�rio pr�prio, dispon�vel no endere�o eletr�nico do Minist�rio da Justi�a.
                <br><br>
                Art. 3�  Excetuando-se o nome dos genitores do requerido, a falta de preenchimento 
                de quaisquer dos dados constantes do formul�rio a que se refere o artigo anterior 
                obstar� a emiss�o do documento.
                <br><br>
                Art. 4�  Os dados informados s�o de responsabilidade exclusiva do requerente, 
                ficando este respons�vel pela eventual inexatid�o deles decorrentes.
                <br><br>
                Art. 5�  A expedi��o das Certid�es dever� ser confirmada no endere�o eletr�nico do 
                Minist�rio da Justi�a, conforme instru��es constantes em campo pr�prio do 
                referido documento.
                <br><br>
                Art. 6�  Fica o Departamento de Estrangeiros da Secretaria Nacional de Justi�a 
                autorizado a dirimir os casos omissos e as situa��es especiais decorrentes da 
                presta��o do servi�o de expedi��o das Certid�es a que se refere esta Portaria.
                <br><br>
                Art. 7�  A Certid�o Negativa ou Positiva de Naturaliza��o serve como instrumento 
                legal para todos os fins de direito, respeitadas as exig�ncias inerentes � 
                finalidade do documento.
                <br><br>
                Art. 8�  Esta Portaria entra em vigor na data de sua publica��o. 
                <br><br>
                <center><b>ROMEU TUMA J�NIOR</b></center>
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
