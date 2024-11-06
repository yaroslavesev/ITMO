<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="by.yaroslavesev.lab2.models.Point" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.util.Locale" %>
<%
    ArrayList<Point> results = (ArrayList<Point>) session.getAttribute("results");
    if (results == null) {
        results = new ArrayList<>();
        session.setAttribute("results", results);
    }
    String lastX = (String) session.getAttribute("lastX");
    String lastY = (String) session.getAttribute("lastY");
    String lastR = (String) session.getAttribute("lastR");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Laboratory №1</title>
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
    <form action="controller" method="post">
        <table class="main">
            <tr>
                <td>
                    <table class="main__input-data">
                        <tr class="main__input-data__x">
                            <td class="main__input-data__x__logo">
                                <img src="images/letter-x.png" alt="X icon">
                            </td>
                            <td colspan="1">
                                <label>
                                    <input type="text" name="x" class="main__input-data__input-field"
                                           placeholder="Введите X (от -3 до 5)"
                                           value="<%= lastX != null ? lastX : "" %>">
                                </label>
                            </td>
                        </tr>
                        <tr class="main__input-data__y">
                            <td class="main__input-data__y__logo">
                                <img src="images/letter-y.png" alt="Y icon">
                            </td>
                            <td colspan="2">
                                <table>
                                    <tr>
                                        <td>
                                            <input type="radio" id="yChoice1" name="yValue" value="-3" <%= "-3".equals(lastY) ? "checked" : "" %>/>
                                            <label for="yChoice1">-3</label>
                                        </td>
                                        <td>
                                            <input type="radio" id="yChoice2" name="yValue" value="-2" <%= "-2".equals(lastY) ? "checked" : "" %>/>
                                            <label for="yChoice2">-2</label>
                                        </td>
                                        <td>
                                            <input type="radio" id="yChoice3" name="yValue" value="-1" <%= "-1".equals(lastY) ? "checked" : "" %>/>
                                            <label for="yChoice3">-1</label>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <input type="radio" id="yChoice4" name="yValue" value="0" <%= "0".equals(lastY) ? "checked" : "" %>/>
                                            <label for="yChoice4">0</label>
                                        </td>
                                        <td>
                                            <input type="radio" id="yChoice5" name="yValue" value="1" <%= "1".equals(lastY) ? "checked" : "" %>/>
                                            <label for="yChoice5">1</label>
                                        </td>
                                        <td>
                                            <input type="radio" id="yChoice6" name="yValue" value="2" <%= "2".equals(lastY) ? "checked" : "" %>/>
                                            <label for="yChoice6">2</label>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <input type="radio" id="yChoice7" name="yValue" value="3" <%= "3".equals(lastY) ? "checked" : "" %>/>
                                            <label for="yChoice7">3</label>
                                        </td>
                                        <td>
                                            <input type="radio" id="yChoice8" name="yValue" value="4" <%= "4".equals(lastY) ? "checked" : "" %>/>
                                            <label for="yChoice8">4</label>
                                        </td>
                                        <td>
                                            <input type="radio" id="yChoice9" name="yValue" value="5" <%= "5".equals(lastY) ? "checked" : "" %>/>
                                            <label for="yChoice9">5</label>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr class="main__input-data__r">
                            <td class="main__input-data__r__logo">
                                <img src="images/letter-r.png" alt="R icon">
                            </td>
                            <td colspan="1" class="r-buttons">
                                <button type="button" data-value="1" class="<%= "1".equals(lastR) ? "active" : "" %>">1</button>
                                <button type="button" data-value="1.5" class="<%= "1.5".equals(lastR) ? "active" : "" %>">1.5</button>
                                <button type="button" data-value="2" class="<%= "2".equals(lastR) ? "active" : "" %>">2</button>
                                <button type="button" data-value="2.5" class="<%= "2.5".equals(lastR) ? "active" : "" %>">2.5</button>
                                <button type="button" data-value="3" class="<%= "3".equals(lastR) ? "active" : "" %>">3</button>
                                <input type="hidden" name="r" id="hiddenRValue" value="<%= lastR != null ? lastR : "" %>">
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td>
                    <button type="submit" class="submit-button" id="submitButton">Отправить</button>
                </td>
            </tr>
            <tr class="centered-container">
                <td class="svg-container" rowspan="6">
                    <svg xmlns="http://www.w3.org/2000/svg" width="420" height="420">
                        <line x1="0" y1="210" x2="480" y2="210" stroke="#000720"></line>
                        <line x1="210" y1="0" x2="210" y2="420" stroke="#000720"></line>

                        <polygon points="420,210 415,215 415,205" fill="#000720" stroke="#000720"></polygon>
                        <polygon points="210,0 205,5 215,5" fill="#000720" stroke="#000720"></polygon>
                        <line x1="350" y1="208" x2="350" y2="212" stroke="#000720"></line>
                        <text x="345" y="200">R</text>

                        <line x1="280" y1="208" x2="280" y2="212" stroke="#000720"></line>
                        <text x="270" y="200">R/2</text>

                        <line x1="140" y1="208" x2="140" y2="212" stroke="#000720"></line>
                        <text x="130" y="200">-R/2</text>

                        <line x1="70" y1="208" x2="70" y2="212" stroke="#000720"></line>
                        <text x="60" y="200">-R</text>

                        <line x1="208" y1="70" x2="212" y2="70" stroke="#000720"></line>
                        <text x="217" y="75">R</text>

                        <line x1="208" y1="140" x2="212" y2="140" stroke="#000720"></line>
                        <text x="217" y="145">R/2</text>

                        <line x1="208" y1="280" x2="212" y2="280" stroke="#000720"></line>
                        <text x="217" y="285">-R/2</text>

                        <line x1="208" y1="350" x2="212" y2="350" stroke="#000720"></line>
                        <text x="217" y="355">-R</text>
                        <polygon points="210,210 70,210 210,70" fill-opacity="0.4" stroke="navy" fill="blue"></polygon>
                        <rect x="210" y="140" width="140" height="70" fill-opacity="0.4" stroke="navy" fill="blue"></rect>
                        <path d="M 210 210 L 70 210 A 140 140 0 0 0 210 350 Z" fill-opacity="0.4" stroke="navy" fill="blue"></path>
                        <circle id="pointer" r="5" cx="210" cy="210" fill-opacity="0.9" fill="red" stroke="firebrick" visibility="hidden"></circle>
                        <g id="points"></g>
                    </svg>
                </td>
            </tr>
        </table>
    </form>
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
            <td><%= LocalDate.now() %> ms</td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
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
<script src="js/script.js"></script>
<script>
    <% for (Point result : results) { %>
    drawPoint(<%= String.format(Locale.US, "%f", result.getX()) %>, <%= String.format(Locale.US, "%f", result.getY()) %>, <%= String.format(Locale.US, "%f", result.getR()) %>, <%= result.isInside() %>);
    <% } %>
</script>

</body>
</html>
