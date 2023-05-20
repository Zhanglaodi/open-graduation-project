$(document).ready(function () {
    var cardContainer = $("#cardContainer");
    var drawCardButton = $("#drawCardButton");
    var flipAllButton = $("#flipAllButton");
    var isFlipped = false;

    drawCardButton.click(function () {
        cardContainer.empty();
        const info = {
            token: "0207bf59-8cc8-4fb2-892d-af05dc923e68", timeStamp: Math.round(Date.now() / 1000)
        }
        isFlipped = false;

        $.ajax({
            url: "http://localhost:8080/api/raffle/draw_cards",
            type: 'POST',
            data: info,
            success: function (response) {
                var cardData = response.data; // 根据后台返回的数据结构获取卡片数据

                // 创建卡片元素
                for (var i = 0; i < cardData.length; i++) {
                    var card = $("<div>").addClass("card");
                    var inner = $("<div>").addClass("inner");
                    var front = $("<div>").addClass("front");
                    var back = $("<div>").addClass("back");

                    var frontText = $("<div>").addClass("front-text").text("点击翻牌");
                    var backContent = $("<div>").addClass("back-content");
                    var backImage = $("<img>").addClass("back-image").attr("src", cardData[i].postImg);
                    var backText = $("<div>").addClass("back-text").text(cardData[i].user_name);

                    front.append(frontText);
                    backContent.append(backImage);
                    backContent.append(backText);
                    back.append(backContent);

                    // 添加点击事件
                    card.click(function () {
                        if (!$(this).hasClass("flipped")) {
                            $(this).addClass("flipped");
                        }
                    });

                    inner.append(front);
                    inner.append(back);
                    card.append(inner);
                    cardContainer.append(card);
                }

                isFlipped = true;
            },
            error: function (xhr, status, error) {
                console.error('请求错误：', error);
            }
        });

    });

    flipAllButton.click(function () {
        $(".card").addClass("flipped");
    });
});
