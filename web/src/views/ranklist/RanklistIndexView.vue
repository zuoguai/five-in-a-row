<template>
    <CotentField>
        <table class="table table-striped table-hover">
            <thead>
                <tr>
                    <th>玩家：</th>
                    <th>胜场：</th>
                    <th>胜率：</th>
                    <th>胜点：</th>


                </tr>
            </thead>
            <tbody>
                <tr v-for="item in ranklists" :key="item.id">
                    <td>
                        <img :src="item.photo" alt="" class="ranklist-user-photo" />
                        &nbsp;
                        <span class="ranklist-user-username">{{ item.username }}</span>
                    </td>
                    <td>
                        {{ item.win }}
                    </td>
                    <td>
                        {{ Math.trunc((item.win / (item.win + item.lose)) * 100) }}%
                    </td>
                    <td>
                        {{ item.rating }}
                    </td>

                </tr>
            </tbody>
        </table>
        <nav aria-label="Page navigation example">
            <ul class="pagination" style="float:right">
                <li class="page-item" @click="click_page(-2)"><a class="page-link" href="#">上一页</a></li>
                <li @click="click_page(page.number)" :class="'page-item ' + page.is_active" v-for=" page in showPage"
                    :key="page.number">
                    <a class="page-link" href="#">{{ page.number }}</a>
                </li>

                <li class="page-item" @click="click_page(-1)"><a class="page-link" href="#">下一页</a></li>
            </ul>
        </nav>
    </CotentField>
</template>

<script>
import CotentField from '@/components/ContentField.vue'
import { useStore } from 'vuex';
import $ from 'jquery'
import { ref } from 'vue';

export default {
    components: {
        CotentField
    },
    setup() {
        const store = useStore();
        const ranklists = ref([]);
        const PAGE = 6;
        let currentPage = 1;
        let totalPage = 1;
        let showPage = ref([]);

        const update_pages = () => {
            let max_pages = parseInt(Math.ceil(totalPage / PAGE));
            // console.log(max_pages)

            let new_page = [];
            for (let i = currentPage - 2; i < currentPage + 2; i++) {
                if (i >= 1 && i <= max_pages) {
                    new_page.push({
                        number: i,
                        is_active: i === currentPage ? "active" : "",
                    })
                }
            }

            showPage.value = new_page;

        }

        const click_page = page => {
            if (page === -1) {
                page = currentPage + 1;

            } else if (page === -2) {
                page = currentPage - 1;
            }
            let max_pages = parseInt(Math.ceil(totalPage / PAGE))
            if (page >= 1 && page <= max_pages) {
                pull_page(page);
            }
        }

        const pull_page = page => {
            currentPage = page;
            $.ajax({
                url: store.state.user.BaseHttpUrl + "api/ranklist/getlist/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                data: {
                    page,
                },
                success(resp) {

                    ranklists.value = resp.users;
                    // console.log(ranklists);
                    totalPage = resp.users_count;
                    update_pages();


                },

            })

        }

        pull_page(currentPage)
        return {
            click_page,
            ranklists,
            showPage
        }
    }
}
</script>


<style scoped>
img.ranklist-user-photo {
    width: 5vh;
    border-radius: 15%;
}

td {
    font-size: x-large;
    font-weight: 600;
    font-family: 黑体;
}

span {
    font-size: x-large;
    font-weight: 600;

    font-family: 楷体;


}

th {
    font-size: x-large;
    font-weight: 1000;

    font-family: 楷体;
}
</style>