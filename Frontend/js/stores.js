let LOCATION = {center: [27.5059009818467, 53.911709911502975], zoom: 14};
const urlParams = new URLSearchParams(window.location.search);
const storeId = parseInt(urlParams.get('id'));

let INC_POINT = {
    coordinates: [27.5059009818467, 53.911709911502975],
    title: "Магазин на Ольшевского",
};

if (storeId === 1) {
    LOCATION = {center: [27.589268759196912, 53.91781343124961], zoom: 14};
    INC_POINT = {
        coordinates: [27.589268759196912, 53.91781343124961],
        title: "ТЦ Московско-Венский",
    };
    const shop2Element = document.getElementById('shop_2');
    if (shop2Element) {
        shop2Element.style.display = 'none';
    }
    const shop1Element = document.getElementById('shop_1');
    if (shop1Element) {
        shop1Element.style.display = 'block';
    }
}

const POINTS = [];

function diagram(props) {
    const div = document.createElement("div");

    const diagram = document.createElement("div");

    diagram.className = "pie-marker";
    diagram.style.color = props.color;

    const gradient = [];
    let previous = 0;
    for (let i = 0; i < props.colors.length; i += 1) {
        const p = props.colors[i];
        const deg = (360 / 100) * p.percentage;
        gradient.push(`${p.color} ${previous}deg ${previous + deg}deg`);
        previous = previous + deg;
    }

    diagram.style.background = "conic-gradient(" + gradient.join(", ") + ")";

    const title = document.createElement("div");
    title.innerHTML = props.title;
    title.className = "pie-marker-title";

    div.appendChild(diagram);
    div.appendChild(title);

    return div;
}

function circle(props) {
    const circle = document.createElement("div");
    circle.classList.add("circle");
    circle.style.color = props.color;
    props.radius && circle.style.setProperty("--radius", props.radius);
    props.icon && circle.style.setProperty("--icon", props.icon);
    circle.title = props.title;
    return circle;
}

function icon(props) {
    const circle = document.createElement("div");
    circle.classList.add("icon");
    circle.style.color = props.color;
    circle.style.backgroundImage = `url(${props.icon})`;
    circle.style.setProperty("--size", props.size);

    if (props.title) {
        const title = document.createElement("div");
        title.innerHTML = props.title;
        title.className = "icon-title";
        circle.appendChild(title);
    }

    return circle;
}
