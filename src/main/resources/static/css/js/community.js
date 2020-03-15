/**
 * 点击提交一个评论
 */
function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    comment2target(questionId, 1, content);
}

/**
 * 提交一个评论的评论
 * @param e
 */
function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-" + commentId).val();
    comment2target(commentId, 2, content);
}

/**
 * 提交后的逻辑处理
 * @param targetId 提交的评论对应的是问题还是评论的id
 * @param type 提交的评论对应的是问题还是评论
 * @param content
 */
function comment2target(targetId, type, content) {
    if (!content) {
        alert("不能回复空内容~~~");
        return;
    }

    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            if (response.code == 200) {
                // window.location.reload();
                parent.location.reload();
            } else {
                if (response.code == 2003) {
                    let isAccepted = confirm(response.message);
                    if (isAccepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=c05a2ccc4a4995c1b2e2&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                        window.localStorage.setItem("closable",true);
                    }
                } else {
                    alert(response.message);
                }
            }
        },
        dataType: "json"
    });
}

/**
 * 展开二级评论  //TODO 提交对评论的评论话，框要继续展开
 */
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);
    // 获取一下二级评论的展开状态
    var boolean = e.getAttribute("data-collapse");
    if (boolean) {
        // 折叠二级评论
        comments.addClass("collapse");
        e.setAttribute("data-collapse",false);
        e.classList.remove("active");
    } else {
        let subCommentContainer = $("#comment-" + id);
        if (subCommentContainer.children().length !== 1) {
            //展开二级评论
            comments.removeClass("collapse");
            // 标记二级评论展开状态
            e.setAttribute("data-collapse", "true");
            e.classList.add("active");
        } else {
            $.getJSON("/comment/" + id, function (data) {
                $.each(data.data.reverse(), function (index, comment) {
                    let mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "src": comment.user.avatarUrl
                    }));

                    let mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.name
                    })).append($("<div/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class": "menu"
                    }).append($("<span/>", {
                        "class": "float-right",
                        //TODO 格式化时间并且显示
                        // "html": moment(comment.gmtCreate).format('YYYY-MM-DD')
                    })));

                    let mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    let commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);
                });
                //展开二级评论
                comments.removeClass("collapse");
                // 标记二级评论展开状态
                e.setAttribute("data-collapse", "true");
                e.classList.add("active");
            });
        }
    }
}

function showSelectTag() {
    $("#select-tag").show();
}

function selectTag(e) {
    let value = e.getAttribute("data-tag");
    let previous = $("#tag").val();
    if (previous.indexOf(value) == -1) {
        if (previous) {
            $("#tag").val(previous + ',' + value);
        } else {
            $("#tag").val(value);

        }
    }
}

