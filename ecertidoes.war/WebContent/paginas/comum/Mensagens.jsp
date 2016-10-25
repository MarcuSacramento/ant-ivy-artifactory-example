<%@include file="/WebContent/paginas/comum/Global.jspf"%>

<center>
    <div style="font-family:Verdana; color: #CC0000; font-size: 12px">
	   <strong><html:errors footer="errors.barra.atencao"/></strong>
	</div>

	<html:messages id="msg" message="true" footer="msg.cadastrosucesso">
        <div style="color: #3399CC; font-size: 12px">
            <strong><c:out value="${msg}"/></strong>
	    </div>
	</html:messages>
</center>
