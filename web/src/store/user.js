import $ from 'jquery'
import store from '.';

export default {
    state: {
        id: 0,
        username: "",
        photo: "",
        rating: "",
        token: "",
        is_login: false,
        is_logining: true,
        win: 0,
        lose: 0,
        // BaseHttpUrl: "http://localhost:5000/",
        BaseHttpUrl: "https://fir.alowlife.com/",

        // BaseWsUrl: "ws://localhost:5000/",
        BaseWsUrl: "wss://fir.alowlife.com/",



    },
    getters: {

    },
    mutations: {
        updateUser(state, user) {
            state.id = user.id;
            state.username = user.username;
            state.photo = user.photo;
            state.is_login = user.is_login;
            state.rating = user.rating;
            state.win = user.win;
            state.lose = user.lose;

        },

        updateToken(state, token) {
            state.token = token;
        },
        updateRating(state, rating) {
            state.rating = rating
        },

        logout(state) {
            state.id = "";
            state.username = "";
            state.photo = "";
            state.token = "";
            state.is_login = false
            state.rating = ""
        },
        updateLogining(state, is_logining) {
            state.is_logining = is_logining
        }


    },
    actions: {
        login(context, data) {
            $.ajax({
                url: store.state.user.BaseHttpUrl + "api/user/account/login/",
                type: "post",
                data: {
                    username: data.username,
                    password: data.password,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        localStorage.setItem("jwt_token", resp.token);
                        context.commit("updateToken", resp.token);
                        data.success(resp);
                    } else {
                        data.error(resp);
                    }

                },
                error(resp) {
                    data.error(resp);
                },
            })
        },
        getinfo(context, data) {
            $.ajax({
                url: store.state.user.BaseHttpUrl + "api/user/account/info/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + context.state.token,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        context.commit("updateUser", {
                            ...resp,
                            is_login: true,
                        }),
                            data.success(resp);

                    } else {
                        data.error(resp);
                    }
                },
                error(resp) {
                    data.error(resp);
                }
            })
        },
        logout(context) {
            $.ajax(
                localStorage.removeItem("jwt_token"),
                context.commit("logout"),

            )
        }

    },
    modules: {
    }
}
