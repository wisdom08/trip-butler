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
                                <button id="modal_btn" type="button" class="btn btn-success openBtn" onclick='openModal(${JSON.stringify(newsDto)})' data-toggle="modal" data-target="#exampleModal" data-whatever="@getbootstrap">의견 공유</button>
                                <h5 class="card-title">${newsDto.title}</h5>
                                    <p class="card-text">${newsDto.contents}</p>
                                    <p class="card-text"><small class="text-muted">${newsDto.date}</small></p>
                                    <p class="card-text"><small class="text-muted">${newsDto.section}</small></p>
                                    <p class="card-text"><small class="text-muted">${newsDto.press}</small></p>
                                    <p class="card-text"><small class="text-muted">${newsDto.author}</small></p>

<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="exampleModalLabel">의견 공유</h4>
      </div>
      <div class="modal-body">
        <form action="/api/posts" method="post">
         <div class="form-group">
            <label for="username" class="col-form-label">기사 제목:</label>
            <input type="hidden" class="form-control news-id" id="newsId" name="newsId"/>
            <input type="text" class="form-control news-title" id="title" name="title" disabled/>
         </div>
          
         <div class="form-group">
           <label for="contents" class="control-label">의견:</label>
           <textarea class="form-control" id="contents" data-value="" name="contents"></textarea>
         </div>
             
         <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
          <input type="submit" class="btn btn-success" value="등록"/>
        </div>

      </form>
      </div>
    </div>
  </div>
</div>
                            </div>`
    $('#news_list').append(tempHtml);
}

function openModal(newsDto) {
  $('#exampleModal').on('show.bs.modal', function () {
    const modal = $(this)
    modal.find('#newsId').val(newsDto.id);
    modal.find('#title').val(newsDto.title);
  })
}
