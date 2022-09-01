console.log("test")

$(document).ready(function() {
    getNewsList();
})

function getNewsList() {
    $.ajax({
        type: 'GET',
        url: '/search',
        success: function (response) {
            for (let i = 0; i < response.length; i++) {
                let message = response[i];
                let id = message['id'];
                let date = message['date'];
                let press = message['press'];
                let author = message['author'];
                let title = message['title'];
                let contents = message['contents'];
                let url = message['url'];
                let keyword = message['keyword'];
                let location = message['location'];
                let organization = message['organization'];
                addHTML(id, title, author, contents, press, date, url, keyword, location, organization);
            }
        }
    })
}

function addHTML(id, title, author, contents, press, date, url, keyword, location, organization) {
    let tempHtml = `<div class="card" id="${id}-list">
                                <div class="card-body">
                                    <h5 class="card-title">${title}</h5>
                                    <p class="card-text">${contents}</p>
                                    <p class="card-text"><small class="text-muted">${date}</small></p>
                                    <p class="card-text"><small class="text-muted">${url}</small></p>
                                    <p class="card-text"><small class="text-muted">${keyword}</small></p>
                                    <p class="card-text"><small class="text-muted">${location}</small></p>
                                    <p class="card-text"><small class="text-muted">${organization}</small></p>
                                </div>
                            </div>`
    $('#news_list').append(tempHtml);
}