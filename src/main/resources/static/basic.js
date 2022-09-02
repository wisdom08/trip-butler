console.log("test")

$(document).ready(function () {
    $('#search-input').on('keypress', function (e) {
        if (e.key == 'Enter') {
            getNewsList();
        }
    });
})

    function getNewsList() {
        let keyword = $('#search-input').val();
        if (keyword == '') {
            alert('검색어를 입력해주세요.');
            $('#search-input').focus();
            return;
        }

        $.ajax({
            type: 'GET',
            url: `/search?keyword=${keyword}`,
            success: function (response) {
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