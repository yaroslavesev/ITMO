const buttons = document.querySelectorAll('.r-buttons button');
let selectedR = null;

buttons.forEach(button => {
    button.addEventListener('click', () => {
        buttons.forEach(btn => btn.classList.remove('active'));
        button.classList.add('active');
        selectedR = button.dataset.value;
        console.log('Selected R:', selectedR);
    });
});


const submitButton = document.getElementById('submitButton');
const xInput = document.querySelector('.main__input-data__input-field');
const yInputs = document.querySelectorAll('input[name="yValue"]');
const resultBody = document.getElementById('resultBody');

function isValidX(xValue) {

    const numX = parseFloat(xValue);


    if (isNaN(numX)) {
        console.log('X должен быть числом.');
        return false;
    }


    if (numX < -3 || numX > 5) {
        console.log('X должен быть числом в диапазоне от -3 до 5.');
        return false;
    }

    if (numX === -3 || numX === 5) {
        if (xValue.includes('.')) {
            const decimalPart = xValue.split('.')[1];
            if (!/^[0]+$/.test(decimalPart)) {
                console.log('Для -3 и 5 после точки должны быть только нули.');
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
    console.log('Selected Y:', selectedY);
    return selectedY;
}

function sendData() {
    const xValue = xInput.value.trim();
    const yValue = getSelectedY();
    const rValue = selectedR;

    console.log('xValue:', xValue);
    console.log('yValue:', yValue);
    console.log('rValue:', rValue);

    if (!(isValidX(xValue))) {
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

    fetch('/fcgi-bin/Server.jar', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
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
            document.getElementById('pointer').setAttribute('visibility', 'hidden');
        });
}

function drawPoint(x, y, r, isInside) {
    const svg = document.querySelector('svg');
    const pointer = document.getElementById('pointer');
    const centerX = 210
    const centerY = 210;
    const scale = 142 / r;
    const pointX = centerX + x * scale;
    const pointY = centerY - y * scale;
    pointer.setAttribute('cx', pointX);
    pointer.setAttribute('cy', pointY);
    pointer.setAttribute('fill', isInside ? 'green' : 'red');
    pointer.setAttribute('visibility', 'visible');
}

function displayResultInTable(data, xValue, yValue, rValue) {
    let resultHTML = '';

    resultHTML += `
        <tr>
            <td>${xValue}</td>
            <td>${yValue}</td>
            <td>${rValue}</td>
            <td>${data.answer ? 'Да' : 'Нет'}</td>
            <td>${data.executionTime} ms</td>
        </tr>
        `;

    resultBody.innerHTML += resultHTML;
    drawPoint(xValue, yValue, rValue, data.answer);
}

submitButton.addEventListener('click', (event) => {
    event.preventDefault();
    sendData();
});