<template>
    <h2>Kreiraj Restoran</h2>
    <label for="imeRestorana">Ime Restorana:</label>
    <input v-model="restoran.naziv" /><br />
    <label for="tipRestorana">Tip Restorana:</label>
    <input v-model="restoran.tip_restorana" /><br />
    <label for="Lokacija">Geografska Duzina:</label>
    <input v-model.number="restoran.lokacija.geografska_duzina" /><br />
    <label for="Lokacija">Geografska Sirina:</label>
    <input v-model.number="restoran.lokacija.geografska_sirina" /><br />
    <label for="Lokacija">Adresa:</label>
    <input v-model="restoran.lokacija.adresa" /><br />
    <button class="button" v-on:click="redirectToManager()">Postavi!</button>
</template>

<script>
import axios from "axios"
export default {
    name: "CreateRestoranComp",
    data: function () {
        return {
            restoran: {
                naziv: "",
                tip_restorana: "",
                lokacija: {
                    geografska_duzina: "",
                    geografska_sirina: "",
                    adresa: ""
                }
            }
        };
    },
    methods: {
        redirectToManager: function () {



            //console.log(this.$store.getters.korisnik)
            console.log(this.tip_restorana);
            axios
                .post(`http://localhost:8083/api/restorani/kreiraj-restoran?username=${this.$store.getters.korisnik.username}`, this.restoran)
                .then((res) => {
                    console.log(res);
                    this.$store.commit('addRestoran', this.restoran)
                    this.$router.push("/restorani-ispis");
                })
                .catch((err) => {
                    console.log(err);
                    alert("Something went wrong!");
                    
                });

        },
    },
    mounted() {

    }
};
</script>


<style>
</style>