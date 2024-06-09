import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/HomeView.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import(/* webpackChunkName: "about" */ '../views/Login.vue')
  },
  {
    path: '/pocetna',
    name: 'PocetnaStrana',
    meta: {
      authRequired: true,
    },
    component: () => import(/* webpackChunkName: "about" */ '../views/PocetnaStrana.vue')
  },
  {
    path: '/najcitaniji',
    name: 'NajCitanije',
    meta: {
      authRequired: true,
    },
    component: () => import(/* webpackChunkName: "about" */ '../views/NajCitanije.vue')
  },
  {
    path: '/destinacije',
    name: 'AllDestinacije',
    meta: {
      authRequired: true,
    },
    component: () => import(/* webpackChunkName: "about" */ '../views/AllDestinacije.vue')
  },
  {
    path: '/clanci',
    name: 'AllClanak',
    meta: {
      authRequired: true,
    },
    component: () => import(/* webpackChunkName: "about" */ '../views/AllClanci.vue')
  }
  ,
  {
    path: '/korisnici',
    name: 'AllKorisnici',
    meta: {
      authRequired: true,
    },
    component: () => import(/* webpackChunkName: "about" */ '../views/AllKorisnici.vue')
  },
  {
    path: '/novadestinacija',
    name: 'NovaDestinacija',
    meta: {
      authRequired: true,
    },
    component: () => import(/* webpackChunkName: "about" */ '../views/NovaDestinacija.vue')
  },
  {
    path: '/novikorisnik',
    name: 'NoviKorisnik',
    meta: {
      authRequired: true,
    },
    component: () => import(/* webpackChunkName: "about" */ '../views/NoviKorisnik.vue')
  },
  {
    path: '/clancipodestinaciji',
    name: 'ClanciByDestinacija',
    meta: {
      authRequired: true,
    },
    props: true,
    component: ()=>import('../views/AllClanciByDestinacija.vue')
  },
  {
    path: '/izmenadestinacije',
    name: 'IzmenaDestinacije',
    meta: {
      authRequired: true,
    },
    props: true,
    component: ()=>import('../views/IzmenaDestinacije.vue')
  },
  {
    path: '/izmenakorisnika',
    name: 'IzmenaKorisnika',
    meta: {
      authRequired: true,
    },
    props: true,
    component: ()=>import('../views/IzmenaKorisnika.vue')
  },
  {
    path: '/clanak',
    name: 'AClanak',
    meta: {
      authRequired: true,
    },
    props: true,
    component: ()=>import('../views/AClanak.vue')
  }
]

const router = new VueRouter({
  routes
})


router.beforeEach((to, from, next) => {
  if (to.meta.authRequired) {
    const jwt = localStorage.getItem('jwt');

    if (!jwt) {
      next({name: 'Login'});
      return;
    }

    const payload = JSON.parse(atob(jwt.split('.')[1]));
    
    localStorage.setItem("ime", payload.ime);
    localStorage.setItem("uloga", payload.uloga);

    const expDate = new Date(payload.exp * 1000);
    if (expDate < new Date()) {
      next({name: 'Login'});
      return;
    }
  }

  next();
});


export default router
