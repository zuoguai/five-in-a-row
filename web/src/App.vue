<template>
  <NavBar />

  <router-view></router-view>
</template>

<script>
import NavBar from '@/components/NavBar.vue'
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap/dist/js/bootstrap"
// import $ from 'jquery'


export default {
  components: {
    NavBar
  },
  setup() {

    // $.ajax({
    //   url: "http://127.0.0.1/user/account/login/",
    //   type: "post",
    //   data: {
    //     username: "zuoguai",
    //     password: "pa",
    //   },
    //   success(resp) {
    //     console.log(resp);
    //   },
    //   error(resp) {
    //     console.log(resp);
    //   }

    // })
    //   ,
    // $.ajax({
    //   url: "http://127.0.0.1/user/account/info/",
    //   type: "get",
    //   headers: {
    //     Authorization: "Bearer eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIwMTIyNjk4YWY0OWM0NmQzOGRlYTY3NzUzZDI1ZjUzZCIsInN1YiI6IjEiLCJpc3MiOiJzZyIsImlhdCI6MTY3MjczNDg5NiwiZXhwIjoxNjczOTQ0NDk2fQ.4YLA2EHIl6VuYx3JjMr6d2Dhk5iuWwByZGjGttD0vwc",
    //   },
    //   success(resp) {
    //     console.log(resp);
    //   },
    //   error(resp) {
    //     console.log(resp);
    //   },
    // })
    // ,
    // $.ajax({
    //   url: "http://127.0.0.1:8010/user/account/register/",
    //   type: "post",
    //   data: {
    //     username: "e",
    //     password: "peeeee",
    //     confirmedPassword: "peeeee",
    //   },
    //   success(resp) {
    //     console.log(resp);
    //   },
    //   error(resp) {
    //     console.log(resp);
    //   },
    // })


  }

}

</script>

<style>
body {
  background-image: url(@/assets/images/background3.jpg);
  background-size: cover;
}
</style>
