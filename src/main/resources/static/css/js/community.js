function post() {
    let questionId = $("#question_id").val();
    let commentContent = $("#comment_content").val();
    $.ajax({
        type: "POST",
        url: "/comment",
        dataType:"application/json",
        contentType:"application/json",
        data:JSON.stringify( {
            "parentId": questionId,
            "content": commentContent,
            "type": 1
        }),
        success(response) {
            if (response.code==200){
                $("#comment_section").hide();
            }else{
                alert(response.message);
            }
            console.log(response);
        }
    });
}