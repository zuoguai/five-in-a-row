import { createRouter, createWebHistory } from 'vue-router'
import PkIndexView from '@/views/pk/PkIndexView'
import NotFound from '@/views/error/NotFound'
import RanklistIndexView from '@/views/ranklist/RanklistIndexView'
import RecordIndexView from '@/views/record/RecordIndexView'
import UserIndexView from '@/views/user/UserIndexView'
import UserAccountLogin from '@/views/user/account/UserAccountLoginView'
import UserAccountRegister from '@/views/user/account/UserAccountRegisterView'
import store from '@/store/index/'


const routes = [
  {
    path: "/",
    name: "home",
    // redirect: "/user/account/login/",
    redirect: "/pk/",
    meta: {
      requestAuth: true,
    }
  },
  {
    path: "/pk/",
    name: "pk_index",
    component: PkIndexView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: "/ranklist/",
    name: "ranklist_index",
    component: RanklistIndexView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: "/record/",
    name: "record_index",
    component: RecordIndexView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: "/user/",
    name: "user_index",
    component: UserIndexView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: "/user/account/login/",
    name: "login",
    component: UserAccountLogin,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: "/user/account/register/",
    name: "register",
    component: UserAccountRegister,
    meta: {
      requestAuth: false,
    }
  },






  {
    path: "/404/",
    name: "not_found",
    component: NotFound,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: "/:catchAll(.*)",
    redirect: "/404/"

  }



]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router

router.beforeEach((to, from, next) => {
  if (to.meta.requestAuth && !store.state.user.is_login) {
    next({ name: "login" })
  } else {
    next();
  }
})