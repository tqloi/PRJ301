<%-- 
    Document   : list
    Created on : Jun 10, 2024, 6:02:28 PM
    Author     : LENOVO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="Model.Fruit" %>

<section class="jumbotron text-center">
    <h1 class="my-4">OURS PRODUCTS
        <c:if test="${sessionScope.username == 'admin'}">
            <a style="margin: 0px 0px 10px 0px" href="addProduct.jsp" class="btn btn-success ml-3">ADD NEW PRODUCT</a>
        </c:if>
    </h1>
</section>

<table class="table">
    <thead>
        <tr>
            <th scope="col">Ảnh</th>
            <th scope="col">Tên</th>
            <th scope="col">Mô tả</th>
            <th scope="col">Giá</th>
            <th scope="col"></th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="fruit" items="${requestScope.fruits}">
            <tr>
                <td>
                    <img src="images/${fn:replace(fruit.productName, ' ', '')}.jpg" class="product-img" alt="${fruit.productName}" style="width: 250px; height: 200px;">
                </td>
                <td>${fruit.productName}</td>
                <td>${fruit.description}</td>
                <td>$${fruit.price}/kg</td>
                <td>
                    <c:if test="${sessionScope.username == 'admin'}">
                        <div class="row">
                            <div class="col">
                                <form action="LoginServlet" class="d-inline">
                                    <input type="hidden" name="productId" value="${fruit.productId}">
                                    <button type="submit" class="btn btn-warning">Edit</button>
                                </form>
                                <form action="ProductServlet" method="post" class="d-inline">
                                    <input type="hidden" name="productId" value="${fruit.productId}">
                                    <input type="hidden" name="method" value="delete">
                                    <button type="submit" class="btn btn-danger">Delete</button>
                                </form>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${sessionScope.username != 'admin'}">
                        <a href="#" class="btn btn-primary">Add to Cart</a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
