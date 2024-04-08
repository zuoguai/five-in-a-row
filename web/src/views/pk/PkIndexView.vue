<template>


    <PlayGround v-if="$store.state.pk.status === 'playing' || $store.state.pk.status === 'result'" />

    <!-- <PlayGround /> -->





    <MatchGround v-if="$store.state.pk.status === 'matching'" />
    <ResultGround v-if="$store.state.pk.status === 'result'" />





</template>

<script>
// import CotentField from '@/components/ContentField.vue'
import PlayGround from '@/components/PlayGround.vue'
import MatchGround from '@/components/MatchGround.vue'
import ResultGround from '@/components/ResultGround.vue'



import { onMounted, onUnmounted } from 'vue';
import { useStore } from 'vuex';
export default {
    components: {
        // CotentField,
        PlayGround,
        MatchGround,
        ResultGround,
    },
    setup() {
        const store = useStore();
        const socketUrl = store.state.user.BaseWsUrl +`websocket/${store.state.user.token}`

        let socket = null;
        let setIntervalId = 0;

        onMounted(() => {
            // while(socket == null){
                // console.log("create fail, socket is null , please wait ...")
                socket = new WebSocket(socketUrl);
            // }
            console.log(socket)
            store.commit("updateSocket",
                socket
            )
            store.commit("updateOpponent", {
                opponent_username: "敌人",
                opponent_photo: "https://cdn.acwing.com/media/article/image/2022/08/09/1_1db2488f17-anonymous.png",

            })

            socket.onopen = () => {
                store.commit("updateSocket", socket);
            }

            socket.onmessage = msg => {
                const data = JSON.parse(msg.data);
                if (data.event === "start-matching") {
                    store.commit("updateOpponent", {
                        opponent_username: data.opponent_username,
                        opponent_photo: data.opponent_photo,
                        opponent_rating: data.opponent_rating,
                    })

                    setTimeout(() => {
                        store.commit("updateStatus", "playing");

                    }, 1500);
                    // store.commit("updateStep",{
                    //     chess_x:data.chess_x,
                    //     chess_y:data.chess_y,
                    //     chess_id:data.chess_id,
                    //     next:data.next
                    // })



                    store.commit("updateRating", data.rating);

                    console.log(data);
                    store.commit("updateGame", {
                        a_id: data.a_id,
                        b_id: data.b_id,
                        next: data.next
                    })
                    store.commit("updateTimeout", 24);
                    setIntervalId = setInterval(() => {
                        store.commit("updateTimeout", store.state.pk.timeout - 1)
                    }, 1000);

                } else if (data.event === "move") {
                    const game = store.state.pk.gameObject;

                    console.log(data);
                    store.commit("updateStep", {
                        chess_x: data.chess_x,
                        chess_y: data.chess_y,
                        chess_id: data.chess_id,
                        next: data.next
                    })
                    store.commit("updateTimeout", 24);

                    // const [play1, play2] = game.plays;
                    // if(1 ==  chess_id){
                    //     play1.chess[play1.chess.length] = 
                    // }else if(2 == chess_id){

                    // }

                    game.setChess(store.state.pk.chess_x, store.state.pk.chess_y, store.state.pk.chess_id);

                } else if (data.event === "result") {
                    clearInterval(setIntervalId);
                    console.log(data);
                    store.commit("updateLoser", data.loser)
                    store.commit("updateStatus", "result")

                }
            }
            socket.onclose = () => {

            }


        })

        onUnmounted(() => {
            console.log("关闭了页面")
            clearInterval(setIntervalId);

            store.state.pk.socket.send(JSON.stringify({
                event: "stop-matching",
            }));
            store.commit("updateOpponent", {
                opponent_username: "敌人",
                opponent_photo: "https://cdn.acwing.com/media/article/image/2022/08/09/1_1db2488f17-anonymous.png",
                opponent_rating: "",
            })
            store.commit("updateStatus", "matching");
        })
    }



}
</script>


<style scoped>

</style>