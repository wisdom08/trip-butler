const nickname = sessionStorage.getItem("nickname");

$(document).ready(function () {
    setDefaultDate();

    $('.btn_banner').click(function () {
        window.location.reload();
    });

    $('#search-input').on('keypress', function (e) {
        if (e.key === 'Enter') {
            getNewsList();
        }
    });

    $('.btm_image').click(function () {
        getNewsList();
    });
});


function setDefaultDate() {
    const date = new Date();

    let startDay = date.getDate();
    let startMonth = date.getMonth() + 1;
    let startYear = date.getFullYear() - 1;

    if (startMonth < 10) startMonth = "0" + startMonth;
    if (startDay < 10) startDay = "0" + startDay;

    let yearAgo = startYear + "-" + startMonth + "-" + startDay;
    $('#start').val(yearAgo);

    let endDay = date.getDate();
    let endMonth = date.getMonth() + 1;
    let endYear = date.getFullYear();

    if (endMonth < 10) endMonth = "0" + endMonth;
    if (endDay < 10) endDay = "0" + endDay;

    let today = endYear + "-" + endMonth + "-" + endDay;
    $("#end").val(today);
}

function searchWithSection() {
    const secArr = document.getElementsByName("section")
    const selectedSection = [];
    for (let i = 0; i < secArr.length; i++) {
        if (secArr[i].checked) {
            selectedSection.push(secArr[i].value)
        }
    }
    return selectedSection;
}

function searchWithPress() {
    const pressArr = document.getElementsByName("press")
    const selectedPress = [];
    for (let i = 0; i < pressArr.length; i++) {
        if (pressArr[i].checked) {
            selectedPress.push(pressArr[i].value)
        }
    }
    return selectedPress;
}

function getNewsList() {
    let keyword = $('#search-input').val();
    if (keyword == '') {
        alert('검색어를 입력해주세요.');
        $('#search-input').focus();
        return;
    }

    const sectionArr = searchWithSection();
    const pressArr = searchWithPress();
    const startDate = $('#start').val();
    const endDate = $('#end').val();
    if (startDate > endDate) {
        alert('시작일을 종료일보다 빠른 날짜로 설정해주세요.');
    }

    const payload = JSON.stringify({
        searchTerm: keyword,
        section: sectionArr,
        press: pressArr,
        startDate: startDate,
        endDate: endDate,
    })

    $.ajax({
        type: 'POST',
        url: `/api/news/search`,
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        data: payload,
        success: function (response) {
            $('#news_list').empty();
            $(".pagination").empty();

            //검색 결과 보여주기
            for (let i = 0; i < response.length; i++) {
                let newsDto = response[i];
                addHTML(newsDto);
            }

            //전체결과 수
            let numberOfArticles = response.length;
            console.log(numberOfArticles);
            //한 페이지당 결과 수
            let sizePerPage = 10;
            //페이지당 결과만 보이기
            $("#news_list .card").slice(sizePerPage).hide();
            //전체 페이지 수
            let totalPages = Math.ceil(numberOfArticles / sizePerPage);

            // 페이지바 그려주기
            $(".pagination").append(`<li><a id="previous-page" href="javascript:void(0)" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>`);
            $(".pagination").append(`<li class='current-page active'><a href='javascript:void(0)'>1</a></li>`);

            for (let i = 2; i <= 10; i++) {
                $(".pagination").append(`<li class='current-page'><a href='javascript:void(0)'>${i}</a></li>`);
            }

            $(".pagination").append(`<li class='next-page'><a href='javascript:void(0)' aria-label=Next><span aria-hidden=true>&raquo;</span></a></li>`);


            //페이지 버튼 클릭 시
            $(".current-page").on("click", function () {
                // 현재 페이지 버튼이 클릭이 되어있을 때
                if ($(this).hasClass('active')) {
                    return false;
                } else {
                    // 현재 페이지 버튼이 클릭이 되지 않았을 때
                    let currentPage = $(this).index();
                    $(this).addClass('active').siblings().removeClass("active");
                    let totalSize = sizePerPage * currentPage;
                    $("#news_list .card").hide();
                    for (let i = totalSize - sizePerPage; i < totalSize; i++) {
                        $("#news_list .card").eq(i).show();
                    }
                }
            });

            //다음 버튼 클릭 시
            $(".next-page").on("click", function () {
                let currentPage = $(".pagination li.active").index();
                console.log(currentPage);
                //현재 페이지가 마지막일 때
                if (currentPage === totalPages || currentPage > totalPages) {
                    return false;
                } else {
                    currentPage++;
                    $(".pagination li").removeClass('active');
                    $("#news_list .card").hide();

                    let totalSize = sizePerPage * currentPage;
                    console.log(totalSize) //ok
                    for (let i = totalSize - sizePerPage; i < totalSize; i++) {
                        $("#news_list .card").eq(i).show();
                    }
                    console.log("active 켜짐")
                    $(".pagination li.current-page").eq(currentPage - 1).addClass('active');
                console.log('active 꺼짐')
                }
            });

            //이전 버튼 클릭 시
            $("#previous-page").on("click", function () {
                let currentPage = $(".pagination li.active").index();
                console.log(currentPage);
                if (currentPage === 1) {
                    return false;
                } else {
                    currentPage--;
                    $(".pagination li").removeClass("active");
                    $("#news_list .card").hide();

                    let totalSize = sizePerPage * currentPage;
                    for (let i = totalSize - sizePerPage; i < totalSize; i++) {
                        $("#news_list .card").eq(i).show();
                    }
                    $(".pagination li.current-page").eq(currentPage - 1).addClass('active');
                    $(".pagination li#previous-page").removeClass('active');
                }
            });
        }
    });
}

function addHTML(newsDto) {
    let tempHtml = `<div class="card" id="${newsDto.id}-list">
                                <div class="card-body">
                                <div>
                                    <button id="modal_btn" type="button" class="btn btn-success openBtn" onclick='openModal(${JSON.stringify(newsDto)})' data-toggle="modal" data-target="#exampleModal" data-whatever="@getbootstrap">의견 공유</button>
                                    <h3 class="card-title" style="font-weight: bold;">${newsDto.title}</h3>
                                    <h4 class="card-text1"><div class="article"><div class="contentStr">${newsDto.contents}</div></div></h4>
                                </div>
                                <div class="info">
                                    <p class="card-text2"><small class="text-muted">${newsDto.date}</small></p>
                                    <p class="card-text3"><small class="text-muted">${newsDto.section}</small></p>
                                    <p class="card-text4"><small class="text-muted">${newsDto.press}</small></p>
                                    <p class="card-text5"><small class="text-muted">${newsDto.author}</small></p>
                                </div>

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
            <label for="title" class="col-form-label">기사 제목:</label>
            <input type="text" class="form-control news-title" id="title" name="title"/>
            <input type="hidden" class="form-control news-id" id="newsId" name="newsId"/>
            <input type="hidden" class="form-control news-url" id="url" name="url" value="url"/>
         </div>
          
         <div class="form-group">
           <label for="contents" class="control-label">의견:</label>
           <input type="hidden" value="${nickname}" id="nickname" name="nickname">
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

  $(document).ready(function(){
    $('.article').each(function(){
      const content = $(this).children('.contentStr');

      const content_txt = content.text();
      const content_html = content.html();
      const content_txt_short = content_txt.substring(0,150)+"...";
      const btn_more = $('<button type="button" class="btn btn-outline-secondary btn-sm"><a href="javascript:void(0)" class="more">더보기</a></button>');

      $(this).append(btn_more);

      if(content_txt.length >= 200){
        content.html(content_txt_short)

      }else{
        btn_more.hide()
      }

      btn_more.click(toggle_content);
      function toggle_content(){
        if($(this).hasClass('short')){
          $(this).html('더보기');
          content.html(content_txt_short)
          $(this).removeClass('short');
        }else{
          $(this).html('접기');
          content.html(content_html);
          $(this).addClass('short');

        }
      }
    });
  });
}

function openModal(newsDto) {
  $('#exampleModal').on('show.bs.modal', function () {
    const modal = $(this)
    modal.find('#newsId').val(newsDto.id);
    modal.find('#title').val(newsDto.title);
    modal.find('#url').val(newsDto.url);
    modal.find('#contents').val('');
  })
}
