import { createApp } from 'vue'
import { createStore } from 'vuex'
import App from './App.vue'
// I'm also using a router
import router from './router'


const app = createApp(App);

// Create a store for our to do list items
const store = createStore({
    state() {
        return {
            korisnik:
                { username: "", password: "" },
            restoran: {
                naziv: "",
                tip_restorana: "",
                lokacija: {
                    geografska_duzina: "",
                    geografska_sirina: "",
                    adresa: ""
                }
            },
            dostavljac:
            {
                username: "",
                password: "",
                surname: "",
                name: ""
            },
            menadzer:
            {
                username: "",
                password: "",
                surname: "",
                name: ""
            }
        }
    },
    getters: {
        korisnik(state) {
            // state variable contains our state data
            return state.korisnik;
        },

        restoran(state) {
            return state.restoran;
        },
        dostavljac(state) {
            return state.dostavljac;
        },
        menadzer(state) {
            return state.menadzer;
        }
    },
    mutations: {
        addKorisnik(state, dodajKorisnika) {
            if (typeof dodajKorisnika.username == 'string' && typeof dodajKorisnika.password == 'string') {
                state.korisnik = {
                    username: dodajKorisnika.username,
                    lozinka: dodajKorisnika.password
                }
            }
        },
        addRestoran(state, dodajRestoran) {
            if (typeof dodajRestoran.naziv == 'string' && typeof dodajRestoran.tip_restorana == 'string') {
                state.restoran = {
                    naziv: dodajRestoran.naziv,
                    tip_restorana: dodajRestoran.tip_restorana,
                }
            }
        },
        addDostavljac(state, dodajDostavljaca) {
            if (typeof dodajDostavljaca.name == 'string' && typeof dodajDostavljaca.surname == 'string' && typeof dodajDostavljaca.username == 'string' && dodajDostavljaca.password == 'string') {
                state.dostavljac =
                {
                    name: dodajDostavljaca.name,
                    surname: dodajDostavljaca.surname,
                    username: dodajDostavljaca.username,
                    password: dodajDostavljaca.password
                }
            }
        },
        addMenadzer(state, dodajMenadzera) {
            if (typeof dodajMenadzera.name == 'string' && typeof dodajMenadzera.surname == 'string' && typeof dodajMenadzera.username == 'string' && dodajMenadzera.password == 'string') {
                state.dostavljac =
                {
                    name: dodajMenadzera.name,
                    surname: dodajMenadzera.surname,
                    username: dodajMenadzera.username,
                    password: dodajMenadzera.password
                }
            }
        }
    }
});

// We can chain use() functions, so our app is now using a router and our Vuex store
app.use(router).use(store).mount('#app')


