<template>
    <div v-if="!$store.state.user.pulling_info" style="padding: 10px;">

        <div class="row justify-content-md-center">
            <div class="col-3 card">
                <form @submit.prevent="register">
                    <div class="mb-3">
                        <label for="username" class="form-label">用户名</label>
                        <input v-model="username" type="text" class="form-control" id="username" placeholder="请输入用户名">
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label">密码</label>
                        <input v-model="password" type="password" class="form-control" id="password"
                            placeholder="请输入密码">
                    </div>
                    <div class="mb-3">
                        <label for="confirmedPassword" class="form-label">确认密码</label>
                        <input v-model="confirmedPassword" type="password" class="form-control" id="confirmedPassword"
                            placeholder="请输入密码">
                    </div>
                    <div class="error-message">{{ error_message }}</div>
                    <button type="submit" class="btn btn-primary">注册</button>
                </form>
            </div>
        </div>

    </div>
</template>


<script>
// import ContentField from '@/components/ContentField.vue'
import { useStore } from 'vuex';
import { ref } from 'vue';
import router from '@/router/index';
import $ from 'jquery'

export default {
    components: {
        // ContentField
    },
    setup() {
        const store = useStore();
        let username = ref('');
        let password = ref('');
        let confirmedPassword = ref('');
        let error_message = ref('');


        const register = () => {
            error_message.value = '',
                $.ajax({

                    url: store.state.user.BaseHttpUrl + "api/user/account/register/",
                    type: "post",
                    data: {
                        username: username.value,
                        password: password.value,
                        confirmedPassword: confirmedPassword.value,
                    },
                    success(resp) {
                        if (resp.error_message === "success") {
                            store.dispatch("login", {
                                username: username.value,
                                password: password.value,
                                success() {
                                    store.dispatch("getinfo", {
                                        success() {
                                            router.push({ name: "home" })
                                        }
                                    })
                                },
                                error() {

                                }
                            })
                            error_message.value = "注册成功";
                        } else {
                            error_message.value = resp.error_message
                        }
                    },
                    error() {
                        error_message.value = "无法连接服务器";
                    }

                })


        }
        return {
            username,
            password,
            confirmedPassword,
            error_message,
            register,

        }




    }
}
</script>


<style scoped>
button {
    width: 100%;
}

div.error-message {
    color: red;
}

div.card {
    padding: 20px;
    background: rgb(255, 255, 255, 0.6);
}
</style>

<!-- 1.28分 -->