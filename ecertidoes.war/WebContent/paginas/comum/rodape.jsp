<%@include file="/WebContent/paginas/comum/Global.jspf"%>

<div>Versão do sistema: <bean:message key="build.version"/></div>
<div>Versão do Comp DNN: <%= request.getAttribute("versaoCompMjDNN") %></div>

<br /><br />