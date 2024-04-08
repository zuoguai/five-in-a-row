<template>
    <div class="matchground">
        <div class="row">
            <div class="col-4">
                <div class="user_photo">
                    <img :src="$store.state.user.photo" alt="">
                </div>
                <div class="user_username">
                    {{ $store.state.user.username }}
                </div>
            </div>
            <div class="col-4">
                <div class="user-select-wait" v-if="waittime > 0">
                    {{ waittime }}
                </div>
            </div>

            <div class="col-4">
                <div class="user_photo">
                    <img :src="$store.state.pk.opponent_photo" alt="">
                </div>
                <div class="user_username">
                    {{ $store.state.pk.opponent_username }}
                </div>


            </div>
            <div class="col-12" style="text-align: center; padding-top: 5vh;">
                <button type="button" class="btn btn-primary btn-lg" @click="click_match_btn">{{
                    match_btn_info
                }}</button>
            </div>

        </div>
    </div>
</template>

<script>

import { ref } from 'vue';
import { useStore } from 'vuex';




export default {

    setup() {
        const store = useStore();
        let match_btn_info = ref("开始匹配");
        let waittime = ref(0);
        let setIntervalId = 0;




        const click_match_btn = () => {
            console.log("点击按钮");
            if (match_btn_info.value === "开始匹配") {


                match_btn_info.value = "取消"
                waittime.value = 1;
                setIntervalId = setInterval(() => {
                    waittime.value = waittime.value + 1;
                }, 1000)

                store.state.pk.socket.send(JSON.stringify({
                    event: "start-matching",
                }));//有一个api可以向后端发送一个字符串 
                //stringify可以转成字符串


            } else {
                match_btn_info.value = "开始匹配"
                clearInterval(setIntervalId);
                waittime.value = 0;

                store.state.pk.socket.send(JSON.stringify({
                    event: "stop-matching",
                }));//有一个api可以向后端发送一个字符串 
                //stringify可以转成字符串
            }
        }
        store.commit("updateOpponent", {
            opponent_username: "敌人",
            opponent_photo: "https://cdn.acwing.com/media/article/image/2022/08/09/1_1db2488f17-anonymous.png",
            opponent_rating: "150",
        })


        return {
            click_match_btn,
            match_btn_info,
            waittime,

        }

    }

}

</script>


<style scoped>
div.matchground {

    width: 60vw;
    height: 70vh;
    background: RGB(255, 255, 255, 0.5);
    margin: 50px auto;
    border-radius: 5%;
}

div.user_photo {
    /* 左居中 */
    text-align: center;
    /* 上边距 */
    padding-top: 10vh;
}

div.user_photo>img {
    /* 圆形图片 */
    border-radius: 5%;
    width: 20vh;

}

div.user_username {
    text-align: center;

    font-size: 24px;
    font-weight: 600;
    color: #66656e;
    padding-top: 2vh;

}

div.user-select-wait {
    padding-top: 30%;
    text-align: center;

    font-size: 70px;
    font-weight: 600;
    color: #080808;

}
</style>


