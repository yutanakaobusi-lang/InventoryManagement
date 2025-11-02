<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>部品・テープ入庫依頼票（印刷用）</title>
		<jsp:include page="/WEB-INF/jsp/header.jsp" />
    
</head>
<body>

    <h1>部品・テープ入庫依頼票</h1>

    <table>
        <tr>
            <th>部品名</th>
            <th>${dateLabels[0]}</th>
            <th>${dateLabels[1]}</th>
            <th>${dateLabels[2]}</th>
            <th>${dateLabels[3]}</th>
        </tr>

        <c:forEach var="entry" items="${requestMap}">
            <c:set var="name" value="${entry.key}" />
            <c:set var="quantity" value="${quantityMap[entry.key]}" />
            <tr>
                <td>
                    <c:choose>
                        <c:when test="${fn:startsWith(name, '部品')}">
                            ${name}（${quantity}個/ケース）
                        </c:when>
                        <c:otherwise>
                            ${name}（${quantity}個/束）
                        </c:otherwise>
                    </c:choose>
                </td>
                <c:forEach var="val" items="${entry.value}">
                    <td>
                        <c:choose>
                            <c:when test="${fn:startsWith(name, '部品')}">
                                ${val}ケース
                            </c:when>
                            <c:otherwise>
                                ${val}束
                            </c:otherwise>
                        </c:choose>
                    </td>
                </c:forEach>
            </tr>
        </c:forEach>
    </table>

    <br>
    <input type="button" value="印刷する" onclick="window.print();">
    <br><br>
    <form action="requestArrival" method="get">
        <input type="submit" value="入力画面へ戻る">
    </form>
    <br>

		<form action="index.jsp" method="get">
			<input type="submit" value="メニューへ戻る">
		</form>

</body>
</html>
