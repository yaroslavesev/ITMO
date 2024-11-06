const buttons = document.querySelectorAll('.r-buttons button');
let selectedR = null;

buttons.forEach(button => {
    button.addEventListener('click', () => {
        buttons.forEach(btn => btn.classList.remove('active'));
        button.classList.add('active');
        selectedR = button.dataset.value;
        console.log('Selected R:', selectedR);
        document.getElementById('hiddenRValue').value = selectedR;
    });
});

const submitButton = document.getElementById('submitButton');
const xInput = document.querySelector('.main__input-data__input-field');
const yInputs = document.querySelectorAll('input[name="yValue"]');
const resultBody = document.getElementById('resultBody');

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

function sendData(xValue, yValue, rValue) {
    console.log('xValue:', xValue);
    console.log('yValue:', yValue);
    console.log('rValue:', rValue);

    if (!isValidX(xValue)) {
        submitButton.classList.add('error');
        submitButton.classList.remove('success');
        console.log('X должен быть числом от -3 до 5');
        return;
    }

    if (!xValue || !yValue || !rValue) {
        submitButton.classList.add('error');
        submitButton.classList.remove('success');
        console.log('Ошибка валидации');
        return;
    }

    fetch('controller', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'X-Requested-With': 'XMLHttpRequest',
        },
        body: `x=${encodeURIComponent(xValue)}&y=${encodeURIComponent(yValue)}&r=${encodeURIComponent(rValue)}`
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Сетевая ошибка');
            }
            return response.json();
        })
        .then(data => {
            console.log('Ответ от сервера:', data);
            submitButton.classList.remove('error');
            submitButton.classList.add('success');
            displayResultInTable(data, xValue, yValue, rValue);
        })
        .catch(error => {
            console.error('Ошибка отправки данных:', error);
            submitButton.classList.add('error');
            submitButton.classList.remove('success');
        });
}

function drawPoint(x, y, r, isInside) {
    const svg = document.querySelector('svg');
    const pointsGroup = document.getElementById('points');
    const centerX = 210;
    const centerY = 210;
    const scale = 140 / r;
    const pointX = centerX + x * scale;
    const pointY = centerY - y * scale;
    const point = document.createElementNS('http://www.w3.org/2000/svg', 'circle');
    point.setAttribute('cx', pointX);
    point.setAttribute('cy', pointY);
    point.setAttribute('r', 5);
    point.setAttribute('fill', isInside ? 'green' : 'red');
    point.setAttribute('stroke', 'black');
    pointsGroup.appendChild(point);
}

function displayResultInTable(data, xValue, yValue, rValue) {
    let resultHTML = '';

    resultHTML += `
        <tr>
            <td>${xValue}</td>
            <td>${yValue}</td>
            <td>${rValue}</td>
            <td>${data.answer ? 'Да' : 'Нет'}</td>
            <td>${'1 мс'}</td>
        </tr>
    `;

    resultBody.innerHTML += resultHTML;
    drawPoint(xValue, yValue, rValue, data.answer);
}

submitButton.addEventListener('click', (event) => {
    event.preventDefault();
    const xValue = xInput.value.trim();
    const yValue = getSelectedY();
    sendData(xValue, yValue, selectedR);
});

const svg = document.querySelector('svg');
svg.addEventListener('click', function(event) {
    if (!selectedR) {
        alert('Сначала выберите значение R.');
        return;
    }
    const rect = svg.getBoundingClientRect();
    const x = event.clientX - rect.left - 210;
    const y = 210 - (event.clientY - rect.top);
    const scale = selectedR / 140;
    const xValue = (x * scale).toFixed(2);
    const yValue = (y * scale).toFixed(2);
    sendData(xValue, yValue, selectedR);
});



