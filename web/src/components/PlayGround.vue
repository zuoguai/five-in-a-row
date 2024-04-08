<template>
    <div class="play">
        <div class="row">
            <div class="col-1"></div>
            <div class="col-2">
                <div class="play-info">
                    <PlayInfoA />
                </div>
            </div>
            <div class="col-6">

                <div class="play-ground">
                    <GameMap />
                </div>
            </div>
            <div class="col-2">
                <div class="play-info">
                    <PlayInfoB />
                </div>
            </div>
            <div class="col-1"></div>


        </div>

    </div>
</template>

<script>
import GameMap from '@/components/GameMap.vue'
import PlayInfoA from '@/components/PlayInfoA.vue'
import PlayInfoB from '@/components/PlayInfoB.vue'



export default {
    components: {
        GameMap,
        PlayInfoA,
        PlayInfoB,
    }
}
</script>



<style scoped>
div.play-ground {

    width: 100%;
    height: 85vh;
    /* background: rgb(169, 243, 212); */

    margin-top: 1%;
}

/* div.play-info {
    width: 60%;
    height: 80vh;

    margin-top: 50px;
    background: rgb(255, 255, 255, 0.5);

} */
</style>