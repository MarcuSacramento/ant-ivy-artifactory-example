<%@include file="/WebContent/paginas/comum/Global.jspf"%>

<jsp:directive.page contentType="text/html; charset=UTF-8" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
		<link href="${contexto}/WebContent/css/geral.css" rel="stylesheet" type="text/css" />
        <script>
            var contexto = "${contexto}";
        </script>
        <script language="javascript" src="${contexto}/WebContent/js/view.js" type="text/javascript"></script>
        <tiles:insert attribute="javascript" flush="true"/>
    </head>
    <body>
        <tiles:insert attribute="menu" flush="false"/>
        <div class="linha">
            <div class="subTitulo" align="center"><tiles:insert attribute="subtitulo" flush="true"/></div>
        </div>
        <div><tiles:insert attribute="mensagens" flush="false"/></div>
        <tiles:insert attribute="areaTrabalho" flush="true"/>
        
        <script language="javascript" src="${contexto}/WebContent/js/jquery.min.js" type="text/javascript"></script>
        <script language="javascript" src="${contexto}/WebContent/js/ecertidoes.js" type="text/javascript"></script>
        
        <tiles:insert attribute="rodape" flush="true"/>
     </body>
</html>