<template>
    <CotentField>
        <table class="table table-striped table-hover">
            <thead>
                <tr>
                    <th>玩家一：</th>
                    <th>玩家二：</th>
                    <th>对战结果：</th>
                    <th>对战时间：</th>

                </tr>
            </thead>
            <tbody>
                <tr v-for="record in records" :key="record.id">
                    <td>
                        <img :src="record.a_photo" alt="" class="record-user-photo" />
                        &nbsp;
                        <span class="record-user-username">{{
                            record.a_username
                        }}</span>
                    </td>
                    <td>
                        <img :src="record.b_photo" alt="" class="record-user-photo" />
                        &nbsp;
                        <span class="record-user-username">{{ record.b_username }}</span>
                    </td>

                    <td class="record-user-username">
                        {{ record.result }}
                    </td>
                    <td class="record-user-username">
                        {{ record.createtime }}
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
        const records = ref([]);
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
                url: store.state.user.BaseHttpUrl + "api/record/getlist/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                data: {
                    page,
                },
                success(resp) {
                    let copy = [];

                    copy = resp.records;
                    for (let i in copy) {

                        copy[i].createtime = new Date(copy[i].createtime).toLocaleString();
                    }
                    records.value = copy;
                    // console.log(records);
                    totalPage = resp.records_count;
                    update_pages();


                },

            })

        }

        pull_page(currentPage)
        return {
            click_page,
            records,

            showPage
        }
    }
}
</script>


<style scoped>
img.record-user-photo {
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