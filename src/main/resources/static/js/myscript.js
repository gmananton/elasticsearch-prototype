var input = document.querySelector('input');
var newsSearchResult = document.getElementById('newsSearchResult');
var allNewsContainer = document.getElementById('allNews');


document.addEventListener('DOMContentLoaded', function () {
    renderAllNews();
});

input.addEventListener('keyup', searchForNews);


function createNewsDiv(news, backgroundClass, titleTag) {
    var newsDiv = createElement("div", backgroundClass);
    var titleHeader = createElementWithText(titleTag, news.title);
    var bodyParagraph = createElementWithText("p", news.body);
    newsDiv.appendChild(titleHeader);


    news.tags.forEach(t => {
        var tag = createElementWithText("span", t, "badge badge-primary mr-1");
        newsDiv.appendChild(tag);
    })

    newsDiv.appendChild(bodyParagraph);
    return newsDiv;
}

function renderAllNews() {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/listNews", true);
    xhr.onreadystatechange = function () {

        if (xhr.readyState === 4 && xhr.status === 200) {
            // console.clear();
            allNewsContainer.innerHTML = '';
            var foundNews = JSON.parse(xhr.response);
            if (foundNews.length === 0) {
                return;
            }

            foundNews.forEach(news => {
                allNewsContainer.appendChild(createNewsDiv(news, "bg-light font-weight-light mt-2 pb-2 pt-2 pl-2 pr-2 rounded-top rounded-bottom rounded-left rounded-right", "h6"));
            })
        }
    }

    xhr.send(null);
}

function searchForNews() {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/searchNews?query=" + input.value + "&queryType=" + queryType, true);

    xhr.onreadystatechange = function () {
        // Запрос завершен. Здесь можно обрабатывать результат - аппендим результаты в контейнер
        if (xhr.readyState === 4 && xhr.status === 200) {
            console.clear();

            newsSearchResult.innerHTML = '';
            newsSearchResult.classList.remove("bg-info");

            var foundNews = JSON.parse(xhr.response);
            if (foundNews.length === 0) {
                return;
            }

            newsSearchResult.classList.add('bg-info');

            foundNews.forEach(news => {
                console.log(news);
                newsSearchResult.appendChild(createNewsDiv(news, "bg-light font-weight-light mt-2 pb-2 pt-2 pl-2 pr-2 rounded-top rounded-bottom rounded-left rounded-right", "h5"));
            })
        }
    }

    xhr.send(null);
}

function createElement(tagName, clazz) {
    var element = document.createElement(tagName);
    if (clazz !== undefined) {
        element.className = clazz;
    }
    return element;
}

function createElementWithText(tagName, text, clazz) {
    var element = createElement(tagName, clazz);
    var textNode = document.createTextNode(text);
    element.appendChild(textNode);
    return element;
}