function loadInformationById(id, text, tagName) {
    const someDiv = document.querySelector(`#${id}`);
    someDiv.innerHTML = "";
    const tag = document.createElement(`${tagName}`);
    tag.innerHTML = `${text}`;
    someDiv.appendChild(tag);
}

function loadInformationByClass(className, text, tagName) {
    const someDivs = document.querySelectorAll(`.${className}`);
    someDivs.forEach(divElement => {
        divElement.innerHTML = "";
        const tag = document.createElement(tagName);
        tag.innerHTML = text;
        divElement.appendChild(tag);
    });
}