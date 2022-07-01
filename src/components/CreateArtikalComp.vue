<template>
    <h2>Kreiraj Artikal</h2>
    <label for="naziv">Naziv:</label>
    <input v-model="artikal.naziv" /><br />
    <label for="cena">Tip:</label>
    <input v-model="artikal.tip" /><br />
    <label for="tip">Cena:</label>
    <input v-model.number="artikal.cena" /><br />

    <button class="button" v-on:click="redirectToArtikli()">Kreiraj!</button>
</template>

<script>
import axios from "axios"
export default {
    name: "CreateArtikalComp",
    data: function () {
        return {
            artikal: {
                naziv: "",
                cena: "",
                tip: ""
            }
        };
    },
    methods: {
        redirectToArtikli: function () {
            console.log(this.$store.getters.korisnik.username)

            axios
            
                .post(`http://localhost:8083/api/artikli/kreiraj-artikal?username=${this.$store.getters.korisnik.username}`, this.artikal)
                .then((res) => {
                    console.log(res);
                    this.$router.push("/artikli-ispis");
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