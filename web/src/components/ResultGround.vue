<template>
    <div class="result-board">
        <div class="result-board-text" v-if="$store.state.pk.loser === 'A'
        && $store.state.user.id == $store.state.pk.a_id">
            You are loser
        </div>
        <div class="result-board-text" v-else-if="$store.state.pk.loser === 'B'
        && $store.state.user.id == $store.state.pk.b_id">
            You are loser
        </div>
        <div class="result-board-text" v-else>
            You are winner
        </div>
        <div class="result-board-btn" style="text-align: center; padding-top: 5vh;">
            <button type="button" class="btn btn-primary btn-lg" @click="click_btn">{{
                btn_info
            }}</button>
        </div>

    </div>

</template>

<script>

import { ref } from 'vue';
import { useStore } from 'vuex';




export default {

    setup() {
        const store = useStore();
        let btn_info = ref("确认");

        const click_btn = () => {
            console.log("点击按钮");
            if (btn_info.value === "确认") {
                store.commit("updateStatus", "matching");

            } else {
                btn_info.value = "确认"

            }
        }


        return {
            click_btn,
            btn_info,

        }

    }

}

</script>


<style scoped>
div.result-board {
    height: 40vh;
    width: 50vw;
    background: rgb(50, 50, 50, 0.5);
    position: absolute;
    top: 25vh;
    left: 25vw;
    border-radius: 5%;

}

div.result-board-text {
    text-align: center;
    color: white;
    font-weight: 600;
    font-size: 50px;
    font-style: italic;
    padding-top: 10vh;
}
</style>


