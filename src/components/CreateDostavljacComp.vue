<template>
    <h2>Kreiraj Dostavljaca</h2>
    <label for="imeRestorana">Ime:</label>
    <input v-model="dostavljac.name" /><br />
    <label for="tipRestorana">Prezime:</label>
    <input v-model="dostavljac.surname" /><br />
    <label for="Lokacija">Username:</label>
    <input v-model="dostavljac.username" /><br />
    <label for="Lokacija">Sifra:</label>
    <input v-model="dostavljac.password" /><br />
    <button class="button" v-on:click="createDostavljac()">Kreiraj dostavljaca!</button>
</template>

<script>
import axios from "axios"
export default {
    name: "CreateDostavljacComp",
    data: function () {
        return {
            dostavljac: {
                name: "",
                surname: "",
                username: "",
                password: "",
            }
        };
    },
    methods: {
        createDostavljac: function () {



            //console.log(this.$store.getters.korisnik)
            axios
                .post(`http://localhost:8083/api/kreiraj-dostavljaca?username=${this.$store.getters.korisnik.username}`, this.dostavljac)
                .then((res) => {
                    console.log(res);
                    this.$store.commit('addDostavljac', this.dostavljac)
                    this.$router.push("/ispis-dostavljaca");
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