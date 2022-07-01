<template>
    <section>
        <br>
        <h2 style="text-align:center">Lista Menadzera</h2>
        <a v-for="menadzer in menadzeri" :key="menadzer.name" class="card">

            <div class="container">
                <h1> Ime: {{ menadzer.name }} </h1>
                <h1> Prezime: {{ menadzer.surname }} </h1>
                <h1>Username: {{ menadzer.username }} </h1>
                <br>
            </div>

        </a>
        <br>
        <br>
    </section>

</template>

<script>
import axios from 'axios';
export default {
    name: "IspisiMenadzeraComp",
    props: ["menadzer"],
    methods: {
        seeMore: function () {
            this.$router.push("/menadzer?id=" + this.menadzer.id);
        },
        deleteKorisnik: function () {
            fetch("http://localhost:8083/api/korisnici/" + this.menadzer.id, {
                method: "DELETE",
            }).then((res) => {
                if (res.ok) {
                    window.location.reload();
                }
            });
        },
    },
    data: function () {
        return {
            menadzeri: {},
        }
    },
    mounted: function () {
        axios
            .get("http://localhost:8083/api/menadzeri/ispis")
            .then((res) => {
                this.menadzeri = res.data
            })
            .catch((err) => {
                console.log(err)
            })
        /*
                fetch('http://localhost:8083/api/korisnici/ispis',
                    {
                        credentials: 'include'
                    })
                    .then(response => response.json())
                    .then(data => {
                        console.log("Success:", data);
                        this.korisnik = data
                    })
                    .catch((error) => {
                        console.error("Error:", error)
                    });
            }
        */

    }
};
</script>


<style scoped>
section {
    background-color: antiquewhite;
}

a {
    text-decoration: none;
    text-align: center;
}

.container {
    border: 1px solid grey;
}
</style>
