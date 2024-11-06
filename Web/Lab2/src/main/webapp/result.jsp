<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="by.yaroslavesev.lab2.models.Point" %>
<%
    Point currentResult = (Point) request.getAttribute("currentResult");
    ArrayList<Point> results = (ArrayList<Point>) session.getAttribute("results");
    if (results == null) {
        results = new ArrayList<>();
    }
    if (currentResult != null) {
        results.add(currentResult);
        session.setAttribute("results", results);
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Результат</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
        }

        table {
            width: 100%;
            table-layout: fixed;
            border-collapse: collapse;
        }

        .main__input-data {
            margin-top: 100px;
        }

        .main__input-data img {
            width: 65px;
            height: auto;
            max-width: 100%;
            padding-left: 25%;
        }

        .header {
            background-color: black;
            color: white;
            position: relative;
        }

        .footer {
            position: fixed;
            left: 0;
            bottom: 0;
            width: 100%;
            background-color: black;
            color: white;
            text-align: center;
            padding: 10px 0;
            opacity: 0;
            visibility: hidden;
            transition: opacity 0.5s ease, visibility 0.5s ease;
        }

        .footer.visible {
            opacity: 1;
            visibility: visible;
        }

        .footer__logo img {
            width: 80px;
            height: auto;
            max-width: 100%;
        }

        .header__logo img {
            width: 80px;
            height: auto;
            max-width: 100%;
        }

        .footer__year {
            padding: 0;
            margin: 0;
            vertical-align: top;
            display: inline-block;
            font-size: 10px;
            font-weight: bold;
        }

        .footer__logo {
            padding-bottom: 0;
            margin: 0;
        }

        .header__details {
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            text-align: center;
            padding-left: 10px;
            font-family: fantasy;
            font-weight: normal;
            font-style: normal;
        }

        td {
            padding: 10px;
        }

        .main__input-data__input-field {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 16px;
            transition: border-color 0.3s ease, box-shadow 0.3s ease;
        }

        .main__input-data__input-field:hover,
        .main__input-data__input-field:focus {
            border-color: black;
            outline: none;
        }

        .header__details__lab-number {
            font-size: 25px;
            margin-bottom: 10px;
            font-weight: bolder;
        }

        .header__details__student-name {
            padding-bottom: 10px;
        }

        .main__input-data td {
            text-align: center;
        }

        .main__input-data__y table {
            width: auto;
            margin: 0 auto;
        }

        .main__input-data__y td {
            padding: 5px;
        }

        .main__input-data__y input[type="radio"] {
            margin: 0;
        }

        .main__input-data__y table {
            margin-left: 0;
        }

        .main__input-data {
            vertical-align: middle;
        }

        .r-buttons {
            text-align: left;
            margin-left: 0;
        }

        .r-buttons button {
            margin-right: 5px;
            padding: 10px;
            background-color: white;
            border: 3px solid black;
            cursor: pointer;
            font-size: 16px;
            width: 60px;
            border-radius: 7px;
        }

        .r-buttons button.active {
            background-color: black;
            color: white;
            border: 1px solid white;
            border-radius: 7px;
        }

        .centered-container {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .svg-container {
            width: 500px;
            height: 500px;
            border: 5px solid black;
            display: flex;
            justify-content: center;
            align-items: center;
            background-color: white;
            border-radius: 10px;
        }

        .submit-button {
            background-color: black;
            color: white;
            border: 2px solid black;
            padding: 10px 20px;
            font-size: 16px;
            cursor: pointer;
            display: block;
            margin: 20px auto;
            transition: all 0.3s ease;
            border-radius: 8px;
        }

        .submit-button:hover {
            background-color: grey;
            color: black;
            border-color: black;
            border-radius: 8px;
        }

        .submit-button.success {
            border-color: white;
            color: white;
        }

        .submit-button.error {
            border-color: red;
            color: red;
        }

        @media (max-width: 600px) {
            .header__logo img {
                width: 50px;
            }

            .header__details {
                font-size: 10px;
            }

            .header__details__lab-number {
                font-size: 12px;
                margin-top: 30px;
            }

            .main__input-data {
                margin-top: 50px;
            }

            .main__input-data img {
                width: 45px;
            }

            .main__input-data__x input::placeholder {
                font-size: 13px;
            }

            @media (max-width: 475px) {
                .main__input-data__x input::placeholder {
                    font-size: 9px;
                }
            }
        }
    </style>
</head>
<body>
<header>
    <table class="header">
        <tr>
            <td class="header__logo">
                <img src="images/vt_duck.png" alt="VT duck">
            </td>
            <td class="header__details">
                <p class="header__details__lab-number">Лабораторная работа №1</p>
                <p class="header__details__variant">Вариант №408599</p>
                <p class="header__details__student-name">Есев Ярослав P3215</p>
            </td>
        </tr>
    </table>
</header>
<main>
    <table id="resultTable" border="1" style="width:100%; margin-top: 20px;">
        <thead>
        <tr>
            <th>X</th>
            <th>Y</th>
            <th>R</th>
            <th>Попадание</th>
            <th>Время</th>
        </tr>
        </thead>
        <tbody id="resultBody">
        <%
            for (Point result : results) {
        %>
        <tr>
            <td><%= result.getX() %>
            </td>
            <td><%= result.getY() %>
            </td>
            <td><%= result.getR() %>
            </td>
            <td><%= result.isInside() ? "Да" : "Нет" %>
            </td>
            <td><%=1%> ms</td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
    <a href="index.jsp">Новый запрос</a>
</main>
<footer>
    <table class="footer">
        <tr>
            <td class="footer__logo">
                <img src="images/itmo_logo.png" alt="ITMO logo">
            </td>
        </tr>
        <tr>
            <td class="footer__year">
                <p>2024</p>
            </td>
        </tr>
    </table>
</footer>
</body>
</html>
