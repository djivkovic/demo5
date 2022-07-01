<template>
    <h2>Kreiraj Menadzera</h2>
    <label for="name">Ime:</label>
    <input v-model="menadzer.name" /><br />
    <label for="surname">Prezime:</label>
    <input v-model="menadzer.surname" /><br />
    <label for="username">Username:</label>
    <input v-model="menadzer.username" /><br />
    <label for="password">Sifra:</label>
    <input v-model="menadzer.password" /><br />
    <button class="button" v-on:click="createMenadzer()">Kreiraj menadzera!</button>
</template>

<script>
import axios from "axios"
export default {
    name: "CreateMenadzerComp",
    data: function () {
        return {
            menadzer: {
                name: "",
                surname: "",
                username: "",
                password: "",
            }
        };
    },
    methods: {
        createMenadzer: function () {



            //console.log(this.$store.getters.korisnik)
            axios
                .post(`http://localhost:8083/api/kreiraj-menadzera?username=${this.$store.getters.korisnik.username}`, this.menadzer)
                .then((res) => {
                    console.log(res);
                    this.$store.commit('addMenadzer', this.menadzer)
                    this.$router.push("/ispisi-menadzere");
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