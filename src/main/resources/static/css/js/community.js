function post() {
    let questionId = $("#question_id").val();
    let commentContent = $("#comment_content").val();
    if (!commentContent){
        alert("输入内容不能为空！")
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": questionId,
            "content": commentContent,
            "type": 1
        }),
        success:function(response){
            if (response.code == 200) {
                window.location.reload();
            } else {
                if (response.code = 2003) {
                    let isAccepted = confirm(response.message);
                    if (isAccepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=c05a2ccc4a4995c1b2e2&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", true);
                    }
                } else {
                    alert(response.message);
                }
            }
            console.log(response);
        }
    });
}