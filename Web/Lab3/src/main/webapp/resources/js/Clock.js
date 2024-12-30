setInterval(updateClock, 1000);
updateClock();

function updateClock() {
    const clock = document.querySelector('#clock');
    const now = new Date();
    const day = now.getDate().toString();
    const month = now.getMonth().toString();
    const year = now.getFullYear().toString();
    const hours = now.getHours().toString().padStart(2, '0');
    const minutes = now.getMinutes().toString().padStart(2, '0');
    const seconds = now.getSeconds().toString().padStart(2, '0');
    clock.textContent = `${day}.${month}.${year} ${hours}:${minutes}:${seconds}`;
}