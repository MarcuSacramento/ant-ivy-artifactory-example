<%@include file="/WebContent/paginas/comum/Global.jspf"%>
<%
response.sendRedirect(pageContext.getAttribute("contexto") + "/abrirPesquisa/abrirEmissao.do");
//response.sendRedirect(pageContext.getAttribute("contexto") + "/WebContent/paginas/aplicacao/OrientacoesGerais.jsp");
%>