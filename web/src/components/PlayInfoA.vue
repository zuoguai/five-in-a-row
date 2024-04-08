<template>
    <div class="play-info-ground">



        <div class="user_photo">
            <img :src="$store.state.user.photo" alt="">
        </div>




        <div class="user_username">
            <span>{{ $store.state.user.username }}</span>
        </div>


        <div class="play-info-text">
            胜点：{{ $store.state.user.rating }}
        </div>

        <div class="play-info-tip">

            <div class="play-info-text" v-if="$store.state.pk.a_id == $store.state.user.id">
                颜色：黑
            </div>
            <div class="play-info-text" v-else-if="!$store.state.pk.a_id == $store.state.user.id">
                颜色：白
            </div>

        </div>
        <div class="play-info-text" v-if="$store.state.pk.next == $store.state.user.id">
            落子中...
        </div>
        <div class="play-info-text" v-else>
            等待中...
        </div>
        <div class="play-info-text" v-if="$store.state.pk.next == $store.state.user.id">
            {{ $store.state.pk.timeout }}
        </div>
        <div class="play-info-text" v-else>
            24
        </div>

    </div>
</template>

<script>

// import { ref } from 'vue';
// import { useStore } from 'vuex';




export default {

    setup() {
        // const store = useStore();



        // setInterval(() => {
        //     store.commit("setTimeout", parseInt(store.state.pk.timeout) - 1);
        // }, 1000);



        return {


        }

    }

}

</script>


<style scoped>
div.play-info-ground {
    width: 100%;
    height: 100%;
    float: left;
    margin-top: 50px;
    background: rgb(0, 0, 0, 0.5);
    border-radius: 5%;
    /* margin: 50px auto; */

}

div.user_photo {
    /* 左居中 */
    text-align: center;
    /* 上边距 */
    /* padding-top: 5vh; */
    background-color: rgb(13, 192, 247);
    padding-top: 5%;
    padding-bottom: 5%;
    border-radius: 5%;
}

div.user_photo>img {
    /* 圆形图片 */
    border-radius: 5%;
    width: 90%;


}

div.user_username {
    text-align: center;
    font-size: 25px;
    font-weight: 600;
    color: #ffffff;
    padding-top: 2vh;

}

div.play-info-text {
    text-align: center;
    font-family: 楷体;

    font-size: 25px;

    font-weight: 600;
    color: #ffffff;
    padding-top: 2vh;
}
</style>


