const valueArrays = [];

const checkCheckboxes = () => {
    const checkboxPolitics = document.getElementById('politics');
    const checkboxEconomics = document.getElementById('economics');
    const checkboxSociety = document.getElementById('society');
    const checkboxCulture = document.getElementById('culture');
    const checkboxGlobal = document.getElementById('global');
    const checkboxLocal = document.getElementById('local');
    const checkboxSports = document.getElementById('sports');
    const checkboxTech = document.getElementById('tech');

    const checkBoxes = [checkboxPolitics, checkboxEconomics, checkboxSociety, checkboxCulture, checkboxGlobal, checkboxLocal, checkboxSports, checkboxTech];
    for (let i = 0; i < checkBoxes.length; i++) {
        if (checkBoxes[i].checked) {
            valueArrays.push(checkBoxes[i].value);
        }
    }
}

// checkboxValue.checked->true면 value값 모아서 array로 만들고 .push해서 ajax data에 넣어주기
// eventHandler
// id로 select해서 값을 가져오기->내용물 가져오고 싶으면 value에다 같은 내용?

console.log("test")

$(document).ready(function () {
    $('#search-input').on('keypress', function (e) {
        if (e.key == 'Enter') {
            // checkCheckboxes();
            getNewsList();
        }
    });
})

$(document).ready(function () {
    $('.btm_image').click(function () {
        // checkCheckboxes();
            getNewsList();
          });
})


function getNewsList() {
    let keyword = $('#search-input').val();
    if (keyword == '') {
        alert('검색어를 입력해주세요.');
        $('#search-input').focus();
        return;
    }

    const payload = JSON.stringify({fields: ["title", "contents"], searchTerm: keyword})
    $.ajax({
        type: 'POST',
        url: `/api/news/search`,
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        data: payload,
        success: function (response) {
            $('#news_list').empty();
            for (let i = 0; i < response.length; i++) {
                let newsDto = response[i];
                addHTML(newsDto);
            }
        }
    })
}

function addHTML(newsDto) {
    let tempHtml = `<div class="card" id="${newsDto.id}-list">
                                <div class="card-body">
                                <h5 class="card-title">${newsDto.title}</h5>
                                    <p class="card-text">${newsDto.contents}</p>
                                    <p class="card-text"><small class="text-muted">${newsDto.date}</small></p>
                                    <p class="card-text"><small class="text-muted">나중에 카테고리 넣기</small></p>
                                    <p class="card-text"><small class="text-muted">${newsDto.press}</small></p>
                                    <p class="card-text"><small class="text-muted">${newsDto.author}</small></p>
                                </div>
                            </div>`
    $('#news_list').append(tempHtml);
}