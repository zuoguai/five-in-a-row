<template>
    <div class="map" ref="parent">
        <canvas class="gamemap" ref="canvas"> </canvas>
    </div>

</template>

<script>
import { GameMap } from '@/assets/scripts/GameMap';
import { ref, onMounted } from 'vue';
import { useStore } from 'vuex';
export default {
    setup() {
        const store = useStore();
        let parent = ref(null);
        let canvas = ref(null);
        onMounted(() => {
            store.commit("updateGameObject", new GameMap(canvas.value.getContext('2d'), parent.value, store));

        });

        return {
            parent,
            canvas
        }
    }
}


</script>

<style scoped>
div.map {
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    /* background: white; */

}

canvas.gamemap {
    /* cursor: url('@/assets/images/纸飞机.png'), pointer */
    cursor: crosshair;


}
</style>