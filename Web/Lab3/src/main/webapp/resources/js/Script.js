let clickedX, clickedY, clickedR;
const pointsByR = new Map();
const POINTS_KEY = "pointsByR";
const CURRENT_R_KEY = "currentR";
const SERVER_ID_KEY = "serverId";

// Функция для отрисовки точки на графике
function drawPoint(x, y, r, isHit) {
    const overlay = document.getElementById("plotOverlay");
    const scale = 141 / r;
    const centerX = 245;
    const centerY = 245;

    console.log("Drawing point:", { x, y, r, isHit });

    const svgX = centerX + x * scale;
    const svgY = centerY - y * scale;

    const color = isHit ? "green" : "red";

    let svgContainer = document.getElementById("pointsLayer");
    if (!svgContainer) {
        svgContainer = document.createElementNS("http://www.w3.org/2000/svg", "svg");
        svgContainer.setAttribute("id", "pointsLayer");
        svgContainer.setAttribute("width", "490");
        svgContainer.setAttribute("height", "490");
        svgContainer.style.position = "absolute";
        svgContainer.style.top = "0";
        svgContainer.style.left = "0";
        overlay.parentNode?.insertBefore(svgContainer, overlay);
    }

    const circle = document.createElementNS("http://www.w3.org/2000/svg", "circle");
    circle.setAttribute("cx", svgX);
    circle.setAttribute("cy", svgY);
    circle.setAttribute("r", 5);
    circle.setAttribute("fill", color);
    circle.setAttribute("stroke", "black");

    svgContainer.appendChild(circle);

    addPoint(x, y, r, isHit);
}

// Функция для очистки графика
function clearGraph() {
    const svgContainer = document.getElementById("pointsLayer");
    if (svgContainer) {
        svgContainer.innerHTML = "";
    }
}

// Функция для очистки Map и localStorage
function clearAllData() {
    pointsByR.clear();
    localStorage.clear();
    console.log("All data cleared: Map and localStorage.");
}

// Функция для перерисовки всех точек для текущего значения R
function redrawPointsForR(r) {
    console.log("Redrawing points for R:", r);
    const points = pointsByR.get(r) || [];
    console.log("Points to redraw:", points);
    points.forEach(({ x, y, isHit }) => drawPoint(x, y, r, isHit));
}

// Добавление точки в Map и сохранение в localStorage
function addPoint(x, y, r, isHit) {
    if (!pointsByR.has(r)) {
        pointsByR.set(r, []);
    }

    const points = pointsByR.get(r);

    // Проверяем, чтобы не было дублей точек с одинаковыми x,y для данного R
    if (!points.some(point => point.x === x && point.y === y)) {
        points.push({ x, y, isHit });
        savePointsToStorage(); // Сохранение после добавления точки
    }
}

// Сохранение Map (pointsByR) в localStorage
function savePointsToStorage() {
    const pointsObject = {};
    pointsByR.forEach((arr, key) => {
        pointsObject[key] = arr;
    });
    localStorage.setItem(POINTS_KEY, JSON.stringify(pointsObject));
    console.log("Saved points to localStorage:", pointsObject);
}

// Загрузка Map (pointsByR) из localStorage
function loadPointsFromStorage() {
    const pointsJSON = localStorage.getItem(POINTS_KEY);
    if (pointsJSON) {
        const pointsObject = JSON.parse(pointsJSON);
        Object.entries(pointsObject).forEach(([r, points]) => {
            pointsByR.set(parseFloat(r), points);
        });
        console.log("Loaded points from localStorage:", pointsByR);
    } else {
        console.log("No points found in localStorage.");
    }
}

// Обработка клика на графике
function handlePlotClick(event) {
    const overlay = document.getElementById("plotOverlay");
    const rect = overlay.getBoundingClientRect();

    const clickX = event.clientX - rect.left;
    const clickY = event.clientY - rect.top;

    const centerX = 245;
    const centerY = 245;

    const rInput = document.getElementById("form:rSpinner_input");
    const r = parseFloat(rInput ? rInput.value : 2) || 2;

    const x = ((clickX - centerX) / (141 / r)).toFixed(4);
    const y = ((centerY - clickY) / (141 / r)).toFixed(4);

    clickedX = parseFloat(x);
    clickedY = parseFloat(y);
    clickedR = r;

// Вызываем пульт p:remoteCommand для проверки попадания
    sendHitCheck([{ name: "x", value: x }, { name: "y", value: y }, { name: "r", value: r }]);
}

// Обработка изменений R
function handleRChange(newR) {
    const r = parseFloat(newR);
    if (isNaN(r)) return;
    clickedR = r;
    saveCurrentR(r); // Сохраняем текущее значение R
    clearGraph();
    redrawPointsForR(r);
}

// Сохранение текущего значения R в localStorage
function saveCurrentR(r) {
    localStorage.setItem(CURRENT_R_KEY, r);
    console.log("Saved R to localStorage:", r);
}

// Загрузка текущего значения R из localStorage
function loadCurrentR() {
    const storedR = localStorage.getItem(CURRENT_R_KEY);
    console.log("Loaded R from localStorage:", storedR);
    return storedR ? parseFloat(storedR) : null;
}

// Инициализация при загрузке страницы
document.addEventListener("DOMContentLoaded", () => {
    const metaTag = document.querySelector('meta[name="server-id"]');
    const currentServerId = metaTag ? metaTag.getAttribute('content') : null;

    if (!currentServerId) {
        console.error("No serverId found. Points cannot be synchronized with server restarts.");
        // Можем загрузить без очистки, но при отсутствии serverId логика перезапуска не сработает
        loadPointsFromStorage();
        const storedR = loadCurrentR();
        if (storedR !== null) {
            clickedR = storedR;
            redrawPointsForR(storedR);
        }
        return;
    }

    const storedServerId = localStorage.getItem(SERVER_ID_KEY);

    if (!storedServerId || storedServerId !== currentServerId) {
        console.log("Server restart detected. Clearing all data.");
        clearAllData();
        localStorage.setItem(SERVER_ID_KEY, currentServerId);
    } else {
        // Сервер не перезапускался
        console.log("No server restart detected. Loading existing data.");
        loadPointsFromStorage();
        const storedR = loadCurrentR();
        if (storedR !== null) {
            clickedR = storedR;
            redrawPointsForR(storedR);
        } else {
            console.log("No saved R, skipping redraw.");
        }
    }
});