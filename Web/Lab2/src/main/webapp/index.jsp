<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="by.yaroslavesev.lab2.models.Point" %>
<%@ page import="java.time.LocalDate" %>
<%
  // Получаем список результатов из сессии или инициализируем новый
  ArrayList<Point> results = (ArrayList<Point>) session.getAttribute("results");
  if (results == null) {
    results = new ArrayList<>();
    session.setAttribute("results", results);
  }
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <!-- Мета-теги и стили -->
  <meta charset="UTF-8">
  <title>Laboratory №1</title>
  <style>
    <!-- Вставьте сюда ваши стили из предоставленного HTML-документа -->
    /* Ваш CSS код */
  </style>
</head>
<body>
<header>
  <!-- Шапка из предоставленного HTML-документа -->
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
  <!-- Форма ввода -->
  <form action="controller" method="get">
    <table class="main">
      <tr>
        <td>
          <table class="main__input-data">
            <!-- Поле ввода X -->
            <tr class="main__input-data__x">
              <td class="main__input-data__x__logo">
                <img src="images/letter-x.png" alt="X icon">
              </td>
              <td colspan="1">
                <label>
                  <input type="text" name="x" class="main__input-data__input-field" placeholder="Введите X (от -3 до 5)">
                </label>
              </td>
            </tr>
            <!-- Выбор Y -->
            <tr class="main__input-data__y">
              <td class="main__input-data__y__logo">
                <img src="images/letter-y.png" alt="Y icon">
              </td>
              <td colspan="2">
                <table>
                  <!-- Переиспользуем ваш HTML-код для радио-кнопок Y -->
                  <!-- ... (вставьте здесь радио-кнопки для Y) -->
                  <tr>
                    <td>
                      <input type="radio" id="yChoice1" name="yValue" value="-3" />
                      <label for="yChoice1">-3</label>
                    </td>
                    <td>
                      <input type="radio" id="yChoice2" name="yValue" value="-2" />
                      <label for="yChoice2">-2</label>
                    </td>
                    <td>
                      <input type="radio" id="yChoice3" name="yValue" value="-1" />
                      <label for="yChoice3">-1</label>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <input type="radio" id="yChoice4" name="yValue" value="0" />
                      <label for="yChoice4">0</label>
                    </td>
                    <td>
                      <input type="radio" id="yChoice5" name="yValue" value="1" />
                      <label for="yChoice5">1</label>
                    </td>
                    <td>
                      <input type="radio" id="yChoice6" name="yValue" value="2" />
                      <label for="yChoice6">2</label>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <input type="radio" id="yChoice7" name="yValue" value="3" />
                      <label for="yChoice7">3</label>
                    </td>
                    <td>
                      <input type="radio" id="yChoice8" name="yValue" value="4" />
                      <label for="yChoice8">4</label>
                    </td>
                    <td>
                      <input type="radio" id="yChoice9" name="yValue" value="5" />
                      <label for="yChoice9">5</label>
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
            <!-- Выбор R -->
            <tr class="main__input-data__r">
              <td class="main__input-data__r__logo">
                <img src="images/letter-r.png" alt="R icon">
              </td>
              <td colspan="1" class="r-buttons">
                <!-- Кнопки R -->
                <button type="button" data-value="1">1</button>
                <button type="button" data-value="1.5">1.5</button>
                <button type="button" data-value="2">2</button>
                <button type="button" data-value="2.5">2.5</button>
                <button type="button" data-value="3">3</button>
                <!-- Скрытое поле для выбранного R -->
                <input type="hidden" name="r" id="hiddenRValue">
              </td>
            </tr>
          </table>
        </td>
      </tr>
      <!-- Кнопка отправки -->
      <tr>
        <td>
          <button type="submit" class="submit-button" id="submitButton">Отправить</button>
        </td>
      </tr>
      <!-- SVG-область -->
      <tr class="centered-container">
        <td class="svg-container" rowspan="6">
          <!-- Вставьте ваш SVG-код здесь -->
          <!-- ... -->
          <svg xmlns="http://www.w3.org/2000/svg" width="420" height="420">
            <!-- Ваш SVG-код -->
            <!-- ... -->
            <circle id="pointer" r="5" cx="210" cy="210" fill-opacity="0.9" fill="red" stroke="firebrick" visibility="hidden"></circle>
          </svg>
        </td>
      </tr>
    </table>
  </form>
  <!-- Таблица результатов -->
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
    <%-- Выводим результаты из сессии --%>
    <%
      for (Point result : results) {
    %>
    <tr>
      <td><%= result.getX() %></td>
      <td><%= result.getY() %></td>
      <td><%= result.getR() %></td>
      <td><%= result.isInside() ? "Да" : "Нет" %></td>
      <td><%= LocalDate.now() %> ms</td>
    </tr>
    <%
      }
    %>
    </tbody>
  </table>
</main>
<footer>
  <!-- Футер из предоставленного HTML-документа -->
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
<!-- Ваши скрипты -->
<script>
  <!-- Интеграция вашего JavaScript-кода -->
  // Обработка кнопок R
  const buttons = document.querySelectorAll('.r-buttons button');
  let selectedR = null;

  buttons.forEach(button => {
    button.addEventListener('click', () => {
      buttons.forEach(btn => btn.classList.remove('active'));
      button.classList.add('active');
      selectedR = button.dataset.value;
      console.log('Selected R:', selectedR);
      // Обновляем скрытое поле с выбранным R
      document.getElementById('hiddenRValue').value = selectedR;
    });
  });

  // Валидация формы
  const submitButton = document.getElementById('submitButton');
  const xInput = document.querySelector('.main__input-data__input-field');
  const yInputs = document.querySelectorAll('input[name="yValue"]');

  function isValidX(xValue) {
    const numX = parseFloat(xValue);

    if (isNaN(numX)) {
      alert('X должен быть числом.');
      return false;
    }

    if (numX < -3 || numX > 5) {
      alert('X должен быть числом в диапазоне от -3 до 5.');
      return false;
    }

    if (numX === -3 || numX === 5) {
      if (xValue.includes('.')) {
        const decimalPart = xValue.split('.')[1];
        if (!/^[0]+$/.test(decimalPart)) {
          alert('Для -3 и 5 после точки должны быть только нули.');
          return false;
        }
      }
    }
    return true;
  }

  function getSelectedY() {
    let selectedY = null;
    yInputs.forEach(input => {
      if (input.checked) {
        selectedY = input.value;
      }
    });
    return selectedY;
  }

  submitButton.addEventListener('click', (event) => {
    const xValue = xInput.value.trim();
    const yValue = getSelectedY();
    const rValue = selectedR;

    if (!isValidX(xValue)) {
      event.preventDefault();
      return;
    }

    if (!xValue || !yValue || !rValue) {
      alert('Пожалуйста, заполните все поля.');
      event.preventDefault();
      return;
    }
  });
</script>
</body>
</html>
