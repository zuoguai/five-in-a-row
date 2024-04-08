<template>
    <div class="row">
        <div class="col-3"></div>
        <div class="col-6" style="padding-top: 10vh;">
            <div class="user-info">
                <div class="user-info-photo">
                    <span class="user-info-photo">头像:</span>
                    <img :src="$store.state.user.photo" />
                </div>
                <div class="user-info-item">
                    <span class="user-info-text">昵称:</span>
                    <span class="user-info-content">{{ $store.state.user.username }}</span>

                </div>
                <div class="user-info-item">
                    <span class="user-info-text">胜点:</span>
                    <span class="user-info-content">{{ $store.state.user.rating }}</span>

                </div>
                <div class="user-info-item">
                    <span class="user-info-text">胜率:</span>
                    <span class="user-info-content">{{
                        Math.trunc(parseInt($store.state.user.win) * 100 /
                            (parseInt($store.state.user.win)
                                + parseInt($store.state.user.lose)))
                    }}%</span>

                </div>
                <div class="user-info-item">
                    <span class="user-info-text">胜场:</span>
                    <span class="user-info-content">{{ $store.state.user.win }}</span>

                </div>
                <div class="user-info-item">
                    <span class="user-info-text">败场:</span>
                    <span class="user-info-content">{{ $store.state.user.lose }}</span>

                </div>

            </div>
        </div>
        <div class="col-3"></div>

    </div>
</template>

<script>

</script>


<style scoped>
div.user-info {
    padding-top: 5vh;

    padding-left: 10vh;
    padding-right: 10vh;
    background-color: rgb(72, 75, 74, 0.5);
    padding-bottom: 5vh;


}

div.user-info-photo {
    padding-top: 0vh;
    padding-bottom: 16vh;

}

div.user-info-item {
    padding-left: 0vh;
    padding-right: 5vh;
    padding-bottom: 0vh;
}

span.user-info-photo {
    text-align: center;
    color: white;
    font-size: 30px;
    font-family: 楷体;
}

div.user-info-photo>img {
    width: 10vw;
    float: right;
    padding: auto;

    border-radius: 15%;
}



span.user-info-text {
    color: white;
    font-size: 30px;
    font-family: 楷体;
}

span.user-info-content {
    float: right;

    text-align: center;
    color: white;
    font-size: 30px;
    font-family: 微软雅黑;
}
</style>