<template>
    <!-- <CotentField> -->
    <div style="padding: 10px;"></div>

    <div class="row justify-content-md-center">
        <div class="col-3 card">
            <form @submit.prevent="login">
                <div class="mb-3">
                    <label for="username" class="form-label">用户名：</label>
                    <input v-model="username" type="text" class="form-control" id="username" placeholder="请输入用户名">
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label">密码</label>
                    <input v-model="password" type="password" class="form-control" id="password" placeholder="请输入密码">
                </div>
                <div class="error-message">{{ error_message }}</div>
                <button type="submit" class="btn btn-primary">登录</button>
            </form>

        </div>
    </div>
    <!-- </CotentField> -->


</template>

<script>
// import CotentField from '@/components/ContentField.vue'
import { useStore } from 'vuex';
import { ref } from 'vue';
import router from '@/router/index';



export default {
    components: {
        // CotentField
    },
    setup() {
        const store = useStore();
        let username = ref("");
        let password = ref("");
        let error_message = ref("");


        const jwt_token = localStorage.getItem("jwt_token");
        if (jwt_token) {
            store.commit("updateToken", jwt_token);
            store.dispatch("getinfo", {
                success() {
                    router.push({ name: "home" });
                    store.commit("updateLogining", false);
                },
                error() {
                    store.commit("updateLogining", false)
                }
            })

        } else {
            store.commit("updateLogining", false)
        }

        const login = () => {

            error_message.value = '';
            store.dispatch("login", {
                username: username.value,
                password: password.value,
                success() {
                    store.dispatch("getinfo", {
                        success() {
                            router.push({ name: "home" });
                            // console.log(store.state.user);
                        }
                    })
                },
                error() {
                    error_message.value = "用户名或密码错误";
                }
            })
        }
        return {
            username,
            password,
            error_message,
            login,
        }
    }
}
</script>


<style scoped>
button {
    width: 100%;
}


div.error-message {
    color: blue;
}


div.card {
    padding: 20px;
    background: rgb(255, 255, 255, 0.6);
}
</style>