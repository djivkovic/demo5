import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import KorisnikView from '../views/KorisnikView.vue'
import AddKorisnikView from '../views/AddKorisnikView.vue'
import LoginView from '../views/LoginView.vue'
import PorudzbineView from '../views/PorudzbineView.vue'
import LogoutView from '../views/LogoutView.vue'
import Restoran1IspisView from '../views/SingleRestoranView'
import SingleKorisnikView from '../views/SingleKorisnikView'
import AboutUsComp from '../components/AboutUsComp.vue'
import RestoranNazivPretragaComp from '../components/RestoranNazivPretragaComp.vue'
import RestoranTipPretragaComp from '../components/RestoranTipPretragaComp.vue'
import KorpaView from '../views/KorpaView.vue'
import ArtikliView from '../views/ArtikliView.vue'
import RestoraniIspisView from '../views/RestoraniIspisView.vue'
import CreateRestoranView from '../views/CreateRestoranView.vue'
import CreateArtikalView from '../views/CreateArtikalView.vue'
import IspisDostavljacaView from '../views/IspisDostavljacaView.vue'
import CreateDostavljacView from '../views/CreateDostavljacView.vue'
import IspisiMenadzereView from '../views/IspisiMenadzereView.vue'
import CreateMenadzerView from '../views/CreateMenadzerView.vue'
import DeleteRestoranView from '../views/DeleteRestoranView.vue'
import AdminIspisImeView from '../views/AdminIspisImeView.vue'
import AdminIspisPrezimeView from '../views/AdminIspisPrezimeView.vue'






const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: AboutUsComp
  },
  {
    path: '/korisnik',
    name: 'korisnik',
    component: KorisnikView

  },
  {
    path: '/add-korisnik',
    name: 'add-korisnik',
    component: AddKorisnikView
  },
  {
    path: '/login-korisnik',
    name: 'login-korisnik',
    component: LoginView
  },
  {
    path: '/logout-korisnik',
    name: 'logout-korisnik',
    component: LogoutView
  },
  {
    path: '/restoran-ispis/:id',
    name: 'restoran-ispis',
    component: Restoran1IspisView
  },
  {
    path: '/single-korisnik/:id',
    name: 'single-korisnik',
    component: SingleKorisnikView
  },
  {
    path: '/restoran-naziv/:naziv',
    name: 'restoran-naziv',
    component: RestoranNazivPretragaComp
  },
  {
    path: '/restoran-tip/:tip_restorana',
    name: 'restoran-tip',
    component: RestoranTipPretragaComp
  },
  {
    path: '/korpa',
    name: 'korpa',
    component: KorpaView
  },
  {
    path: '/artikli-ispis',
    name: 'artikli-ispis',
    component: ArtikliView
  },
  {
    path: '/restorani-ispis',
    name: 'restorani-ispis',
    component: RestoraniIspisView
  },
  {
    path: '/create-restoran',
    name: 'create-restoran',
    component: CreateRestoranView
  },
  {
    path: '/list-poruzbine',
    name: 'list-poruzbine',
    component: PorudzbineView
  },
  {
    path: '/create-artikal',
    name: 'create-artikal',
    component: CreateArtikalView
  },
  {
    path: '/ispis-dostavljaca',
    name: 'ispis-dostavljaca',
    component: IspisDostavljacaView
  },
  {
    path: '/create-dostavljaca',
    name: 'create-dostavljaca',
    component: CreateDostavljacView
  },
  {
    path: '/ispisi-menadzere',
    name: 'ispisi-menadzere',
    component: IspisiMenadzereView
  },
  {
    path: '/create-menadzer',
    name: 'create-menadzer',
    component: CreateMenadzerView
  },

  {
    path: '/delete-restoran/:naziv',
    name: 'delete-restoran',
    component: DeleteRestoranView
  },
  {
    path: '/admin-ispis-ime/:name',
    name: 'admin-ispis-ime',
    component: AdminIspisImeView
  },
  {
    path: '/admin-ispis-prezime/:surname',
    name: 'admin-ispis-prezime',
    component: AdminIspisPrezimeView
  },



]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
